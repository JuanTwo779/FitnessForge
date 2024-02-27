package com.example.fitnessforge.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "exercise")
public class ExerciseEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String uid; //Firebase uid

    public String name;
    public String muscle;
    public int sets;
    public int reps;

    public ExerciseEntity(String uid, String name, String muscle, int sets, int reps) {
        this.uid = uid;
        this.name = name;
        this.muscle = muscle;
        this.sets = sets;
        this.reps = reps;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public int getSets() {
        return sets;
    }

    public String getStringSets(){
        return "Sets: " + getSets();
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public String getStringReps(){
        return "Reps: " + getReps();
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public static List<ExerciseEntity> sampleExerciseList() {
        List<ExerciseEntity> exerciseEntityList = new ArrayList<>();
        exerciseEntityList.add(new ExerciseEntity("Barbell curl", "Biceps", "ABCDEFG",0, 0));
        exerciseEntityList.add(new ExerciseEntity("Pull ups", "Biceps", "ABCDEFG",0, 0));
        return exerciseEntityList;
    }
}
