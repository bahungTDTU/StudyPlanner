package com.example.studyplanner.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.studyplanner.R;
import com.example.studyplanner.data.model.User;
import com.example.studyplanner.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    private MainViewModel mainViewModel;
    private User currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the user object passed from the ProfileFragment
        if (getArguments() != null) {
            currentUser = getArguments().getParcelable("user_profile");
        }

        if (currentUser != null) {
            populateFields();
        }

        binding.buttonSaveChanges.setOnClickListener(v -> saveChanges());
    }

    private void populateFields() {
        binding.textFieldFullName.getEditText().setText(currentUser.fullName);
        binding.textFieldDob.getEditText().setText(currentUser.dateOfBirth);
        binding.textFieldSchool.getEditText().setText(currentUser.schoolName);
        binding.textFieldAcademicProcess.getEditText().setText(currentUser.academicProcess);
    }

    private void saveChanges() {
        if (currentUser != null) {
            currentUser.fullName = binding.textFieldFullName.getEditText().getText().toString().trim();
            currentUser.dateOfBirth = binding.textFieldDob.getEditText().getText().toString().trim();
            currentUser.schoolName = binding.textFieldSchool.getEditText().getText().toString().trim();
            currentUser.academicProcess = binding.textFieldAcademicProcess.getEditText().getText().toString().trim();

            mainViewModel.updateUser(currentUser);

            Toast.makeText(getContext(), "Profile Updated!", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this).popBackStack();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
