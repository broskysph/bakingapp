package com.example.bakingapp.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView tv_recipe;
    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_recipe = itemView.findViewById(R.id.tv_recipe);
    }
    public void setRecipe(String name){
        tv_recipe.setText(name);
    }
}
