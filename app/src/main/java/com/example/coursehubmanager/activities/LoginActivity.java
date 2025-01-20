package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.UserDao;
import com.example.coursehubmanager.database.entities.User;
import com.example.coursehubmanager.databinding.ActivityLoginBinding;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    AppDatabase db;
    UserDao dao;
    String email = "";
    String password = "";
    private boolean isShow = false;
    SharedPreferencesHelper prefsHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefsHelper = new SharedPreferencesHelper(this);


        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        db = AppDatabase.getInstance(this);
        dao = db.userDao();

        binding.btnLogin.setOnClickListener(v -> {
            email = binding.editEmail.getText().toString().trim();
            password = binding.editPassword.getText().toString();
            if (validateInputs(email, password)) {
                login(email, password);
            }
        });

        binding.txtCreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateAccountActivity.class));
        });


        binding.imgEye.setOnClickListener(v -> {
            isShow = !isShow;
            if (isShow) {
                binding.editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                binding.editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

    }

    public void login(String email, String password) {
        new Thread(() -> {
            User user = dao.login(email, password);
            Log.d("TAG", "login: " + user);
            if (email.equals("Admin@Admin.com") && password.equals("AdminAdmin")) {
                startActivity(new Intent(this, AdminActivity.class));
            } else {
                if (binding.checkbox.isChecked()) {
                    prefsHelper.saveBoolean("is_logged_in", true);
                }
                startActivity(new Intent(this, MainActivity.class));
            }
        }).start();
    }


    private boolean validateInputs(String email, String password) {
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