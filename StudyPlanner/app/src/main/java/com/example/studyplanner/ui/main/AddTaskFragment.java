package com.example.studyplanner.ui.main;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.studyplanner.NotificationReceiver;
import com.example.studyplanner.R;
import com.example.studyplanner.data.model.Subject;
import com.example.studyplanner.data.model.Task;
import com.example.studyplanner.databinding.FragmentAddTaskBinding;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AddTaskFragment extends Fragment {

    private FragmentAddTaskBinding binding;
    private MainViewModel mainViewModel;
    private List<Subject> subjectList = new ArrayList<>();
    private final Calendar startCalendar = Calendar.getInstance();
    private final Calendar deadlineCalendar = Calendar.getInstance();
    private Task existingTask = null;
    private boolean isStartTimeSet = false;
    private boolean isDeadlineSet = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            existingTask = getArguments().getParcelable("task_to_edit");
        }

        setupSpinners();
        if (existingTask != null) {
            populateFieldsForEdit();
        }

        binding.buttonPickStartTime.setOnClickListener(v -> showDateTimePicker(true));
        binding.buttonPickDeadline.setOnClickListener(v -> showDateTimePicker(false));
        binding.buttonSaveTask.setOnClickListener(v -> saveTask());
        setupUI(view);
    }

    private void populateFieldsForEdit() {
        binding.textFieldTaskTitle.getEditText().setText(existingTask.title);
        binding.textFieldTaskDescription.getEditText().setText(existingTask.description);

        if (existingTask.startTime != null) {
            startCalendar.setTime(existingTask.startTime);
            updateDateTimeButtonText(binding.buttonPickStartTime, existingTask.startTime);
            isStartTimeSet = true;
        }
        if (existingTask.deadline != null) {
            deadlineCalendar.setTime(existingTask.deadline);
            updateDateTimeButtonText(binding.buttonPickDeadline, existingTask.deadline);
            isDeadlineSet = true;
        }
    }

    private void setupSpinners() {
        // Subject Spinner
        mainViewModel.userSubjects.observe(getViewLifecycleOwner(), subjects -> {
            if (subjects != null) {
                subjectList = subjects;
                List<String> subjectNames = subjects.stream().map(s -> s.name).collect(Collectors.toList());
                ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, subjectNames);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerSubject.setAdapter(subjectAdapter);

                if (existingTask != null) {
                    for (int i = 0; i < subjectList.size(); i++) {
                        if (subjectList.get(i).subjectId == existingTask.subjectId) {
                            binding.spinnerSubject.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });

        // Task Type Spinner
        ArrayAdapter<CharSequence> taskTypeAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.task_types, android.R.layout.simple_spinner_item);
        taskTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTaskType.setAdapter(taskTypeAdapter);

        if (existingTask != null) {
            for (int i = 0; i < taskTypeAdapter.getCount(); i++) {
                if (taskTypeAdapter.getItem(i).toString().equals(existingTask.taskType)) {
                    binding.spinnerTaskType.setSelection(i);
                    break;
                }
            }
        }
    }

    private void showDateTimePicker(final boolean isStartTime) {
        final Calendar currentCalendar = isStartTime ? startCalendar : deadlineCalendar;

        new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            currentCalendar.set(Calendar.YEAR, year);
            currentCalendar.set(Calendar.MONTH, month);
            currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(requireContext(), (timeView, hourOfDay, minute) -> {
                currentCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                currentCalendar.set(Calendar.MINUTE, minute);

                if (isStartTime) {
                    isStartTimeSet = true;
                    updateDateTimeButtonText(binding.buttonPickStartTime, startCalendar.getTime());
                } else {
                    isDeadlineSet = true;
                    updateDateTimeButtonText(binding.buttonPickDeadline, deadlineCalendar.getTime());
                }
            }, currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(Calendar.MINUTE), false).show();
        }, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateTimeButtonText(Button button, Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy 'at' h:mm a", Locale.getDefault());
            button.setText(sdf.format(date));
        }
    }

    private void saveTask() {
        hideKeyboard();
        String title = binding.textFieldTaskTitle.getEditText().getText().toString().trim();
        String description = binding.textFieldTaskDescription.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(title) || !isStartTimeSet || !isDeadlineSet || binding.spinnerSubject.getSelectedItemPosition() < 0) {
            Toast.makeText(getContext(), "Please fill all fields and select a subject.", Toast.LENGTH_LONG).show();
            return;
        }

        if (startCalendar.getTimeInMillis() >= deadlineCalendar.getTimeInMillis()) {
            Toast.makeText(getContext(), "Start time must be before the deadline.", Toast.LENGTH_SHORT).show();
            return;
        }

        String taskType = binding.spinnerTaskType.getSelectedItem().toString();
        Subject selectedSubject = subjectList.get(binding.spinnerSubject.getSelectedItemPosition());

        if (existingTask == null) {
            Task newTask = new Task();
            newTask.title = title;
            newTask.description = description;
            newTask.taskType = taskType;
            newTask.subjectId = selectedSubject.subjectId;
            newTask.startTime = startCalendar.getTime();
            newTask.deadline = deadlineCalendar.getTime();
            newTask.status = "Not Started";
            mainViewModel.insertTask(newTask);
            scheduleNotification(newTask);
            Toast.makeText(getContext(), "Task Saved!", Toast.LENGTH_SHORT).show();
        } else {
            existingTask.title = title;
            existingTask.description = description;
            existingTask.taskType = taskType;
            existingTask.subjectId = selectedSubject.subjectId;
            existingTask.startTime = startCalendar.getTime();
            existingTask.deadline = deadlineCalendar.getTime();
            mainViewModel.updateTask(existingTask);
            Toast.makeText(getContext(), "Task Updated!", Toast.LENGTH_SHORT).show();
        }

        NavHostFragment.findNavController(this).popBackStack();
    }

    private void scheduleNotification(Task task) {
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;

        Intent intent = new Intent(requireContext(), NotificationReceiver.class);
        intent.putExtra(NotificationReceiver.EXTRA_NOTIFICATION_ID, task.taskId);
        intent.putExtra(NotificationReceiver.EXTRA_TASK_TITLE, task.title);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                task.taskId,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        long oneDayInMillis = 24 * 60 * 60 * 1000;
        long triggerTime = task.deadline.getTime() - oneDayInMillis;

        if (triggerTime > System.currentTimeMillis()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
                }
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            }
        }
    }

    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideKeyboard();
                return false;
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
