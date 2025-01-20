package com.example.coursehubmanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursehubmanager.database.entities.Lesson;

import java.util.List;

@Dao
public interface LessonDao {
    // إضافة درس جديد
    @Insert
    void insertLesson(Lesson lesson);

    // جلب الدروس حسب الكورس
    @Query("SELECT * FROM lessons ")
    LiveData<List<Lesson>> getLessonsByCourseId();

    // تحديث درس
    @Update
    void updateLesson(Lesson lesson);

    // حذف درس
    @Delete
    void deleteLesson(Lesson lesson);

    @Insert
    void insertLessons(List<Lesson> lessons);
//
//    @Query("SELECT COUNT(*) FROM lessons WHERE courseId = :courseId")
//    int getTotalLessonsCount(int courseId);
//
//    // عدد الدروس التي تمت مشاهدتها في كورس معين
//    @Query("SELECT COUNT(*) FROM lessons WHERE courseId = :courseId AND isWatched = 1")
//    int getWatchedLessonsCount(int courseId);

    @Query("SELECT COUNT(*) FROM lessons WHERE courseId = :courseId AND isWatched = 1")
    LiveData<Integer> getWatchedLessonsCountLive(int courseId);

    @Query("SELECT COUNT(*) FROM lessons WHERE courseId = :courseId")
    LiveData<Integer> getTotalLessonsCountLive(int courseId);

    @Query("UPDATE lessons SET isWatched = 1 WHERE lessonId = :lessonId")
    void markLessonAsWatched(int lessonId);



}