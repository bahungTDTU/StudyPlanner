package com.example.studyplanner.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studyplanner.R;
import com.example.studyplanner.data.model.Task;
import com.example.studyplanner.data.model.TaskWithSubject;

public class CompletedTaskAdapter extends ListAdapter<TaskWithSubject, CompletedTaskAdapter.CompletedTaskViewHolder> {

    private final TaskAdapter.OnTaskActionListener mListener;

    public CompletedTaskAdapter(TaskAdapter.OnTaskActionListener listener) {
        super(DIFF_CALLBACK);
        mListener = listener;
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
    public CompletedTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_completed_task, parent, false);
        return new CompletedTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTaskViewHolder holder, int position) {
        TaskWithSubject taskWithSubject = getItem(position);
        holder.bind(taskWithSubject, mListener);
    }

    static class CompletedTaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, subject;
        private final ImageView moreOptions;

        public CompletedTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_task_title);
            subject = itemView.findViewById(R.id.tv_task_subject);
            moreOptions = itemView.findViewById(R.id.iv_more);
        }

        public void bind(TaskWithSubject taskWithSubject, final TaskAdapter.OnTaskActionListener listener) {
            title.setText(taskWithSubject.task.title);
            subject.setText(taskWithSubject.subjectName);

            if (listener != null) {
                moreOptions.setOnClickListener(v -> showPopupMenu(v.getContext(), taskWithSubject.task, listener));
            }
        }

        private void showPopupMenu(Context context, Task task, TaskAdapter.OnTaskActionListener listener) {
            PopupMenu popup = new PopupMenu(context, moreOptions);
            popup.getMenu().add(Menu.NONE, 1, 1, "Edit");
            popup.getMenu().add(Menu.NONE, 2, 2, "Mark as Incomplete");
            popup.getMenu().add(Menu.NONE, 3, 3, "Delete");
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case 1:
                        listener.onEditTask(task);
                        return true;
                    case 2:
                        listener.onUpdateStatus(task, "Pending");
                        return true;
                    case 3:
                        listener.onDeleteTask(task);
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        }
    }
}
