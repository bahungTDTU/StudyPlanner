package com.example.studyplanner.data.model;

import java.util.List;

public class SubjectPerformance {
    private final String subjectName;
    private final int overallPercentage;
    private final List<Float> weeklyPerformance;

    public SubjectPerformance(String subjectName, int overallPercentage, List<Float> weeklyPerformance) {
        this.subjectName = subjectName;
        this.overallPercentage = overallPercentage;
        this.weeklyPerformance = weeklyPerformance;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getOverallPercentage() {
        return overallPercentage;
    }

    public List<Float> getWeeklyPerformance() {
        return weeklyPerformance;
    }
}
