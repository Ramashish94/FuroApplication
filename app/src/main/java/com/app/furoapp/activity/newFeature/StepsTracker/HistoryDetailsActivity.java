package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.app.furoapp.R;
import com.github.mikephil.charting.charts.BarChart;

public class HistoryDetailsActivity extends AppCompatActivity {

    private BarChart barchart;
    public SwitchCompat switchBtnWeekly, switchBtnMontly, switchBtnAllType;
    ImageView ivHistoryCross;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        initViews();
        clickEvent();

    }

    private void initViews() {
        barchart = findViewById(R.id.barchart);
        switchBtnWeekly = findViewById(R.id.switchBtnWeekly);
        switchBtnMontly = findViewById(R.id.switchBtnMonthly);
        switchBtnAllType = findViewById(R.id.switchBtnAlType);
        ivHistoryCross = findViewById(R.id.ivHistoryCross);
    }

    private void clickEvent() {
        switchBtnWeekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Weekly";
                    switchBtnAllType.setChecked(false);
                    switchBtnMontly.setChecked(false);

                }

            }
        });

        switchBtnMontly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Monthly";
                    switchBtnWeekly.setChecked(false);
                    switchBtnAllType.setChecked(false);

                }
            }
        });

        switchBtnAllType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "All Time";
                    switchBtnWeekly.setChecked(false);
                    switchBtnMontly.setChecked(false);
                }
            }
        });
    }
}