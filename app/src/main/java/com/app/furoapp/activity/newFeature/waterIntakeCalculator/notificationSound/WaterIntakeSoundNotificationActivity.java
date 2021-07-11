package com.app.furoapp.activity.newFeature.waterIntakeCalculator.notificationSound;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.CreatePlaneActivity;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class WaterIntakeSoundNotificationActivity extends AppCompatActivity {

    public TabLayout tbSetting;
    public TabItem tab1NotificationSound, tab2SameAsDevice;
    public ViewPager viewPager;
    public NotificationSoundPagerAdapter notificationSoundPagerAdapter;
    public ImageView ivLikedImg, ivSavedImg, ivBackArrow;
    public ImageView ivBackIcon;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_inake_sound_notifiaton);


        initViews();
        tabAndPagerListiner();
    }


    private void initViews() {
        tbSetting = findViewById(R.id.tbSetting);
        tab1NotificationSound = findViewById(R.id.tab1NotificationSound);
        tab2SameAsDevice = findViewById(R.id.tab2SameAsDevice);
        viewPager = findViewById(R.id.viewPager);
        ivBackArrow = findViewById(R.id.ivBackIcon);
    }

    private void tabAndPagerListiner() {
        notificationSoundPagerAdapter = new NotificationSoundPagerAdapter(getSupportFragmentManager(), tbSetting.getTabCount());
        viewPager.setAdapter(notificationSoundPagerAdapter);

      /*  viewPager.setOffscreenPageLimit(2);
        tbSavedBookmarked.setupWithViewPager(viewPager);*/

        tbSetting.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1)
                    notificationSoundPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tbSetting));
        // it is provided listen of scroll or page change
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), CreatePlaneActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(getApplicationContext(), CreatePlaneActivity.class);
        startActivity(intent);
        finish();
    }
}