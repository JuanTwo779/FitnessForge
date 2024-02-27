package com.example.fitnessforge.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.fitnessforge.databinding.FragmentReminderBinding;
import com.example.fitnessforge.dialog.ReminderDialog;
import com.example.fitnessforge.notification.Notification;
import com.example.fitnessforge.notification.OnTimeSetListener;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ReminderFragment extends Fragment implements OnTimeSetListener {

    private FragmentReminderBinding binding;
    private int selectedHour = -1;
    private int selectedMinute = -1;

    public ReminderFragment(){}

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentReminderBinding.inflate(inflater, container, false);

        //Open the time picker dialog
        binding.timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        //Initiates the reminder
        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedHour != -1 && selectedMinute != -1){
                    startNotification();
                }
            }
        });

        return binding.getRoot();
    }

    //Uses work manager to make a OneTimeWork request to run a notification on the user specified time
    private void startNotification() {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(Notification.class)
                .setInitialDelay(calculateInitialDelay(selectedHour, selectedMinute), TimeUnit.MILLISECONDS)
                .addTag("notification_work")
                .build();
        WorkManager.getInstance(requireContext())
                .enqueueUniqueWork("notification_work",
                        ExistingWorkPolicy.REPLACE,
                        oneTimeWorkRequest);
    }

    //convert the selected time to long -> used as delay for work manager
    private long calculateInitialDelay(int selectedHour, int selectedMinute) {
        Calendar now = Calendar.getInstance();
        Calendar selectedTime = Calendar.getInstance();
        selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
        selectedTime.set(Calendar.MINUTE, selectedMinute);
        selectedTime.set(Calendar.SECOND, 0);
        selectedTime.set(Calendar.MILLISECOND, 0);
        if (selectedTime.before(now)) {
            selectedTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        return selectedTime.getTimeInMillis() - now.getTimeInMillis();
    }

    //TimePicker dialog
    private void showTimePickerDialog() {
        ReminderDialog dialog = new ReminderDialog();
        dialog.setOnTimeSetListener(this);
        dialog.show(getChildFragmentManager(), "reminder dialog tag");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        selectedHour = hourOfDay;
        selectedMinute = minute;
        binding.timeText.setText("Selected Time: " + hourOfDay + ":" +minute);
    }
}
