package com.example.bakingapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Database.ApplicationDB;
import com.example.bakingapp.Database.entity.RecipeEntity;
import com.example.bakingapp.MainActivity2;
import com.example.bakingapp.R;
import com.example.bakingapp.adapter.RecipeAdapter;
import com.example.bakingapp.delegate.IViewCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements IViewCallback {
    Context context;
    RecyclerView rv;
    final RecipeAdapter recipeAdapter = new RecipeAdapter();
    public FavoriteFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fav, container, false);
        context = rootView.getContext();

        rv = rootView.findViewById(R.id.rv_recipe);
        context = container.getContext();
        rv.setLayoutManager(new LinearLayoutManager(container.getContext()));

        recipeAdapter.setCallback(this);
        rv.setAdapter(recipeAdapter);
        return rootView;
    }
    private void getListRecipe(){
        List<RecipeEntity> data;
        data = ApplicationDB.getInstance(context).dao().selectRecipe();
        Boolean isFav = false;
        List<String> temp = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            try {
                JSONObject json = new JSONObject(data.get(i).getData());
                temp.add(json.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        recipeAdapter.setData(temp);
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getListRecipe();
    }

    @Override
    public void itemPressedCallback(String jsonData) {
        Intent intent = new Intent(context, MainActivity2.class);
        intent.putExtra("jsondata", jsonData);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Brosky", "result===========");
        if(requestCode == 100){
            getListRecipe();
            Log.d("Brosky", "Masuk if");
        }
        else{
            Log.d("Brosky", "Masuk else");
        }
    }
}
