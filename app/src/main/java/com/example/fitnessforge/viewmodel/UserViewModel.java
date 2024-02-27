package com.example.fitnessforge.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessforge.entity.User;
import com.example.fitnessforge.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }
    public void insertUser(User user) {
        userRepository.insert(user);
        Log.i("UserDetails", user.toString());
    }

    public LiveData<User> getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    public void deleteAllRows() {
        userRepository.deleteAllRows();
    }

}
