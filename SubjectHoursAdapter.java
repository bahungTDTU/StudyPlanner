package com.example.studyplanner.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studyplanner.data.model.SubjectHours;
import com.example.studyplanner.databinding.ItemSubjectHoursBinding;
import java.util.ArrayList;
import java.util.List;

public class SubjectHoursAdapter extends RecyclerView.Adapter<SubjectHoursAdapter.SubjectHoursViewHolder> {

    private final List<SubjectHours> subjectHoursList = new ArrayList<>();

    @NonNull
    @Override
    public SubjectHoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubjectHoursBinding binding = ItemSubjectHoursBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SubjectHoursViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectHoursViewHolder holder, int position) {
        holder.bind(subjectHoursList.get(position));
    }

    @Override
    public int getItemCount() {
        return subjectHoursList.size();
    }

    public void submitList(List<SubjectHours> newList) {
        this.subjectHoursList.clear();
        this.subjectHoursList.addAll(newList);
        notifyDataSetChanged();
    }

    static class SubjectHoursViewHolder extends RecyclerView.ViewHolder {
        private final ItemSubjectHoursBinding binding;

        public SubjectHoursViewHolder(ItemSubjectHoursBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SubjectHours subjectHours) {
            binding.textViewSubjectName.setText(subjectHours.subjectName);
            binding.textViewSubjectHours.setText(subjectHours.formattedHours);
        }
    }
}
