package com.example.studyplanner.data.model;

import androidx.room.Embedded;

public class TaskWithSubject {
    @Embedded
    public Task task;
    public String subjectName;
}
