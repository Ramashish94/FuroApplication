package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.app.furoapp.R;
import com.app.furoapp.utils.FuroPrefs;

public class PreviewActivityTwo extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_two);
        imageView = findViewById(R.id.imagePreview);
        FuroPrefs.getString(getApplicationContext(),"image");
    }
}
