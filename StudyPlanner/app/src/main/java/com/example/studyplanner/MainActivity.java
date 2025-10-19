package com.example.studyplanner;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.studyplanner.util.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "study_planner_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.main_nav_graph);

        // Setup BottomNav unconditionally
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view);
        NavigationUI.setupWithNavController(bottomNav, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.dashboardFragment ||
                destination.getId() == R.id.calendarFragment ||
                destination.getId() == R.id.reportsFragment ||
                destination.getId() == R.id.profileFragment) {
                bottomNav.setVisibility(View.VISIBLE);
            } else {
                bottomNav.setVisibility(View.GONE);
            }
        });

        // Determine the start destination
        SessionManager sessionManager = SessionManager.getInstance(this);
        if (sessionManager.getUserId() == -1) {
            graph.setStartDestination(R.id.loginFragment);
        } else {
            graph.setStartDestination(R.id.dashboardFragment);
        }

        navController.setGraph(graph);
    }
}
