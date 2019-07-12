package com.crosska.frigo;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    ImageView logo_picture;
    Button register_button;
    Button login_button;
    TextView login_textview;
    TextView password_textview;
    EditText login_edittext;
    EditText password_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_menu);

        AssignElements();

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/bahnschrift.ttf");
        register_button.setTypeface(type);
        login_button.setTypeface(type);
        login_textview.setTypeface(type);
        password_textview.setTypeface(type);
        login_edittext.setTypeface(type);
        password_edittext.setTypeface(type);

        MenuStarted();
    }

    private void MenuStarted() {
        //Toast toast = Toast.makeText(getApplicationContext(), "Андрей", Toast.LENGTH_LONG );
        //toast.show();
    }

    private void AssignElements() {
        logo_picture = findViewById(R.id.image_logo);
        register_button = findViewById(R.id.register_button);
        login_button = findViewById(R.id.login_button);
        login_textview = findViewById(R.id.login_textview_help);
        password_textview = findViewById(R.id.password_textview_help);
        login_edittext = findViewById(R.id.login_enter);
        password_edittext = findViewById(R.id.password_enter);
    }

}
