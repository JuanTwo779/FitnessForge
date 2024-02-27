package com.example.fitnessforge.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessforge.R;
import com.example.fitnessforge.databinding.FragmentPlannerBinding;
import com.example.fitnessforge.dialog.DayPlanDialog;
import com.example.fitnessforge.dialog.UpdateDayPlanDialog;
import com.example.fitnessforge.entity.DayPlan;
import com.example.fitnessforge.entity.ExerciseEntity;
import com.example.fitnessforge.entity.Workout;
import com.example.fitnessforge.entity.WorkoutLog;
import com.example.fitnessforge.viewmodel.DayPlanViewModel;
import com.example.fitnessforge.viewmodel.ExerciseEntityViewModel;
import com.example.fitnessforge.viewmodel.WorkoutLogViewModel;
import com.example.fitnessforge.viewmodel.WorkoutViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PlannerFragment extends Fragment {
    private FragmentPlannerBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DayPlanViewModel dayPlanViewModel;
    private WorkoutViewModel workoutViewModel;
    private ExerciseEntityViewModel exerciseEntityViewModel;
    private WorkoutLogViewModel workoutLogViewModel;
    private DatabaseReference rootDatabaseRef;

    private final String realTimePath = "https://fitnessforge-6feba-default-rtdb.asia-southeast1.firebasedatabase.app/";

    public PlannerFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentPlannerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //Firebase
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        rootDatabaseRef = FirebaseDatabase.getInstance(realTimePath).getReference();

        //ViewModels
        dayPlanViewModel = new ViewModelProvider(this).get(DayPlanViewModel.class);
        workoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        exerciseEntityViewModel = new ViewModelProvider(this).get(ExerciseEntityViewModel.class);
        workoutLogViewModel = new ViewModelProvider(this).get(WorkoutLogViewModel.class);

        //populates the dialog
        highlightCurrentCard();
        populatePlanner();

        //Calender
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        //OnClickListeners for each day - play or edit day plan
        //monday
        binding.mondayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDayPlanDialog("Monday", view);
            }
        });
        binding.mondayPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayOfWeek == 2){
                    playDayPlanDialog("Monday", view);
                }
            }
        });
        //tuesday
        binding.tuesdayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDayPlanDialog("Tuesday", view);
            }
        });
        binding.tuesdayPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayOfWeek == 3){
                    playDayPlanDialog("Tuesday", view);
                }
            }
        });
        //Wednesday
        binding.wednesdayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDayPlanDialog("Wednesday", view);
            }
        });
        binding.wednesdayPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayOfWeek == 4){
                    playDayPlanDialog("Wednesday", view);
                }
            }
        });
        //Thursday
        binding.thursdayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDayPlanDialog("Thursday", view);
            }
        });
        binding.thursdayPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayOfWeek == 5){
                    playDayPlanDialog("Thursday", view);
                }
            }
        });
        //Friday
        binding.fridayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDayPlanDialog("Friday", view);
            }
        });
        binding.fridayPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayOfWeek == 6){
                    playDayPlanDialog("Friday", view);
                }
            }
        });
        //Saturday
        binding.saturdayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDayPlanDialog("Saturday", view);
            }
        });
        binding.saturdayPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayOfWeek == 7){
                    playDayPlanDialog("Saturday", view);
                }
            }
        });
        //Sunday
        binding.sundayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDayPlanDialog("Sunday", view);
            }
        });
        binding.sundayPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayOfWeek == 1){
                    playDayPlanDialog("Sunday", view);
                }
            }
        });

        //test methods
//        workoutLogViewModel.getWorkoutLogsForCurrentMonth(currentUser.getUid()).observe(getViewLifecycleOwner(), new Observer<List<WorkoutLog>>() {
//            @Override
//            public void onChanged(List<WorkoutLog> workoutLogs) {
//                for (WorkoutLog logs : workoutLogs){
//                    Log.e("getWorkoutLogsForCurrentMonth", logs.toString());
//                }
//            }
//        });

        return view;
    }

    //Inserts workout log to Room DB and Firebase DB
    public void insertLog(Long duration){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        WorkoutLog workoutLog = new WorkoutLog(currentUser.getUid(), currentDate, duration);
        workoutLogViewModel.insert(workoutLog);
//        Log.e("insertLog", workoutLog.toString());

        //removes any local logs that are not dated to the current month
        workoutLogViewModel.deleteWorkoutLogsNotInCurrentMonth(currentUser.getUid());

        //Firebase insertion
        DatabaseReference logsRef = rootDatabaseRef.child("workoutLogs");
        Map<String, Object> logValues = workoutLog.toMap();
        logsRef.push().setValue(logValues);
    }

    public ExerciseEntity getExercise(long exerciseId) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CompletableFuture<ExerciseEntity> completableFuture = exerciseEntityViewModel.findByIdFuture(exerciseId);
            if (completableFuture != null){
                try {
                    return completableFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //Opens play day plan dialog given a day
    private void playDayPlanDialog(String day, View view) {
        DayPlanDialog dialog = new DayPlanDialog();
        dialog.setPlannerFragment(PlannerFragment.this);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CompletableFuture<DayPlan> completableFuture = dayPlanViewModel.findByDayFuture(day, currentUser.getUid());
            completableFuture.thenAccept(dayPlan -> {
                if (dayPlan != null){
                    Log.e("playDayPlanDialog", dayPlan.toString());
                    dialog.setDayPlan(dayPlan);

                    if (dayPlan.getWorkoutId() != null){
                        dialog.show(((AppCompatActivity) view.getContext())
                                .getSupportFragmentManager(), "Play DayPlan Dialog Tag");
                    }
                }

            });
        }



    }

    //Updates day plan with workout
    public void updateDayPlan(String day, String workoutName){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CompletableFuture<DayPlan> completableFuture = dayPlanViewModel.findByDayFuture(day, currentUser.getUid());
            completableFuture.thenAccept(dayPlan -> {
                if (dayPlan != null){
                    Log.e("updateDayPlan", dayPlan.toString());
                    getWorkoutIdByName(workoutName, new Consumer<Long>() {
                        @Override
                        public void accept(Long workoutId) {
                            if (workoutId != null) {
                                dayPlan.setWorkoutId(workoutId);
                                Log.e("updateDayPlan", dayPlan.toString());
                                dayPlanViewModel.update(dayPlan);
                            } else {
                                Log.e("updateDayPlan", "Workout ID not found");
                            }
                        }
                    });
                }
            });
        }
    }

    //Opens update day plan dialog given and day and workout
    public void updateDayPlanDialog(String day, View view){
        UpdateDayPlanDialog dialog = new UpdateDayPlanDialog();
        dialog.setPlannerFragment(PlannerFragment.this);
        dialog.setDay(day);

        dayPlanViewModel.getDayPlanByDayAndUid(currentUser.getUid(), day).observe(getViewLifecycleOwner(), new Observer<DayPlan>() {
            @Override
            public void onChanged(DayPlan dayPlan) {
                if (dayPlan != null && dayPlan.getWorkoutId() != null) { // get the associated workout
                    Long workoutId = dayPlan.getWorkoutId();
                    Workout workout = getWorkout(workoutId);
                    Log.e("updateDayPlanDialog", workout.toString());
                    dialog.setWorkoutName(workout.getName());
                    dialog.setWorkout(workout);
                }
            }
        });
        dialog.show(((AppCompatActivity) view.getContext())
                .getSupportFragmentManager(), "Update DayPlan Dialog Tag");
    }

    //Get the workouts created by the logged in user
    public LiveData<List<Workout>> getWorkouts(){
        MutableLiveData<List<Workout>> workoutLiveData = new MutableLiveData<>();
        workoutViewModel.getWorkoutsById(currentUser.getUid()).observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workoutList) {
                workoutLiveData.setValue(workoutList);
            }
        });
        return workoutLiveData;
    }

    //Populates the planner with the assigned workout given the day
    public void populatePlanner(){
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        for (String day : daysOfWeek) {
            dayPlanViewModel.getDayPlanByDayAndUid(currentUser.getUid(), day).observe(getViewLifecycleOwner(), new Observer<DayPlan>() {
                @Override
                public void onChanged(DayPlan dayPlan) {
                    if (dayPlan == null) { // day plan doesn't exist create a null day plan
                        insertNullDayPlan(day);
                    } else { // update UI if day plan exist
                        Workout workout = getWorkout(dayPlan.getWorkoutId());
                        if (workout != null) {
                            updateUI(day, workout.getName());
                        } else {
                            Log.i("populatePlanner", "Workout is null for day: " + day);
                            updateUI(day, "");
                        }
                    }
                }
            });
        }
    }

    private void updateUI(String day, String workoutName) {
        switch (day) {
            case "Monday":
                binding.mondayWorkout.setText(workoutName);
                break;
            case "Tuesday":
                binding.tuesdayWorkout.setText(workoutName);
                break;
            case "Wednesday":
                binding.wednesdayWorkout.setText(workoutName);
                break;
            case "Thursday":
                binding.thursdayWorkout.setText(workoutName);
                break;
            case "Friday":
                binding.fridayWorkout.setText(workoutName);
                break;
            case "Saturday":
                binding.saturdayWorkout.setText(workoutName);
                break;
            case "Sunday":
                binding.sundayWorkout.setText(workoutName);
                break;
        }
    }
    private void insertNullDayPlan(String day) {
        DayPlan nullDayPlan = new DayPlan(day, currentUser.getUid(), null);
        dayPlanViewModel.insert(nullDayPlan);
    }

    private void getWorkoutIdByName(String workoutName, Consumer<Long> callback){
        if (workoutName != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                CompletableFuture<Long> completableFuture = workoutViewModel.getWorkoutIdByNameAndUid(workoutName, currentUser.getUid());
                completableFuture.thenAccept(workoutId -> {
                    if (workoutId != null) {
                        Log.e("getWorkoutIdByName", workoutId.toString());
                        callback.accept(workoutId);
                    } else {
                        Log.e("getWorkoutIdByName", "Workout not found");
                        callback.accept(null); // Handle the case where workoutId is not found
                    }
                });
            }
        } else {
            Log.e("Get workout", "Workout ID is null");
            callback.accept(null); // Handle the case where workoutName is null
        }
    }

    public Workout getWorkout(Long workoutId){
        if (workoutId != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                CompletableFuture<Workout> completableFuture = workoutViewModel.findByIdFuture(workoutId);
                try {
                    return completableFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    Log.e("getWorkout", "Error fetching workout: " + e.getMessage());
                }
            }
        } else {
            Log.i("getWorkout", "Workout ID is null");
        }
        return null;
    }

    //Colours the current day's card
    private void highlightCurrentCard(){
        Calendar calender = Calendar.getInstance();
        int dayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
        int colourPrimary = Color.parseColor("#FF5C5C");
        int white = Color.parseColor("#FFFFFF");

        if (dayOfWeek == 1){
            binding.sundayCard.setCardBackgroundColor(colourPrimary);
            binding.sundayWorkout.setTextColor(white);
            binding.sundayTitle.setTextColor(white);
            binding.sundaySmallText.setTextColor(white);
            binding.sundayPlay.setVisibility(View.VISIBLE);
        } else if (dayOfWeek == 2) {
            binding.mondayCard.setCardBackgroundColor(colourPrimary);
            binding.mondayWorkout.setTextColor(white);
            binding.mondayTitle.setTextColor(white);
            binding.mondaySmallText.setTextColor(white);
            binding.mondayPlay.setVisibility(View.VISIBLE);
        } else if (dayOfWeek == 3) {
            binding.tuesdayCard.setCardBackgroundColor(colourPrimary);
            binding.tuesdayWorkout.setTextColor(white);
            binding.tuesdayTitle.setTextColor(white);
            binding.tuesdaySmallText.setTextColor(white);
            binding.tuesdayPlay.setVisibility(View.VISIBLE);
        } else if (dayOfWeek == 4) {
            binding.wednesdayCard.setCardBackgroundColor(colourPrimary);
            binding.wednesdayWorkout.setTextColor(white);
            binding.wednesdayTitle.setTextColor(white);
            binding.wednesdaySmallText.setTextColor(white);
            binding.wednesdayPlay.setVisibility(View.VISIBLE);
        } else if (dayOfWeek == 5) {
            binding.thursdayCard.setCardBackgroundColor(colourPrimary);
            binding.thursdayWorkout.setTextColor(white);
            binding.thursdayTitle.setTextColor(white);
            binding.thursdaySmallText.setTextColor(white);
            binding.thursdayPlay.setVisibility(View.VISIBLE);
        } else if (dayOfWeek == 6) {
            binding.fridayCard.setCardBackgroundColor(colourPrimary);
            binding.fridayWorkout.setTextColor(white);
            binding.fridayTitle.setTextColor(white);
            binding.fridaySmallText.setTextColor(white);
            binding.fridayPlay.setVisibility(View.VISIBLE);
        } else if (dayOfWeek == 7) {
            binding.saturdayCard.setCardBackgroundColor(colourPrimary);
            binding.saturdayWorkout.setTextColor(white);
            binding.saturdayTitle.setTextColor(white);
            binding.saturdaySmallText.setTextColor(white);
            binding.saturdayPlay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
