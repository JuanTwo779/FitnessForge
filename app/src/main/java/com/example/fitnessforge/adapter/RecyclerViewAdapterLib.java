package com.example.fitnessforge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessforge.api.ApiNinjaService;
import com.example.fitnessforge.databinding.RvLayoutLibBinding;
import com.example.fitnessforge.dialog.LibraryDialog;
import com.example.fitnessforge.entity.ExerciseEntity;
import com.example.fitnessforge.model.Exercise;
import com.example.fitnessforge.viewmodel.ExerciseEntityViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class RecyclerViewAdapterLib extends RecyclerView.Adapter
        <RecyclerViewAdapterLib.ViewHolder>  {
    private static List<Exercise> exercises;
    public RecyclerViewAdapterLib(List<Exercise> exercises){
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvLayoutLibBinding binding = RvLayoutLibBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterLib.ViewHolder holder, int position) {
        final Exercise exercise = exercises.get(position);
        holder.binding.exerciseName.setText(exercise.getName());
        holder.binding.difficulty.setText(exercise.getDifficulty());

        //Opens library dialog to show details of selected exercise
        holder.binding.learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LibraryDialog dialogFragment = new LibraryDialog();
                dialogFragment.setExercise(exercise);
                dialogFragment.show(((AppCompatActivity) view.getContext()).getSupportFragmentManager(), "Library Dialog Tag");
            }
        });

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RvLayoutLibBinding binding;
        public ViewHolder(RvLayoutLibBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
