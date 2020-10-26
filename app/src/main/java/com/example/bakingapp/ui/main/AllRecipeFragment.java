package com.example.bakingapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.MainActivity2;
import com.example.bakingapp.R;
import com.example.bakingapp.adapter.RecipeAdapter;
import com.example.bakingapp.delegate.IViewCallback;
import com.example.bakingapp.viewmodel.PageViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AllRecipeFragment extends Fragment implements IViewCallback {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    RecyclerView rv;
    Context context;

    int indexGlobal;

//    public static AllRecipeFragment newInstance(int index) {
//        AllRecipeFragment fragment = new AllRecipeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(ARG_SECTION_NUMBER, index);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);


        pageViewModel.getRecipe();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        rv = root.findViewById(R.id.rv_recipe);
        context = container.getContext();
        rv.setLayoutManager(new LinearLayoutManager(container.getContext()));
        final RecipeAdapter recipeAdapter = new RecipeAdapter();
        recipeAdapter.setCallback(this);
        rv.setAdapter(recipeAdapter);

        pageViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                recipeAdapter.setData(strings);
                recipeAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    @Override
    public void itemPressedCallback(String jsonData) {
        Intent intent = new Intent(context, MainActivity2.class);
        intent.putExtra("jsondata", jsonData);
        startActivity(intent);
    }
}