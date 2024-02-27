package com.example.fitnessforge.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.fitnessforge.dao.ExerciseEntityDao;
import com.example.fitnessforge.database.FitnessForgeDatabase;
import com.example.fitnessforge.entity.ExerciseEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ExerciseEntityRepository {
    private ExerciseEntityDao exerciseEntityDao;

    public ExerciseEntityRepository(Application application){
        FitnessForgeDatabase db = FitnessForgeDatabase.getInstance(application);
        exerciseEntityDao = db.exerciseEntityDao();
    }

    public LiveData<List<ExerciseEntity>> getExercisesById(String uid){
        return exerciseEntityDao.getAllByUid(uid);
    }

    public void insert(final ExerciseEntity exerciseEntity){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseEntityDao.insert(exerciseEntity);
            }
        });
    }

    public void delete(final ExerciseEntity exerciseEntity){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseEntityDao.delete(exerciseEntity);
            }
        });
    }

    public void updateExercise(final ExerciseEntity exerciseEntity){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseEntityDao.updateExercise(exerciseEntity);
            }
        });
    }

    public CompletableFuture<ExerciseEntity> findByIdFuture(final long exerciseId){
        return CompletableFuture.supplyAsync(new Supplier<ExerciseEntity>() {
            @Override
            public ExerciseEntity get() {
                return exerciseEntityDao.findById(exerciseId);
            }
        }, FitnessForgeDatabase.databaseWriteExecutor);
    }

    public CompletableFuture<Long> getExerciseIdByNameAndUid(String name, String uid){
        return CompletableFuture.supplyAsync(() -> exerciseEntityDao.getExerciseIdByNameAndUid(name, uid), FitnessForgeDatabase.databaseWriteExecutor);
    }

}
