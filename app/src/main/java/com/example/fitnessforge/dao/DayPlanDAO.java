package com.example.fitnessforge.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessforge.entity.DayPlan;

import java.util.List;

@Dao
public interface DayPlanDAO {

    //User can't create day plan -> used to make seven empty day plans
    @Insert
    void insert(DayPlan dayPlan);

    //Users update the empty day plans
    @Update
    void updateDayPlan(DayPlan dayPlan);

    @Query("SELECT * FROM dayplan WHERE id =:dayPlanId LIMIT 1")
    DayPlan findById(Long dayPlanId);

    @Query("SELECT * FROM dayplan WHERE day =:day AND uid =:uid LIMIT 1")
    DayPlan findByDay(String day, String uid);

    @Query("SELECT * FROM dayplan WHERE day =:day AND uid =:uid LIMIT 1")
    LiveData<DayPlan> getDayPlanByDay(String day, String uid);


}
