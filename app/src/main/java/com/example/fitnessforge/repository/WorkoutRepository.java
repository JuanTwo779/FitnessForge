package com.example.fitnessforge.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.fitnessforge.dao.WorkoutDAO;
import com.example.fitnessforge.database.FitnessForgeDatabase;
import com.example.fitnessforge.entity.Workout;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class WorkoutRepository {

    private WorkoutDAO workoutDAO;

    public WorkoutRepository(Application application){
        FitnessForgeDatabase db = FitnessForgeDatabase.getInstance(application);
        workoutDAO = db.workoutDAO();
    }

    public LiveData<List<Workout>> getWorkoutsById(String uid){
        return workoutDAO.getAllByUid(uid);
    }

    public void insert(final Workout workout){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                workoutDAO.insert(workout);
            }
        });
    }

    public void delete(final Workout workout){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                workoutDAO.delete(workout);
            }
        });
    }

    public void updateWorkout(final Workout workout){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                workoutDAO.updateWorkout(workout);
            }
        });
    }

    public CompletableFuture<Workout> findByIdFuture(final long workoutId){
        return CompletableFuture.supplyAsync(new Supplier<Workout>() {
            @Override
            public Workout get() {
                return workoutDAO.findById(workoutId);
            }
        }, FitnessForgeDatabase.databaseWriteExecutor);
    }

    public CompletableFuture<Long> getWorkoutIdByNameAndUid(String name, String uid){
        return  CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                return workoutDAO.getWorkoutIdByNameAndUid(name, uid);
            }
        }, FitnessForgeDatabase.databaseWriteExecutor);
    }
}
