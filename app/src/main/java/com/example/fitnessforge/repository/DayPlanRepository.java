package com.example.fitnessforge.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.fitnessforge.dao.DayPlanDAO;
import com.example.fitnessforge.database.FitnessForgeDatabase;
import com.example.fitnessforge.entity.DayPlan;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class DayPlanRepository {
    private DayPlanDAO planDAO;

    public DayPlanRepository(Application application){
        FitnessForgeDatabase db = FitnessForgeDatabase.getInstance(application);
        planDAO = db.dayPlanDAO();
    }

    public LiveData<DayPlan> getDayPlanByDay(String uid, String day){
        return planDAO.getDayPlanByDay(day, uid);
    }

    public void insert(final DayPlan dayPlan){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                planDAO.insert(dayPlan);
            }
        });
    }

    public void updateDayPlan(final DayPlan dayPlan){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                planDAO.updateDayPlan(dayPlan);
            }
        });
    }

    public CompletableFuture<DayPlan> findByDayFuture(final String day, final String uid){
        return CompletableFuture.supplyAsync(new Supplier<DayPlan>() {
            @Override
            public DayPlan get() {
                return planDAO.findByDay(day, uid);
            }
        }, FitnessForgeDatabase.databaseWriteExecutor);
    }

    public CompletableFuture<DayPlan> findByIdFuture(final Long dayPlanId){
        return CompletableFuture.supplyAsync(new Supplier<DayPlan>() {
            @Override
            public DayPlan get() {
                return planDAO.findById(dayPlanId);
            }
        }, FitnessForgeDatabase.databaseWriteExecutor);
    }


}
