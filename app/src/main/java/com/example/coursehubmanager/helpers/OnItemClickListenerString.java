package com.example.coursehubmanager.helpers;


import com.example.coursehubmanager.database.entities.Category;

public interface OnItemClickListenerString<T> {
    void onItemClick(Category data);
}