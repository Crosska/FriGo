package com.crosska.frigo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    ImageView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        loading = findViewById(R.id.loading_imageview);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        loading.startAnimation(animation);

        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                SQLiteDatabase database = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                try {
                    //database.execSQL("DROP TABLE accounts;");
                    database.execSQL("CREATE TABLE IF NOT EXISTS accounts (login TEXT, password TEXT, name TEXT, surname TEXT, sex INTEGER, avatar TEXT, recent INTEGER);");
                    //database.execSQL("INSERT INTO accounts VALUES ('crosska', 'Doryan', 'Владислав', 'Музычин' , 1, 1);");
                    Cursor query = database.rawQuery("SELECT * FROM accounts WHERE recent = 1;", null);
                    if (query.moveToFirst()) {
                        do {
                            //Toast.makeText(this, login + " " + password + " " + recent, Toast.LENGTH_LONG).show();
                        }
                        while (query.moveToNext());
                    }
                    query.close();
                } catch (Exception ex) {
                    //Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
                } finally {
                    database.close();
                }
                loadingComplete();
            }
        }.start();
    }

    private void loadingComplete() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
