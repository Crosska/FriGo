package com.crosska.frigo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistryActivity extends AppCompatActivity {

    static final int GALLERY_REQUEST = 1;

    EditText login_edittext;
    EditText password_edittext;
    EditText name_edittext;
    EditText surname_edittext;
    RadioButton male_radiobutton;
    RadioButton female_radiobutton;
    ImageView avatar_imageview;

    private String avatar_file_name = "";
    private String error_message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        login_edittext = findViewById(R.id.login_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        name_edittext = findViewById(R.id.name_edittext);
        surname_edittext = findViewById(R.id.surname_edittext);
        male_radiobutton = findViewById(R.id.male_radiobutton);
        female_radiobutton = findViewById(R.id.female_radiobutton);
        avatar_imageview = findViewById(R.id.avatar_imageview);
    }

    public void avatar_clicked(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;
        ImageView imageView = findViewById(R.id.avatar_imageview);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }
        }
    }

    public void create_account_button_clicked(View view) {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        try {
            if (login_edittext.getText().toString().equals("") && password_edittext.getText().toString().equals("")) {
                error_message = "Введите логин и пароль";
                throw new Exception();
            } else if (login_edittext.getText().toString().equals("")) {
                error_message = "Логин не может быть пустым";
                throw new Exception();
            } else if (password_edittext.getText().toString().equals("")) {
                error_message = "Пароль не может быть пустым";
                throw new Exception();
            } else {
                Cursor query = database.rawQuery("SELECT * FROM accounts WHERE login = '" + login_edittext.getText().toString() + "';", null);
                if (query.moveToFirst()) {
                    do {
                        error_message = "Данный логин уже занят";
                        throw new Exception();
                    }
                    while (query.moveToNext());
                }
                query.close();
            }
            String login = login_edittext.getText().toString();
            String password = password_edittext.getText().toString();
            String name = name_edittext.getText().toString();
            String surname = surname_edittext.getText().toString();
            int sex;
            if (male_radiobutton.isChecked()) {
                sex = 1;
            } else {
                sex = 0;
            }


            database.execSQL("INSERT INTO accounts VALUES ('" + login + "', '" + password + "', '" + name + "', '" + surname + "' , " + sex + ", '" + avatar_file_name + "' ,  0);");
            ((GlobalData) this.getApplication()).setLogin(login);
            ((GlobalData) this.getApplication()).setPassword(password);
            Toast.makeText(this, "Аккаунт успешно создан.", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception ex) {
            Toast.makeText(this, error_message, Toast.LENGTH_LONG).show();
        } finally {
            database.close();
        }
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        avatar_file_name = mImageName;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


}
