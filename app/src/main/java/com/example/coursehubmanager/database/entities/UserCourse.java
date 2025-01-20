package com.example.coursehubmanager.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "user_course",
        primaryKeys = {"userId", "courseId"},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Course.class, parentColumns = "id", childColumns = "courseId", onDelete = ForeignKey.CASCADE)
        })
public class UserCourse {
    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "courseId")
    private int courseId;

    @ColumnInfo(name = "progress")
    private double progress; // مواضيع الكورس


    // Constructor, getters, setters...
}