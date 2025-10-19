package com.example.studyplanner.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.studyplanner.data.model.Task;
import com.example.studyplanner.data.model.TaskWithSubject;

import java.util.List;

@Dao
public interface TaskDao {

    // --- Basic CRUD Operations ---
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT t.*, s.name as subjectName FROM tasks t JOIN subjects s ON t.subjectId = s.subjectId WHERE s.userId = :userId AND t.status != 'Completed' ORDER BY t.deadline ASC")
    LiveData<List<TaskWithSubject>> getPendingTasks(int userId);

    @Query("SELECT t.*, s.name as subjectName FROM tasks t JOIN subjects s ON t.subjectId = s.subjectId WHERE s.userId = :userId AND t.status = 'Completed' ORDER BY t.deadline DESC")
    LiveData<List<TaskWithSubject>> getCompletedTasksWithSubject(int userId);


    // --- Calendar View Queries ---
    @Query("SELECT t.*, s.name as subjectName FROM tasks t JOIN subjects s ON t.subjectId = s.subjectId WHERE s.userId = :userId AND t.deadline BETWEEN :startOfDay AND :endOfDay ORDER BY t.deadline ASC")
    LiveData<List<TaskWithSubject>> getTasksForDate(int userId, long startOfDay, long endOfDay);


    // --- "Overall" Stats Queries ---
    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId)")
    LiveData<Integer> getTotalTaskCount(int userId);

    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId) AND status = :status")
    LiveData<Integer> getTaskCountByStatus(int userId, String status);

    @Query("SELECT * FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId) AND status = 'Completed' ORDER BY deadline DESC")
    LiveData<List<Task>> getCompletedTasksForUser(int userId);

    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId) AND deadline < :now AND status != 'Completed'")
    LiveData<Integer> getOverdueTaskCount(int userId, long now);


    // --- "This Week" Stats Queries ---
    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId) AND deadline BETWEEN :startDate AND :endDate")
    LiveData<Integer> getTotalTaskCountForWeek(int userId, long startDate, long endDate);

    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId) AND status = 'Completed' AND deadline BETWEEN :startDate AND :endDate")
    LiveData<Integer> getCompletedTaskCountForWeek(int userId, long startDate, long endDate);

    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId) AND deadline < :now AND status != 'Completed' AND deadline BETWEEN :startDate AND :endDate")
    LiveData<Integer> getOverdueTaskCountForWeek(int userId, long startDate, long endDate, long now);

    @Query("SELECT * FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId) AND status = 'Completed' AND deadline BETWEEN :startDate AND :endDate")
    LiveData<List<Task>> getCompletedTasksForWeek(int userId, long startDate, long endDate);


    // --- Custom Report Queries ---
    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId = :subjectId AND status = 'Completed' AND deadline BETWEEN :startDate AND :endDate")
    LiveData<Integer> getCompletedTaskCountForSubjectAndDateRange(int subjectId, long startDate, long endDate);

    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId = :subjectId AND deadline < :now AND status != 'Completed' AND deadline BETWEEN :startDate AND :endDate")
    LiveData<Integer> getOverdueTaskCountForSubjectAndDateRange(int subjectId, long startDate, long endDate, long now);

    @Query("SELECT * FROM tasks WHERE subjectId = :subjectId AND status = 'Completed' AND deadline BETWEEN :startDate AND :endDate")
    LiveData<List<Task>> getCompletedTasksForSubjectAndDateRange(int subjectId, long startDate, long endDate);

    // --- New Queries for "All Subjects" Custom Report ---
    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId) AND status = 'Completed' AND deadline BETWEEN :startDate AND :endDate")
    LiveData<Integer> getCompletedTaskCountForUserAndDateRange(int userId, long startDate, long endDate);

    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId) AND deadline < :now AND status != 'Completed' AND deadline BETWEEN :startDate AND :endDate")
    LiveData<Integer> getOverdueTaskCountForUserAndDateRange(int userId, long startDate, long endDate, long now);

    @Query("SELECT * FROM tasks WHERE subjectId IN (SELECT subjectId FROM subjects WHERE userId = :userId) AND status = 'Completed' AND deadline BETWEEN :startDate AND :endDate")
    LiveData<List<Task>> getCompletedTasksForUserAndDateRange(int userId, long startDate, long endDate);

    // --- New Queries for "Overall" Custom Report by Subject
    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId = :subjectId AND status = 'Completed'")
    LiveData<Integer> getCompletedTaskCountForSubject(int subjectId);

    @Query("SELECT COUNT(*) FROM tasks WHERE subjectId = :subjectId AND deadline < :now AND status != 'Completed'")
    LiveData<Integer> getOverdueTaskCountForSubject(int subjectId, long now);

    @Query("SELECT * FROM tasks WHERE subjectId = :subjectId AND status = 'Completed'")
    LiveData<List<Task>> getCompletedTasksForSubject(int subjectId);
}
