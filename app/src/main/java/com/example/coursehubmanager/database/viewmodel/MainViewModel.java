package com.example.coursehubmanager.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coursehubmanager.database.entities.User;
import com.example.coursehubmanager.helpers.MyApplication;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.Lesson;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Category>> categories;
    private LiveData<List<Course>> courses;
//    private Course course;
    private AppDatabase db;
    private SharedPreferencesHelper prefsHelper;

    public MainViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(MyApplication.getInstance().getApplicationContext());
        prefsHelper = new SharedPreferencesHelper(application);
        categories = db.categoryDao().getAllCategories();
        courses = db.courseDao().getAllCourses();

    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public LiveData<List<Course>> getCoursesByCategoryId(int id) {
        return db.courseDao().getCoursesByCategory(id);
    }

    public LiveData<List<Course>> getCoursesByCompletion(boolean isCompleted) {
        return db.courseDao().getCoursesByCompletion(isCompleted);
    }

    public Course getCourseById(int courseId) {
        return db.courseDao().getCourseById(courseId);
    }

    public User getUserByEmail(String email) {
        return db.userDao().getUserByEmail(email);
    }

    public LiveData<List<Lesson>> getLessonsForCourse(int courseId) {
        return db.lessonDao().getLessonsByCourseId();
    }

//    public void addOrDeleteBookmark(int courseId) {
//        prefsHelper.addItemToList("bookmarks", courseId);
//    }

    public void updatePercentage(int courseId, double percentage) {
        db.courseDao().updateCoursePercentage(courseId, percentage);
    }

//    // حساب التقدم بناءً على الدروس المرتبطة بالكورس
//    public double getCourseCompletionPercentage(List<Lesson> lessons) {
//        if (lessons == null || lessons.isEmpty()) return 0;
//
//        double totalCompletion = 0;
//        for (Lesson lesson : lessons) {
//            totalCompletion += lesson.getCompletionPercentage();
//        }
//
//        return totalCompletion / lessons.size(); // النسبة المئوية المتوسطة لجميع الدروس
//    }

    public void updateUser(User user) {
        db.userDao().updateUser(user);
    }

    public void addCategory(Category category) {
        db.categoryDao().insertCategory(category);
    }
    public void updateCategory(Category category) {
        db.categoryDao().updateCategory(category);
    }
    public void addLesson(Lesson lesson) {
        db.lessonDao().insertLesson(lesson);
    }

    public void addCourse(Course course) {
        db.courseDao().insert(course);
    }
    public void deleteCourse(int courseId) {
        db.courseDao().deleteCourse(courseId);
    }

}
