package com.example.fitnessforge.fragment;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessforge.adapter.RecyclerViewAdapterEx;
import com.example.fitnessforge.databinding.FragmentExerciseBinding;
import com.example.fitnessforge.dialog.ExerciseDialog;
import com.example.fitnessforge.entity.ExerciseEntity;
import com.example.fitnessforge.viewmodel.ExerciseEntityViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ExerciseFragment extends Fragment {

    private FragmentExerciseBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private List<ExerciseEntity> exerciseEntities;
    private RecyclerViewAdapterEx adapter;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private ExerciseEntityViewModel exerciseEntityViewModel;

    public ExerciseFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExerciseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //Firebase
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        //ViewModel
        exerciseEntityViewModel = new ViewModelProvider(this).get(ExerciseEntityViewModel.class);

        //recycler view
        exerciseEntities = new ArrayList<>();
        exerciseEntityViewModel.getExercisesById(currentUser.getUid()).observe(getViewLifecycleOwner(), new Observer<List<ExerciseEntity>>() {
            @Override
            public void onChanged(List<ExerciseEntity> exerciseEntityList) {
                exerciseEntities.clear();
                exerciseEntities.addAll(exerciseEntityList);
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new RecyclerViewAdapterEx(exerciseEntities, this);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        //opens the dialog to add an exercise
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExerciseDialog dialog = new ExerciseDialog();
                dialog.setExerciseFragment(ExerciseFragment.this);
                dialog.show(((AppCompatActivity) view.getContext()).getSupportFragmentManager(), "Exercise Dialog Tag");
            }
        });

        return view;
    }

    public void insertExercise(String name, String muscle, String sets, String reps){
        int intSets = Integer.parseInt(sets);
        int intReps = Integer.parseInt(reps);
        exerciseEntityViewModel.insert(new ExerciseEntity(currentUser.getUid(), name, muscle,
                intSets,intReps));
    }

    public void deleteExercise(long exerciseId){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            CompletableFuture<ExerciseEntity> completableFuture =
                    exerciseEntityViewModel.findByIdFuture(exerciseId);
            completableFuture.thenApply(exerciseEntity -> {
                if (exerciseEntity != null){
                    exerciseEntityViewModel.delete(exerciseEntity);
                } else {
                    Log.i("Delete exercise", "Iid does not exist");
                }
                return exerciseEntity;
            });
        }
    }

    public void updateExercise(long exerciseId, String name, String muscle, String sets, String reps){
        int intSets = Integer.parseInt(sets);
        int intReps = Integer.parseInt(reps);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CompletableFuture<ExerciseEntity> completableFuture =
                    exerciseEntityViewModel.findByIdFuture(exerciseId);
            completableFuture.thenApply(exerciseEntity -> {
                if (exerciseEntity != null){
                    exerciseEntity.setName(name);
                    exerciseEntity.setMuscle(muscle);
                    exerciseEntity.setSets(intSets);
                    exerciseEntity.setReps(intReps);
                    exerciseEntityViewModel.update(exerciseEntity);
                } else {
                    Log.i("Update exercise", "Iid does not exist");
                }
                return exerciseEntity;
            });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
