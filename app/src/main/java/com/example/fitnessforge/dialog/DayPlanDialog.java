package com.example.fitnessforge.dialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitnessforge.databinding.DialogDayplanBinding;
import com.example.fitnessforge.entity.DayPlan;
import com.example.fitnessforge.entity.ExerciseEntity;
import com.example.fitnessforge.entity.Workout;
import com.example.fitnessforge.fragment.PlannerFragment;

public class DayPlanDialog extends AppCompatDialogFragment {
    private DialogDayplanBinding binding;
    private DayPlan dayPlan;
    private PlannerFragment plannerFragment;
    private Handler handler;
    private long startTime;
    private boolean isTimerRunning = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = DialogDayplanBinding.inflate(inflater, container,false);
         getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

         populateDialog();
         handler = new Handler(Looper.getMainLooper());

         //Start timer button
         binding.startButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startTimer();
                 binding.finishButton.setClickable(true);
                 binding.startButton.setClickable(false);

             }
         });
         //Finish timer button + creates new log from planner fragment
         binding.finishButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 stopTimer();
                 binding.finishButton.setClickable(false);
                 binding.startButton.setClickable(true);

                 long currentTime = System.currentTimeMillis();
                 long elapsedTime = currentTime - startTime;
                 long durationSeconds = elapsedTime / 1000;

//                 binding.day.setText(durationSeconds+"");
                 plannerFragment.insertLog(durationSeconds);
                 dismiss();
             }
         });

        return binding.getRoot();
    }

    /**
     * Populates the dialog with the passed in DayPlan
     */
    private void populateDialog() {
        if (dayPlan != null){
            binding.day.setText(dayPlan.getDay());

            //get workout ID
            Long workoutId = dayPlan.getWorkoutId();

            //get workout
            Workout workout = plannerFragment.getWorkout(workoutId);

            //populate dialog
            if (workout.getExerciseId1() != null){
                ExerciseEntity exercise = plannerFragment.getExercise(workout.getExerciseId1());
                binding.exercise1.setText(exercise.getName());
                binding.set1.setText(exercise.getSets() + "");
                binding.rep1.setText(exercise.getReps()+ "");
            }
            if (workout.getExerciseId2() != null){
                ExerciseEntity exercise = plannerFragment.getExercise(workout.getExerciseId2());
                binding.exercise2.setText(exercise.getName());
                binding.set2.setText(exercise.getSets()+ "");
                binding.rep2.setText(exercise.getReps()+ "");
            }
            if (workout.getExerciseId3() != null){
                ExerciseEntity exercise = plannerFragment.getExercise(workout.getExerciseId3());
                binding.exercise3.setText(exercise.getName());
                binding.set3.setText(exercise.getSets()+ "");
                binding.rep3.setText(exercise.getReps()+ "");
            }
            if (workout.getExerciseId4() != null){
                ExerciseEntity exercise = plannerFragment.getExercise(workout.getExerciseId4());
                binding.exercise4.setText(exercise.getName());
                binding.set4.setText(exercise.getSets()+ "");
                binding.rep4.setText(exercise.getReps()+ "");
            }
            if (workout.getExerciseId5() != null){
                ExerciseEntity exercise = plannerFragment.getExercise(workout.getExerciseId5());
                binding.exercise5.setText(exercise.getName());
                binding.set5.setText(exercise.getSets()+ "");
                binding.rep5.setText(exercise.getReps()+ "");
            }
            if (workout.getExerciseId6() != null){
                ExerciseEntity exercise = plannerFragment.getExercise(workout.getExerciseId6());
                binding.exercise6.setText(exercise.getName());
                binding.set6.setText(exercise.getSets()+ "");
                binding.rep6.setText(exercise.getReps()+ "");
            }
            if (workout.getExerciseId7() != null){
                ExerciseEntity exercise = plannerFragment.getExercise(workout.getExerciseId7());
                binding.exercise7.setText(exercise.getName());
                binding.set7.setText(exercise.getSets()+ "");
                binding.rep7.setText(exercise.getReps()+ "");
            }
        }
    }

    //Method to start the timer
    private void startTimer() {
        startTime = System.currentTimeMillis();
        isTimerRunning = true;
        handler.postDelayed(timerRunnable, 0);
    }

    //method to stop the timer
    private void stopTimer() {
        isTimerRunning = false;
        handler.removeCallbacks(timerRunnable);
    }

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isTimerRunning) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                updateTimerDisplay(elapsedTime);
                handler.postDelayed(this, 1000);
            }
        }
    };

    private void updateTimerDisplay(long elapsedTime) {
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        seconds %= 60;

        String timeText = String.format("%02d:%02d", minutes, seconds);
        binding.timer.setText(timeText);
    }

    public void setDayPlan(DayPlan dayPlan) {
        this.dayPlan = dayPlan;
    }

    public void setPlannerFragment(PlannerFragment plannerFragment) {
        this.plannerFragment = plannerFragment;
    }
}
