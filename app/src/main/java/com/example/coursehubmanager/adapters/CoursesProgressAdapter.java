package com.example.coursehubmanager.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursehubmanager.helpers.OnItemClickListener;
import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.databinding.RecCourseProgressItemsBinding;

import java.util.ArrayList;
import java.util.List;

public class CoursesProgressAdapter extends RecyclerView.Adapter<CoursesProgressAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private List<Course> courses = new ArrayList<>();
    private List<Double> percentages = new ArrayList<>();

    int progress = 0;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecCourseProgressItemsBinding binding = RecCourseProgressItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courses.get(position);
//        double percentage = percentages.get(position);
        progress = (int) course.getPercentage() ;

        holder.binding.txtTeacherName.setText(course.getMentorName());
        holder.binding.txtCourseName.setText(course.getCourseName());

        Glide.with(holder.binding.getRoot())
                .load(R.drawable.course)
                .placeholder(R.drawable.course)
                .into(holder.binding.imgCourse);


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position, false);
            }
        });

        holder.binding.txtProgress.setText(progress + " %");
        holder.binding.progressBar.setProgress(progress);

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final RecCourseProgressItemsBinding binding;

        public ViewHolder(RecCourseProgressItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Course> newData/*, int prog*/) {
        this.courses = newData;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addPercentage(double newData/*, int prog*/) {
        percentages.add(newData);
        notifyDataSetChanged();
    }
}
