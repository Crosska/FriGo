package com.crosska.frigo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    ImageView loading;
    TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        loading = findViewById(R.id.loading_imageview);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        loading.startAnimation(animation);
        label = findViewById(R.id.app_name_textview);
        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                loadingComplete();
            }
        }.start();

    }

    private void loadingComplete() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
