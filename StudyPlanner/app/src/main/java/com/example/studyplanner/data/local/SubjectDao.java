package com.example.studyplanner.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import com.example.studyplanner.data.model.Subject;
import com.example.studyplanner.data.model.TaskWithSubject;

import java.util.List;
import androidx.room.Transaction;
import com.example.studyplanner.data.relation.SubjectWithTasks;

@Dao
public interface SubjectDao {
    @Insert
    void insert(Subject subject);

    @Query("SELECT * FROM subjects WHERE userId = :userId ORDER BY name ASC")
    LiveData<List<Subject>> getSubjectsForUser(int userId);

    @Transaction
    @Query("SELECT * FROM subjects WHERE userId = :userId")
    LiveData<List<SubjectWithTasks>> getSubjectsWithTasks(int userId);

    @Transaction
    @Query("SELECT t.*, s.name as subjectName FROM tasks t JOIN subjects s ON t.subjectId = s.subjectId WHERE s.subjectId IN (:subjectIds)")
    LiveData<List<TaskWithSubject>> getTasksWithSubjects(List<Integer> subjectIds);

    @Delete void delete(Subject subject);
}
