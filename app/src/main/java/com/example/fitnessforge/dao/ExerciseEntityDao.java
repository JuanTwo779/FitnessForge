package com.example.fitnessforge.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessforge.entity.ExerciseEntity;

import java.util.List;

@Dao
public interface ExerciseEntityDao {

    @Query("SELECT * FROM exercise WHERE uid =:id")
    LiveData<List<ExerciseEntity>> getAllByUid(String id);

    @Insert
    void insert(ExerciseEntity exerciseEntity);

    @Delete
    void delete(ExerciseEntity exerciseEntity);

    @Update
    void updateExercise(ExerciseEntity exerciseEntity);

    //For delete and update
    @Query("SELECT * FROM exercise WHERE id =:exerciseId LIMIT 1")
    ExerciseEntity findById(long exerciseId);

    //For adding exercise to workout
    @Query("SELECT id FROM exercise WHERE uid =:uid AND name =:name")
    long getExerciseIdByNameAndUid(String name, String uid);


}
