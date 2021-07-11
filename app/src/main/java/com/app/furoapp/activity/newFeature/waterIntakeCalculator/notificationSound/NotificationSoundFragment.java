package com.app.furoapp.activity.newFeature.waterIntakeCalculator.notificationSound;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.furoapp.R;

public class NotificationSoundFragment extends Fragment {


    public NotificationSoundFragment() {
        // Required empty public constructor
    }

    public static NotificationSoundFragment newInstance(String param1, String param2) {
        NotificationSoundFragment fragment = new NotificationSoundFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_sound, container, false);
        return view;
    }
}