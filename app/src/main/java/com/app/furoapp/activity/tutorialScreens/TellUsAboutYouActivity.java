package com.app.furoapp.activity.tutorialScreens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.ActivityTellUsAboutYouBinding;
import com.app.furoapp.model.whatBringsYoutoFuro.WhatBringsYouToFuroRequest;
import com.app.furoapp.model.whatBringsYoutoFuro.WhatBringsYouToFuroResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TellUsAboutYouActivity extends AppCompatActivity {

    ActivityTellUsAboutYouBinding binding;

    TextView weightPickerValueTv, heightPickerValueTv, getStarted, tvFeet, tvInches;
    private String whatBringsToYouFuro, userId;
    public String userHeightInCm, userWeightInKg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tell_us_about_you);

        whatBringsToYouFuro = (FuroPrefs.getString(getApplicationContext(), "whatbringstoyouarraylist"));
        userId = FuroPrefs.getString(getApplicationContext(), "loginUserId");

        tvFeet = binding.feet;
        tvInches = binding.inches;
        setOnClickListnerBack();
        setOntvGetStartedClickListner();


        heightPickerValueTv = findViewById(R.id.height_value_tv);
        final RulerValuePicker heightPicker = findViewById(R.id.height_ruler_picker);
        heightPicker.selectValue(156);
        heightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onValueChange(final int selectedValue) {
                userHeightInCm = String.valueOf(selectedValue);
                heightPickerValueTv.setText(userHeightInCm + " cms");
                centimeterToFeet(String.valueOf(selectedValue));

            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                userHeightInCm = String.valueOf(selectedValue);
                heightPickerValueTv.setText(userHeightInCm + " cms");
            }
        });

        weightPickerValueTv = findViewById(R.id.weight_value_tv);
        final RulerValuePicker weightPicker = findViewById(R.id.weight_ruler_picker);
        weightPicker.selectValue(55);
        weightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                userWeightInKg = String.valueOf(selectedValue);
                weightPickerValueTv.setText(userWeightInKg + " kgs");

            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                userWeightInKg = String.valueOf(selectedValue);
                weightPickerValueTv.setText(userWeightInKg + " kgs");

            }
        });

        //here data must be an instance of the class MarsDataProvider

    }


    public void setOnClickListnerBack() {
        binding.ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TellUsAboutYouActivity.this, WhatBringsYouToFuroActivity.class);
                startActivity(intent);

            }
        });


    }

    public void setOntvGetStartedClickListner() {

        binding.tvGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatBringYouToFuroValidation();
            }
        });

    }

    public void whatBringYouToFuroValidation() {
        boolean check = true;
//        String height = weightPickerValueTv.getText().toString().trim();
//        String weight = heightPickerValueTv.getText().toString().trim();
        if (TextUtils.isEmpty(userHeightInCm.trim()) || weightPickerValueTv.length() == 0) {
            Util.displayToast(getApplicationContext(), "Please select height");
            check = false;
        }

        if (TextUtils.isEmpty(userWeightInKg.trim()) || heightPickerValueTv.length() == 0) {
            Util.displayToast(getApplicationContext(), "Please select Weight");
            check = false;
        }
        if (TextUtils.isEmpty(whatBringsToYouFuro.trim())) {
            Util.displayToast(getApplicationContext(), "select reason");
            check = false;

        }
        if (check) {

            WhatBringsYouToFuroRequest whatBringsYouToFuroRequest = new WhatBringsYouToFuroRequest();
            whatBringsYouToFuroRequest.setHeight(userHeightInCm);
            whatBringsYouToFuroRequest.setWeight(userWeightInKg);
            whatBringsYouToFuroRequest.setReasons(whatBringsToYouFuro);
            whatBringsYouToFuroRequest.setUserId(userId);
            Util.isInternetConnected(getApplicationContext());
            Util.showProgressDialog(getApplicationContext());
            RestClient.userChooseOneReason(whatBringsYouToFuroRequest, new Callback<WhatBringsYouToFuroResponse>() {
                @Override
                public void onResponse(Call<WhatBringsYouToFuroResponse> call, Response<WhatBringsYouToFuroResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response != null) {
                            if (response.body() != null) {
                                if (response.body().getStatus().equalsIgnoreCase("200")) {

                                    Intent intent = new Intent(TellUsAboutYouActivity.this, HomeMainActivity.class);
                                    intent.putExtra("contestpage", "");
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(TellUsAboutYouActivity.this, "failure", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    }else if (response.code() == 500) {
                        Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(getApplicationContext(), +response.code(), Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(getApplicationContext(), +response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WhatBringsYouToFuroResponse> call, Throwable t) {
                    Toast.makeText(TellUsAboutYouActivity.this, "Some thing went wrong!", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }


    public String centimeterToFeet(String centemeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if (!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            System.out.println((dCentimeter / 2.54) - (feetPart * 12));
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
            String inchesss = String.valueOf(inchesPart);
            String feettt = String.valueOf(feetPart);
            tvInches.setText(inchesss);
            tvFeet.setText(feettt);
        }
        return String.format("%d' %d''", feetPart, inchesPart);
    }


}




