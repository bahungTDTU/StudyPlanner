package com.example.studyplanner.ui.auth;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.studyplanner.R;
import com.example.studyplanner.util.SessionManager;

public class SplashFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isAdded() && getContext() != null) {
                SessionManager sessionManager = SessionManager.getInstance(requireContext());
                if (sessionManager.isLoggedIn()) {
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_splashFragment_to_dashboardFragment);
                } else {
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_splashFragment_to_loginFragment);
                }
            }
        }, 1500); // 1.5 second delay
    }
}
