package com.example.fitnessforge.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessforge.entity.WorkoutLog;
import com.example.fitnessforge.repository.WorkoutLogRepository;

import java.util.Date;
import java.util.List;

public class WorkoutLogViewModel extends AndroidViewModel {
    private WorkoutLogRepository repository;
    public WorkoutLogViewModel(@NonNull Application application) {
        super(application);
        repository = new WorkoutLogRepository(application);
    }

    public LiveData<List<WorkoutLog>> getWorkoutLogsForCurrentMonth(String uid){
        return repository.getWorkoutLogsForCurrentMonth(uid);
    }

    public LiveData<List<WorkoutLog>> getWorkoutLogsBetweenDates(String userId, Date startDate, Date endDate) {
        return repository.getWorkoutLogsBetweenDates(userId, startDate, endDate);
    }

    public void deleteWorkoutLogsNotInCurrentMonth(String uid){
        repository.deleteWorkoutLogsNotInCurrentMonth(uid);
    }

    public void insert(WorkoutLog workoutLog){repository.insert(workoutLog);}

    public void insertAll(List<WorkoutLog> logs){
        repository.insertAll(logs);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
