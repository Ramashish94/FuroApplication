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
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.AllTimeHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.MonthlyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.WeeklyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.adapter.LeaderBoardAllTimeAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.adapter.LeaderBoardMonthlyAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.adapter.LeaderBoardWeeklyAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model.Data;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model.LeadBoardModel;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model.LeaderBoardResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderBoardActivity extends AppCompatActivity implements LeaderBoardWeeklyAdapter.WeeklyItemClickCallBack,
        LeaderBoardMonthlyAdapter.MonthlyItemClickCallBack, LeaderBoardAllTimeAdapter.AllTimeClickCallBack {
    RecyclerView rvLeaderBoardRecy;
    LeaderBoardWeeklyAdapter leaderBoardWeeklyAdapter;
    LeaderBoardMonthlyAdapter leaderBoardMonthlyAdapter;
    LeaderBoardAllTimeAdapter leaderBoardAllTimeAdapter;
    List<LeadBoardModel> leadBoardModelList = new ArrayList<>();
    public SwitchCompat switchBtnWeekly, switchBtnMontly, switchBtnAllType;
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
        switchBtnAllType = findViewById(R.id.switchBtnAlType);
        ivLeadBoardCross = findViewById(R.id.ivLeadBoardCross);
        clickEvent();

        callLeaderBoardApi("All Time");

    }

    private void clickEvent() {
        ivLeadBoardCross.setOnClickListener(v -> {
            finish();
        });

        switchBtnWeekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Weekly";
                    switchBtnAllType.setChecked(false);
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
                    switchBtnAllType.setChecked(false);
                    callLeaderBoardApi("Monthly");/*str_act,*/
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
                    callLeaderBoardApi("All Time");/*/*str_act,*/
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
                        if (type.equalsIgnoreCase("Weekly")) {
                            setAdapter("Weekly", response.body().getData());
                        } else if (type.equalsIgnoreCase("Monthly")) {
                            setAdapter("Monthly", response.body().getData());
                        } else if (type.equalsIgnoreCase("All Time")) {
                            setAdapter("All Time", response.body().getData());
                        } else {
                            setAdapter("All Time", response.body().getData());
                        }
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(LeaderBoardActivity.this, "Internal server error !", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 200) {
                    Toast.makeText(LeaderBoardActivity.this, "Session expired Please login again !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LeaderBoardResponse> call, Throwable t) {
                Toast.makeText(LeaderBoardActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setLeadBoardRecyAdapter() {
        leaderBoardWeeklyAdapter = new LeaderBoardWeeklyAdapter(getApplicationContext(), leadBoardModelList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvLeaderBoardRecy.setLayoutManager(layoutManager);
        rvLeaderBoardRecy.setItemAnimator(new DefaultItemAnimator());
        rvLeaderBoardRecy.setAdapter(leaderBoardWeeklyAdapter);
        /*List<LeadBoardModel> leadBoardModelArrayList = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            LeadBoardModel leadBoardModel = new LeadBoardModel();
            leadBoardModel.setName("Sager");
            leadBoardModel.setScore("100");
            leadBoardModelArrayList.add(leadBoardModel);
        }
        LeadBoardAdapter leadBoardAdapter = new LeadBoardAdapter(getApplicationContext(), leadBoardModelArrayList, this);
        rvLeaderBoardRecy.setAdapter(leadBoardAdapter);*/
    }

    private void setAdapter(String type, Data data) {
        if (type.equalsIgnoreCase("Weekly")) {
            //if (data != null && data.get() != null && data.getWeeklyDataLists().size() > 0) {
            rvLeaderBoardRecy.setLayoutManager(new LinearLayoutManager(this));
            //   leaderBoardWeeklyAdapter = new LeaderBoardWeeklyAdapter(getApplicationContext(), data.getWeeklyDataLists(), this);
            rvLeaderBoardRecy.setAdapter(leaderBoardWeeklyAdapter);
            leaderBoardWeeklyAdapter.notifyDataSetChanged();
            //}
        } else if (type.equalsIgnoreCase("Monthly")) {
            // if (data != null && data.getMonthlyDataLists() != null && data.getAllTimeCounterList().size() > 0) {
            rvLeaderBoardRecy.setLayoutManager(new LinearLayoutManager(this));
            //     leaderBoardMonthlyAdapter = new LeaderBoardMonthlyAdapter(getApplicationContext(), data.getMonthlyDataLists(), this);
            rvLeaderBoardRecy.setAdapter(leaderBoardMonthlyAdapter);
            leaderBoardMonthlyAdapter.notifyDataSetChanged();
            // }
        } else if (type.equalsIgnoreCase("All Time")) {
            // if (data != null && data.getAllTimeCounterList() != null && data.getAllTimeCounterList().size() > 0) {
            rvLeaderBoardRecy.setLayoutManager(new LinearLayoutManager(this));
            //   leaderBoardAllTimeAdapter = new LeaderBoardAllTimeAdapter(getApplicationContext(), data.getAllTimeCounterList(), this);
            rvLeaderBoardRecy.setAdapter(leaderBoardAllTimeAdapter);
            leaderBoardAllTimeAdapter.notifyDataSetChanged();
            // }
        }
    }

    @Override
    public void weekItemClick(int position, String name, String score) {

    }

    @Override
    public void monthlyItemClick(int position, String name, String score) {

    }

    @Override
    public void allTimeItemClick(int position, String name, String score) {

    }
}