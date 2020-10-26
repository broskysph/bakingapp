package com.example.bakingapp.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;

public class StepViewHolder extends RecyclerView.ViewHolder {
    TextView tv_step;
    public StepViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_step = itemView.findViewById(R.id.tv_step);
    }
    public void setStep(String name){
        tv_step.setText(name);
    }
}
