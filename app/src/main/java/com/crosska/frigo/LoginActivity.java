package com.crosska.frigo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    EditText login_edittext;
    EditText password_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        login_edittext = findViewById(R.id.login_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        read_recent_account();
    }

    public void onBackPressed() {
        this.finishAffinity();
    }

    public void registry_button_clicked(View view) {
        Intent intent = new Intent(this, RegistryActivity.class);
        startActivity(intent);
    }

    public void login_button_clicked(View view) {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        try {
            String entered_login = login_edittext.getText().toString();
            String entered_password = password_edittext.getText().toString();
            Cursor query = database.rawQuery("SELECT 1 FROM accounts WHERE login = '" + entered_login + "' AND password = '" + entered_password + "';", null);
            if (query.moveToFirst()) {
                ((GlobalData) this.getApplication()).setLogin(entered_login);
                ((GlobalData) this.getApplication()).setPassword(entered_password);
                write_recent_account();
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Ошибка, неправильный логин или пароль", Toast.LENGTH_LONG).show();
            }
            query.close();
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            database.close();
        }
    }

    private void write_recent_account() {
        FileOutputStream FileOutput = null;
        try {
            FileOutput = openFileOutput("RecentAccounts.txt", MODE_PRIVATE);
            FileOutput.write(login_edittext.getText().toString().getBytes());
            FileOutput.write("\n".getBytes());
            FileOutput.write(password_edittext.getText().toString().getBytes());
        } catch (IOException ex) {
            Toast.makeText(this, "Ошибка при записи недавнего аккаунта", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (FileOutput != null) FileOutput.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void read_recent_account() {
        Context context = getApplicationContext();
        String pre_path = context.getFilesDir().getPath();
        String file_path = pre_path + "/RecentAccounts.txt";
        String[] dataLines = new String[2];
        try {
            File file = new File(file_path);
            FileReader FileInput = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(FileInput);
            int i = 0;
            String temp_line;
            while ((temp_line = bufferedReader.readLine()) != null) {
                dataLines[i] = temp_line;
                i++;
            }
            bufferedReader.close();
            ((GlobalData) this.getApplication()).setLogin(dataLines[0]);
            ((GlobalData) this.getApplication()).setPassword(dataLines[1]);
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            //Toast.makeText(this, "Ошибка при чтении недавнего аккаунта", Toast.LENGTH_LONG).show();
        }
    }

}
