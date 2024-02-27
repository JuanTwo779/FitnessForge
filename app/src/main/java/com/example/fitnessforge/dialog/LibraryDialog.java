package com.example.fitnessforge.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitnessforge.databinding.DialogLibraryBinding;
import com.example.fitnessforge.model.Exercise;

public class LibraryDialog extends AppCompatDialogFragment {

    private DialogLibraryBinding binding;

    private Exercise exercise;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogLibraryBinding.inflate(inflater, container, false);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //populate the dialog given the passed in exercise
        binding.exerciseName.setText(exercise.getName());
        binding.equipment.setText(exercise.getEquipment());
        binding.muscleGroup.setText(exercise.getMuscle());
        binding.instructions.setText(exercise.getInstructions());


        return binding.getRoot();
    }

    public void setExercise(Exercise exercise){
        this.exercise = exercise;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
