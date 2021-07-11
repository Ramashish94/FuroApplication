package com.app.furoapp.activity.newFeature.waterIntakeCalculator.notificationSound;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.furoapp.activity.newFeature.likeAndSaved.SavedList.SavedFragment;
import com.app.furoapp.activity.newFeature.likeAndSaved.likedList.LikeFragment;

public class NotificationSoundPagerAdapter extends FragmentPagerAdapter {
    public int tabCount;

    public NotificationSoundPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NotificationSoundFragment();
            case 1:
                return new SameAsDeviceFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}