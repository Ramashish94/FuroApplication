package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.app.furoapp.R;

public class AddNewSlotPreferActivity extends AppCompatActivity {
    public ImageView ivContinue, ivSkip;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_slot_prefer);
        findViews();
        clicklistner();
    }

    private void findViews() {
        ivContinue = findViewById(R.id.ivContinue);
        ivSkip = findViewById(R.id.ivSkip);
    }


    private void clicklistner() {
        ivContinue.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), StepGoalActivityActivity.class);
            startActivity(intent);
            finish();
        });
        ivSkip.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), WantToAcivedActivity.class);
            startActivity(intent);
            finish();
        });
    }

}