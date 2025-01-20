package com.example.coursehubmanager.fragments;

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

import com.example.coursehubmanager.helpers.SharedPreferencesHelper;
import com.example.coursehubmanager.adapters.CoursesProgressAdapter;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.CourseDao;
import com.example.coursehubmanager.database.dao.LessonDao;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.Lesson;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.FragmentCategoryStatusBinding;

import java.util.List;

public class CategoryStatusFragment extends Fragment {

    private FragmentCategoryStatusBinding binding;
    private RecyclerView recyclerView;
    //    private CoursesAdapter adapter;
    private CoursesProgressAdapter adapter;
    private List<String> itemList;
    //    private List<Course> itemList;
    private CourseDao courseDao;
    private LessonDao lessonDao;
    private AppDatabase db;
    private MainViewModel courseViewModel;

    private static final String ARG_CATEGORY = "category";
    SharedPreferencesHelper prefsHelper;

    public static CategoryStatusFragment newInstance(int position) {
        CategoryStatusFragment fragment = new CategoryStatusFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryStatusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = AppDatabase.getInstance(requireContext());
        courseViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        if (getArguments() != null) {
            boolean status = getArguments().getInt(ARG_CATEGORY) == 1;
            Log.d("TAG", "onCreateView:status " + status);
            recyclerView = binding.recCourses;
            binding.recCourses.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new CoursesProgressAdapter();
            binding.recCourses.setAdapter(adapter);

            courseViewModel.getCoursesByCompletion(status).observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                @Override
                public void onChanged(List<Course> courses) {
//
//                    for (Course course : courses) {
//                        calculate(course);
//                    }
                    adapter.setData(courses);
                }
            });

        }
        return root;
    }

//    private void calculate(Course course) {
//        courseViewModel.getLessonsForCourse(course.getCourseId()).observe(getViewLifecycleOwner(), new Observer<List<Lesson>>() {
//            @Override
//            public void onChanged(List<Lesson> lessons) {
//                if(lessons!=null && !lessons.isEmpty()){
//                    double totalCompletion = 0;
//                    for (Lesson lesson:lessons){
//                        totalCompletion += lesson.getCompletionPercentage();
//                    }
//                    double completionPercentage = totalCompletion / lessons.size();
//                    course.setPercentage(completionPercentage);
//                    courseViewModel.updatePercentage(course.getCourseId(),completionPercentage);
//                    Log.d("TAG", "onChanged:completionPercentage "+ completionPercentage);
//                }
//
//            }
//        });
//    }

}