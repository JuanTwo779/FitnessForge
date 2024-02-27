package com.example.fitnessforge.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.fitnessforge.dao.WorkoutLogDAO;
import com.example.fitnessforge.database.FitnessForgeDatabase;
import com.example.fitnessforge.entity.WorkoutLog;

import java.util.Date;
import java.util.List;

public class WorkoutLogRepository {
    private WorkoutLogDAO workoutLogDAO;

    public WorkoutLogRepository(Application application){
        FitnessForgeDatabase db = FitnessForgeDatabase.getInstance(application);
        workoutLogDAO = db.workoutLogDAO();
    }

    public LiveData<List<WorkoutLog>> getWorkoutLogsForCurrentMonth(String uid){
        return workoutLogDAO.getWorkoutLogsForCurrentMonth(uid);
    }

    public LiveData<List<WorkoutLog>> getWorkoutLogsBetweenDates(String userId, Date startDate, Date endDate) {
        return workoutLogDAO.getWorkoutLogsBetweenDates(userId, startDate, endDate);
    }

    public void deleteWorkoutLogsNotInCurrentMonth(String uid){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                workoutLogDAO.deleteWorkoutLogsNotInCurrentMonth(uid);
            }
        });
    }

    public void insert(final WorkoutLog workoutLog){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                workoutLogDAO.insert(workoutLog);
            }
        });
    }

    public void insertAll(final List<WorkoutLog> workoutLogs){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                workoutLogDAO.insertAll(workoutLogs);
            }
        });
    }

    public void deleteAll(){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                workoutLogDAO.deleteAll();
            }
        });
    }
}
