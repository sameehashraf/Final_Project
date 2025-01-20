package com.example.coursehubmanager.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.User;

import java.util.List;

@Dao
public interface AppDao {
    // User Operations
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User login(String email, String password);

    // Category Operations
    @Insert
    void insertCategory(Category category);

    @Query("SELECT * FROM categories")
    List<Category> getAllCategories();

    // Course Operations
    @Insert
    void insertCourse(Course course);

    @Query("SELECT * FROM courses WHERE categoryId = :categoryId")
    List<Course> getCoursesByCategory(int categoryId);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);
}

