package com.example.studyplanner.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.studyplanner.R;
import com.example.studyplanner.databinding.FragmentReportsBinding;

public class ReportsFragment extends Fragment {

    private FragmentReportsBinding binding;
    private MainViewModel mainViewModel;
    private SubjectPerformanceAdapter performanceAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReportsBinding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel.loadUserId(); // Ensure data is loaded for the current user
        setupRecyclerView();

        mainViewModel.totalStudyHours.observe(getViewLifecycleOwner(), hours -> {
            if (hours != null && !hours.isEmpty()) {
                binding.tvTotalStudyHours.setText(hours);
            } else {
                binding.tvTotalStudyHours.setText("0"); // Default to 0 if no hours logged
            }
        });

        mainViewModel.subjectPerformances.observe(getViewLifecycleOwner(), performances -> {
            if (performances != null && !performances.isEmpty()) {
                performanceAdapter.setPerformanceList(performances);
                binding.rvSubjectPerformance.setVisibility(View.VISIBLE);
            } else {
                binding.rvSubjectPerformance.setVisibility(View.GONE);
            }
        });

        binding.ivBack.setOnClickListener(v -> {
            if (!NavHostFragment.findNavController(this).navigateUp()) {
                requireActivity().onBackPressed();
            }
        });
    }

    private void setupRecyclerView() {
        performanceAdapter = new SubjectPerformanceAdapter();
        binding.rvSubjectPerformance.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvSubjectPerformance.setAdapter(performanceAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
