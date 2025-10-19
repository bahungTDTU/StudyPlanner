package com.example.studyplanner.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "subjects",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE))
public class Subject {
    @PrimaryKey(autoGenerate = true)
    public int subjectId;

    public int userId; // Foreign key for the user

    @NonNull
    public String name;
}