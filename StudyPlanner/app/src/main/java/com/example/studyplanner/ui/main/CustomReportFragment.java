package com.example.studyplanner.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.studyplanner.R;
import com.example.studyplanner.data.model.Subject;
import com.example.studyplanner.databinding.FragmentCustomReportBinding;

import java.util.ArrayList;
import java.util.List;

public class CustomReportFragment extends Fragment {

    private FragmentCustomReportBinding binding;
    private MainViewModel mainViewModel;
    private List<Subject> subjectList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomReportBinding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel.loadUserId();
        setupSpinners();
        observeReportData();

        binding.buttonGenerateReport.setOnClickListener(v -> generateReport());
    }

    private void setupSpinners() {
        // --- Subject Spinner ---
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerSubjectReport.setAdapter(adapter);

        mainViewModel.userSubjects.observe(getViewLifecycleOwner(), subjects -> {
            if (subjects != null) {
                subjectList = subjects;
                List<String> subjectNames = new ArrayList<>();
                subjectNames.add("All Subjects");
                for (Subject subject : subjects) {
                    subjectNames.add(subject.name);
                }
                adapter.clear();
                adapter.addAll(subjectNames);
                adapter.notifyDataSetChanged();
            }
        });

        // --- Date Range Spinner ---
        ArrayAdapter<CharSequence> dateAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.report_date_ranges,
                android.R.layout.simple_spinner_item
        );
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerDateRangeReport.setAdapter(dateAdapter);
    }

    private void generateReport() {
        if (subjectList.isEmpty() && binding.spinnerSubjectReport.getSelectedItemPosition() <= 0) {
            Toast.makeText(getContext(), "Please add a subject first.", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progressBarReport.setVisibility(View.VISIBLE);
        binding.reportResultsContainer.setVisibility(View.GONE);

        int subjectPosition = binding.spinnerSubjectReport.getSelectedItemPosition();
        int dateRangePosition = binding.spinnerDateRangeReport.getSelectedItemPosition();

        int subjectId = (subjectPosition == 0) ? -1 : subjectList.get(subjectPosition - 1).subjectId;

        mainViewModel.setReportFilters(subjectId, dateRangePosition);
    }

    private void observeReportData() {
        mainViewModel.reportCompletedCount.observe(getViewLifecycleOwner(), count -> {
            if (binding.progressBarReport.getVisibility() == View.VISIBLE) {
                binding.progressBarReport.setVisibility(View.GONE);
                binding.reportResultsContainer.setVisibility(View.VISIBLE);
            }
            binding.textViewReportCompleted.setText(count != null ? String.valueOf(count) : "0");
        });

        mainViewModel.reportOverdueCount.observe(getViewLifecycleOwner(), count -> {
            binding.textViewReportOverdue.setText(count != null ? String.valueOf(count) : "0");
        });

        mainViewModel.reportStudyHours.observe(getViewLifecycleOwner(), hours -> {
            binding.textViewReportHours.setText(hours != null ? hours : "0h 0m");
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
