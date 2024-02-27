package com.example.fitnessforge.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Exercise {

    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("muscle")
    private String muscle;
    @SerializedName("equipment")
    private String equipment;
    @SerializedName("difficulty")
    private String difficulty;
    @SerializedName("instructions")
    private String instructions;

    public Exercise() {
    }

    public Exercise(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public Exercise(String name, String type, String muscle, String equipment, String difficulty, String instructions) {
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    //sample list
    public static List<Exercise> sampleList() {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Incline Hammer Curls", "beginner"));
        exercises.add(new Exercise("Barbell Curl", "intermediate"));
        exercises.add(new Exercise("Zottman Curl", "intermediate"));
        exercises.add(new Exercise("Concentration Curl", "intermediate"));
        exercises.add(new Exercise("Wide-grip barbell Curl", "intermediate"));
        exercises.add(new Exercise("Hammer Curl", "intermediate"));
        exercises.add(new Exercise("EZ-Bar Curl", "intermediate"));
        return exercises;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", muscle='" + muscle + '\'' +
                ", equipment='" + equipment + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }

}
