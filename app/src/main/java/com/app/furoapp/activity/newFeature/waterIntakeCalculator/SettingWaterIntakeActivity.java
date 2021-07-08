package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.AddNewSlotPreferActivity;

public class SettingWaterIntakeActivity extends AppCompatActivity {
    public ImageView ivBackIcon;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_water_intake);

        findViews();
        clickEvent();
    }


    private void findViews() {
        ivBackIcon = findViewById(R.id.ivBackIcon);
    }


    private void clickEvent() {
        ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), CreatePlaneActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(getApplicationContext(), CreatePlaneActivity.class);
        startActivity(intent);
        finish();
    }
}