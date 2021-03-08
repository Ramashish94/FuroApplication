package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;

public class InternetActivity extends AppCompatActivity {
    private TextView tryAgainInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        tryAgainInternet = findViewById(R.id.tryAgain);
        tryAgainInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
