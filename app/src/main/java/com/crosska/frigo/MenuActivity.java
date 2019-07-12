package com.crosska.frigo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    ImageView logo_picture;
    Button log_button;
    Button register_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_menu);
        logo_picture = findViewById(R.id.image_logo);
        log_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);
        log_button.setMinWidth(register_button.getWidth());

        MenuStarted();
    }

    private void MenuStarted() {
        //Toast toast = Toast.makeText(getApplicationContext(), "Андрей", Toast.LENGTH_LONG );
        //toast.show();
    }

}
