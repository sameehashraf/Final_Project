package com.example.coursehubmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubmanager.activities.CourseDetailsActivity;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;
import com.example.coursehubmanager.adapters.CoursesAdapter;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.CourseDao;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.FragmentCategoryBinding;

import java.util.List;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private RecyclerView recyclerView;
    private CoursesAdapter adapter;
    private List<String> itemList;
    private CourseDao dao;
    private AppDatabase db;
    private MainViewModel courseViewModel;

    private static final String ARG_CATEGORY = "category";
    SharedPreferencesHelper prefsHelper;

    public static CategoryFragment newInstance(Category category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = AppDatabase.getInstance(requireContext());
        dao = db.courseDao();
        prefsHelper = new SharedPreferencesHelper(requireContext());
        courseViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        RecyclerView recyclerView = binding.recCourses;

        if (getArguments() != null) {
            Category category = getArguments().getParcelable(ARG_CATEGORY);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recCourses.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new CoursesAdapter();
            binding.recCourses.setAdapter(adapter);

            courseViewModel.getCoursesByCategoryId(category.getCategoryId()).observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                @Override
                public void onChanged(List<Course> courses) {
                    for (Course course : courses) {
                            Log.d("TAG", "onChanged: " + category.getCategoryId());
                            Log.d("TAG", "onChanged: " + category.getCategoryName());
                            Log.d("TAG", "onChanged: " + course.getCategoryId());
                            adapter.setData(courses, category.getCategoryName());
                    }
                }
            });

            adapter.setOnItemClickListener((position, isIcon) -> {
                if (!isIcon) {
                    Intent intent = new Intent(requireContext(), CourseDetailsActivity.class);
                    intent.putExtra("courseId", position); // Pass data to the new activity
                    startActivity(intent);
                } else {
//                    AddOrDeleteBookmark(position);
                }
            });
            recyclerView.setAdapter(adapter);

//            List<Course> courses = dao.getAllCourses();
//            for (Course course : Objects.requireNonNull(courses)) {
//                Log.d("Course", course.getCourseName());
//            }
        }

        return root;
    }
//
//    private void AddOrDeleteBookmark(int id) {
//        prefsHelper.addItemToList("bookmarks", id);
//        Log.d("TAG", "AddOrDeleteBookmark: " + prefsHelper.getIntegerList("bookmarks").get(0));
//    }
}