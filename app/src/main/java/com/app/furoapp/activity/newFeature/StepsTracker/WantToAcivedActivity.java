package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.padometer.PedoMeterMainActivity;

public class WantToAcivedActivity extends AppCompatActivity {
    public ImageView ivContinue, ivAddNewSlot;
    Intent intent;
    public LinearLayout llLooseWeight, llStayHealthy, llStayActive;
    public ImageView ivLooseWeightUnselected, ivLooseWeightSelected, ivStayHealthyUnSelected, ivStayHealthySelected, ivStayActiveUnSelected, ivStayActiveSelected;
    public TextView tvLooseWeight, tvStayHealthy, tvStayActive, ivSkip;
    private String getAchievedVal;
    private boolean isAchievedSelected;

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
        llLooseWeight = findViewById(R.id.llLooseWeight);
        llStayHealthy = findViewById(R.id.llStayHealthy);
        llStayActive = findViewById(R.id.llStayActive);
        ivLooseWeightUnselected = findViewById(R.id.ivLooseWeightUnselected);
        ivLooseWeightSelected = findViewById(R.id.ivLooseWeightSelected);
        ivStayHealthyUnSelected = findViewById(R.id.ivStayHealthyUnSelected);
        ivStayHealthySelected = findViewById(R.id.ivStayHealthySelected);
        ivStayActiveUnSelected = findViewById(R.id.ivStayActiveUnSelected);
        ivStayActiveSelected = findViewById(R.id.ivStayActiveSelected);
        tvLooseWeight = findViewById(R.id.tvLooseWeight);
        tvStayHealthy = findViewById(R.id.tvStayHealthy);
        tvStayActive = findViewById(R.id.tvStayActive);
    }

    private void clicklistner() {
        llLooseWeight.setOnClickListener(v -> {
            isAchievedSelected = true;
            selectionView(llLooseWeight);
            ivLooseWeightSelected.setVisibility(View.VISIBLE);
            ivLooseWeightUnselected.setVisibility(View.GONE);
            ivStayHealthyUnSelected.setVisibility(View.VISIBLE);
            ivStayHealthySelected.setVisibility(View.GONE);
            ivStayActiveUnSelected.setVisibility(View.VISIBLE);
            ivStayActiveSelected.setVisibility(View.GONE);
            tvLooseWeight.setTextColor(Color.parseColor("#19CFE6"));
            tvStayHealthy.setTextColor(Color.parseColor("#ffffff"));
            tvStayActive.setTextColor(Color.parseColor("#ffffff"));
            getAchievedVal = String.valueOf(12000);
            Log.d("getAchievedVal", String.valueOf(getAchievedVal));

        });
        llStayHealthy.setOnClickListener(v -> {
            isAchievedSelected = true;
            selectionView(llStayHealthy);
            ivLooseWeightSelected.setVisibility(View.GONE);
            ivLooseWeightUnselected.setVisibility(View.VISIBLE);
            ivStayHealthyUnSelected.setVisibility(View.GONE);
            ivStayHealthySelected.setVisibility(View.VISIBLE);
            ivStayActiveUnSelected.setVisibility(View.VISIBLE);
            ivStayActiveSelected.setVisibility(View.GONE);
            tvLooseWeight.setTextColor(Color.parseColor("#ffffff"));
            tvStayHealthy.setTextColor(Color.parseColor("#19CFE6"));
            tvStayActive.setTextColor(Color.parseColor("#ffffff"));
            getAchievedVal = String.valueOf(1000);
            Log.d("getAchievedVal", String.valueOf(getAchievedVal));

        });
        llStayActive.setOnClickListener(v -> {
            isAchievedSelected = true;
            selectionView(llStayActive);
            ivLooseWeightSelected.setVisibility(View.GONE);
            ivLooseWeightUnselected.setVisibility(View.VISIBLE);
            ivStayHealthyUnSelected.setVisibility(View.VISIBLE);
            ivStayHealthySelected.setVisibility(View.GONE);
            ivStayActiveUnSelected.setVisibility(View.GONE);
            ivStayActiveSelected.setVisibility(View.VISIBLE);
            tvLooseWeight.setTextColor(Color.parseColor("#ffffff"));
            tvStayHealthy.setTextColor(Color.parseColor("#ffffff"));
            tvStayActive.setTextColor(Color.parseColor("#19CFE6"));
            getAchievedVal = String.valueOf(5000);
            Log.d("getAchievedVal", String.valueOf(getAchievedVal));
        });

        ivContinue.setOnClickListener(v -> {
            if (isAchievedSelected) {
                //intent = new Intent(getApplicationContext(), PedoMeterMainActivity.class);
                intent = new Intent(getApplicationContext(), FqStepsCounterActivity.class);
                intent.putExtra("getAchievedVal", getAchievedVal);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Please select which you want to achieved !", Toast.LENGTH_SHORT).show();
            }

        });
        ivSkip.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), CreateYourStepGoalActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void selectionView(LinearLayout selectedView) {
        llLooseWeight.setBackgroundResource(R.drawable.bg_view_clip);
        llStayHealthy.setBackgroundResource(R.drawable.bg_view_clip);
        llStayActive.setBackgroundResource(R.drawable.bg_view_clip);
        selectedView.setBackgroundResource(R.drawable.white_bg_for_what_we_achived);
    }
}