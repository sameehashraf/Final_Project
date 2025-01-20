package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.UserDao;
import com.example.coursehubmanager.database.entities.User;
import com.example.coursehubmanager.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {
    ActivityCreateAccountBinding binding;
    AppDatabase db;
    UserDao dao;
    String username;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getInstance(this);
        dao = db.userDao();

        binding.btnSignUp.setOnClickListener(v -> {
            username = binding.editFullName.getText().toString().trim();
            email = binding.editEmail.getText().toString().trim();
            password = binding.editPassword.getText().toString();
            if (validateInputs(username, email, password)) {

                insertUser();
                startActivity(new Intent(this, MainActivity.class));


            }
        });


    }

    private void insertUser() {
        new Thread(() -> {
            User user = new User(username,email,password);
            dao.insertUser(user);
            Log.d("TAG", "createUser:user " + user);
        }).start();


    }

    private boolean validateInputs(String username, String email, String password) {
        if (username.isEmpty()) {
            showError(getString(R.string.username_cannot_be_empty));
            return false;
        }
        if (email.isEmpty() || !isValidEmail(email)) {
            showError(getString(R.string.enter_a_valid_email_address));
            return false;
        }
        if (password.isEmpty() || password.length() < 8 || !isValidPassword(password)) {
            showError(getString(R.string.password_must_be_at_least_8_characters));
            return false;
        }
        return true;
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Regex: At least one letter, one number, and one special character
        return password.trim().length() >= 8;
    }

}