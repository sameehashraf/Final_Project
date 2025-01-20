package com.example.coursehubmanager.adapters;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursehubmanager.R;
import com.example.coursehubmanager.helpers.MyApplication;
import com.example.coursehubmanager.helpers.OnItemClickListener;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.databinding.RecCourseItemsBinding;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    private List<Course> courses = new ArrayList<>();
    private OnItemClickListener listener;
    String category="";
    boolean isBookmarked;

    // Setter for the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecCourseItemsBinding binding = RecCourseItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferencesHelper prefsHelper = new SharedPreferencesHelper(MyApplication.getInstance().getApplicationContext());

        Course course = courses.get(position);
        holder.binding.txtCourseName.setText(course.getCourseName());
        holder.binding.txtCategory.setText(category);

        byte[] decodedString = Base64.decode(course.getCourseImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Glide.with(holder.binding.getRoot())
                .load(decodedByte)
                .placeholder(R.drawable.course)
                .into(holder.binding.imgCourse);


        if (isBookmarked) {
            holder.binding.imgBookmark.setImageResource(R.drawable.bookmark_filled);
            isBookmarked = true;
        } else {
            holder.binding.imgBookmark.setImageResource(R.drawable.bookmark_outlined);
            isBookmarked = false;
        }

        holder.binding.imgBookmark.setOnClickListener(v -> {
            isBookmarked = !isBookmarked;
            notifyItemChanged(position); // تحديث العنصر في الـ RecyclerView
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position, false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final RecCourseItemsBinding binding;

        public ViewHolder(RecCourseItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Course> newData, String category) {
        this.courses = newData;
        this.category = category;
        notifyDataSetChanged();
    }
}
