package com.example.fitnessforge.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessforge.entity.Workout;

import java.util.List;

@Dao
public interface WorkoutDAO {

    //get user's workouts
    @Query("SELECT * FROM workout WHERE uid =:id")
    LiveData<List<Workout>> getAllByUid(String id);

    //Insert
    @Insert
    void insert(Workout workout);

    //Delete
    @Delete
    void delete(Workout workout);

    //Update
    @Update
    void updateWorkout(Workout workout);

    //For delete and update
    @Query("SELECT * FROM workout WHERE id =:workoutId LIMIT 1")
    Workout findById(long workoutId);

    //For adding workout to day plan
    @Query("SELECT * FROM workout WHERE uid =:uid AND name =:name")
    long getWorkoutIdByNameAndUid(String name, String uid);



}
