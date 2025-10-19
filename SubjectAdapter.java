package com.example.studyplanner.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studyplanner.R;
import com.example.studyplanner.data.model.Task;
import com.example.studyplanner.data.model.TaskWithSubject;
import com.example.studyplanner.data.relation.SubjectWithTasks;
import com.example.studyplanner.databinding.ItemSubjectGroupBinding;
import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private final List<SubjectWithTasks> subjectList = new ArrayList<>();
    private final TaskAdapter.OnTaskActionListener taskListener;

    public SubjectAdapter(TaskAdapter.OnTaskActionListener taskListener) {
        this.taskListener = taskListener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubjectGroupBinding binding = ItemSubjectGroupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SubjectViewHolder(binding, taskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.bind(subjectList.get(position));
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public void submitList(List<SubjectWithTasks> subjects) {
        this.subjectList.clear();
        this.subjectList.addAll(subjects);
        notifyDataSetChanged();
    }

    // This is the ViewHolder with the new logic
    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        private final ItemSubjectGroupBinding binding;
        private final TaskAdapter.OnTaskActionListener taskListener;
        // 1. Give each ViewHolder its own "expanded" state
        private boolean isExpanded = false;

        public SubjectViewHolder(ItemSubjectGroupBinding binding, TaskAdapter.OnTaskActionListener taskListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.taskListener = taskListener;
        }

        public void bind(SubjectWithTasks subjectWithTasks) {
            binding.textViewSubjectName.setText(subjectWithTasks.subject.name);

            // Set up the inner RecyclerView (this part is the same)
            TaskAdapter innerTaskAdapter = new TaskAdapter(taskListener);
            binding.recyclerViewInnerTasks.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            binding.recyclerViewInnerTasks.setAdapter(innerTaskAdapter);
            List<TaskWithSubject> tasksWithSubject = new ArrayList<>();
            if (subjectWithTasks.tasks != null) {
                for (Task task : subjectWithTasks.tasks) {
                    TaskWithSubject taskWithSubject = new TaskWithSubject();
                    taskWithSubject.task = task;
                    taskWithSubject.subjectName = subjectWithTasks.subject.name;
                    tasksWithSubject.add(taskWithSubject);
                }
            }
            innerTaskAdapter.submitList(tasksWithSubject);

            // 2. Set the initial state when binding
            updateExpandedState();

            // 3. Set the click listener for the expand button
            binding.buttonExpand.setOnClickListener(v -> {
                isExpanded = !isExpanded; // Toggle the state
                updateExpandedState();
            });

            // (Optional but good) Make the title clickable too
            binding.textViewSubjectName.setOnClickListener(v -> {
                isExpanded = !isExpanded;
                updateExpandedState();
            });
        }

        // 4. A helper method to handle the UI changes
        private void updateExpandedState() {
            if (isExpanded) {
                binding.recyclerViewInnerTasks.setVisibility(View.VISIBLE);
                binding.buttonExpand.setImageResource(R.drawable.ic_expand_less);
            } else {
                binding.recyclerViewInnerTasks.setVisibility(View.GONE);
                binding.buttonExpand.setImageResource(R.drawable.ic_expand_more);
            }
        }
    }
}
