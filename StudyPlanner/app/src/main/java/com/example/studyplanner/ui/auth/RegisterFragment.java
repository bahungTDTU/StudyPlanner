package com.example.studyplanner.ui.auth;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.studyplanner.R;
import com.example.studyplanner.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private AuthViewModel authViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        NavController navController = NavHostFragment.findNavController(this);

        authViewModel.getRegistrationSuccess().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                Toast.makeText(getContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            } else {
                Toast.makeText(getContext(), "Registration failed. Email or username might already be in use.", Toast.LENGTH_LONG).show();
            }
        });

        binding.buttonRegister.setOnClickListener(v -> {
            hideKeyboard(); 
            String username = binding.textFieldUsername.getEditText().getText().toString().trim();
            String email = binding.textFieldEmail.getEditText().getText().toString().trim();
            String password = binding.textFieldPassword.getEditText().getText().toString().trim();
            String confirmPassword = binding.textFieldConfirmPassword.getEditText().getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            authViewModel.register(username, email, password);
        });

        binding.textViewGoToLogin.setOnClickListener(v -> {
            navController.navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }

    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
