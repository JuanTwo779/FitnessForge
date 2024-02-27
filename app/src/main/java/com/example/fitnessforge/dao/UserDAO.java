package com.example.fitnessforge.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessforge.entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM user WHERE uid = :userId")
    LiveData<User> getUserById(String userId);

    @Query("DELETE FROM user")
    void deleteAllRows();
}
