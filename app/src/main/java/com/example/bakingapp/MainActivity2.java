package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakingapp.Database.ApplicationDB;
import com.example.bakingapp.Database.entity.RecipeEntity;
import com.example.bakingapp.adapter.StepAdapter;
import com.example.bakingapp.delegate.IViewCallback;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements IViewCallback {
    TextView tvNamaRecipe, tvNumOfServings, tvIngredients;
    ImageView start;
    RecyclerView rv;
    Button btnStarOff, btnStarOn;
    String tempJsonData = "";
    LinearLayout loader;
    PlayerView playerView;
    SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvNamaRecipe = findViewById(R.id.tv_nama_recipe);
        tvNumOfServings = findViewById(R.id.num_of_servings);
        tvIngredients = findViewById(R.id.tv_ingredients);
        rv = findViewById(R.id.rv_step);
        btnStarOff = findViewById(R.id.btn_star_off);
        btnStarOn = findViewById(R.id.btn_star_on);
        loader = findViewById(R.id.loader);
        playerView = findViewById(R.id.video_view);
        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext());


        loader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.setVisibility(View.GONE);
                player.stop();
            }
        });

        StepAdapter stepAdapter = new StepAdapter();
        stepAdapter.setCallback(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(stepAdapter);
        setStar();
        Intent intent = getIntent();
        try {
            JSONObject json = new JSONObject(intent.getStringExtra("jsondata"));
            tempJsonData = intent.getStringExtra("jsondata");
            tvNamaRecipe.setText(json.getString("name"));
            tvNumOfServings.setText("Number of Servings: " + json.getString("servings"));
            JSONArray ingredients = json.getJSONArray("ingredients");
            JSONArray steps = json.getJSONArray("steps");
            String temp = "";
            for (int i = 0; i < ingredients.length(); i++) {
                temp += (i + 1) + ". " + ingredients.getJSONObject(i).getString("ingredient") + ": " + ingredients.getJSONObject(i).getString("quantity") + " " + ingredients.getJSONObject(i).getString("measure") + "\n";
            }
            List<String> step = new ArrayList<>();
            for (int i = 0; i < steps.length(); i++) {
                step.add(steps.getJSONObject(i).toString());
            }
            tvIngredients.setText(temp);
            stepAdapter.setData(step);
            stepAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Toast.makeText(this, "Gagal mengirim data", Toast.LENGTH_LONG);
            finish();
            e.printStackTrace();
        }
        btnStarOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFav = false;
                setStar();
                setDatabase();
            }
        });
        btnStarOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFav = true;
                setStar();
                setDatabase();
            }
        });

    }

    Boolean isFav = true;

    @Override
    protected void onResume() {
        super.onResume();
        getListRecipe();
    }

    @SuppressLint("ResourceAsColor")
    public void setStar() {
        if (isFav) {
            btnStarOn.setVisibility(View.VISIBLE);
            btnStarOff.setVisibility(View.GONE);
            Log.d("Brosky", "Terang");


        } else {
            btnStarOn.setVisibility(View.GONE);
            btnStarOff.setVisibility(View.VISIBLE);
            Log.d("Brosky", "Gelap");
        }
    }

    public void setDatabase() {
        RecipeEntity re = new RecipeEntity();
        re.setData(tempJsonData);
        if (isFav) {
            ApplicationDB.getInstance(getApplicationContext()).dao().insertRecipe(re);
            Log.d("BroskyInsert", "Insert Data sukses" + tempJsonData);
        } else {
            List<RecipeEntity> data;
            int idDatabase = 0;
            data = ApplicationDB.getInstance(getApplicationContext()).dao().selectRecipe();
            for (int i = 0; i < data.size(); i++) {
                try {
                    JSONObject json = new JSONObject(data.get(i).getData());
                    JSONObject id = new JSONObject(tempJsonData);
                    if (json.getString("id").equals(id.getString("id"))) {
                        idDatabase = data.get(i).getId();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Brosky", "Disini2");
                }
            }
            re.setId(idDatabase);
            ApplicationDB.getInstance(getApplicationContext()).dao().deleteRecipe(re);
            Log.d("BroskyDelete", "Delete Data sukses" + idDatabase);
        }
    }

    @Override
    public void itemPressedCallback(String jsonData) {
        loader.setVisibility(View.VISIBLE);
        try {
            JSONObject json = new JSONObject(jsonData);
            String url = json.getString("videoURL");
            MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                    .createMediaSource(Uri.parse(url));
            player.prepare(mediaSource);
            playerView.setPlayer(player);
            player.setPlayWhenReady(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Brosky", jsonData);

    }

    private void getListRecipe() {
        List<RecipeEntity> data;
        data = ApplicationDB.getInstance(getApplicationContext()).dao().selectRecipe();
        Boolean isFav = false;
        for (int i = 0; i < data.size(); i++) {
            try {
                JSONObject json = new JSONObject(data.get(i).getData());
                JSONObject id = new JSONObject(tempJsonData);
                if (json.getString("id").equals(id.getString("id"))) {
                    isFav = true;
                }
            } catch (JSONException e) {
                Log.d("Brosky", "Disini");
                e.printStackTrace();
            }
        }
        this.isFav = isFav;
        setStar();
        Log.d("BroskyGetListRecipe", data.toString());
    }
}