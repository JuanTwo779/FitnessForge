package com.example.fitnessforge.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.fitnessforge.database.FitnessForgeDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class DayPlan {

    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String uid;
    @NonNull
    public String day;
    @Nullable
    public Long workoutId;

    public DayPlan(){};

    public DayPlan(@NonNull String day, String uid, @Nullable Long workoutId) {
        this.day = day;
        this.uid = uid;
        this.workoutId = workoutId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getDay() {
        return day;
    }

    public void setDay(@NonNull String day) {
        this.day = day;
    }

    @Nullable
    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(@Nullable Long workoutId) {
        this.workoutId = workoutId;
    }

    @NonNull
    @Override
    public String toString() {
        return "DayPlan{" +
                "id=" + id +
                ", day='" + day + '\'' +
                ", workoutId=" + workoutId +
                '}';
    }
}
