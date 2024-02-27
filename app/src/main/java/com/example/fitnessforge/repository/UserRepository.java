package com.example.fitnessforge.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.fitnessforge.dao.UserDAO;
import com.example.fitnessforge.database.FitnessForgeDatabase;
import com.example.fitnessforge.entity.User;


public class UserRepository {
    private UserDAO userDAO;

    public UserRepository(Application application){
        FitnessForgeDatabase db = FitnessForgeDatabase.getInstance(application);
        userDAO = db.userDAO();
    }

    public void insert(final User user){
        FitnessForgeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.insert(user);
            }
        });
    }

    public LiveData<User> getUserById(String userId) {
        return userDAO.getUserById(userId);
    }

    public void deleteAllRows(){
        userDAO.deleteAllRows();
    }





}
