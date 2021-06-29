package com.app.furoapp.activity.newFeature.bmiCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

public class FindYourBmiActivity extends AppCompatActivity {
    public LinearLayout llFindUrs, llFindForOthers;
    public TextView tvRecordedScores, tvFindYours, tvFidForOthers;
    private String findYoursType;
    public String findBmiType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_b_m_i);
        initViews();
        clickEvent();
    }

    private void initViews() {
        llFindUrs = findViewById(R.id.llFindUrs);
        llFindForOthers = findViewById(R.id.llFindForOthers);
        tvRecordedScores = findViewById(R.id.tvRecordedScores);
        tvFindYours = findViewById(R.id.tvFindYours);
        tvFidForOthers = findViewById(R.id.tvFidForOthers);
    }

    private void clickEvent() {
        llFindUrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                llFindForOthers.setBackgroundResource(R.drawable.find_yours_others);
                llFindUrs.setBackgroundResource(R.drawable.btn_next_shape);
                findBmiType = tvFindYours.getText().toString();
                Intent intent = new Intent(getApplicationContext(), SelectGenderAndAgeActivity.class);
                intent.putExtra("findBmiType", findBmiType);
                FuroPrefs.putString(getApplicationContext(), Constants.FIND_TYPE, findYoursType);
                startActivity(intent);

            }
        });

        llFindForOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                llFindForOthers.setBackgroundResource(R.drawable.btn_next_shape);
                llFindUrs.setBackgroundResource(R.drawable.find_yours_others);
                Intent intent = new Intent(getApplicationContext(), CalculateBMIActivity.class);
                startActivity(intent);
            }
        });

        tvRecordedScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordDataActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llFindUrs.setBackgroundResource(R.drawable.find_yours_others);
        llFindForOthers.setBackgroundResource(R.drawable.find_yours_others);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}