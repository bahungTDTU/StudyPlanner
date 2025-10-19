package com.example.studyplanner.data.model;

public class SubjectHours {
    public final String subjectName;
    public final String formattedHours;
    public final long totalMillis; // We need this for the chart

    public SubjectHours(String subjectName, String formattedHours, long totalMillis) {
        this.subjectName = subjectName;
        this.formattedHours = formattedHours;
        this.totalMillis = totalMillis;
    }
}