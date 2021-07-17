package com.app.furoapp.activity.newFeature.waterIntakeCalculator.notificationSound;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.furoapp.R;
import com.app.furoapp.adapter.NotificationSoundAdapter;
import com.app.furoapp.model.Settings.NotificationSound;
import com.app.furoapp.utils.BaseUtil;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.List;

public class NotificationSoundFragment extends Fragment implements NotificationSoundAdapter.Callback {

    private Intent intent;
    private RecyclerView rvSounds;
    private NotificationSoundAdapter notificationSoundAdapter;

    public NotificationSoundFragment() {
        // Required empty public constructor
    }

    public static NotificationSoundFragment newInstance() {
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
        rvSounds =view.findViewById(R.id.rv_sounds);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNotificationSoundAdapter();
    }

    private void setNotificationSoundAdapter() {
        int selectedId = FuroPrefs.getInt(getActivity(), Constants.NOTIFICATION_WATER_INTAKE_SELECTED_SOUND_KEY, 0);
        List<NotificationSound> list = BaseUtil.getNotificationSoundList(getActivity());
        if (selectedId != 0) {
            for (int selected = 0; selected < list.size(); selected++) {
                list.get(selected).setSelected(list.get(selected).getId() == selectedId);
            }
        }
        notificationSoundAdapter = new NotificationSoundAdapter(getActivity(), list, this);
        rvSounds.setAdapter(notificationSoundAdapter);
    }

    @Override
    public void onClickItem(NotificationSound notificationSound, int pos) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), notificationSound.getPath());
        mediaPlayer.start();
        notificationSoundAdapter.updateItem(pos);
    }

}