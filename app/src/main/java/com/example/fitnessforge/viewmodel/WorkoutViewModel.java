package com.example.fitnessforge.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessforge.entity.Workout;
import com.example.fitnessforge.repository.WorkoutRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WorkoutViewModel extends AndroidViewModel {
    private WorkoutRepository repository;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        repository = new WorkoutRepository(application);
    }

    public LiveData<List<Workout>> getWorkoutsById(String uid){
        return repository.getWorkoutsById(uid);
    }

    public void insert(Workout workout){repository.insert(workout);}

    public void delete(Workout workout){repository.delete(workout);}

    public void update(Workout workout){repository.updateWorkout(workout);}

    public CompletableFuture<Workout> findByIdFuture(final long workoutId){
        return repository.findByIdFuture(workoutId);
    }

    public CompletableFuture<Long> getWorkoutIdByNameAndUid(String name, String uid){
        return repository.getWorkoutIdByNameAndUid(name, uid);
    }
}
