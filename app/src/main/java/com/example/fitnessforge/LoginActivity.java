package com.example.fitnessforge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessforge.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Firebase
        auth = FirebaseAuth.getInstance();

        //Go to register activity
        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //Go into application
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = binding.emailEditText.getText().toString();
                String txtPwd = binding.passwordEditText.getText().toString();
                if (txtEmail.isEmpty() || txtPwd.isEmpty()){
                    toastMsg("Empty fields");
                } else {
                    loginUser(txtEmail, txtPwd);
                }
            }
        });
    }

    //Checks with firebase to ensure user exists then starts main activity
    private void loginUser(String txtEmail, String txtPwd){
        auth.signInWithEmailAndPassword(txtEmail, txtPwd)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String msg = "Login Successful";
                        toastMsg(msg);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                });

    }

    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
