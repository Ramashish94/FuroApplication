package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.app.furoapp.R;
import com.app.furoapp.databinding.ActivityChallangeRuleBinding;

public class ChallangeRuleActivity extends AppCompatActivity {

    ActivityChallangeRuleBinding activityChallangeRuleBinding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChallangeRuleBinding = DataBindingUtil.setContentView(this, R.layout.activity_challange_rule);
        activityChallangeRuleBinding.crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
