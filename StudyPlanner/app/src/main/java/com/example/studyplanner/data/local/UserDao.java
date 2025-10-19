package com.example.studyplanner.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studyplanner.data.model.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertAndGetId(User user);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User findByEmail(String email);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User findByUsername(String username);

    @Query("SELECT * FROM users WHERE email = :identifier OR username = :identifier LIMIT 1")
    User findByIdentifier(String identifier);

    // This line will now work correctly
    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    LiveData<User> getUserById(int userId);

    @Update
    void update(User user);
}