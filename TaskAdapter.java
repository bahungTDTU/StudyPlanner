package com.example.studyplanner.ui.main;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studyplanner.R;
import com.example.studyplanner.data.model.Task;
import com.example.studyplanner.data.model.TaskWithSubject;
import com.example.studyplanner.databinding.ItemTaskUpcomingBinding;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TaskAdapter extends ListAdapter<TaskWithSubject, TaskAdapter.TaskViewHolder> {

    private final OnTaskActionListener listener;

    public TaskAdapter(OnTaskActionListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskUpcomingBinding binding = ItemTaskUpcomingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskWithSubject currentTask = getItem(position);
        holder.bind(currentTask, listener);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final ItemTaskUpcomingBinding binding;

        public TaskViewHolder(@NonNull ItemTaskUpcomingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final TaskWithSubject taskWithSubject, final OnTaskActionListener listener) {
            Task task = taskWithSubject.task;

            binding.tvTaskTitle.setText(task.title);
            if (taskWithSubject.subjectName != null) {
                binding.tvSubjectName.setText(taskWithSubject.subjectName);
            } else {
                binding.tvSubjectName.setText("N/A");
            }
            binding.tvDueDate.setText(formatDueDate(task.deadline));

            String taskType = task.taskType != null ? task.taskType : "";
            switch (taskType) {
                case "Essay":
                    binding.ivTaskIcon.setImageResource(R.drawable.ic_task_essay);
                    break;
                case "Lab Report":
                    binding.ivTaskIcon.setImageResource(R.drawable.ic_task_lab);
                    break;
                case "Quiz":
                default:
                    binding.ivTaskIcon.setImageResource(R.drawable.ic_task_quiz);
                    break;
            }

            binding.ivMenu.setOnClickListener(v -> showPopupMenu(v, task, listener));
            itemView.setOnClickListener(v -> listener.onEditTask(task));
        }

        private void showPopupMenu(View view, Task task, OnTaskActionListener listener) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.inflate(R.menu.task_item_menu);

            Menu menu = popup.getMenu();
            if ("Completed".equals(task.status)) {
                menu.findItem(R.id.action_complete_task).setTitle("Mark as Incomplete");
            } else {
                menu.findItem(R.id.action_complete_task).setTitle("Mark as Complete");
            }

            popup.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.action_edit_task) {
                    listener.onEditTask(task);
                    return true;
                } else if (itemId == R.id.action_delete_task) {
                    listener.onDeleteTask(task);
                    return true;
                } else if (itemId == R.id.action_complete_task) {
                    if ("Completed".equals(task.status)) {
                        listener.onUpdateStatus(task, "Not Started");
                    } else {
                        listener.onUpdateStatus(task, "Completed");
                    }
                    return true;
                }
                return false;
            });
            popup.show();
        }

        private String formatDueDate(Date deadline) {
            if (deadline == null) return "";
            long diff = deadline.getTime() - System.currentTimeMillis();
            if (diff < 0) return "Overdue";

            long days = TimeUnit.MILLISECONDS.toDays(diff);

            if (days == 0) {
                return "Today";
            } else if (days == 1) {
                return "Tomorrow";
            } else if (days < 7) {
                return "In " + days + " days";
            } else {
                return "Next week";
            }
        }
    }

    private static final DiffUtil.ItemCallback<TaskWithSubject> DIFF_CALLBACK = new DiffUtil.ItemCallback<TaskWithSubject>() {
        @Override
        public boolean areItemsTheSame(@NonNull TaskWithSubject oldItem, @NonNull TaskWithSubject newItem) {
            return oldItem.task.taskId == newItem.task.taskId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskWithSubject oldItem, @NonNull TaskWithSubject newItem) {
            return oldItem.task.equals(newItem.task) &&
                    Objects.equals(oldItem.subjectName, newItem.subjectName);
        }
    };

    public interface OnTaskActionListener {
        void onUpdateStatus(Task task, String newStatus);
        void onDeleteTask(Task task);
        void onEditTask(Task task);
    }
}
