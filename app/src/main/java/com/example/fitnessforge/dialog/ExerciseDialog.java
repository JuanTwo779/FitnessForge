package com.example.fitnessforge.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitnessforge.databinding.DialogExerciseBinding;
import com.example.fitnessforge.databinding.DialogLibraryBinding;
import com.example.fitnessforge.entity.ExerciseEntity;
import com.example.fitnessforge.fragment.ExerciseFragment;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDialog extends AppCompatDialogFragment {

    private DialogExerciseBinding binding;
    private ExerciseEntity exerciseEntity;

    private ExerciseFragment exerciseFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogExerciseBinding.inflate(inflater, container,false);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        //populates the exercise dialog
        if (exerciseEntity != null){
            binding.exerciseName.setText(exerciseEntity.getName());
            binding.sets.setText(String.valueOf(exerciseEntity.getSets()));
            binding.reps.setText(String.valueOf(exerciseEntity.getReps()));
        }


        displaySpinner();

        //creates or updates exercise
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String exerciseName = binding.exerciseName.getText().toString();
                String selectedMuscle = (String) binding.muscleSpinner.getSelectedItem();
                String exerciseSets = binding.sets.getText().toString();
                String exerciseReps = binding.reps.getText().toString();

                if (exerciseName.isEmpty() || exerciseSets.isEmpty() || exerciseReps.isEmpty()){
                    toastMsg("Empty fields");
                }

                if (exerciseEntity != null){ //update
                    if (selectedMuscle.equals("Select a muscle group")){
                        selectedMuscle = exerciseEntity.getMuscle();
                    }

                    exerciseFragment.updateExercise(
                            exerciseEntity.getId(),
                            exerciseName,
                            selectedMuscle,
                            exerciseSets,
                            exerciseReps
                    );
                    dismiss();
                } else { //insert
                    if (selectedMuscle.equals("Select a muscle group")){
                        toastMsg("Select a muscle group");
                    }
                    exerciseFragment.insertExercise(
                            exerciseName,
                            selectedMuscle,
                            exerciseSets,
                            exerciseReps
                    );
                    dismiss();
                }

            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return binding.getRoot();
    }

    private void toastMsg(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void displaySpinner(){
        Spinner muscleSpinner = binding.muscleSpinner;
        List<String> list = new ArrayList<>();
        list.add("Select a muscle group");
        list.add("abdominals");
        list.add("abductors");
        list.add("adductors");
        list.add("biceps");
        list.add("calves");
        list.add("chest");
        list.add("forearms");
        list.add("glutes");
        list.add("hamstrings");
        list.add("lats");
        list.add("lower_back");
        list.add("middle_back");
        list.add("neck");
        list.add("quadriceps");
        list.add("traps");
        list.add("triceps");

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>
                (requireContext(), android.R.layout.simple_spinner_item, list);
        muscleSpinner.setAdapter(spinnerAdapter);

    }


    public void setExerciseEntity(ExerciseEntity exerciseEntity){
        this.exerciseEntity = exerciseEntity;
    }

    public void setExerciseFragment(ExerciseFragment fragment){
        exerciseFragment = fragment;
    }
}
