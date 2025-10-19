package com.example.studyplanner.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studyplanner.data.model.Subject;
import com.example.studyplanner.databinding.ItemSubjectManageBinding;
import java.util.ArrayList;
import java.util.List;

public class ManageSubjectAdapter extends RecyclerView.Adapter<ManageSubjectAdapter.SubjectViewHolder> {

    private final List<Subject> subjectList = new ArrayList<>();
    private final OnSubjectDeleteListener listener;

    public interface OnSubjectDeleteListener {
        void onDeleteSubject(Subject subject);
    }

    public ManageSubjectAdapter(OnSubjectDeleteListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubjectManageBinding binding = ItemSubjectManageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SubjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.bind(subject);
        holder.binding.buttonDeleteSubject.setOnClickListener(v -> listener.onDeleteSubject(subject));
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public void submitList(List<Subject> subjects) {
        this.subjectList.clear();
        this.subjectList.addAll(subjects);
        notifyDataSetChanged();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        private final ItemSubjectManageBinding binding;

        public SubjectViewHolder(ItemSubjectManageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Subject subject) {
            binding.textViewSubjectName.setText(subject.name);
        }
    }
}
