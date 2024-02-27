package com.example.fitnessforge.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Workout {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String uid; //Firebase uid

    @NonNull
    public String name;
    @Nullable
    public Long exerciseId1;
    @Nullable
    public Long exerciseId2;
    @Nullable
    public Long exerciseId3;
    @Nullable
    public Long exerciseId4;
    @Nullable
    public Long exerciseId5;
    @Nullable
    public Long exerciseId6;
    @Nullable
    public Long exerciseId7;

    public Workout(){}

    public Workout(@NonNull String name, String uid, @Nullable Long exerciseId1, @Nullable Long exerciseId2,
                   @Nullable Long exerciseId3, @Nullable Long exerciseId4,
                   @Nullable Long exerciseId5, @Nullable Long exerciseId6,
                   @Nullable Long exerciseId7) {
        this.name = name;
        this.uid = uid;
        this.exerciseId1 = exerciseId1;
        this.exerciseId2 = exerciseId2;
        this.exerciseId3 = exerciseId3;
        this.exerciseId4 = exerciseId4;
        this.exerciseId5 = exerciseId5;
        this.exerciseId6 = exerciseId6;
        this.exerciseId7 = exerciseId7;
    }
    public static List<Workout> sampleWorkoutList(){
        List<Workout> list = new ArrayList<>();
        list.add(new Workout("Workout 1", "sdflkghasldkf", 2L, 9L, null, null, null, null, null));
        list.add(new Workout("Workout 2", "sdflkghasldkf",2L, 9L, 23L, 40L, null, null, null));
        list.add(new Workout("Workout 3", "sdflkghasldkf",1L, 19L, 46L, 409L, 50L, 20L, 78L));
        return list;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Nullable
    public Long getExerciseId1() {
        return exerciseId1;
    }

    public void setExerciseId1(@Nullable Long exerciseId1) {
        this.exerciseId1 = exerciseId1;
    }

    @Nullable
    public Long getExerciseId2() {
        return exerciseId2;
    }

    public void setExerciseId2(@Nullable Long exerciseId2) {
        this.exerciseId2 = exerciseId2;
    }

    @Nullable
    public Long getExerciseId3() {
        return exerciseId3;
    }

    public void setExerciseId3(@Nullable Long exerciseId3) {
        this.exerciseId3 = exerciseId3;
    }

    @Nullable
    public Long getExerciseId4() {
        return exerciseId4;
    }

    public void setExerciseId4(@Nullable Long exerciseId4) {
        this.exerciseId4 = exerciseId4;
    }

    @Nullable
    public Long getExerciseId5() {
        return exerciseId5;
    }

    public void setExerciseId5(@Nullable Long exerciseId5) {
        this.exerciseId5 = exerciseId5;
    }

    @Nullable
    public Long getExerciseId6() {
        return exerciseId6;
    }

    public void setExerciseId6(@Nullable Long exerciseId6) {
        this.exerciseId6 = exerciseId6;
    }

    @Nullable
    public Long getExerciseId7() {
        return exerciseId7;
    }

    public void setExerciseId7(@Nullable Long exerciseId7) {
        this.exerciseId7 = exerciseId7;
    }

    @NonNull
    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", exerciseId1=" + exerciseId1 +
                ", exerciseId2=" + exerciseId2 +
                ", exerciseId3=" + exerciseId3 +
                ", exerciseId4=" + exerciseId4 +
                ", exerciseId5=" + exerciseId5 +
                ", exerciseId6=" + exerciseId6 +
                ", exerciseId7=" + exerciseId7 +
                '}';
    }
}
