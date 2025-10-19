package com.example.studyplanner.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.studyplanner.data.model.Subject;
import com.example.studyplanner.data.model.SubjectHours;
import com.example.studyplanner.data.model.SubjectPerformance;
import com.example.studyplanner.data.model.Task;
import com.example.studyplanner.data.model.User;
import com.example.studyplanner.data.model.TaskWithSubject;
import com.example.studyplanner.data.relation.SubjectWithTasks;
import com.example.studyplanner.repository.StudyPlannerRepository;
import com.example.studyplanner.util.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainViewModel extends AndroidViewModel {

    private final StudyPlannerRepository repository;
    private final SessionManager sessionManager;

    // --- Private LiveData Triggers ---
    private final MutableLiveData<Integer> userIdLiveData = new MutableLiveData<>();
    private final MutableLiveData<Date> selectedDate = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isOverallStats = new MutableLiveData<>(true);
    private final MutableLiveData<Pair<Integer, Integer>> reportFilters = new MutableLiveData<>();


    // --- Public LiveData for UI Observation ---
    public final LiveData<List<Subject>> userSubjects;
    public final LiveData<List<SubjectWithTasks>> subjectsWithTasks;
    public final LiveData<User> currentUser;
    public final MediatorLiveData<List<TaskWithSubject>> calendarTasks = new MediatorLiveData<>();
    public final LiveData<List<TaskWithSubject>> pendingTasks;
    public final LiveData<List<TaskWithSubject>> completedTasks;
    public final LiveData<String> totalStudyHours;
    public final LiveData<List<SubjectPerformance>> subjectPerformances;

    // --- Final Display LiveData for Stats Screen ---
    public final LiveData<Integer> displayTotalTaskCount;
    public final LiveData<Integer> displayCompletedTaskCount;
    public final LiveData<Integer> displayOverdueTaskCount;
    public final LiveData<List<SubjectHours>> displayStudyHoursBySubject;
    private LiveData<List<TaskWithSubject>> currentCalendarSource = null;

    // --- Final Display LiveData for Custom Report Screen ---
    public final LiveData<Integer> reportCompletedCount;
    public final LiveData<Integer> reportOverdueCount;
    public final LiveData<String> reportStudyHours;


    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new StudyPlannerRepository(application);
        sessionManager = SessionManager.getInstance(application);

        // --- Reactive Data Streams for Main App Features ---
        userSubjects = Transformations.switchMap(userIdLiveData, repository::getSubjectsForUser);
        subjectsWithTasks = Transformations.switchMap(userIdLiveData, repository::getSubjectsWithTasks);
        currentUser = Transformations.switchMap(userIdLiveData, repository::getUserById);
        pendingTasks = Transformations.switchMap(userIdLiveData, repository::getPendingTasks);
        completedTasks = Transformations.switchMap(userIdLiveData, repository::getCompletedTasksWithSubject);

        calendarTasks.addSource(userIdLiveData, userId -> updateCalendarSource());
        calendarTasks.addSource(selectedDate, date -> updateCalendarSource());

        // --- "Overall" Stats Data Streams ---
        LiveData<Integer> totalTaskCountOverall = Transformations.switchMap(userIdLiveData, repository::getTotalTaskCount);
        LiveData<Integer> completedTaskCountOverall = Transformations.switchMap(userIdLiveData, repository::getCompletedTaskCount);
        LiveData<Integer> overdueTaskCountOverall = Transformations.switchMap(userIdLiveData, repository::getOverdueTaskCount);
        LiveData<List<Task>> completedTasksOverall = Transformations.switchMap(userIdLiveData, repository::getCompletedTasksForUser);
        LiveData<List<SubjectHours>> studyHoursOverall = calculateStudyHoursBySubject(userSubjects, completedTasksOverall);
        totalStudyHours = calculateTotalStudyHours(completedTasksOverall);
        subjectPerformances = calculateSubjectPerformance(subjectsWithTasks);

        // --- "This Week" Stats Data Streams ---
        LiveData<Integer> totalTaskCountWeek = Transformations.switchMap(userIdLiveData, repository::getTotalTaskCountForWeek);
        LiveData<Integer> completedTaskCountWeek = Transformations.switchMap(userIdLiveData, repository::getCompletedTaskCountForWeek);
        LiveData<Integer> overdueTaskCountWeek = Transformations.switchMap(userIdLiveData, repository::getOverdueTaskCountForWeek);
        LiveData<List<Task>> completedTasksWeek = Transformations.switchMap(userIdLiveData, repository::getCompletedTasksForWeek);
        LiveData<List<SubjectHours>> studyHoursWeek = calculateStudyHoursBySubject(userSubjects, completedTasksWeek);

        // --- Mediator LiveData that switches stats based on the filter state ---
        displayTotalTaskCount = Transformations.switchMap(isOverallStats, isOverall -> isOverall ? totalTaskCountOverall : totalTaskCountWeek);
        displayCompletedTaskCount = Transformations.switchMap(isOverallStats, isOverall -> isOverall ? completedTaskCountOverall : completedTaskCountWeek);
        displayOverdueTaskCount = Transformations.switchMap(isOverallStats, isOverall -> isOverall ? overdueTaskCountOverall : overdueTaskCountWeek);
        displayStudyHoursBySubject = Transformations.switchMap(isOverallStats, isOverall -> isOverall ? studyHoursOverall : studyHoursWeek);

        // --- Reactive Data Streams for Custom Report ---
        reportCompletedCount = Transformations.switchMap(reportFilters, filters ->
                repository.getCompletedTaskCountForReport(userIdLiveData.getValue(), filters.first, filters.second));

        reportOverdueCount = Transformations.switchMap(reportFilters, filters ->
                repository.getOverdueTaskCountForReport(userIdLiveData.getValue(), filters.first, filters.second));

        LiveData<List<Task>> reportCompletedTasks = Transformations.switchMap(reportFilters, filters ->
                repository.getCompletedTasksForReport(userIdLiveData.getValue(), filters.first, filters.second));

        reportStudyHours = calculateTotalStudyHours(reportCompletedTasks);
    }

    private void updateCalendarSource() {
        Integer userId = userIdLiveData.getValue();
        Date date = selectedDate.getValue();

        if (userId == null || date == null) {
            return;
        }

        if (currentCalendarSource != null) {
            calendarTasks.removeSource(currentCalendarSource);
        }

        currentCalendarSource = repository.getTasksForDate(userId, date);
        calendarTasks.addSource(currentCalendarSource, tasks -> calendarTasks.setValue(tasks));
    }

    private LiveData<List<SubjectHours>> calculateStudyHoursBySubject(LiveData<List<Subject>> subjectsSource, LiveData<List<Task>> completedTasksSource) {
        MediatorLiveData<List<SubjectHours>> result = new MediatorLiveData<>();
        Runnable update = () -> {
            List<Subject> subjects = subjectsSource.getValue();
            List<Task> tasks = completedTasksSource.getValue();
            if (subjects == null) {
                result.setValue(new ArrayList<>());
                return;
            }
            result.setValue(processHours(subjects, tasks));
        };
        result.addSource(subjectsSource, subjects -> update.run());
        result.addSource(completedTasksSource, tasks -> update.run());
        return result;
    }

    private LiveData<List<SubjectPerformance>> calculateSubjectPerformance(LiveData<List<SubjectWithTasks>> source) {
        return Transformations.map(source, subjectsWithTasks -> {
            if (subjectsWithTasks == null) return new ArrayList<>();

            return subjectsWithTasks.stream().map(subjectWithTasks -> {
                String subjectName = subjectWithTasks.subject.name;
                List<Task> tasks = subjectWithTasks.tasks;

                // Overall Performance
                int totalTasks = tasks.size();
                long completedTasks = tasks.stream().filter(t -> "Completed".equals(t.status)).count();
                int overallPercentage = (totalTasks == 0) ? 0 : (int) ((completedTasks * 100L) / totalTasks);

                // Weekly Performance for the last 4 weeks
                List<Float> weeklyPercentages = new ArrayList<>();
                for (int w = 3; w >= 0; w--) { // W4, W3, W2, W1 -> final list will be W1, W2, W3, W4
                    Calendar weekStart = Calendar.getInstance();
                    weekStart.add(Calendar.WEEK_OF_YEAR, -(w + 1));
                    long weekStartMillis = weekStart.getTimeInMillis();

                    Calendar weekEnd = Calendar.getInstance();
                    weekEnd.add(Calendar.WEEK_OF_YEAR, -w);
                    long weekEndMillis = weekEnd.getTimeInMillis();

                    List<Task> tasksInWeek = tasks.stream()
                            .filter(t -> t.deadline != null && t.deadline.getTime() > weekStartMillis && t.deadline.getTime() <= weekEndMillis)
                            .collect(Collectors.toList());

                    long weeklyCompleted = tasksInWeek.stream().filter(t -> "Completed".equals(t.status)).count();
                    int weeklyTotal = tasksInWeek.size();

                    float percentage = (weeklyTotal == 0) ? 0f : ((float) weeklyCompleted / weeklyTotal) * 100f;
                    weeklyPercentages.add(percentage);
                }

                return new SubjectPerformance(subjectName, overallPercentage, weeklyPercentages);
            }).collect(Collectors.toList());
        });
    }

    private List<SubjectHours> processHours(List<Subject> subjects, List<Task> tasks) {
        if (subjects == null) return new ArrayList<>();
        Map<Integer, Long> subjectMillisMap = new HashMap<>();
        if (tasks != null) {
            for (Task task : tasks) {
                if (task.startTime != null && task.deadline != null) {
                    long duration = task.deadline.getTime() - task.startTime.getTime();
                    subjectMillisMap.put(task.subjectId, subjectMillisMap.getOrDefault(task.subjectId, 0L) + duration);
                }
            }
        }
        List<SubjectHours> resultList = new ArrayList<>();
        for (Subject subject : subjects) {
            long totalMillis = subjectMillisMap.getOrDefault(subject.subjectId, 0L);
            long hours = TimeUnit.MILLISECONDS.toHours(totalMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(totalMillis) % 60;
            resultList.add(new SubjectHours(subject.name, hours + "h " + minutes + "m", totalMillis));
        }
        return resultList;
    }

    private LiveData<String> calculateTotalStudyHours(LiveData<List<Task>> tasksSource) {
        return Transformations.map(tasksSource, tasks -> {
            if (tasks == null) return "0";
            long totalMillis = 0;
            for (Task task : tasks) {
                if (task.startTime != null && task.deadline != null) {
                    totalMillis += (task.deadline.getTime() - task.startTime.getTime());
                }
            }
            return String.valueOf(TimeUnit.MILLISECONDS.toHours(totalMillis));
        });
    }

    // --- Public Methods for Fragments to Call ---
    public void setStatsFilter(boolean isOverall) { isOverallStats.setValue(isOverall); }
    public void loadUserId() {
        int userId = sessionManager.getUserId();
        if (userId != -1 && (userIdLiveData.getValue() == null || !userIdLiveData.getValue().equals(userId))) {
            userIdLiveData.setValue(userId);
        }
    }
    public void setSelectedDate(Date date) { selectedDate.setValue(date); }
    public void setReportFilters(int subjectId, int dateRange) {
        reportFilters.setValue(new Pair<>(subjectId, dateRange));
    }


    // --- Database Actions ---
    public void insertSubject(String subjectName) {
        Integer userId = userIdLiveData.getValue();
        if (userId != null) {
            Subject newSubject = new Subject();
            newSubject.userId = userId;
            newSubject.name = subjectName;
            repository.insertSubject(newSubject);
        }
    }
    public void insertTask(Task task) { repository.insertTask(task); }
    public void updateTask(Task task) { repository.updateTask(task); }
    public void deleteTask(Task task) { repository.deleteTask(task); }
    public void deleteSubject(Subject subject) { repository.deleteSubject(subject); }
    public void updateUser(User user) { repository.updateUser(user); }
}

// A simple Pair class, as android.util.Pair is sometimes discouraged.
class Pair<F, S> {
    public final F first;
    public final S second;
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
