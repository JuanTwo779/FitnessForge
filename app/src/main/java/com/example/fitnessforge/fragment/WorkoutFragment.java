package com.example.fitnessforge.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessforge.adapter.RecyclerViewAdapterWork;
import com.example.fitnessforge.databinding.FragmentWorkoutBinding;
import com.example.fitnessforge.dialog.WorkoutDialog;
import com.example.fitnessforge.entity.ExerciseEntity;
import com.example.fitnessforge.entity.Workout;
import com.example.fitnessforge.viewmodel.ExerciseEntityViewModel;
import com.example.fitnessforge.viewmodel.WorkoutViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class WorkoutFragment extends Fragment {

    private FragmentWorkoutBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private List<Workout> workouts;
    private RecyclerViewAdapterWork adapter;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    private WorkoutViewModel workoutViewModel;

    private ExerciseEntityViewModel exerciseEntityViewModel;

    public  WorkoutFragment (){}

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //Firebase
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        //ViewModels
        workoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        exerciseEntityViewModel = new ViewModelProvider(this).get(ExerciseEntityViewModel.class);

        //gets and updates workouts list given changes to user's workouts
        workouts = new ArrayList<>();
        workoutViewModel.getWorkoutsById(currentUser.getUid()).observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workoutList) {
                workouts.clear();
                workouts.addAll(workoutList);
                adapter.notifyDataSetChanged();
            }
        });

        //populate recylcer view given the workouts list
        adapter = new RecyclerViewAdapterWork(workouts, this);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        //opens the Workout dialog to add a new workout
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutDialog dialog = new WorkoutDialog();
                dialog.setWorkoutFragment(WorkoutFragment.this);
                dialog.show(((AppCompatActivity) view.getContext()).getSupportFragmentManager(), "Workout Dialog Tag");
            }
        });

        return view;
    }

    public void insertWorkout(String name, Long e1, Long e2, Long e3, Long e4, Long e5, Long e6, Long e7){
        workoutViewModel.insert(new Workout(name, currentUser.getUid(), e1,e2,e3,e4,e5,e6,e7));
    }

    public void deleteWorkout(long workoutId){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            CompletableFuture<Workout> completableFuture = workoutViewModel.findByIdFuture(workoutId);
            completableFuture.thenApply(workout -> {
                if (workout != null){
                    workoutViewModel.delete(workout);
                } else {
                    Log.i("Delete exercise", "Workout not found");
                }
                return workout;
            });
        }
    }

    public void updateWorkout(long workoutId, String name, @Nullable Long e1, @Nullable Long e2,
                              @Nullable Long e3, @Nullable Long e4, @Nullable Long e5,
                              @Nullable Long e6, @Nullable Long e7){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CompletableFuture<Workout> completableFuture = workoutViewModel.findByIdFuture(workoutId);
            completableFuture.thenApply(workout -> {
               if (workout != null) {
                   workout.setName(name);
                   workout.setUid(currentUser.getUid());
                   workout.setExerciseId1(e1);
                   workout.setExerciseId2(e2);
                   workout.setExerciseId3(e3);
                   workout.setExerciseId4(e4);
                   workout.setExerciseId5(e5);
                   workout.setExerciseId6(e6);
                   workout.setExerciseId7(e7);
                   Log.e("WorkFragment Update", workout.toString());
                   workoutViewModel.update(workout);
               } else {
                   Log.i("Update exercise", "Workout not found");
               }
               return workout;
            });
        }
    }

    public LiveData<List<ExerciseEntity>> getExercises(){
        MutableLiveData<List<ExerciseEntity>> exerciseEntitiesLiveData = new MutableLiveData<>();
        exerciseEntityViewModel.getExercisesById(currentUser.getUid()).observe(getViewLifecycleOwner(), new Observer<List<ExerciseEntity>>() {
            @Override
            public void onChanged(List<ExerciseEntity> exerciseEntityList) {
                exerciseEntitiesLiveData.setValue(exerciseEntityList);
            }
        });
        return exerciseEntitiesLiveData;
    }

    public ExerciseEntity getExercise(long exerciseId) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CompletableFuture<ExerciseEntity> completableFuture = exerciseEntityViewModel.findByIdFuture(exerciseId);
            try {
                return completableFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Long getExerciseIdByNameUid(String name){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            CompletableFuture<Long> completableFuture = exerciseEntityViewModel.getExerciseIdByNameAndUid(name, currentUser.getUid());
            try {
                // Wait for the CompletableFuture to complete and get the result
                return completableFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                // Handle any exceptions
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
