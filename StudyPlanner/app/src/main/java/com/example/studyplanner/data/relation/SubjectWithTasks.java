package com.example.studyplanner.data.relation;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.example.studyplanner.data.model.Subject;
import com.example.studyplanner.data.model.Task;
import java.util.List;

public class SubjectWithTasks {
    @Embedded
    public Subject subject;

    @Relation(
            parentColumn = "subjectId",
            entityColumn = "subjectId"
    )
    public List<Task> tasks;
}