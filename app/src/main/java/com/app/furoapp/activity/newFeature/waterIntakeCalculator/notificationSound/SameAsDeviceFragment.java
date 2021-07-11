package com.app.furoapp.activity.newFeature.waterIntakeCalculator.notificationSound;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.furoapp.R;

public class SameAsDeviceFragment extends Fragment {

    public SameAsDeviceFragment() {
        // Required empty public constructor
    }


    public static SameAsDeviceFragment newInstance() {
        SameAsDeviceFragment fragment = new SameAsDeviceFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_same_as_device, container, false);
        return view;
    }
}