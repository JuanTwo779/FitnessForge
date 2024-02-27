package com.example.fitnessforge.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessforge.adapter.RecyclerViewAdapterLib;
import com.example.fitnessforge.api.ApiNinjaService;
import com.example.fitnessforge.api.RetrofitClient;
import com.example.fitnessforge.databinding.FragmentLibraryBinding;
import com.example.fitnessforge.model.Exercise;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {
    private FragmentLibraryBinding binding;

    private RecyclerView.LayoutManager layoutManager;
    private List<Exercise> exercises;
    private RecyclerViewAdapterLib adapter;

    private String API_KEY = "DCjvBSY1IK/whxDnmRBpPg==BWZHmE5BpgUJMA7o";
    private ApiNinjaService service;

    public LibraryFragment(){}


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        displaySpinner();

        //API
        service = RetrofitClient.apiService();
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.muscleSpinner.getSelectedItem().toString().equals("Select a muscle group") || !binding.keyword.getText().toString().isEmpty()){
                    initiateRecyclerView(binding.muscleSpinner.getSelectedItem().toString(), binding.keyword.getText().toString());
                } else {
                    toastMsg("Select a muscle in the drop down box");
                }
            }
        });

        return view;
    }

    private void toastMsg(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //Initiates the recycler view with exercises from API given filters
    private void initiateRecyclerView(String muscle, String keyword){
        exercises = new ArrayList<>();
        Call<List<Exercise>> call;

        if (muscle.equals("Select a muscle group") && !keyword.isEmpty()){ //just keyword
            call = service.getExercisesKeyword(keyword, API_KEY);
        } else if (!muscle.equals("Select a muscle group") && keyword.isEmpty()) { //just muscle
            call = service.getExercises(muscle, API_KEY);
        } else { //keyword and muscle selected
            call = service.getExercisesKeywordMuscle(keyword, muscle, API_KEY);
        }


        assert call != null;
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful()){
                    exercises = response.body();
                    Log.d("ExerciseData", "Response body: " + exercises);
                    adapter = new RecyclerViewAdapterLib(exercises);
                    binding.recyclerView.addItemDecoration(new DividerItemDecoration
                            (requireContext(), LinearLayoutManager.VERTICAL));
                    binding.recyclerView.setAdapter(adapter);

                    layoutManager = new LinearLayoutManager(requireContext());
                    binding.recyclerView.setLayoutManager(layoutManager);
                } else {
                    Log.i("Error", "Response failed");
                }

            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                toastMsg("Response failed");
            }
        });
    }

    //displays spinner with the exercise API muscle types
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
