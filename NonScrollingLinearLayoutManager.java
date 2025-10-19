package com.example.studyplanner.ui.main;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * A custom LinearLayoutManager that disables its own scrolling abilities.
 * This is essential for using a RecyclerView inside a ScrollView.
 * It forces the RecyclerView to measure its entire height, allowing the parent
 * ScrollView to handle all scrolling correctly.
 */
public class NonScrollingLinearLayoutManager extends LinearLayoutManager {

    public NonScrollingLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        // This is the magic line. By returning false, we tell the RecyclerView
        // that it is not allowed to scroll internally.
        return false;
    }
}

