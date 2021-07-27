package com.app.furoapp.activity.newFeature.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.notification.adapter.ChallangeNotificationAdapter;
import com.app.furoapp.activity.newFeature.notification.adapter.CronNotificationAdapter;
import com.app.furoapp.activity.newFeature.notification.adapter.DailyFeedNotificationAdapter;
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.DailyFeedNotification;
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.Datum;
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.Datum__1;
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.Datum__2;
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.NotificationResponses;
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

    public RecyclerView rvChallangeNotification, rvDailyReadNotification, rvCronNotification;
    ChallangeNotificationAdapter challangeNotificationAdapter;
    DailyFeedNotificationAdapter dailyFeedNotificationAdapter;
    CronNotificationAdapter cronNotificationAdapter;
    List<Datum__1> challengeNotificationList = new ArrayList<>();
    List<Datum__2> cronNotificationList = new ArrayList<>();
    List<Datum> dailyFeedNotificationList = new ArrayList<>();
    TextView tvNosOfDailyNotification, tvNosOfChallengeNotification;

    private String getAccessToken;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_section);

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        rvChallangeNotification = findViewById(R.id.rvChallangeNotification);
        rvDailyReadNotification = findViewById(R.id.rvDailyReadNotification);
        rvCronNotification = findViewById(R.id.rvCronNotification);

        tvNosOfChallengeNotification = findViewById(R.id.tvNosOfChallengeNotification);
        tvNosOfDailyNotification = findViewById(R.id.tvNosOfDailyNotification);

        setDailyReadNotificationAdapter();
        setChallangeNotificationRecyAdapter();
        setCronNotificationAdapter();

        callNotificationApi();

    }

    private void callNotificationApi() {
        if (Util.isInternetConnected(getApplicationContext())) {
            Util.showProgressDialog(getApplicationContext());
            RestClient.getNotificationData(getAccessToken, new Callback<NotificationResponses>() {
                @Override
                public void onResponse(Call<NotificationResponses> call, Response<NotificationResponses> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            // daily notification;
                            notifyDailyAdapter(response.body().getDailyFeedNotification().getData());
                            //challenge notification;
                            notifyChallengeAdapter(response.body().getChallengeNotification().getData());
                            // setChalleneNotificationData(response.body().getChallengeNotification());
                            // cron notification
                            notifyCronAdapter(response.body().getCronNotification().getData());

                        }

                    } else if (response.code() == 500) {
                        Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NotificationResponses> call, Throwable t) {
                    Toast.makeText(NotificationSectionActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void setDailyReadNotificationAdapter() {
        dailyFeedNotificationAdapter = new DailyFeedNotificationAdapter(getApplicationContext(), dailyFeedNotificationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvDailyReadNotification.setLayoutManager(layoutManager);
        rvDailyReadNotification.setItemAnimator(new DefaultItemAnimator());
        rvDailyReadNotification.setAdapter(dailyFeedNotificationAdapter);
    }

    private void notifyDailyAdapter(List<Datum> data) {
        dailyFeedNotificationList.clear();
        dailyFeedNotificationList.addAll(data);
        if (dailyFeedNotificationList != null && dailyFeedNotificationList.size() > 0) {
            dailyFeedNotificationAdapter.notifyDataSetChanged();
        }

//        dailyFeedNotificationAdapter = new DailyFeedNotificationAdapter(getApplicationContext(),data);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        rvDailyReadNotification.setLayoutManager(layoutManager);
//        rvDailyReadNotification.setItemAnimator(new DefaultItemAnimator());
//        rvDailyReadNotification.setAdapter(dailyFeedNotificationAdapter);
//        dailyFeedNotificationAdapter.notifyDataSetChanged();

    }

    private void setChallangeNotificationRecyAdapter() {
        challangeNotificationAdapter = new ChallangeNotificationAdapter(getApplicationContext(), challengeNotificationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvChallangeNotification.setLayoutManager(layoutManager);
        rvChallangeNotification.setItemAnimator(new DefaultItemAnimator());
        rvChallangeNotification.setAdapter(challangeNotificationAdapter);
    }

    private void notifyChallengeAdapter(List<Datum__1> data) {
        challengeNotificationList.clear();
        challengeNotificationList.addAll(data);
        if (challengeNotificationList != null && challengeNotificationList.size() > 0) {
            challangeNotificationAdapter.notifyDataSetChanged();
        }

//        challangeNotificationAdapter = new ChallangeNotificationAdapter(getApplicationContext(), data);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        rvChallangeNotification.setLayoutManager(layoutManager);
//        rvChallangeNotification.setItemAnimator(new DefaultItemAnimator());
//        rvChallangeNotification.setAdapter(challangeNotificationAdapter);
//        challangeNotificationAdapter.notifyDataSetChanged();
    }

    private void setCronNotificationAdapter() {
        cronNotificationAdapter = new CronNotificationAdapter(getApplicationContext(), cronNotificationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvCronNotification.setLayoutManager(layoutManager);
        rvCronNotification.setItemAnimator(new DefaultItemAnimator());
        rvCronNotification.setAdapter(cronNotificationAdapter);
    }

    private void notifyCronAdapter(List<Datum__2> data) {
        cronNotificationList.clear();
        cronNotificationList.addAll(data);
        if (cronNotificationList != null && cronNotificationList.size() > 0) {
            cronNotificationAdapter.notifyDataSetChanged();
        }

//        cronNotificationAdapter = new CronNotificationAdapter(getApplicationContext(), data);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        rvCronNotification.setLayoutManager(layoutManager);
//        rvCronNotification.setItemAnimator(new DefaultItemAnimator());
//        rvCronNotification.setAdapter(cronNotificationAdapter);
//        cronNotificationAdapter.notifyDataSetChanged();
    }

}