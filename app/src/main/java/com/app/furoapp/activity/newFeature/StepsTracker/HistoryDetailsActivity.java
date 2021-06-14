package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.historyAdapter.AllTimeHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeCounter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.HistoryResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.adapter.MonthlyDataAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.adapter.WeeklyDataAdapter;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.github.mikephil.charting.charts.BarChart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailsActivity extends AppCompatActivity {

    private BarChart barchart;
    public SwitchCompat switchBtnWeekly, switchBtnMontly, switchBtnAllType;
    ImageView ivHistoryCross;
    private String type;
    AllTimeHistoryAdapter allTimeHistoryAdapter;
    MonthlyDataAdapter monthlyDataAdapter;
    WeeklyDataAdapter weeklyDataAdapter;
    private String str_act, userImageUpdated;
    private String getAccessToken;
    TextView tvTotSteps, tvDailyAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);


        initViews();
        clickEvent();
        callApi();

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

    }


    private void initViews() {
        barchart = findViewById(R.id.barchart);
        switchBtnWeekly = findViewById(R.id.switchBtnWeekly);
        switchBtnMontly = findViewById(R.id.switchBtnMonthly);
        switchBtnAllType = findViewById(R.id.switchBtnAlType);
        ivHistoryCross = findViewById(R.id.ivHistoryCross);
        tvTotSteps = findViewById(R.id.tvTotSteps);
        tvDailyAverage = findViewById(R.id.tvDailyAverage);
    }

    private void clickEvent() {


    }


    private void callApi() {
        switchBtnWeekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Weekly";
                    switchBtnAllType.setChecked(false);
                    switchBtnMontly.setChecked(false);
                    callHistoryApi(str_act, "week");
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
                    callHistoryApi(str_act, "Monthly");

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
                    callHistoryApi(str_act, "All Time");

                }
            }
        });
        callHistoryApi();
    }

    private void callHistoryApi() {
        Util.showProgressDialog(getApplicationContext());
        RestClient.getHistoryData(getAccessToken, new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getData().getAllTimeCounterList() != null) {
                            setData(response.body().getData().getAllTimeCounterList().get(0));

                        }
                    }

                } else if (response.code() == 500) {
                    Toast.makeText(HistoryDetailsActivity.this, "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(HistoryDetailsActivity.this, "Session expire please login again", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {

            }
        });
    }

    private void setData(AllTimeCounter allTimeCounter) {
        if (allTimeCounter != null) {
            tvTotSteps.setText("" + allTimeCounter.getCountSteps());
            tvDailyAverage.setText("" + allTimeCounter.getDailyAverage());
        }
    }

    private void callHistoryApi(String str_act, String type) {

    }
}