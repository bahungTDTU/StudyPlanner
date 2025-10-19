package com.example.studyplanner.ui.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.studyplanner.R;
import com.example.studyplanner.data.model.Subject;
import com.example.studyplanner.databinding.FragmentMySubjectsBinding;
import com.google.android.material.textfield.TextInputEditText;

public class MySubjectsFragment extends Fragment implements ManageSubjectAdapter.OnSubjectDeleteListener {

    private FragmentMySubjectsBinding binding;
    private MainViewModel mainViewModel;
    private ManageSubjectAdapter subjectAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMySubjectsBinding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel.loadUserId();
        setupRecyclerView();

        mainViewModel.userSubjects.observe(getViewLifecycleOwner(), subjects -> {
            if (subjects != null) {
                subjectAdapter.submitList(subjects);
            }
        });

        binding.buttonAddNewSubject.setOnClickListener(v -> showAddSubjectDialog());
    }

    private void setupRecyclerView() {
        subjectAdapter = new ManageSubjectAdapter(this);
        binding.recyclerViewMySubjects.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewMySubjects.setAdapter(subjectAdapter);
    }

    private void showAddSubjectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_subject, null);
        final TextInputEditText input = dialogView.findViewById(R.id.et_subject_name);

        builder.setView(dialogView)
               .setTitle("Add New Subject")
               .setPositiveButton("Save", (dialog, which) -> {
                   String subjectName = input.getText().toString().trim();
                   if (!subjectName.isEmpty()) {
                       mainViewModel.insertSubject(subjectName);
                       Toast.makeText(getContext(), "Subject Saved!", Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(getContext(), "Subject name cannot be empty.", Toast.LENGTH_SHORT).show();
                   }
               })
               .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    public void onDeleteSubject(Subject subject) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Subject")
                .setMessage("Are you sure? This will also delete all associated tasks.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    mainViewModel.deleteSubject(subject);
                    Toast.makeText(getContext(), "Subject Deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
