package com.example.fitnessforge.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.Observer;

import com.example.fitnessforge.databinding.DialogWorkoutBinding;
import com.example.fitnessforge.entity.ExerciseEntity;
import com.example.fitnessforge.entity.Workout;
import com.example.fitnessforge.fragment.WorkoutFragment;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDialog extends AppCompatDialogFragment {
    private DialogWorkoutBinding binding;
    private Workout workout;
    private WorkoutFragment workoutFragment;
    private Long ex1;
    private Long ex2;
    private Long ex3;
    private Long ex4;
    private Long ex5;
    private Long ex6;
    private Long ex7;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogWorkoutBinding.inflate(inflater, container, false);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Populate dialog
        if (workout != null) {
//            Log.e("Workout passed into dialog", workout.toString());
            populateDialog();
        }
        populateSpinners();

        //creates or updates the workout
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.workoutName.getText().toString();

                if (name.isEmpty()){
                    toastMsg("Enter a name");
                } else {
                    if (workout != null){ //update
                        workoutFragment.updateWorkout(workout.getId(),name,ex1,ex2,ex3,ex4,ex5,ex6,ex7);
                        dismiss();
                    } else { //insert
                        workoutFragment.insertWorkout(name,ex1,ex2,ex3,ex4,ex5,ex6,ex7);
//                        Log.i("Workout", "Workout created");
                        dismiss();
                    }
                }

            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //selecting exercises in spinners populates the related textViews
        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerSelection = adapterView.getItemAtPosition(i).toString();
                Long exerciseId = workoutFragment.getExerciseIdByNameUid(spinnerSelection);
                if (exerciseId != null && !spinnerSelection.equals("Select")){
//                    Log.i("ExerciseID", "Exercise ID");
//                    Log.i("ExerciseID", String.valueOf(exerciseId));
                    ex1 = exerciseId;
                    ExerciseEntity exercise = getExercise(exerciseId);
                    if (exercise != null){
//                        Log.i("Exercise", "Exercise");
                        binding.exercise1.setText(exercise.getName());
                        binding.set1.setText(exercise.getSets() + "");
                        binding.rep1.setText(exercise.getReps() + "");
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Selection", "Null");
            }
        });
        binding.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerSelection = adapterView.getItemAtPosition(i).toString();
                Long exerciseId = workoutFragment.getExerciseIdByNameUid(spinnerSelection);
                if (exerciseId != null && !spinnerSelection.equals("Select")){
//                    Log.i("ExerciseID", "Exercise ID");
//                    Log.i("ExerciseID", String.valueOf(exerciseId));
                    ex2 = exerciseId;
                    ExerciseEntity exercise = getExercise(exerciseId);
                    if (exercise != null){
//                        Log.i("Exercise", "Exercise");
                        binding.exercise2.setText(exercise.getName());
                        binding.set2.setText(exercise.getSets() + "");
                        binding.rep2.setText(exercise.getReps() + "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Selection", "Null");
            }
        });
        binding.spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerSelection = adapterView.getItemAtPosition(i).toString();
                Long exerciseId = workoutFragment.getExerciseIdByNameUid(spinnerSelection);
                if (exerciseId != null && !spinnerSelection.equals("Select")){
//                    Log.i("ExerciseID", "Exercise ID");
//                    Log.i("ExerciseID", String.valueOf(exerciseId));
                    ex3 = exerciseId;
                    ExerciseEntity exercise = getExercise(exerciseId);
                    if (exercise != null){
//                        Log.i("Exercise", "Exercise");
                        binding.exercise3.setText(exercise.getName());
                        binding.set3.setText(exercise.getSets() + "");
                        binding.rep3.setText(exercise.getReps() + "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Selection", "Null");
            }
        });
        binding.spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerSelection = adapterView.getItemAtPosition(i).toString();
                Long exerciseId = workoutFragment.getExerciseIdByNameUid(spinnerSelection);
                if (exerciseId != null && !spinnerSelection.equals("Select")){
//                    Log.i("ExerciseID", "Exercise ID");
//                    Log.i("ExerciseID", String.valueOf(exerciseId));
                    ex4 = exerciseId;
                    ExerciseEntity exercise = getExercise(exerciseId);
                    if (exercise != null){
//                        Log.i("Exercise", "Exercise");
                        binding.exercise4.setText(exercise.getName());
                        binding.set4.setText(exercise.getSets() + "");
                        binding.rep4.setText(exercise.getReps() + "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Selection", "Null");
            }
        });
        binding.spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerSelection = adapterView.getItemAtPosition(i).toString();
                Long exerciseId = workoutFragment.getExerciseIdByNameUid(spinnerSelection);
                if (exerciseId != null && !spinnerSelection.equals("Select")){
//                    Log.i("ExerciseID", "Exercise ID");
//                    Log.i("ExerciseID", String.valueOf(exerciseId));
                    ex5 = exerciseId;
                    ExerciseEntity exercise = getExercise(exerciseId);
                    if (exercise != null){
//                        Log.i("Exercise", "Exercise");
                        binding.exercise5.setText(exercise.getName());
                        binding.set5.setText(exercise.getSets() + "");
                        binding.rep5.setText(exercise.getReps() + "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Selection", "Null");
            }
        });
        binding.spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerSelection = adapterView.getItemAtPosition(i).toString();
                Long exerciseId = workoutFragment.getExerciseIdByNameUid(spinnerSelection);
                if (exerciseId != null && !spinnerSelection.equals("Select")){
//                    Log.i("ExerciseID", "Exercise ID");
//                    Log.i("ExerciseID", String.valueOf(exerciseId));
                    ex6 = exerciseId;
                    ExerciseEntity exercise = getExercise(exerciseId);
                    if (exercise != null){
//                        Log.i("Exercise", "Exercise");
                        binding.exercise6.setText(exercise.getName());
                        binding.set6.setText(exercise.getSets() + "");
                        binding.rep6.setText(exercise.getReps() + "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Selection", "Null");
            }
        });
        binding.spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerSelection = adapterView.getItemAtPosition(i).toString();
                Long exerciseId = workoutFragment.getExerciseIdByNameUid(spinnerSelection);
                if (exerciseId != null && !spinnerSelection.equals("Select")){
//                    Log.i("ExerciseID", "Exercise ID");
//                    Log.i("ExerciseID", String.valueOf(exerciseId));
                    ex7 = exerciseId;
                    ExerciseEntity exercise = getExercise(exerciseId);
                    if (exercise != null){
//                        Log.i("Exercise", "Exercise");
                        binding.exercise7.setText(exercise.getName());
                        binding.set7.setText(exercise.getSets() + "");
                        binding.rep7.setText(exercise.getReps() + "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Selection", "Null");
            }
        });

        return binding.getRoot();
    }

    //populates all the spinners with the user's created exercises
    private void populateSpinners() {
        workoutFragment.getExercises().observe(getViewLifecycleOwner(), new Observer<List<ExerciseEntity>>() {
            @Override
            public void onChanged(List<ExerciseEntity> exerciseEntityList) {
                List<String> exerciseNames = new ArrayList<>();
                exerciseNames.add("Select");
                for (ExerciseEntity exerciseEntity : exerciseEntityList){
                    exerciseNames.add(exerciseEntity.getName());
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, exerciseNames);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinner1.setAdapter(spinnerAdapter);
                binding.spinner2.setAdapter(spinnerAdapter);
                binding.spinner3.setAdapter(spinnerAdapter);
                binding.spinner4.setAdapter(spinnerAdapter);
                binding.spinner5.setAdapter(spinnerAdapter);
                binding.spinner6.setAdapter(spinnerAdapter);
                binding.spinner7.setAdapter(spinnerAdapter);
            }
        });
    }

    private void toastMsg(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //populates the dialog using the passed in workout -> for updating the workout
    private void populateDialog() {
        binding.workoutName.setText(workout.getName());

        //exercise 1
        if (workout.getExerciseId1() != null){
            ExerciseEntity ex1 = getExercise(workout.getExerciseId1());
            if (ex1 != null){
                binding.exercise1.setText(ex1.getName());
                binding.set1.setText(ex1.getSets()+"");
                binding.rep1.setText(ex1.getReps()+"");
                this.ex1 = ex1.getId();
            }
        }

        //exercise 2
        if (workout.getExerciseId2() != null){
            ExerciseEntity ex2 = getExercise(workout.getExerciseId2());
            if (ex2 != null){
                binding.exercise2.setText(ex2.getName());
                binding.set2.setText(ex2.getSets()+"");
                binding.rep2.setText(ex2.getReps()+"");
                this.ex2 = ex2.getId();
            }
        }

        //exercise 3
        if (workout.getExerciseId3() != null) {
            ExerciseEntity ex3 = getExercise(workout.getExerciseId3());
            if (ex3 != null){
                binding.exercise3.setText(ex3.getName());
                binding.set3.setText(ex3.getSets()+"");
                binding.rep3.setText(ex3.getReps()+"");
                this.ex3 = ex3.getId();
            }
        }

        //exercise 4
        if (workout.getExerciseId4() != null) {
            ExerciseEntity ex4 = getExercise(workout.getExerciseId4());
            if (ex4 !=null){
                binding.exercise4.setText(ex4.getName());
                binding.set4.setText(ex4.getSets()+"");
                binding.rep4.setText(ex4.getReps()+"");
                this.ex4 = ex4.getId();
            }
        }

        //exercise 5
        if (workout.getExerciseId5() != null) {
            ExerciseEntity ex5 = getExercise(workout.getExerciseId5());
            if (ex5!=null){
                binding.exercise5.setText(ex5.getName());
                binding.set5.setText(ex5.getSets()+"");
                binding.rep5.setText(ex5.getReps()+"");
                this.ex5 = ex5.getId();
            }
        }

        //exercise 6
        if (workout.getExerciseId6() != null) {
            ExerciseEntity ex6 = getExercise(workout.getExerciseId6());
            if (ex6!=null){
                binding.exercise6.setText(ex6.getName());
                binding.set6.setText(ex6.getSets()+"");
                binding.rep6.setText(ex6.getReps()+"");
                this.ex6 = ex6.getId();
            }
        }

        //exercise 7
        if (workout.getExerciseId7() != null) {
            ExerciseEntity ex7 = getExercise(workout.getExerciseId7());
            if (ex7 != null){
                binding.exercise7.setText(ex7.getName());
                binding.set7.setText(ex7.getSets()+"");
                binding.rep7.setText(ex7.getReps()+"");
                this.ex7 = ex7.getId();
            }
        }

    }

    private ExerciseEntity getExercise(long exerciseId){
        return workoutFragment.getExercise(exerciseId);
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public void setWorkoutFragment(WorkoutFragment workoutFragment) {
        this.workoutFragment = workoutFragment;
    }
}
