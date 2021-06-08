package com.app.furoapp.activity.newFeature.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.notification.challangeNotification.ChallengeNotification;
import com.app.furoapp.activity.newFeature.notification.challangeNotification.ChallengeNotificationResp;
import com.app.furoapp.activity.newFeature.notification.dailyNotification.DailyFeedNotification;
import com.app.furoapp.activity.newFeature.notification.dailyNotification.DailyFeedNotificationResponse;
import com.app.furoapp.activity.newFeature.notification.notificationModel.DailyFeedNotificationAdapter;
import com.app.furoapp.model.challangeNotification.ChallangeNotificationResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSectionActivity extends AppCompatActivity {

    public RecyclerView rvChallangeNotification, rvDailyReadNotification;
    ChallangeNotificationAdapter challangeNotificationAdapter;
    DailyFeedNotificationAdapter dailyFeedNotificationAdapter;
    List<ChallengeNotification> challengeNotificationList = new ArrayList<>();
    List<DailyFeedNotification> dailyFeedNotificationList = new ArrayList<>();
    TextView tvNosOfDailyNotification, tvNosOfChallengeNotification;

    private String getAccessToken;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_section);

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        rvChallangeNotification = findViewById(R.id.rvChallangeNotification);
        rvDailyReadNotification = findViewById(R.id.rvDailyReadNotification);
        tvNosOfChallengeNotification = findViewById(R.id.tvNosOfChallengeNotification);
        tvNosOfDailyNotification = findViewById(R.id.tvNosOfDailyNotification);

        setChallangeNotificationRecyAdapter();
        setDailyReadNotificationAdapter();
        getChallengeNotificationApi();
        callDailyFeedNotificationApi();
    }

    private void getChallengeNotificationApi() {
        if (Util.isInternetConnected(getApplicationContext())) {
            Util.showProgressDialog(getApplicationContext());
            RestClient.getChallangeNotificationData(getAccessToken, new Callback<ChallengeNotificationResp>() {
                @Override
                public void onResponse(Call<ChallengeNotificationResp> call, Response<ChallengeNotificationResp> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null && response.body().getStatus() != null) {
                            challangeNotificationAdapter(response.body().getChallengeNotification());
                        }

                    } else if (response.code() == 500) {
                        Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(getApplicationContext(), +response.code(), Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(getApplicationContext(), +response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ChallengeNotificationResp> call, Throwable t) {

                }
            });

        }
    }


    private void setChallangeNotificationRecyAdapter() {
        challangeNotificationAdapter = new ChallangeNotificationAdapter(getApplicationContext(), challengeNotificationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvChallangeNotification.setLayoutManager(layoutManager);
        rvChallangeNotification.setItemAnimator(new DefaultItemAnimator());
        rvChallangeNotification.setAdapter(challangeNotificationAdapter);
    }

    private void challangeNotificationAdapter(List<ChallengeNotification> challengeNotification) {
        challengeNotificationList.clear();
        challengeNotificationList.addAll(challengeNotification);
        if (challengeNotificationList != null && challengeNotificationList.size() > 0) {
            challangeNotificationAdapter.notifyDataSetChanged();
        }
    }

    private void callDailyFeedNotificationApi() {
        Util.showProgressDialog(getApplicationContext());
        RestClient.getDailyNotificationData(getAccessToken, new Callback<DailyFeedNotificationResponse>() {
            @Override
            public void onResponse(Call<DailyFeedNotificationResponse> call, Response<DailyFeedNotificationResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null && response.body().getStatus() != null) {
                        notifyDailyNotificationAdapter(response.body().getDailyFeedNotification());
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getApplicationContext(), +response.code(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(getApplicationContext(), +response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DailyFeedNotificationResponse> call, Throwable t) {

            }
        });
    }

    private void setDailyReadNotificationAdapter() {
        dailyFeedNotificationAdapter = new DailyFeedNotificationAdapter(getApplicationContext(), dailyFeedNotificationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvDailyReadNotification.setLayoutManager(layoutManager);
        rvDailyReadNotification.setItemAnimator(new DefaultItemAnimator());
        rvDailyReadNotification.setAdapter(dailyFeedNotificationAdapter);
    }

    private void notifyDailyNotificationAdapter(List<DailyFeedNotification> dailyFeedNotification) {
        dailyFeedNotificationList.clear();
        dailyFeedNotificationList.addAll(dailyFeedNotification);
        if (dailyFeedNotificationList != null && dailyFeedNotificationList.size() > 0) {
            challangeNotificationAdapter.notifyDataSetChanged();
        }
    }


}