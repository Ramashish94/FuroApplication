package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.furoapp.R;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

public class TrackYourStepsStartActivity extends AppCompatActivity {
    public ImageView ivStartJourneyBtn, ivProceedWithSavedData, ivModifiedSavedData, ivCancel;
    private View includePopUpMenuOfTrackSteps;
    private LinearLayout llTrackYourSteps;
    Intent intent;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_steps_tracker);
        initViews();
        clickEvent();

    }

    private void initViews() {
        ivStartJourneyBtn = findViewById(R.id.ivStartJourneyBtn);
        ivProceedWithSavedData = findViewById(R.id.ivProceedWithSavedData);
        ivModifiedSavedData = findViewById(R.id.ivModifiedSavedData);
        ivCancel = findViewById(R.id.ivCancel);
        includePopUpMenuOfTrackSteps = findViewById(R.id.includePopUpMenuOfTrackSteps);
        llTrackYourSteps = findViewById(R.id.llTrackYourSteps);

    }

    private void clickEvent() {
        ivStartJourneyBtn.setOnClickListener(v -> {
            includePopUpMenuOfTrackSteps.setVisibility(View.VISIBLE);
            llTrackYourSteps.setClickable(false);
        });

        ivProceedWithSavedData.setOnClickListener(v -> {
            if (FuroPrefs.getString(getApplicationContext(), Constants.GENDER) != null
                    && FuroPrefs.getString(getApplicationContext(), Constants.USER_HEIGHT_IN_CM) != null
                    && FuroPrefs.getString(getApplicationContext(), Constants.USER_WEIGHT_IN_KG) != null) {
                intent = new Intent(getApplicationContext(), WantToAcivedActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), TellUsMoreOnTrackStepsActivity.class);
            }
            includePopUpMenuOfTrackSteps.setVisibility(View.GONE);
            startActivity(intent);
        });

        ivModifiedSavedData.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), TellUsMoreOnTrackStepsActivity.class);
            startActivity(intent);
            includePopUpMenuOfTrackSteps.setVisibility(View.GONE);
        });

        ivCancel.setOnClickListener(v -> {
            includePopUpMenuOfTrackSteps.setVisibility(View.GONE);
        });
    }


}