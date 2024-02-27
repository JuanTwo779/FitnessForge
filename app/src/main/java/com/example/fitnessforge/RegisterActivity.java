package com.example.fitnessforge;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessforge.databinding.ActivityRegisterBinding;
import com.example.fitnessforge.entity.User;
import com.example.fitnessforge.viewmodel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    private UserViewModel userViewModel;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Firebase
        auth = FirebaseAuth.getInstance();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //Slider for age
        binding.ageSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.ageText.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //Go to login page
        binding.returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        //Registers the user to the application
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt = binding.emailRegEditText.getText().toString();
                String passwordTxt = binding.passwordRegEditText.getText().toString();
                String conPasswordTxt = binding.confirmRegEditText.getText().toString();
                String firstNameTxt = binding.nameRegEditText.getText().toString();
                String lastNameTxt = binding.surnameRegEditText.getText().toString();

                int selectedId = binding.genderRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String gender = selectedRadioButton.getText().toString();
                String age = binding.ageText.getText().toString();

                if (TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passwordTxt)){
                    toastMsg("Empty Username or Password");
                } else if (passwordTxt.length() < 6) {
                    toastMsg("Password is too short");
                } else if (TextUtils.isEmpty(firstNameTxt) || TextUtils.isEmpty(lastNameTxt)) {
                    toastMsg("Empty name field");
                } else if (!passwordTxt.equals(conPasswordTxt)) {
                    toastMsg("Passwords don't match");
                } else if (selectedId == -1) {
                    toastMsg("Select a gender");
                } else if (age.equals("0")) {
                    toastMsg("Select a valid age");
                } else {
                    registerUser(emailTxt, passwordTxt, firstNameTxt, lastNameTxt, age, gender);
                }
            }
        });
    }

    //Registers the email and password to firebase
    private void registerUser(String emailTxt, String passwordTxt, String firstName, String lastName, String age, String gender) {
        auth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = task.getResult().getUser();
                            if (user != null){
                                String uid = user.getUid();
                                storeDetails(uid, emailTxt, firstName, lastName, age, gender);
                                toastMsg("Registration Successful");
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                        } else {
                            toastMsg("Registration Unsuccessful");
                        }
                    }
                });
    }

    //Stores user details into Room DB
    private void storeDetails(String uid, String emailTxt, String firstName, String lastName, String age, String gender) {
        User user = new User(uid, emailTxt, firstName, lastName, age, gender);
        userViewModel.insertUser(user);

    }

    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
