package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;

public class FqStepsCounterActivity extends AppCompatActivity {
    public ImageView ivBackIcon, ivSetting, ivLeadBord, ivModified, ivHistory;
    public TextView tvCountsSteps, tvTotNumberOfSteps, tvTimes, tvDistance, tvActivateStepsCounter, tvDiActivate;
    public View incudeCongratsStepsTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fq_steps_counter);
        initViews();
        clickEvent();
    }

    private void initViews() {
        ivBackIcon = findViewById(R.id.ivSetting);
        ivSetting = findViewById(R.id.ivSetting);
        ivLeadBord = findViewById(R.id.ivLeadBord);
        ivModified = findViewById(R.id.ivModified);
        ivHistory = findViewById(R.id.ivHistory);
        tvCountsSteps = findViewById(R.id.tvCountsSteps);
        tvTotNumberOfSteps = findViewById(R.id.tvTotNumberOfSteps);
        tvTimes = findViewById(R.id.tvTimes);
        tvDistance = findViewById(R.id.tvDistance);
        tvActivateStepsCounter = findViewById(R.id.tvActivateStepsCounter);
        tvDiActivate = findViewById(R.id.tvDiActivate);
        incudeCongratsStepsTrack = findViewById(R.id.incudeCongratsStepsTrack);
    }

    private void clickEvent() {
        ivBackIcon.setOnClickListener(v -> {
            finish();
        });

        ivSetting.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
            startActivity(intent);
        });
        ivLeadBord.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LeadBoardActivity.class);
            startActivity(intent);
        });

        tvActivateStepsCounter.setOnClickListener(v -> {
            tvDiActivate.setVisibility(View.VISIBLE);
            tvActivateStepsCounter.setVisibility(View.GONE);
        });
        tvDiActivate.setOnClickListener(v -> {
            tvDiActivate.setVisibility(View.VISIBLE);
            tvActivateStepsCounter.setVisibility(View.GONE);
        });

    }
}