package com.example.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.delegate.IViewCallback;
import com.example.bakingapp.viewholder.RecipeViewHolder;
import com.example.bakingapp.viewholder.StepViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter {
    List<String> stepList = new ArrayList<>();
    IViewCallback callback = null;
    public void setData(List<String> step){
        this.stepList = step;
    }
    public void setCallback(IViewCallback callback) {
        this.callback = callback;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewholder = null;
        View tmpView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_step, parent, false);
        viewholder = new StepViewHolder(tmpView);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            final JSONObject json = new JSONObject(stepList.get(position));
            String step = json.getString("shortDescription");
            ((StepViewHolder) holder).setStep(step);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null){
                     callback.itemPressedCallback(json.toString());
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }
}
