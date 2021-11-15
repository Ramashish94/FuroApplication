package com.app.furoapp.activity.newFeature.caloriesCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.ContactUsActivity;

public class CalculateCaloriesStartActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_calculate_calories);
        tvStart = findViewById(R.id.tvStart);

        tvStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStart:
                Intent intent = new Intent(getApplicationContext(), LetsSetYourGoalActivity.class);
                startActivity(intent);
                break;


        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}