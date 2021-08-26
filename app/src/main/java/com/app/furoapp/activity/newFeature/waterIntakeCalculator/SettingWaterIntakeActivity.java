package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.adapter.NotificationSoundAdapter;
import com.app.furoapp.model.Settings.NotificationSound;
import com.app.furoapp.utils.BaseUtil;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.List;

public class SettingWaterIntakeActivity extends AppCompatActivity implements NotificationSoundAdapter.Callback {
    public ImageView ivBackIcon;
    private Intent intent;
    private LinearLayout llNotificationSound, llSameAsDevice;
    private RecyclerView rv_sounds;
    private TextView tvNotificationSound, tvSameAsDevice;
    private NotificationSoundAdapter notificationSoundAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_water_intake);

        findViews();
        clickEvent();

    }


    private void findViews() {
        ivBackIcon = findViewById(R.id.ivBackIcon);
        llNotificationSound = findViewById(R.id.llNotificationSound);
        llSameAsDevice = findViewById(R.id.llSameAsDevice);
        rv_sounds = findViewById(R.id.rv_sounds);
        tvNotificationSound = findViewById(R.id.tvNotificationSound);
        tvSameAsDevice = findViewById(R.id.tvSameAsDevice);

        llNotificationSound.setVisibility(View.VISIBLE);
        llSameAsDevice.setVisibility(View.GONE);
        setNotificationSoundAdapter();
    }


    private void clickEvent() {
        ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), CreateAHydrationPlaneActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvNotificationSound.setOnClickListener(v -> {
            llNotificationSound.setVisibility(View.VISIBLE);
            llSameAsDevice.setVisibility(View.GONE);
            tvNotificationSound.setTextColor(Color.parseColor("#19CFE6"));
            tvSameAsDevice.setTextColor(Color.parseColor("#000000"));
            setNotificationSoundAdapter();
        });
        tvSameAsDevice.setOnClickListener(v -> {
            llNotificationSound.setVisibility(View.GONE);
            llSameAsDevice.setVisibility(View.VISIBLE);
            tvNotificationSound.setTextColor(Color.parseColor("#000000"));
            tvSameAsDevice.setTextColor(Color.parseColor("#19CFE6"));
        });

    }


    private void setNotificationSoundAdapter() {
        int selectedId = FuroPrefs.getInt(this, Constants.NOTIFICATION_WATER_INTAKE_SELECTED_SOUND_KEY, 0);
        List<NotificationSound> list = BaseUtil.getNotificationSoundList(this);
        if (selectedId != 0) {
            for (int selected = 0; selected < list.size(); selected++) {
                list.get(selected).setSelected(list.get(selected).getId() == selectedId);
            }
        }
        notificationSoundAdapter = new NotificationSoundAdapter(this, list, this);
        rv_sounds.setAdapter(notificationSoundAdapter);
    }

    @Override
    public void onClickItem(NotificationSound notificationSound, int pos) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, notificationSound.getPath());
        mediaPlayer.start();
        notificationSoundAdapter.updateItem(pos);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(getApplicationContext(), CreateAHydrationPlaneActivity.class);
        startActivity(intent);
        finish();
    }
}