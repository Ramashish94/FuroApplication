package com.app.furoapp.activity.newFeature.caloriesCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.TellUsMoreOnTrackStepsActivity;

public class LetsSetYourGoalActivity extends AppCompatActivity implements View.OnClickListener {
    public LinearLayout llContinue;
    Intent intent;
    private TextView tvLooseWeight, tvMaintainWeight, tvGainWeight, tvNo3, tvNo4, tvNo5, tvSedentary, tvLightActive, tvModeratelyActive, tvVeryActive, tvActive;
    private boolean isAchievedWeightGoal, isNoOfMealSelected,isLevelOfActivity;
    private String getWeightGoalValue, getNoOfMealSelectedValue, getLevelOfActivityValue;
    private String TAG = "LetsSetYourGoalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_set_your_goal);
        initViews();
        clickEvent();
    }

    private void initViews() {
        tvLooseWeight = findViewById(R.id.tvLooseWeight);
        tvMaintainWeight = findViewById(R.id.tvMaintainWeight);
        tvGainWeight = findViewById(R.id.tvGainWeight);
        tvNo3 = findViewById(R.id.tvNo3);
        tvNo4 = findViewById(R.id.tvNo4);
        tvNo5 = findViewById(R.id.tvNo5);
        tvSedentary = findViewById(R.id.tvSedentary);
        tvLightActive = findViewById(R.id.tvLightActive);
        tvModeratelyActive = findViewById(R.id.tvModeratelyActive);
        tvVeryActive = findViewById(R.id.tvVeryActive);
        tvActive = findViewById(R.id.tvActive);
        llContinue = findViewById(R.id.llContinue);
    }

    private void clickEvent() {
        llContinue.setOnClickListener(this);
        tvLooseWeight.setOnClickListener(this);
        tvMaintainWeight.setOnClickListener(this);
        tvGainWeight.setOnClickListener(this);
        tvNo3.setOnClickListener(this);
        tvNo4.setOnClickListener(this);
        tvNo5.setOnClickListener(this);
        tvSedentary.setOnClickListener(this);
        tvLightActive.setOnClickListener(this);
        tvModeratelyActive.setOnClickListener(this);
        tvVeryActive.setOnClickListener(this);
        tvActive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llContinue:
                if (isAchievedWeightGoal) {
                    if (isNoOfMealSelected) {
                        if (isLevelOfActivity) {
                            intent = new Intent(getApplicationContext(), TellUsMoreOnTrackStepsActivity.class);
                            intent.putExtra("CalorieIntakeCalculator", "CalorieIntakeCalculator");
                            startActivity(intent);
                        }else {
                            Toast.makeText(this, "Please Select the Level of activity!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "Please Select the number of meals intake per day!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please Select Choose your weight goal!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvLooseWeight:
                isAchievedWeightGoal = true;
                tvLooseWeight.setTextColor(Color.parseColor("#19CFE6"));
                tvMaintainWeight.setTextColor(Color.parseColor("#979797"));
                tvGainWeight.setTextColor(Color.parseColor("#979797"));
                tvLooseWeight.setBackgroundResource(R.drawable.bg_set_ur_goal_calorie);
                tvMaintainWeight.setBackgroundResource(0);
                tvGainWeight.setBackgroundResource(0);
                getWeightGoalValue = tvLooseWeight.getText().toString();
                Log.d(TAG, "onClick() called with: tvLooseWeight = [" + getWeightGoalValue + "]");
                break;

            case R.id.tvMaintainWeight:
                isAchievedWeightGoal = true;
                tvMaintainWeight.setTextColor(Color.parseColor("#19CFE6"));
                tvLooseWeight.setTextColor(Color.parseColor("#979797"));
                tvGainWeight.setTextColor(Color.parseColor("#979797"));
                tvMaintainWeight.setBackgroundResource(R.drawable.bg_set_ur_goal_calorie);
                tvGainWeight.setBackgroundResource(0);
                tvLooseWeight.setBackgroundResource(0);
                getWeightGoalValue = tvMaintainWeight.getText().toString();
                Log.d(TAG, "onClick() called with: tvMaintainWeight = [" + getWeightGoalValue + "]");
                break;

            case R.id.tvGainWeight:
                isAchievedWeightGoal = true;
                tvGainWeight.setTextColor(Color.parseColor("#19CFE6"));
                tvMaintainWeight.setTextColor(Color.parseColor("#979797"));
                tvLooseWeight.setTextColor(Color.parseColor("#979797"));
                tvGainWeight.setBackgroundResource(R.drawable.bg_set_ur_goal_calorie);
                tvMaintainWeight.setBackgroundResource(0);
                tvLooseWeight.setBackgroundResource(0);
                getWeightGoalValue = tvGainWeight.getText().toString();
                Log.d(TAG, "onClick() called with: tvGainWeight = [" + getWeightGoalValue + "]");
                break;
            case R.id.tvNo3:
                isNoOfMealSelected = true;
                tvNo3.setTextColor(Color.parseColor("#19CFE6"));
                tvNo4.setTextColor(Color.parseColor("#979797"));
                tvNo5.setTextColor(Color.parseColor("#979797"));
                getNoOfMealSelectedValue = tvNo3.getText().toString();
                Log.d(TAG, "onClick() called with: tvNo3 = [" + getNoOfMealSelectedValue + "]");
                break;
            case R.id.tvNo4:
                isNoOfMealSelected = true;
                tvNo3.setTextColor(Color.parseColor("#979797"));
                tvNo4.setTextColor(Color.parseColor("#19CFE6"));
                tvNo5.setTextColor(Color.parseColor("#979797"));
                getNoOfMealSelectedValue = tvNo4.getText().toString();
                Log.d(TAG, "onClick() called with: tvNo4 = [" + getNoOfMealSelectedValue + "]");
                break;
            case R.id.tvNo5:
                isNoOfMealSelected = true;
                tvNo3.setTextColor(Color.parseColor("#979797"));
                tvNo4.setTextColor(Color.parseColor("#979797"));
                tvNo5.setTextColor(Color.parseColor("#19CFE6"));
                getNoOfMealSelectedValue = tvNo5.getText().toString();
                Log.d(TAG, "onClick() called with: tvNo5 = [" + getNoOfMealSelectedValue + "]");
                break;
            case R.id.tvSedentary:
                isLevelOfActivity=true;
                tvSedentary.setTextColor(Color.parseColor("#19CFE6"));
                tvLightActive.setTextColor(Color.parseColor("#979797"));
                tvModeratelyActive.setTextColor(Color.parseColor("#979797"));
                tvVeryActive.setTextColor(Color.parseColor("#979797"));
                tvActive.setTextColor(Color.parseColor("#979797"));
                getLevelOfActivityValue = tvSedentary.getText().toString();
                Log.d(TAG, "onClick() called with: tvSedentary = [" + getLevelOfActivityValue + "]");
                break;
            case R.id.tvLightActive:
                isLevelOfActivity=true;
                tvSedentary.setTextColor(Color.parseColor("#979797"));
                tvLightActive.setTextColor(Color.parseColor("#19CFE6"));
                tvModeratelyActive.setTextColor(Color.parseColor("#979797"));
                tvVeryActive.setTextColor(Color.parseColor("#979797"));
                tvActive.setTextColor(Color.parseColor("#979797"));
                getLevelOfActivityValue = tvLightActive.getText().toString();
                Log.d(TAG, "onClick() called with: tvLightActive = [" + getLevelOfActivityValue + "]");
                break;
            case R.id.tvModeratelyActive:
                isLevelOfActivity=true;
                tvSedentary.setTextColor(Color.parseColor("#979797"));
                tvLightActive.setTextColor(Color.parseColor("#979797"));
                tvModeratelyActive.setTextColor(Color.parseColor("#19CFE6"));
                tvVeryActive.setTextColor(Color.parseColor("#979797"));
                tvActive.setTextColor(Color.parseColor("#979797"));
                getLevelOfActivityValue = tvModeratelyActive.getText().toString();
                Log.d(TAG, "onClick() called with: tvModeratelyActive = [" + getLevelOfActivityValue + "]");
                break;
            case R.id.tvVeryActive:
                isLevelOfActivity=true;
                tvSedentary.setTextColor(Color.parseColor("#979797"));
                tvLightActive.setTextColor(Color.parseColor("#979797"));
                tvModeratelyActive.setTextColor(Color.parseColor("#979797"));
                tvVeryActive.setTextColor(Color.parseColor("#19CFE6"));
                tvActive.setTextColor(Color.parseColor("#979797"));
                getLevelOfActivityValue = tvVeryActive.getText().toString();
                Log.d(TAG, "onClick() called with: tvVeryActive = [" + getLevelOfActivityValue + "]");
                break;
            case R.id.tvActive:
                isLevelOfActivity=true;
                tvSedentary.setTextColor(Color.parseColor("#979797"));
                tvLightActive.setTextColor(Color.parseColor("#979797"));
                tvModeratelyActive.setTextColor(Color.parseColor("#979797"));
                tvVeryActive.setTextColor(Color.parseColor("#979797"));
                tvActive.setTextColor(Color.parseColor("#19CFE6"));
                getLevelOfActivityValue = tvActive.getText().toString();
                Log.d(TAG, "onClick() called with: tvActive = [" + getLevelOfActivityValue + "]");
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

   /* private boolean otherTestChargedValidation() {
        if (tvLooseWeight.isSelected() || tvMaintainWeight.isSelected() || tvGainWeight.isSelected()) {
            getWeightGoalVal = tvLooseWeight.getText().toString();
            Log.d(TAG, "otherTestChargedValidation() called: tvLooseWeight="+getWeightGoalVal);
        } else if (tvMaintainWeight.isSelected()) {
            getWeightGoalVal = tvLooseWeight.getText().toString();
            Log.d(TAG, "otherTestChargedValidation() called: tvMaintainWeight="+getWeightGoalVal);
        } else if (tvGainWeight.isSelected()) {
            getWeightGoalVal = tvLooseWeight.getText().toString();
            Log.d(TAG, "otherTestChargedValidation() called: tvGainWeight="+getWeightGoalVal);
        } else {
            Toast.makeText(this, "Please Select Choose your weight goal!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }*/

    private void selectionView(TextView selectedView) {
        tvLooseWeight.setBackgroundResource(R.drawable.bg_view_clip);
        tvMaintainWeight.setBackgroundResource(R.drawable.bg_view_clip);
        tvGainWeight.setBackgroundResource(R.drawable.bg_view_clip);
        selectedView.setBackgroundResource(R.drawable.white_bg_for_what_we_achived);
    }

    @Override
    protected void onResume() {
        super.onResume();
        clickEvent();
    }
}