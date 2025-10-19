package com.example.studyplanner.ui.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.studyplanner.R;
import com.example.studyplanner.data.model.Task;
import com.example.studyplanner.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment implements TaskAdapter.OnTaskActionListener {

    private FragmentDashboardBinding binding;
    private MainViewModel mainViewModel;
    private TaskAdapter upcomingTaskAdapter;
    private TaskAdapter completedTaskAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);

        mainViewModel.loadUserId();
        setupRecyclerViews();

        mainViewModel.currentUser.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.tvGreeting.setText("Hello, " + user.username + "!");
            }
        });

        mainViewModel.pendingTasks.observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) {
                upcomingTaskAdapter.submitList(tasks);
            }
        });

        mainViewModel.completedTasks.observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) {
                completedTaskAdapter.submitList(tasks);
            }
        });

        mainViewModel.displayTotalTaskCount.observe(getViewLifecycleOwner(), total -> updateProgressBar());
        mainViewModel.displayCompletedTaskCount.observe(getViewLifecycleOwner(), completed -> updateProgressBar());

        binding.fabAddTask.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_addTaskFragment);
        });
    }

    private void setupRecyclerViews() {
        upcomingTaskAdapter = new TaskAdapter(this);
        binding.recyclerViewTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewTasks.setAdapter(upcomingTaskAdapter);
        binding.recyclerViewTasks.setNestedScrollingEnabled(false);

        completedTaskAdapter = new TaskAdapter(this);
        binding.recyclerViewCompletedTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewCompletedTasks.setAdapter(completedTaskAdapter);
        binding.recyclerViewCompletedTasks.setNestedScrollingEnabled(false);
    }

    private void updateProgressBar() {
        Integer total = mainViewModel.displayTotalTaskCount.getValue();
        Integer completed = mainViewModel.displayCompletedTaskCount.getValue();

        if (completed != null && total != null && total > 0) {
            int progress = (int) ((completed / (float) total) * 100);
            binding.progressBar.setProgress(progress, true);
            binding.tvProgress.setText(String.format("%d%% Complete", progress));
        } else {
            binding.progressBar.setProgress(0, true);
            binding.tvProgress.setText("0% Complete");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            NavHostFragment.findNavController(this).navigate(R.id.profileFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdateStatus(Task task, String newStatus) {
        task.status = newStatus;
        mainViewModel.updateTask(task);
        String message = "Completed".equals(newStatus) ? "Task marked as complete!" : "Task marked as incomplete.";
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteTask(Task task) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    mainViewModel.deleteTask(task);
                    Toast.makeText(getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onEditTask(Task task) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("task_to_edit", task);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_dashboardFragment_to_addTaskFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
