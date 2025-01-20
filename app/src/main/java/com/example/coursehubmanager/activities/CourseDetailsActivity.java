package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityCourseDetailsBinding;

public class CourseDetailsActivity extends AppCompatActivity {
    ActivityCourseDetailsBinding binding;
    MainViewModel viewModel;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        int id = getIntent().getIntExtra("courseId", -1);

        new Thread(() -> {
            course = viewModel.getCourseById(id);
            binding.txtTitle.setText(course.getCourseName());
            binding.txtMentor.setText(course.getMentorName());
            binding.txtPrice.setText("$"+course.getPrice());
            binding.txtStudents.setText(course.getStudentCount());
            binding.txthours.setText(course.getHours());
            binding.txtAbout.setText(course.getDescription());

        });
        binding.imgBack.setOnClickListener(v -> finish());

        binding.btnShowCourseContent.setOnClickListener(v -> {
            Intent intent = new Intent(this, CourseContentActivity.class);
            intent.putExtra("courseId", id); // Pass data to the new activity
            startActivity(intent);
        });


        binding.btnJoinCourse.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}