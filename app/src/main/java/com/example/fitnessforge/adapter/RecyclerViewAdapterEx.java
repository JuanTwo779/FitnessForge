package com.example.fitnessforge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessforge.databinding.RvLayoutExBinding;
import com.example.fitnessforge.dialog.ExerciseDialog;
import com.example.fitnessforge.entity.ExerciseEntity;
import com.example.fitnessforge.fragment.ExerciseFragment;

import java.util.List;

public class RecyclerViewAdapterEx extends RecyclerView.Adapter<RecyclerViewAdapterEx.ViewHolder>{

    private static List<ExerciseEntity> exerciseEntityList;
    private ExerciseFragment exerciseFragment;
    public RecyclerViewAdapterEx(List<ExerciseEntity> exerciseEntityList, ExerciseFragment exerciseFragment){

        this.exerciseEntityList = exerciseEntityList;
        this.exerciseFragment = exerciseFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvLayoutExBinding binding = RvLayoutExBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ExerciseEntity exercise = exerciseEntityList.get(position);
        holder.binding.exerciseName.setText(exercise.getName());
        holder.binding.exerciseMuscle.setText(exercise.getMuscle());
        holder.binding.exerciseSet.setText(exercise.getStringSets());
        holder.binding.exerciseReps.setText(exercise.getStringReps());

        //deletes the selected exercise
        holder.binding.exerciseDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = exercise.getId();
                exerciseFragment.deleteExercise(id);

                notifyDataSetChanged();
            }
        });

        //opens the Exercise dialog to update the selected exercise
        holder.binding.exerciseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExerciseDialog dialog = new ExerciseDialog();
                dialog.setExerciseEntity(exercise);
                dialog.setExerciseFragment(exerciseFragment);
                dialog.show(((AppCompatActivity) view.getContext()).getSupportFragmentManager(), "Exercise Dialog Tag");
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseEntityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private RvLayoutExBinding binding;
        public ViewHolder(RvLayoutExBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
