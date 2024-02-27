package com.example.fitnessforge.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitnessforge.databinding.DialogReminderBinding;
import com.example.fitnessforge.notification.OnTimeSetListener;

public class ReminderDialog extends AppCompatDialogFragment{

    private OnTimeSetListener onTimeSetListener;
    private DialogReminderBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogReminderBinding.inflate(inflater, container, false);

        //set the onTimeListener with the user selected time using the time picker
        binding.setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = binding.timePicker.getHour();
                int minute = binding.timePicker.getMinute();
                if (onTimeSetListener != null){
                    onTimeSetListener.onTimeSet(hour, minute);
                }
                dismiss();
            }
        });

        return binding.getRoot();
    }

    public void setOnTimeSetListener(OnTimeSetListener listener) {
        this.onTimeSetListener = listener;
    }
}
