package com.example.fitnessforge.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessforge.entity.DayPlan;
import com.example.fitnessforge.repository.DayPlanRepository;

import java.util.concurrent.CompletableFuture;

public class DayPlanViewModel extends AndroidViewModel {
    private DayPlanRepository repository;
    public DayPlanViewModel(@NonNull Application application) {
        super(application);
        repository = new DayPlanRepository(application);
    }

    public LiveData<DayPlan> getDayPlanByDayAndUid(String uid, String day){
        return repository.getDayPlanByDay(uid, day);
    }

    public void insert(DayPlan dayPlan){repository.insert(dayPlan);}

    public void update(DayPlan dayPlan){repository.updateDayPlan(dayPlan);}

    public CompletableFuture<DayPlan> findByIdFuture(final Long dayPlanId){
        return repository.findByIdFuture(dayPlanId);
    }

    public CompletableFuture<DayPlan> findByDayFuture(final String day, final String uid){
        return repository.findByDayFuture(day, uid);
    }
}
