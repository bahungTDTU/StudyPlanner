package com.example.studyplanner.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

// Corrected import path
import com.example.studyplanner.data.local.AppDatabase;

import com.example.studyplanner.data.local.SubjectDao;
import com.example.studyplanner.data.local.TaskDao;
import com.example.studyplanner.data.local.UserDao;
import com.example.studyplanner.data.model.Subject;
import com.example.studyplanner.data.model.Task;
import com.example.studyplanner.data.model.User;
import com.example.studyplanner.data.model.TaskWithSubject;
import com.example.studyplanner.data.relation.SubjectWithTasks;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StudyPlannerRepository {

    private final UserDao userDao;
    private final SubjectDao subjectDao;
    private final TaskDao taskDao;

    public StudyPlannerRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDao = db.userDao();
        this.subjectDao = db.subjectDao();
        this.taskDao = db.taskDao();
    }

    // --- User Methods ---
    public void registerUser(User user, MutableLiveData<Boolean> registrationSuccess) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (userDao.findByEmail(user.email) == null && userDao.findByUsername(user.username) == null) {
                long newUserId = userDao.insertAndGetId(user);
                // Create a default subject for the new user
                Subject defaultSubject = new Subject();
                defaultSubject.userId = (int) newUserId;
                defaultSubject.name = "General";
                subjectDao.insert(defaultSubject);
                registrationSuccess.postValue(true);
            } else {
                registrationSuccess.postValue(false);
            }
        });
    }

    public LiveData<User> loginUser(String identifier, String password) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = userDao.findByIdentifier(identifier);
            if (user != null && user.password.equals(password)) {
                userLiveData.postValue(user);
            } else {
                userLiveData.postValue(null);
            }
        });
        return userLiveData;
    }

    public LiveData<User> getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    public void updateUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.update(user));
    }


    // --- Subject Methods ---
    public LiveData<List<Subject>> getSubjectsForUser(int userId) {
        return subjectDao.getSubjectsForUser(userId);
    }

    public void insertSubject(Subject subject) {
        AppDatabase.databaseWriteExecutor.execute(() -> subjectDao.insert(subject));
    }

    public void deleteSubject(Subject subject) {
        AppDatabase.databaseWriteExecutor.execute(() -> subjectDao.delete(subject));
    }

    public LiveData<List<TaskWithSubject>> getPendingTasks(int userId) {
        return taskDao.getPendingTasks(userId);
    }

    public LiveData<List<TaskWithSubject>> getCompletedTasksWithSubject(int userId) {
        return taskDao.getCompletedTasksWithSubject(userId);
    }


    // --- Task Methods for Calendar ---
    public LiveData<List<TaskWithSubject>> getTasksForDate(int userId, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalendarToStartOfDay(calendar);
        long startOfDay = calendar.getTimeInMillis();
        setCalendarToEndOfDay(calendar);
        long endOfDay = calendar.getTimeInMillis();
        return taskDao.getTasksForDate(userId, startOfDay, endOfDay);
    }


    // --- Basic Task CRUD ---
    public void insertTask(Task task) { AppDatabase.databaseWriteExecutor.execute(() -> taskDao.insert(task)); }
    public void updateTask(Task task) { AppDatabase.databaseWriteExecutor.execute(() -> taskDao.update(task)); }
    public void deleteTask(Task task) { AppDatabase.databaseWriteExecutor.execute(() -> taskDao.delete(task)); }


    // --- Relational (Grouped Dashboard) Methods ---
    public LiveData<List<SubjectWithTasks>> getSubjectsWithTasks(int userId) {
        return subjectDao.getSubjectsWithTasks(userId);
    }


    // --- "Overall" Analytics Methods ---
    public LiveData<Integer> getTotalTaskCount(int userId) { return taskDao.getTotalTaskCount(userId); }
    public LiveData<Integer> getCompletedTaskCount(int userId) { return taskDao.getTaskCountByStatus(userId, "Completed"); }
    public LiveData<Integer> getOverdueTaskCount(int userId) { return taskDao.getOverdueTaskCount(userId, System.currentTimeMillis()); }
    public LiveData<List<Task>> getCompletedTasksForUser(int userId) { return taskDao.getCompletedTasksForUser(userId); }


    // --- "This Week" Analytics Methods ---
    public LiveData<Integer> getTotalTaskCountForWeek(int userId) {
        long[] weekRange = getWeekDateRange();
        return taskDao.getTotalTaskCountForWeek(userId, weekRange[0], weekRange[1]);
    }
    public LiveData<Integer> getCompletedTaskCountForWeek(int userId) {
        long[] weekRange = getWeekDateRange();
        return taskDao.getCompletedTaskCountForWeek(userId, weekRange[0], weekRange[1]);
    }
    public LiveData<Integer> getOverdueTaskCountForWeek(int userId) {
        long[] weekRange = getWeekDateRange();
        return taskDao.getOverdueTaskCountForWeek(userId, weekRange[0], weekRange[1], System.currentTimeMillis());
    }
    public LiveData<List<Task>> getCompletedTasksForWeek(int userId) {
        long[] weekRange = getWeekDateRange();
        return taskDao.getCompletedTasksForWeek(userId, weekRange[0], weekRange[1]);
    }

    // --- Custom Report Methods (Corrected Logic) ---
    public LiveData<Integer> getCompletedTaskCountForReport(int userId, int subjectId, int dateRange) {
        if (dateRange == 0) { // Overall
            if (subjectId == -1) { // All Subjects
                return taskDao.getTaskCountByStatus(userId, "Completed");
            } else { // Specific Subject
                return taskDao.getCompletedTaskCountForSubject(subjectId);
            }
        } else { // Specific Date Range
            long[] range = getReportDateRange(dateRange);
            if (subjectId == -1) { // All Subjects
                return taskDao.getCompletedTaskCountForUserAndDateRange(userId, range[0], range[1]);
            } else { // Specific Subject
                return taskDao.getCompletedTaskCountForSubjectAndDateRange(subjectId, range[0], range[1]);
            }
        }
    }

    public LiveData<Integer> getOverdueTaskCountForReport(int userId, int subjectId, int dateRange) {
        long now = System.currentTimeMillis();
        if (dateRange == 0) { // Overall
            if (subjectId == -1) { // All Subjects
                return taskDao.getOverdueTaskCount(userId, now);
            } else { // Specific Subject
                return taskDao.getOverdueTaskCountForSubject(subjectId, now);
            }
        } else { // Specific Date Range
            long[] range = getReportDateRange(dateRange);
            if (subjectId == -1) { // All Subjects
                return taskDao.getOverdueTaskCountForUserAndDateRange(userId, range[0], range[1], now);
            } else { // Specific Subject
                return taskDao.getOverdueTaskCountForSubjectAndDateRange(subjectId, range[0], range[1], now);
            }
        }
    }

    public LiveData<List<Task>> getCompletedTasksForReport(int userId, int subjectId, int dateRange) {
        if (dateRange == 0) { // Overall
            if (subjectId == -1) { // All Subjects
                return taskDao.getCompletedTasksForUser(userId);
            } else { // Specific Subject
                return taskDao.getCompletedTasksForSubject(subjectId);
            }
        } else { // Specific Date Range
            long[] range = getReportDateRange(dateRange);
            if (subjectId == -1) { // All Subjects
                return taskDao.getCompletedTasksForUserAndDateRange(userId, range[0], range[1]);
            } else { // Specific Subject
                return taskDao.getCompletedTasksForSubjectAndDateRange(subjectId, range[0], range[1]);
            }
        }
    }


    // --- Helper Methods ---
    private long[] getWeekDateRange() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        setCalendarToStartOfDay(calendar);
        long startOfWeek = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_YEAR, 6);
        setCalendarToEndOfDay(calendar);
        long endOfWeek = calendar.getTimeInMillis();
        return new long[]{startOfWeek, endOfWeek};
    }

    private long[] getReportDateRange(int dateRange) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        switch (dateRange) {
            case 1: // This Semester (last 4 months)
                start.add(Calendar.MONTH, -4);
                break;
            case 2: // Last Month
                start.add(Calendar.MONTH, -1);
                break;
            default: // Overall (Should not be handled here anymore, but as a fallback)
                start.setTimeInMillis(0);
                break;
        }
        setCalendarToStartOfDay(start);
        setCalendarToEndOfDay(end);
        return new long[]{start.getTimeInMillis(), end.getTimeInMillis()};
    }

    private void setCalendarToStartOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void setCalendarToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
