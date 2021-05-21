package com.app.furoapp.activity.newFeature.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.furoapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationSectionActivity extends AppCompatActivity {

    public RecyclerView rvChallangeNotification, rvDailyReadNotification;
    List<NotificationModelTests> notificationModelTestsList = new ArrayList<>();
    NotificationAdapter notificationAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_section);
        rvChallangeNotification = findViewById(R.id.rvChallangeNotification);
        rvDailyReadNotification = findViewById(R.id.rvDailyReadNotification);
        setChallangeNotificationRecyAdapter();
        setDailyReadNotificationAdapter();
    }

    private void setChallangeNotificationRecyAdapter() {
        notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationModelTestsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvChallangeNotification.setLayoutManager(layoutManager);
        rvChallangeNotification.setItemAnimator(new DefaultItemAnimator());
        rvChallangeNotification.setAdapter(notificationAdapter);
        List<NotificationModelTests> notificationModelTestsArrayList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            NotificationModelTests notificationModelTests = new NotificationModelTests();
            notificationModelTests.setNotification("meditation and work schedule");
            notificationModelTests.setNotificationReadTime("6 min read");
            notificationModelTestsArrayList.add(notificationModelTests);
        }
        NotificationAdapter notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationModelTestsArrayList);
        rvChallangeNotification.setAdapter(notificationAdapter);
    }

    private void setDailyReadNotificationAdapter() {
        notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationModelTestsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvDailyReadNotification.setLayoutManager(layoutManager);
        rvDailyReadNotification.setItemAnimator(new DefaultItemAnimator());
        rvDailyReadNotification.setAdapter(notificationAdapter);
        List<NotificationModelTests> notificationModelTestsArrayList = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            NotificationModelTests notificationModelTests = new NotificationModelTests();
            notificationModelTests.setNotification("meditation and work schedule");
            notificationModelTests.setNotificationReadTime("7 min read");
            notificationModelTestsArrayList.add(notificationModelTests);
        }
        NotificationAdapter notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationModelTestsArrayList);
        rvDailyReadNotification.setAdapter(notificationAdapter);
    }


}