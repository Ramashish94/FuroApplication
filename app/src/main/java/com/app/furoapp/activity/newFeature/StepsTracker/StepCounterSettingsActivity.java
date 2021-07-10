package com.app.furoapp.activity.newFeature.StepsTracker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.adapter.NotificationSoundAdapter;
import com.app.furoapp.model.Settings.NotificationSound;
import com.app.furoapp.utils.BaseUtil;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.ArrayList;
import java.util.List;

public class StepCounterSettingsActivity extends AppCompatActivity implements NotificationSoundAdapter.Callback {
    public ImageView ivBackIcon;
    private Intent intent;
    private RecyclerView rvSounds;
    private NotificationSoundAdapter notificationSoundAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter_settings);

        findViews();
        clickEvent();
    }

    private void findViews() {
        ivBackIcon = findViewById(R.id.ivBackIcon);
        rvSounds = findViewById(R.id.rv_sounds);
        setNotificationSoundAdapter();
    }

    private void clickEvent() {
        ivBackIcon.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), AddNewSlotPreferActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setNotificationSoundAdapter() {
        int selectedId = FuroPrefs.getInt(this, Constants.NOTIFICATION_SOUND_LIST_KEY, 0);
        List<NotificationSound> list = BaseUtil.getNotificationSoundList(this);
        if (selectedId != 0) {
            for (int selected = 0; selected < list.size(); selected++) {
                list.get(selected).setSelected(list.get(selected).getId() == selectedId);
            }
        }
        notificationSoundAdapter = new NotificationSoundAdapter(this, list, this);
        rvSounds.setAdapter(notificationSoundAdapter);
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
        intent = new Intent(getApplicationContext(), AddNewSlotPreferActivity.class);
        startActivity(intent);
        finish();
    }
}