package com.example.fitnessforge.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.fitnessforge.dao.DayPlanDAO;
import com.example.fitnessforge.dao.ExerciseEntityDao;
import com.example.fitnessforge.dao.UserDAO;
import com.example.fitnessforge.dao.WorkoutDAO;
import com.example.fitnessforge.dao.WorkoutLogDAO;
import com.example.fitnessforge.entity.DayPlan;
import com.example.fitnessforge.entity.ExerciseEntity;
import com.example.fitnessforge.entity.User;
import com.example.fitnessforge.entity.Workout;
import com.example.fitnessforge.entity.WorkoutLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, ExerciseEntity.class, Workout.class, DayPlan.class, WorkoutLog.class}, version = 7, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class FitnessForgeDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract ExerciseEntityDao exerciseEntityDao();

    public abstract WorkoutDAO workoutDAO();

    public abstract DayPlanDAO dayPlanDAO();

    public abstract WorkoutLogDAO workoutLogDAO();

    private static FitnessForgeDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized FitnessForgeDatabase getInstance(final Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    FitnessForgeDatabase.class, "FitnessForgeDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
