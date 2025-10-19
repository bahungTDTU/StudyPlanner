package com.example.studyplanner.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studyplanner.R;
import com.example.studyplanner.data.model.Task;
import com.example.studyplanner.data.model.TaskWithSubject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CalendarTaskAdapter extends ListAdapter<TaskWithSubject, CalendarTaskAdapter.TaskViewHolder> {

    public CalendarTaskAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TaskWithSubject> DIFF_CALLBACK = new DiffUtil.ItemCallback<TaskWithSubject>() {
        @Override
        public boolean areItemsTheSame(@NonNull TaskWithSubject oldItem, @NonNull TaskWithSubject newItem) {
            return oldItem.task.taskId == newItem.task.taskId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskWithSubject oldItem, @NonNull TaskWithSubject newItem) {
            return oldItem.task.equals(newItem.task) && oldItem.subjectName.equals(newItem.subjectName);
        }
    };

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskWithSubject taskWithSubject = getItem(position);
        holder.bind(taskWithSubject);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, time;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_task_title);
            time = itemView.findViewById(R.id.tv_task_time);
        }

        public void bind(TaskWithSubject taskWithSubject) {
            Task task = taskWithSubject.task;
            title.setText("Study Session: " + taskWithSubject.subjectName);
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
            String startTime = sdf.format(task.startTime);
            String endTime = sdf.format(task.deadline);
            time.setText(startTime + " - " + endTime);
        }
    }
}
