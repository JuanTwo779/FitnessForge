package com.example.fitnessforge.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessforge.entity.ExerciseEntity;
import com.example.fitnessforge.repository.ExerciseEntityRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ExerciseEntityViewModel extends AndroidViewModel {
    private ExerciseEntityRepository repository;
    public ExerciseEntityViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseEntityRepository(application);
    }

    public LiveData<List<ExerciseEntity>> getExercisesById(String uid){
        return repository.getExercisesById(uid);
    }

    public void insert(ExerciseEntity exerciseEntity){
        repository.insert(exerciseEntity);
    }

    public void delete(ExerciseEntity exerciseEntity){
        repository.delete(exerciseEntity);
    }

    public void update(ExerciseEntity exerciseEntity){
        repository.updateExercise(exerciseEntity);
    }

    public CompletableFuture<ExerciseEntity> findByIdFuture(final long exerciseId){
        return repository.findByIdFuture(exerciseId);
    }

    public CompletableFuture<Long> getExerciseIdByNameAndUid(String name, String uid){
        return repository.getExerciseIdByNameAndUid(name, uid);
    }


}
