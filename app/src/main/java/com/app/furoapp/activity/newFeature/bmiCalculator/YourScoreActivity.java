package com.app.furoapp.activity.newFeature.bmiCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.bmiModel.BmiStoreDataRequest;
import com.app.furoapp.activity.newFeature.bmiCalculator.bmiModel.BmiStoreDataResponse;
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
    public Button btnSave;
    public ImageView ivBackArrow;
    public TextView tvYouRBmiScore;
    public String genderVal, userAge, userHeightInCm, userWeight;
    public double bmiScore = 0;
    public double valueheight = 0;
    public double valueweight = 0;
    public String getAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_score);

        llDiscard = findViewById(R.id.llDiscard);
        btnSave = findViewById(R.id.btn_save);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        tvYouRBmiScore = findViewById(R.id.tvYouRBmiScore);

        bmiScoreCalculation();
        clickEvent();
    }

    private void bmiScoreCalculation() {
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        genderVal = FuroPrefs.getString(getApplicationContext(), Constants.GENDER_VAL);
        userAge = FuroPrefs.getString(getApplicationContext(), Constants.USER_AGE_SELECT);
        valueheight = Double.parseDouble(FuroPrefs.getString(getApplicationContext(), Constants.USER_HEIGHT_IN_CM));
        valueweight = Double.parseDouble(FuroPrefs.getString(getApplicationContext(), Constants.USER_WEIGHT));

        //[weight (kg) / height (cm) / height (cm)] x 10,000
        bmiScore = (valueweight / (valueheight * valueheight) * 10000);
        tvYouRBmiScore.setText(new DecimalFormat("##.##").format(bmiScore));
    }

    private void clickEvent() {
        llDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToSaveBmiApi();
            }
        });
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callToSaveBmiApi() {
        BmiStoreDataRequest bmiStoreDataRequest = new BmiStoreDataRequest();
        bmiStoreDataRequest.setGender(genderVal);
        bmiStoreDataRequest.setAge(userAge);
        bmiStoreDataRequest.setHeight(String.valueOf(valueheight));
        bmiStoreDataRequest.setWeight(String.valueOf(valueweight));
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