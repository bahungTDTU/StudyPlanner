package com.example.studyplanner.ui.main;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;


public class NonScrollingLinearLayoutManager extends LinearLayoutManager {

    public NonScrollingLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}

