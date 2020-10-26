package com.example.bakingapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends AndroidViewModel {
    Context context;
    public PageViewModel(@NonNull Application application){
        super(application);
        this.context = getApplication().getApplicationContext();
    }

    private MutableLiveData<List<String>> listRecipe = new MutableLiveData<>();

    public LiveData<List<String>> getData(){
        return listRecipe;
    }


    public void getRecipe() {
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        RequestQueue queue = Volley.newRequestQueue(context);
        String response = "";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Brosky",response);
//                        List<Movie> movieList = new ArrayList<>();
                        List<String> recipes = new ArrayList<>();
                        try {
                            JSONArray json = new JSONArray(response);
                            for(int i=0;i<json.length();i++){
                                recipes.add(json.getString(i));
                            }
                            listRecipe.postValue(recipes);
                        } catch (JSONException e) {
                            Log.d("BroskyException",e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BroskyError",error.toString());
            }
        });
        queue.add(stringRequest);
    }
}