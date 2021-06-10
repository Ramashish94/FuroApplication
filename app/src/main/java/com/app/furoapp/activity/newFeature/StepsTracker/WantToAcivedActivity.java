package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.app.furoapp.R;

public class WantToAcivedActivity extends AppCompatActivity {
    public ImageView ivContinue, ivSkip, ivAddNewSlot;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_to_acived);
        findViews();
        clicklistner();

    }

    private void findViews() {
        ivContinue = findViewById(R.id.ivContinue);
        ivSkip = findViewById(R.id.ivSkip);
    }

    private void clicklistner() {
        ivContinue.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), FqStepsCounterActivity.class);
            startActivity(intent);

        });
        ivSkip.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), StepGoalActivityActivity.class);
            startActivity(intent);

        });
    }

}