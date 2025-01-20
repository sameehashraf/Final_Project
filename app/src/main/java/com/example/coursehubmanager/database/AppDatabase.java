package com.example.coursehubmanager.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.coursehubmanager.helpers.MyApplication;
import com.example.coursehubmanager.database.dao.CategoryDao;
import com.example.coursehubmanager.database.dao.CourseDao;
import com.example.coursehubmanager.database.dao.LessonDao;
import com.example.coursehubmanager.database.dao.UserDao;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.Lesson;
import com.example.coursehubmanager.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Course.class, Lesson.class, Category.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract CourseDao courseDao();

    public abstract LessonDao lessonDao();

    public abstract CategoryDao categoryDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addCallback(databaseCallback) // إضافة الكال باك
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // تشغيل كود لإضافة بيانات وهمية
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase database = getInstance(MyApplication.getInstance().getApplicationContext());
                populateFakeData(database);
            });
        }
    };

    private static void populateFakeData(AppDatabase database) {
        // التصنيفات
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Programming"));
        categories.add(new Category("Design"));
        categories.add(new Category("Business"));
        categories.add(new Category("Photography"));
        categories.add(new Category("Content Creation"));
        categories.add(new Category("Translating"));
        database.categoryDao().insertCategories(categories);

        // الكورسات
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Java Programming", "Learn Java basics.", "Dr. Mahmoud", 1, false, 49.99, 30, 30, "R.drawable.course.jpeg", 20));
        courses.add(new Course("Android Development", "Build Android apps.", "Dr. Yousef", 1, false, 59.99, 40, 35, "R.drawable.course.jpeg", 50));
        courses.add(new Course("UX Design", "Learn UX principles.", "Dr. Mohammed", 2, true, 29.99, 20, 20, "R.drawable.course.jpeg", 100));
        database.courseDao().insertCourses(courses);

        // الدروس
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(0, "Introduction to Java", "Java Basics", "https://youtube.com/java_intro", true, 100, 20));
        lessons.add(new Lesson(0, "OOP Concepts", "Object-Oriented Programming", "https://youtube.com/oop", false, 100, 80));
        lessons.add(new Lesson(1, "Android Basics", "Android Components", "https://youtube.com/android_basics", true, 100, 100));
        database.lessonDao().insertLessons(lessons);

        // المستخدمين
        List<User> users = new ArrayList<>();
        users.add(new User("Sameeh", "Sameeh@gmail.com", "123123123"));
        users.add(new User("Mo", "mo@gmail.com", "123123123"));
        users.add(new User("Admin", "Admin@Admin.com", "AdminAdmin"));
        database.userDao().insertUsers(users);
    }


}
