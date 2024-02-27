package com.example.fitnessforge.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessforge.databinding.FragmentProfileBinding;
import com.example.fitnessforge.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private UserViewModel userViewModel;

    public ProfileFragment(){}

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //Update UI with the user's details
        userViewModel.getUserById(getCurrentUserId()).observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.textViewEmail.setText(user.getEmail());
                binding.textViewFirstName.setText(user.getFirstName());
                binding.textViewLastName.setText(user.getLastName());
                binding.textViewGender.setText(user.getGender());
                binding.textViewAge.setText(user.getAge());
            }

        });


        return view;
    }

    private String getCurrentUserId(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        return currentUser.getUid();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
