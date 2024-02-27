package com.example.fitnessforge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessforge.databinding.RvLayoutWorkBinding;
import com.example.fitnessforge.dialog.WorkoutDialog;
import com.example.fitnessforge.entity.Workout;
import com.example.fitnessforge.fragment.WorkoutFragment;
import com.example.fitnessforge.viewmodel.WorkoutViewModel;

import java.util.List;

public class RecyclerViewAdapterWork extends RecyclerView.Adapter<RecyclerViewAdapterWork.ViewHolder>{

    private static List<Workout> workoutList;
    private WorkoutFragment workoutFragment;

    public RecyclerViewAdapterWork(List<Workout> workoutList, WorkoutFragment workoutFragment){
        this.workoutList = workoutList;
        this.workoutFragment = workoutFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvLayoutWorkBinding binding = RvLayoutWorkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Workout workout = workoutList.get(position);
        holder.binding.workoutName.setText(workout.getName());

        //deletes the selected workout
        holder.binding.workoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutFragment.deleteWorkout(workout.getId());
                notifyDataSetChanged();
            }
        });

        //opens the Workout dialog to update the selected workout
        holder.binding.workoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutDialog dialog = new WorkoutDialog();
                dialog.setWorkout(workout);
                dialog.setWorkoutFragment(workoutFragment);
                dialog.show(((AppCompatActivity) view.getContext()).getSupportFragmentManager(), "Workout Dialog Tag");
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private RvLayoutWorkBinding binding;
        public ViewHolder(RvLayoutWorkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
