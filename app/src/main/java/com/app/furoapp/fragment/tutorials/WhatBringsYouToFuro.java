package com.app.furoapp.fragment.tutorials;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.WhatbringstoyoufuroBinding;
import com.app.furoapp.utils.FuroPrefs;

import java.util.ArrayList;
import java.util.List;

import static com.app.furoapp.enums.EnumConstants.HOME_TUTORIAL_PAGE;
import static com.app.furoapp.enums.EnumConstants.TUTORIAL_HEIGHT;

public class WhatBringsYouToFuro extends Fragment {
    TutorialScreen tutorialScreen;
    private TextView loseWeight, getInShape, runMoreOften, fitnessTrainingTv,  next_tv,eatinghaealtytv,healthylifestyletv,staymotivatedtv,somethingelsetv;
    String lose_weight, get_in_shape, eating_healthy, healthy_lifestyle, stay_motivated,fitness_training_often,something_else;


    List<String> hash_set = new ArrayList<String>();
    WhatbringstoyoufuroBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tutorialScreen = (TutorialScreen) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.whatbringstoyoufuro, container, false);
        View view = binding.getRoot();
        fitnessTrainingTv= binding.fitnesstraining;
        loseWeight = binding.tvLoseWeight;
        eatinghaealtytv = binding.eatinghealthy;
        healthylifestyletv = binding.healthylifestyle;
        staymotivatedtv= binding.etstaymotivated;
        getInShape = binding.tvGetInShape;

       /* getInShape = binding.tvGetInShape;*/
      /*  runMoreOften = binding.tvRunMoreOften;*/
        /*runARace = binding.tvRunARace;*/
        next_tv = binding.NEXT;
        somethingelsetv = binding.etsomethingelse;
        next_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                FuroPrefs.putString(getContext(),"whatbringstoyouarraylist", String.valueOf(hash_set));
                tutorialScreen.setDisplayFragment(TUTORIAL_HEIGHT, bundle);
            }
        });
        setOnClickListeners();
        setOnClickListeners2();

        setOnClickListeners4();
        setOnClickListeners5();
        setOnClickListeners6();
        setOnClickListeners7();
        setOnClickListeners8();
        setOnClickListnerBack();

        return view;
    }

    public WhatBringsYouToFuro() {

    }

    public static WhatBringsYouToFuro newInstance(String name) {
        WhatBringsYouToFuro fragment = new WhatBringsYouToFuro();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOnClickListeners() {
        binding.tvLoseWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lose_weight = loseWeight.getText().toString().trim();

                if (isSelected(lose_weight)) {
                   loseWeight.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                   next_tv.setVisibility(View.VISIBLE);



                } else {
                    loseWeight.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }


            }
        });


    }

    private void setOnClickListeners2() {
        binding.tvGetInShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_in_shape = getInShape.getText().toString().trim();

                if (isSelected(get_in_shape)) {
                    getInShape.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    getInShape.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }

            }
        });


    }



    private void setOnClickListeners4() {
        binding.fitnesstraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fitness_training_often = fitnessTrainingTv.getText().toString().trim();
                if (isSelected(fitness_training_often)) {
                    fitnessTrainingTv.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);

                } else {
                    fitnessTrainingTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }
            }
        });


    }

    private void setOnClickListeners5() {
        binding.eatinghealthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eating_healthy = eatinghaealtytv.getText().toString().trim();
                if (isSelected(eating_healthy)) {
                    eatinghaealtytv.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    eatinghaealtytv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }
            }

        });


    }

    private void setOnClickListeners6() {
        binding.healthylifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                healthy_lifestyle = healthylifestyletv.getText().toString().trim();

                if (isSelected(healthy_lifestyle)) {
                    healthylifestyletv.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    healthylifestyletv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }
            }

        });


    }

    private void setOnClickListeners7() {
        binding.etstaymotivated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stay_motivated = staymotivatedtv.getText().toString().trim();

                if (isSelected(stay_motivated)) {
                    staymotivatedtv.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    staymotivatedtv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }
            }

        });


    }
    private void setOnClickListeners8() {
        binding.etsomethingelse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                something_else = somethingelsetv.getText().toString().trim();

                if (isSelected(something_else)) {
                    somethingelsetv.setBackgroundResource(R.drawable.background_border_gray_with_white_background_rounded);
                    next_tv.setVisibility(View.VISIBLE);
                } else {
                    somethingelsetv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_border_gray_with_white_back_rounded));

                }
            }

        });


    }


    private void setOnClickListnerBack() {
        binding.ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                tutorialScreen.setDisplayFragment(HOME_TUTORIAL_PAGE, bundle);
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




