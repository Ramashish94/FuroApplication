package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.app.furoapp.R;
import com.app.furoapp.databinding.FragmentVideoOverviewBinding;

public class VideoRecordingView extends AppCompatActivity {
    FragmentVideoOverviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_video_recoding_view);
    }
}
