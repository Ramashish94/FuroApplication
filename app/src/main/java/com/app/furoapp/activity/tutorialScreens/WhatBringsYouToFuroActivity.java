package com.app.furoapp.activity.tutorialScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.SplashActivity;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.ActivityWhatBringsYouToFuroBinding;
import com.app.furoapp.utils.FuroPrefs;

import java.util.ArrayList;
import java.util.List;


public class WhatBringsYouToFuroActivity extends AppCompatActivity {
    ActivityWhatBringsYouToFuroBinding binding;
    TutorialScreen tutorialScreen;
    private TextView loseWeight,skip, getInShape, fitnessTrainingTv,  next_tv,eatinghaealtytv,healthylifestyletv,staymotivatedtv,somethingelsetv;
    String lose_weight, get_in_shape, eating_healthy, healthy_lifestyle, stay_motivated,fitness_training_often,something_else;

    List<String> hash_set = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_what_brings_you_to_furo);
        fitnessTrainingTv= binding.fitnesstraining;
        loseWeight = binding.tvLoseWeight;
        skip = binding.tvTextskip;
        eatinghaealtytv = binding.eatinghealthy;
        healthylifestyletv = binding.healthylifestyle;
        staymotivatedtv= binding.etstaymotivated;
        getInShape = binding.tvGetInShape;
        next_tv = binding.NEXT;
        somethingelsetv = binding.etsomethingelse;


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WhatBringsYouToFuroActivity.this, HomeMainActivity.class);
                intent.putExtra("contestpage","");
                startActivity(intent);
                finish();
            }
        });

        next_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hash_set.size() >0){

                    FuroPrefs.putString(getApplicationContext(),"whatbringstoyouarraylist", String.valueOf(hash_set));
                    //    tutorialScreen.setDisplayFragment(TUTORIAL_HEIGHT, bundle);

                    Intent intent = new Intent(WhatBringsYouToFuroActivity.this, TellUsAboutYouActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(WhatBringsYouToFuroActivity.this, "Select atleast one goal  ", Toast.LENGTH_SHORT).show();
                }



            }
        });
        setOnClickListenerstvLoseWeight();
        setOnClickListeners2tvGetInShape();
        setOnClickListeners4fitness_training_often();
        setOnClickListeners5eatinghealthy();
        setOnClickListeners6healthy_lifestyle();
        setOnClickListeners7etstaymotivated();
        setOnClickListeners8etsomethingelse();
        setOnClickListnerBack();


    }


    private void setOnClickListenerstvLoseWeight() {
        binding.tvLoseWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lose_weight = loseWeight.getText().toString().trim();

                if (isSelected(lose_weight)) {
                    loseWeight.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    loseWeight.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }


            }
        });


    }

    private void setOnClickListeners2tvGetInShape() {
        binding.tvGetInShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_in_shape = getInShape.getText().toString().trim();

                if (isSelected(get_in_shape)) {
                    getInShape.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    getInShape.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }

            }
        });


    }



    private void setOnClickListeners4fitness_training_often() {
        binding.fitnesstraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fitness_training_often = fitnessTrainingTv.getText().toString().trim();
                if (isSelected(fitness_training_often)) {
                    fitnessTrainingTv.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);

                } else {
                    fitnessTrainingTv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }
            }
        });


    }

    private void setOnClickListeners5eatinghealthy() {
        binding.eatinghealthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eating_healthy = eatinghaealtytv.getText().toString().trim();
                if (isSelected(eating_healthy)) {
                    eatinghaealtytv.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    eatinghaealtytv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }
            }

        });


    }

    private void setOnClickListeners6healthy_lifestyle() {
        binding.healthylifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                healthy_lifestyle = healthylifestyletv.getText().toString().trim();

                if (isSelected(healthy_lifestyle)) {
                    healthylifestyletv.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    healthylifestyletv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }
            }

        });


    }

    private void setOnClickListeners7etstaymotivated() {
        binding.etstaymotivated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stay_motivated = staymotivatedtv.getText().toString().trim();

                if (isSelected(stay_motivated)) {
                    staymotivatedtv.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    staymotivatedtv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }
            }

        });


    }
    private void setOnClickListeners8etsomethingelse() {
        binding.etsomethingelse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                something_else = somethingelsetv.getText().toString().trim();

                if (isSelected(something_else)) {
                    somethingelsetv.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    somethingelsetv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }
            }

        });


    }


    private void setOnClickListnerBack() {
        binding.ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WhatBringsYouToFuroActivity.this, WelcomeUserYouAreInActivity.class);
                startActivity(intent);

            }
        });


    }


    public boolean isSelected(String text) {
        for (String string : hash_set) {
            if (string.equalsIgnoreCase(text)) {
                hash_set.remove(text);
                return false;
            }
        }
        hash_set.add(text);
        return true;

    }
}
