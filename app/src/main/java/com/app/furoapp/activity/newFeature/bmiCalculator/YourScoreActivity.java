package com.app.furoapp.activity.newFeature.bmiCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.storeBmiModel.BmiStoreDataRequest;
import com.app.furoapp.activity.newFeature.bmiCalculator.storeBmiModel.BmiStoreDataResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourScoreActivity extends AppCompatActivity {
    public LinearLayout llDiscard;
    public Button btnSaveData;
    public ImageView ivBackArrow, ivDiscard;
    public TextView tvYouRBmiScore, tvFindYourBmi;
    public String genderVal, userAge, userHeightInCm, userWeight;
    public double bmiScore;
    public String getAccessToken;
    public double userHeightValInCm;
    public double userWeightValInKg;
    private String findForOthersBmi;
    public String bmiType = "Find yours";
    private String findBmiType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_score);

        llDiscard = findViewById(R.id.llDiscard);
        btnSaveData = findViewById(R.id.btnSaveData);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        tvYouRBmiScore = findViewById(R.id.tvYouRBmiScore);
        tvFindYourBmi = findViewById(R.id.tvFindYourBmi);
        ivDiscard = findViewById(R.id.ivDiscard);

        findBmiType = getIntent().getStringExtra("getFindBmiType");

        if (bmiType.equalsIgnoreCase(findBmiType)) {
            btnSaveData.setVisibility(View.VISIBLE);
            ivDiscard.setVisibility(View.VISIBLE);
            tvFindYourBmi.setVisibility(View.GONE);
        }else {
            btnSaveData.setVisibility(View.GONE);
            ivDiscard.setVisibility(View.GONE);
            tvFindYourBmi.setVisibility(View.VISIBLE);
        }


        getManipulateData();
        bmiScoreCalculation();
        clickEvent();


    }


    private void getManipulateData() {
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        findForOthersBmi = FuroPrefs.getString(getApplicationContext(), Constants.FIND_TYPE);
        genderVal = FuroPrefs.getString(getApplicationContext(), Constants.GENDER_VAL);
        userAge = FuroPrefs.getString(getApplicationContext(), Constants.USER_AGE_SELECT);
        userHeightInCm = getIntent().getStringExtra("userHeightInCm");
        userWeight = getIntent().getStringExtra("userWeightInKg");
    }

    private void bmiScoreCalculation() {
        userHeightValInCm = Double.parseDouble(userHeightInCm);
        userWeightValInKg = Double.parseDouble(userWeight);
        //[weight (kg) / height (cm) / height (cm)] x 10,000
        bmiScore = ((userWeightValInKg / userHeightValInCm / userHeightValInCm) * 10000);
        tvYouRBmiScore.setText(new DecimalFormat("##.##").format(bmiScore));
    }

    private void clickEvent() {
        llDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),FindYourBmiActivity.class);
               startActivity(intent);
            }
        });

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToSaveBmiApi();
            }
        });
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FindYourBmiActivity.class);
                startActivity(intent);
            }
        });
    }

    private void callToSaveBmiApi() {
        BmiStoreDataRequest bmiStoreDataRequest = new BmiStoreDataRequest();
        bmiStoreDataRequest.setGender(genderVal);
        bmiStoreDataRequest.setAge(userAge);
        bmiStoreDataRequest.setHeight(String.valueOf(userHeightValInCm));
        bmiStoreDataRequest.setWeight(String.valueOf(userWeightValInKg));
        bmiStoreDataRequest.setBmi(String.valueOf(bmiScore));
        Util.showProgressDialog(getApplicationContext());
        RestClient.getBmiStoreData(getAccessToken, bmiStoreDataRequest, new Callback<BmiStoreDataResponse>() {
            @Override
            public void onResponse(Call<BmiStoreDataResponse> call, Response<BmiStoreDataResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus() != null) {
                        Toast.makeText(YourScoreActivity.this, "Bmi data saved successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BmiStoreDataResponse> call, Throwable t) {
                Toast.makeText(YourScoreActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}