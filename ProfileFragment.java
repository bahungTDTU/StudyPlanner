package com.example.studyplanner.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.studyplanner.R;
import com.example.studyplanner.util.SessionManager;

public class ProfileFragment extends Fragment {

    private MainViewModel mainViewModel;
    private SessionManager sessionManager;
    private TextView tvName, tvEmail, tvProfileNameHeader, tvProfileEmailHeader, tvSchoolName, tvAcademicProcess;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        sessionManager = SessionManager.getInstance(requireContext());

        tvName = view.findViewById(R.id.tv_profile_name);
        tvEmail = view.findViewById(R.id.tv_profile_email);
        tvProfileNameHeader = view.findViewById(R.id.tv_profile_name_header);
        tvProfileEmailHeader = view.findViewById(R.id.tv_profile_email_header);
        tvSchoolName = view.findViewById(R.id.tv_school_name);
        tvAcademicProcess = view.findViewById(R.id.tv_academic_process);
        Button btnLogout = view.findViewById(R.id.btn_logout);
        TextView tvMySubjects = view.findViewById(R.id.tv_my_subjects);

        mainViewModel.loadUserId();
        mainViewModel.currentUser.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                tvName.setText(user.username);
                tvEmail.setText(user.email);
                tvProfileNameHeader.setText(user.username);
                tvProfileEmailHeader.setText(user.email);
                tvSchoolName.setText(user.schoolName);
                tvAcademicProcess.setText(user.academicProcess);
            }
        });

        // Logout button logic
        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            NavHostFragment.findNavController(this).navigate(R.id.action_global_loginFragment);
        });

        view.findViewById(R.id.iv_back).setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
        view.findViewById(R.id.fab_edit_profile).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("user_profile", mainViewModel.currentUser.getValue());
            NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_editProfileFragment, bundle);
        });

        tvMySubjects.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_mySubjectsFragment);
        });
    }
}
