package com.example.fitnessforge.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fitnessforge.entity.WorkoutLog;

import java.util.Date;
import java.util.List;

@Dao
public interface WorkoutLogDAO {

    //Gets the workouts logs of the user within the current month
    @Query("SELECT * FROM WorkoutLog " +
            "WHERE strftime('%m', date / 1000, 'unixepoch') = strftime('%m', 'now') " +
            "AND uid =:uid")
    LiveData<List<WorkoutLog>> getWorkoutLogsForCurrentMonth(String uid);


    //deletes all exercises not within the current month
    @Query("DELETE FROM WorkoutLog " +
            "WHERE strftime('%m', date / 1000, 'unixepoch') != strftime('%m', 'now') " +
            "AND uid =:uid")
    void deleteWorkoutLogsNotInCurrentMonth(String uid);

    //Get the workout logs of the user within the startDate and endDate Date objects passed in
    @Query("SELECT * FROM WorkoutLog WHERE uid = :userId AND date BETWEEN :startDate AND :endDate")
    LiveData<List<WorkoutLog>> getWorkoutLogsBetweenDates(String userId, Date startDate, Date endDate);

    @Insert
    void insert(WorkoutLog workoutLog);

    @Insert
    void insertAll(List<WorkoutLog> workoutLogs);

    @Query("DELETE FROM workoutlog")
    void deleteAll();
}
