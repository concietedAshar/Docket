package com.mrash.docket;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    //variable
    Animation topAnim,bottomAnim;
    ImageView imgDocket;
    TextView tvDocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //hooks

        imgDocket = findViewById(R.id.splash_logo);
        tvDocket = findViewById(R.id.docket_name);

        imgDocket.setAnimation(topAnim);
        tvDocket.setAnimation(bottomAnim);
        //imgDocker.setAnimation(topAnim);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, com.mrash.docket.LoginActivity.class);
                    startActivity(intent);
                    finish();
            }
        },SPLASH_SCREEN);

    }

}