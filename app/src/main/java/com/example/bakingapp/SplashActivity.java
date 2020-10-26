package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    ImageView iv;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv = findViewById(R.id.splashicon);
        tv = findViewById(R.id.textApplication);
        setIconAnimation();
    }
    private void setTextAnimation(){
        final String text = "BACKING APP";
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int i=0;
            @Override
            public void run() {
                if(i == text.length()){
                    try {
                        Thread.sleep(300l);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    tv.setText(tv.getText().toString() + text.charAt(i));
                    handler.postDelayed(this, 70);
                }
                i++;
            }
        }, 1000);
    };
    private void setIconAnimation(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv.setAlpha((float) (iv.getAlpha() + 0.1));
                if(iv.getAlpha()>1.00){
                    setTextAnimation();
                }
                else{
                    handler.postDelayed(this, 100);
                }
            }
        }, 1000);
    }
}