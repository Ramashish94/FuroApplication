package com.app.furoapp.activity.newFeature.bmiCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.furoapp.R;

public class FindYourBmiActivity extends AppCompatActivity {
    public LinearLayout llFindUrs, llFindYoursOthers;
    public TextView tvRecordedScores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_b_m_i);
        initViews();
        clickEvent();
    }

    private void initViews() {
        llFindUrs = findViewById(R.id.llFindUrs);
        llFindYoursOthers = findViewById(R.id.llFindYoursOthers);
        tvRecordedScores = findViewById(R.id.tvRecordedScores);
    }

    private void clickEvent() {
        llFindUrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llFindYoursOthers.setBackgroundResource(R.drawable.find_yours_others);
                llFindUrs.setBackgroundResource(R.drawable.btn_next_shape);
                Intent intent = new Intent(getApplicationContext(), SelectGenderAndAgeActivity.class);
                startActivity(intent);

            }
        });

        llFindYoursOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llFindYoursOthers.setBackgroundResource(R.drawable.btn_next_shape);
                llFindUrs.setBackgroundResource(R.drawable.find_yours_others);
                Intent intent = new Intent(getApplicationContext(), SelectGenderAndAgeActivity.class);
                startActivity(intent);
            }
        });

        tvRecordedScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordedScoreActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llFindUrs.setBackgroundResource(R.drawable.find_yours_others);
        llFindYoursOthers.setBackgroundResource(R.drawable.find_yours_others);

    }
}