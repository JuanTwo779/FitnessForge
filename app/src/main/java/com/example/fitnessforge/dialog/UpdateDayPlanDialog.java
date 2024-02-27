package com.example.fitnessforge.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.Observer;

import com.example.fitnessforge.databinding.DialogUpdatePlanBinding;
import com.example.fitnessforge.entity.Workout;
import com.example.fitnessforge.fragment.PlannerFragment;

import java.util.ArrayList;
import java.util.List;

public class UpdateDayPlanDialog extends AppCompatDialogFragment {
    private DialogUpdatePlanBinding binding;

    private String day;
    private Workout workout;
    private String workoutName;
    private PlannerFragment plannerFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogUpdatePlanBinding.inflate(inflater, container, false);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Populates the dialog
        populateDialog();
        populateSpinner();

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //updates the dayPlan with the user selected workout
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.workoutSpinner.getSelectedItem().toString().equals("Select")){
                    toastMsg("Select a workout");
                } else {
                    plannerFragment.updateDayPlan(day, binding.workoutSpinner.getSelectedItem().toString());
                    dismiss();
                }
            }
        });


        return binding.getRoot();
    }

    //populates spinner with the user's created workouts
    private void populateSpinner(){
        plannerFragment.getWorkouts().observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workoutList) {
                List<String> workoutNames = new ArrayList<>();
                workoutNames.add("Select");
                for (Workout workout : workoutList){
                    workoutNames.add(workout.getName());
                }

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, workoutNames);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.workoutSpinner.setAdapter(spinnerAdapter);
            }
        });
    }

    private void populateDialog(){
        if (day != null) binding.day.setText(day);
    }
    private void toastMsg(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void setWorkoutName(String name){
        workoutName = name;
    }
    public void setDay (String day){
        this.day = day;
    }

    public void setWorkout(Workout workout){
        this.workout = workout;
    }

    public void setPlannerFragment(PlannerFragment plannerFragment){
        this.plannerFragment = plannerFragment;
    }


}
