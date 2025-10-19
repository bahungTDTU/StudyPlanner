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
import com.example.studyplanner.R;
import com.example.studyplanner.databinding.FragmentReportsBinding;

public class ReportsFragment extends Fragment {

    private FragmentReportsBinding binding;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReportsBinding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel.loadUserId(); 

        // Observe LiveData for task counts
        mainViewModel.displayTotalTaskCount.observe(getViewLifecycleOwner(), count -> {
            binding.tvTotalTasks.setText(count != null ? String.valueOf(count) : "0");
        });

        mainViewModel.displayCompletedTaskCount.observe(getViewLifecycleOwner(), count -> {
            binding.tvCompletedTasks.setText(count != null ? String.valueOf(count) : "0");
        });

        mainViewModel.displayOverdueTaskCount.observe(getViewLifecycleOwner(), count -> {
            binding.tvOverdueTasks.setText(count != null ? String.valueOf(count) : "0");
        });

        // Navigation for the back button
        binding.ivBack.setOnClickListener(v -> {
            if (!NavHostFragment.findNavController(this).navigateUp()) {
                requireActivity().onBackPressed();
            }
        });

        // Navigation for the custom report button
        binding.btnGenerateCustomReport.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_reportsFragment_to_customReportFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
