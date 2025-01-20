package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager.adapters.EditCoursesAdapter;
import com.example.coursehubmanager.adapters.LessonsAdapter;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.LessonDao;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.Lesson;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityCourseContentBinding;

import java.util.ArrayList;
import java.util.List;

public class CourseContentActivity extends AppCompatActivity {
    ActivityCourseContentBinding binding;

    private LessonsAdapter adapter;
    private List<String> itemList;
    private LessonDao dao;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getInstance(this);
        dao = db.lessonDao();
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        int id = getIntent().getIntExtra("courseId", -1);

        Log.d("TAG", "onCreate:id " + id);
        binding.imgBack.setOnClickListener(v -> finish());

        binding.recLessons.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LessonsAdapter();
        binding.recLessons.setAdapter(adapter);

        viewModel.getLessonsForCourse(id).observe(this, new Observer<List<Lesson>>() {
            @Override
            public void onChanged(List<Lesson> lessons) {
                Log.d("TAG", "onChanged:lessons.size() "+ lessons.size());
                adapter.setData(lessons);
            }
        });

        adapter.setOnItemClickListener((position, isIcon) -> {
            dao.markLessonAsWatched(position);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com"));
            intent.setPackage("com.google.android.youtube");
            startActivity(intent);
        });
//        binding.recLessons.setAdapter(adapter);

    }
}