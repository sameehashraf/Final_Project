package com.example.coursehubmanager.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    // إضافة مستخدم جديد
    @Insert
    void insertUser(User user);

    // جلب مستخدم باستخدام البريد الإلكتروني وكلمة المرور
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User login(String email, String password);

    // جلب مستخدم باستخدام البريد الإلكتروني وكلمة المرور
    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    // تحديث بيانات المستخدم
    @Update
    void updateUser(User user);

    // حذف مستخدم
    @Delete
    void deleteUser(User user);

    @Insert
    void insertUsers(List<User> users);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

}
