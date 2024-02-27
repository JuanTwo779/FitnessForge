package com.example.fitnessforge.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessforge.api.ApiNinjaService;
import com.example.fitnessforge.api.RetrofitClient;
import com.example.fitnessforge.databinding.FragmentHomeBinding;
import com.example.fitnessforge.entity.WorkoutLog;
import com.example.fitnessforge.model.Quote;
import com.example.fitnessforge.viewmodel.UserViewModel;
import com.example.fitnessforge.viewmodel.WorkoutLogViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private String API_KEY = "DCjvBSY1IK/whxDnmRBpPg==BWZHmE5BpgUJMA7o";
    private ApiNinjaService service;
    private Quote quote;

    private UserViewModel userViewModel;

    private WorkoutLogViewModel workoutLogViewModel;
    private DatabaseReference rootDatabaseRef;
    private final String realTimePath = "https://fitnessforge-6feba-default-rtdb.asia-southeast1.firebasedatabase.app/";


    public HomeFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        workoutLogViewModel = new ViewModelProvider(this).get(WorkoutLogViewModel.class);

        //API
        service = RetrofitClient.apiService();
        getQuote();

        //Firebase
        rootDatabaseRef = FirebaseDatabase.getInstance(realTimePath).getReference();

        //displays the user's name
        userViewModel.getUserById(getCurrentUserId()).observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                String string = "Hi, " + user.getFirstName() + " " + user.getLastName();
                binding.welcomeText.setText(string);
            }
        });

        //observes the user's workoutLogs to update figures
        workoutLogViewModel.deleteWorkoutLogsNotInCurrentMonth(getCurrentUserId());
        workoutLogViewModel.getWorkoutLogsForCurrentMonth(getCurrentUserId()).observe(getViewLifecycleOwner(), new Observer<List<WorkoutLog>>() {
            @Override
            public void onChanged(List<WorkoutLog> workoutLogs) {

                binding.workoutsMonthText.setText(workoutLogs.size()+"");
                Long montlyDuration = 0L;
                for (WorkoutLog logs : workoutLogs){
                    montlyDuration += logs.getDuration();
                    Log.e("getWorkoutLogsForCurrentMonth", logs.toString());
                }
                binding.timeMonth.setText(montlyDuration+"");

            }
        });

        //gets the firebase workout logs
        getFirebaseWorkoutLogs();

        //Tests for chart and workoutLog data
//        workoutLogViewModel.deleteAll();
//        deleteAllFirebaseLogs();
//        createPastAndPresentLogs();



        return view;
    }

    //Test method
    public void deleteAllFirebaseLogs() {
        DatabaseReference logsRef = FirebaseDatabase.getInstance(realTimePath).getReference().child("workoutLogs");

        logsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("deleteAllFirebaseLogs", "Workout log deleted successfully");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("deleteAllFirebaseLogs", Objects.requireNonNull(e.getMessage()));
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DeleteWorkoutLogs", databaseError.getMessage());
            }
        });
    }

    //Test method
    private void createPastAndPresentLogs(){
        DatabaseReference logsRef = FirebaseDatabase.getInstance(realTimePath).getReference().child("workoutLogs");
        Random random = new Random();

        //present workout log
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, random.nextInt(25) + 1);
        Date date = calendar.getTime();
        long duration = random.nextInt(3599) + 1;
        WorkoutLog presentLog = new WorkoutLog(getCurrentUserId(), date, duration);
        workoutLogViewModel.insert(presentLog);
        String logKey1 = logsRef.push().getKey();
        if (logKey1 != null) {
            logsRef.child(logKey1).setValue(presentLog.toMap())
                    .addOnSuccessListener(aVoid -> Log.d("createPastAndPresentLogs", "Workout log inserted successfully"))
                    .addOnFailureListener(e -> Log.e("createPastAndPresentLogs", Objects.requireNonNull(e.getMessage())));
        }

        //past workout log
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 0); //set calender to last month
        date = calendar.getTime();
        duration = random.nextInt(3599) + 1;
        WorkoutLog pastLog = new WorkoutLog(getCurrentUserId(), date, duration);
        workoutLogViewModel.insert(pastLog);
        String logKey2 = logsRef.push().getKey();
        if (logKey2 != null) {
            logsRef.child(logKey2).setValue(pastLog.toMap())
                    .addOnSuccessListener(aVoid -> Log.d("createPastAndPresentLogs", "Workout log inserted successfully"))
                    .addOnFailureListener(e -> Log.e("createPastAndPresentLogs", Objects.requireNonNull(e.getMessage())));
        }

        workoutLogViewModel.deleteWorkoutLogsNotInCurrentMonth(getCurrentUserId());

    }

    //Test method
    public void createSampleWorkoutLogs() {
        DatabaseReference logsRef = FirebaseDatabase.getInstance(realTimePath).getReference().child("workoutLogs");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date;

        //logs for last month
        for (int i = 0; i < 25; i++) {
            Random random = new Random();
            long duration = random.nextInt(3599) + 1;

            calendar.set(Calendar.DAY_OF_MONTH, i);
            date = calendar.getTime();


            WorkoutLog localLog = new WorkoutLog(getCurrentUserId(), date, duration);
            workoutLogViewModel.insert(localLog);

            String logKey = logsRef.push().getKey();

            if (logKey != null) {
                logsRef.child(logKey).setValue(localLog.toMap())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("createFirebaseWorkoutLogs", "Workout log inserted successfully");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("createFirebaseWorkoutLogs", Objects.requireNonNull(e.getMessage()));
                            }
                        });
            }
        }
    }
    //Test method
    private void createSampleLocalLogs(){
        Random random = new Random();
        Calendar calendar1 = Calendar.getInstance();
        Date date;

        for (int i = 0; i < 25; i++){
            long duration = random.nextInt(3599) + 1;
            calendar1.set(Calendar.DAY_OF_MONTH, i);
            date = calendar1.getTime();

            workoutLogViewModel.insert(new WorkoutLog(getCurrentUserId(), date, duration));
        }
    }

    //Gets the user's firebase workout logs and updates the figures on changes
    public void getFirebaseWorkoutLogs(){
        DatabaseReference logsRef = FirebaseDatabase.getInstance(realTimePath).getReference().child("workoutLogs");
        logsRef.orderByChild("uid").equalTo(getCurrentUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long totalDuration = 0;
                long logCount = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    WorkoutLog log = fromSnapshot(dataSnapshot);
                    if (log != null){
                        totalDuration += log.getDuration();
                        logCount++;
                    }
                }
                Log.d("getFirebaseWorkoutLogs", "Total Duration: " + totalDuration + ", Log Count: " + logCount);
                if (binding != null){
                    binding.totalTimeText.setText(totalDuration+"");
                    binding.totalWorkoutsText.setText(logCount+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("getFirebaseWorkoutLogs", "Error getting workout logs: " + error.getMessage());
            }
        });
    }

    private WorkoutLog fromSnapshot(DataSnapshot snapshot) {
        String uid = snapshot.child("uid").getValue(String.class);
        String dateString = snapshot.child("date").getValue(String.class);
        long duration = snapshot.child("duration").getValue(Long.class);

        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new WorkoutLog(uid, date, duration);
    }

    private String getCurrentUserId(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        return currentUser.getUid();
    }

    //Makes a call to web API to get a Quote
    private void getQuote(){
        quote = new Quote();
        Call<List<Quote>> call = service.getQuote("inspirational", API_KEY);
        call.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if (response.isSuccessful()){
                    quote = response.body().get(0);
                    binding.motivationText.setText(quote.getQuote());
                } else {
                    Log.i("Error1", "Response failed");
                }
            }

            @Override
            public void onFailure(Call<List<Quote>> call, @NonNull Throwable t) {
                Log.i("Error2", "Response failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
