package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.adapter.LeadBoardAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.leadboardmodel.LeadBoardModel;

import java.util.ArrayList;
import java.util.List;

public class LeadBoardActivity extends AppCompatActivity implements LeadBoardAdapter.LeadBoardClickCallBack {
    RecyclerView rvLeaderBoardRecy;
    LeadBoardAdapter leadBoardAdapter;
    List<LeadBoardModel> leadBoardModelList = new ArrayList<>();
    public SwitchCompat switchBtnWeekly, switchBtnMontly, switchBtnAllType;
    private String type;
    ImageView ivLeadBoardCross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_board);
        rvLeaderBoardRecy = findViewById(R.id.rvLeaderBoardRecy);
        switchBtnWeekly = findViewById(R.id.switchBtnWeekly);
        switchBtnMontly = findViewById(R.id.switchBtnMonthly);
        switchBtnAllType = findViewById(R.id.switchBtnAlType);
        ivLeadBoardCross = findViewById(R.id.ivLeadBoardCross);
        setLeadBoardRecyAdapter();
        clickEvent();
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
                }
            }
        });
    }

    private void setLeadBoardRecyAdapter() {
        leadBoardAdapter = new LeadBoardAdapter(getApplicationContext(), leadBoardModelList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvLeaderBoardRecy.setLayoutManager(layoutManager);
        rvLeaderBoardRecy.setItemAnimator(new DefaultItemAnimator());
        rvLeaderBoardRecy.setAdapter(leadBoardAdapter);
        List<LeadBoardModel> leadBoardModelArrayList = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            LeadBoardModel leadBoardModel = new LeadBoardModel();
            leadBoardModel.setName("Sager");
            leadBoardModel.setScore("100");
            leadBoardModelArrayList.add(leadBoardModel);
        }
        LeadBoardAdapter leadBoardAdapter = new LeadBoardAdapter(getApplicationContext(), leadBoardModelArrayList, this);
        rvLeaderBoardRecy.setAdapter(leadBoardAdapter);
    }

    @Override
    public void leadBoardItemClick(int position, String name, String score) {

    }
}