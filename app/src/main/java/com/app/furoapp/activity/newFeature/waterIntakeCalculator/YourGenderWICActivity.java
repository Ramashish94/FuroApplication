package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;

public class YourGenderWICActivity extends AppCompatActivity {
    public TextView tvMale, tvFemale;
    public ImageView ivIconOfMale, ivIconOfFemale,ivContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_gender_w_i_c);
        findViews();
        clickListiner();
    }

    private void findViews() {
        ivIconOfMale = findViewById(R.id.ivIconOfMale);
        ivIconOfFemale = findViewById(R.id.ivIconOfFemale);
        tvMale = findViewById(R.id.tvMale);
        tvFemale = findViewById(R.id.tvFemale);
        ivContinue=findViewById(R.id.ivContinue);
    }

    private void clickListiner() {
        ivIconOfMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivIconOfMale.setBackgroundResource(R.drawable.male_icon_selected);
                ivIconOfFemale.setBackgroundResource(R.drawable.unselect_femaleinwtc);
                tvMale.setTextColor(Color.parseColor("#4CCFF9"));
                tvFemale.setTextColor(Color.parseColor("#3D3D3D"));
            }
        });

        ivIconOfFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivIconOfFemale.setBackgroundResource(R.drawable.selected_female_icon);
                ivIconOfMale.setBackgroundResource(R.drawable.unselected_male_icon1);
                tvMale.setTextColor(Color.parseColor("#3D3D3D"));
                tvFemale.setTextColor(Color.parseColor("#4CCFF9"));
            }
        });
        ivContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TellUsMoreActivity.class);
                startActivity(intent);

            }
        });

    }
}