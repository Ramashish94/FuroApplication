package com.app.furoapp.activity.newFeature.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.notification.notificationModel.ChallengeNotification;
import com.app.furoapp.activity.newFeature.notification.notificationModel.CronNotification;
import com.app.furoapp.activity.newFeature.notification.notificationModel.DailyFeedNotification;
import com.app.furoapp.activity.newFeature.notification.notificationModel.DailyFeedNotificationAdapter;
import com.app.furoapp.activity.newFeature.notification.notificationModel.NotificationResponse;
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
    List<CronNotification> cronNotificationList = new ArrayList<>();

    private String getAccessToken;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_section);

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        rvChallangeNotification = findViewById(R.id.rvChallangeNotification);
        rvDailyReadNotification = findViewById(R.id.rvDailyReadNotification);
        setChallangeNotificationRecyAdapter();
        setDailyReadNotificationAdapter();
        getNotificationApi();
    }

    private void getNotificationApi() {
        Util.showProgressDialog(getApplicationContext());
        RestClient.getNotificationData(getAccessToken, new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200 && response.body() != null && response.body().getStatus() != null) {
                    /*Challenge notification*/
                    notifyChallengeNotificationAdapter(response.body().getChallengeNotification());
                    /*Daily Notification*/
                    notifyDailyNotificationAdapter(response.body().getDailyFeedNotification());
                    /*cron notification*/
                    notifyCronNotificationAdapter(response.body().getCronNotification());
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Toast.makeText(NotificationSectionActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setChallangeNotificationRecyAdapter() {
        challangeNotificationAdapter = new ChallangeNotificationAdapter(getApplicationContext(), challengeNotificationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvChallangeNotification.setLayoutManager(layoutManager);
        rvChallangeNotification.setItemAnimator(new DefaultItemAnimator());
        rvChallangeNotification.setAdapter(challangeNotificationAdapter);
    }

    private void notifyChallengeNotificationAdapter(List<ChallengeNotification> challengeNotification) {
        challengeNotificationList.clear();
        challengeNotificationList.addAll(challengeNotification);
        if (challengeNotificationList != null && challengeNotificationList.size() > 0) {
            challangeNotificationAdapter.notifyDataSetChanged();
        }
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

    private void notifyCronNotificationAdapter(List<CronNotification> challengeNotification) {

    }

}