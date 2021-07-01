package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.adapter.MonthlyLeaderBoardAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.adapter.WeeklyLeaderBoardAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.adapter.DailyLeaderBoardAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model.Data;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model.LeaderBoardResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderBoardActivity extends AppCompatActivity implements DailyLeaderBoardAdapter.DailyItemClickCallBack,
        WeeklyLeaderBoardAdapter.WeeklyItemClickCallBack, MonthlyLeaderBoardAdapter.MonthlyClickCallBack {
    RecyclerView rvLeaderBoardRecy;
    DailyLeaderBoardAdapter dailyLeaderBoardAdapter;
    WeeklyLeaderBoardAdapter weeklyLeaderBoardAdapter;
    MonthlyLeaderBoardAdapter monthlyLeaderBoardAdapter;
    public SwitchCompat switchBtnWeekly, switchBtnMontly, switchBtnDaily;
    private String type;
    ImageView ivLeadBoardCross;
    private String getAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_board);
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        rvLeaderBoardRecy = findViewById(R.id.rvLeaderBoardRecy);
        switchBtnWeekly = findViewById(R.id.switchBtnWeekly);
        switchBtnMontly = findViewById(R.id.switchBtnMonthly);
        switchBtnDaily = findViewById(R.id.switchBtnDaily);
        ivLeadBoardCross = findViewById(R.id.ivLeadBoardCross);
        clickEvent();

        callLeaderBoardApi("Daily");

    }

    private void clickEvent() {
        ivLeadBoardCross.setOnClickListener(v -> {
            finish();
        });

        switchBtnDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Daily";
                    switchBtnWeekly.setChecked(false);
                    switchBtnMontly.setChecked(false);
                    callLeaderBoardApi("Daily");/*/*str_act,*/
                }
            }
        });

        switchBtnWeekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Weekly";
                    switchBtnDaily.setChecked(false);
                    switchBtnMontly.setChecked(false);
                    callLeaderBoardApi("Weekly");/*str_act,*/
                }

            }
        });

        switchBtnMontly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Monthly";
                    switchBtnWeekly.setChecked(false);
                    switchBtnDaily.setChecked(false);
                    callLeaderBoardApi("Monthly");/*str_act,*/
                }
            }
        });


        if (type != null) {
            callLeaderBoardApi(type);
        } else {
            callLeaderBoardApi("");
        }
    }

    private void callLeaderBoardApi(String type) {
        RestClient.getLeaderBoardData(getAccessToken, new Callback<LeaderBoardResponse>() {
            @Override
            public void onResponse(Call<LeaderBoardResponse> call, Response<LeaderBoardResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (type.equalsIgnoreCase("Daily")) {
                            setAdapter("Daily", response.body().getData());
                        } else if (type.equalsIgnoreCase("Weekly")) {
                            setAdapter("Weekly", response.body().getData());
                        } else if (type.equalsIgnoreCase("Monthly")) {
                            setAdapter("Monthly", response.body().getData());
                        }
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(LeaderBoardActivity.this, "Internal server error !", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(LeaderBoardActivity.this, "Session expired Please login again !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LeaderBoardResponse> call, Throwable t) {
                Toast.makeText(LeaderBoardActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapter(String type, Data data) {
        if (type.equalsIgnoreCase("Daily")) {
            if (data != null && data.getDailyDataCount() != null && data.getDailyDataCount().size() > 0) {
                rvLeaderBoardRecy.setVisibility(View.VISIBLE);
                rvLeaderBoardRecy.setLayoutManager(new LinearLayoutManager(this));
                dailyLeaderBoardAdapter = new DailyLeaderBoardAdapter(getApplicationContext(), data.getDailyDataCount(), this);
                rvLeaderBoardRecy.setAdapter(dailyLeaderBoardAdapter);
                dailyLeaderBoardAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No daily Records found !", Toast.LENGTH_SHORT).show();
                rvLeaderBoardRecy.setVisibility(View.GONE);
            }
        } else if (type.equalsIgnoreCase("Weekly")) {
            if (data != null && data.getWeeklyDataCount() != null && data.getWeeklyDataCount().size() > 0) {
                rvLeaderBoardRecy.setVisibility(View.VISIBLE);
                rvLeaderBoardRecy.setLayoutManager(new LinearLayoutManager(this));
                weeklyLeaderBoardAdapter = new WeeklyLeaderBoardAdapter(getApplicationContext(), data.getWeeklyDataCount(), this);
                rvLeaderBoardRecy.setAdapter(weeklyLeaderBoardAdapter);
                weeklyLeaderBoardAdapter.notifyDataSetChanged();
            } else {
                rvLeaderBoardRecy.setVisibility(View.GONE);
                Toast.makeText(this, "No weekly Records found !", Toast.LENGTH_SHORT).show();
            }
        } else if (type.equalsIgnoreCase("Monthly")) {
            if (data != null && data.getMonthlyDataCount() != null && data.getMonthlyDataCount().size() > 0) {
                rvLeaderBoardRecy.setVisibility(View.VISIBLE);
                rvLeaderBoardRecy.setLayoutManager(new LinearLayoutManager(this));
                monthlyLeaderBoardAdapter = new MonthlyLeaderBoardAdapter(getApplicationContext(), data.getMonthlyDataCount(), this);
                rvLeaderBoardRecy.setAdapter(monthlyLeaderBoardAdapter);
                monthlyLeaderBoardAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No monthly Records found !", Toast.LENGTH_SHORT).show();
                rvLeaderBoardRecy.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void dailyItemClick(int position, String name, String score) {

    }

    @Override
    public void monthlyItemClick(int position, String name, String score) {

    }


    @Override
    public void weeklyItemClick(int position, String name, String score) {

    }
}