package com.app.furoapp.fragment.challenges;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.databinding.FragmentHomeChooseChallengeBinding;


public class ChallengesFragment extends Fragment {

    HomeMainActivity homeMainActivity;
    FragmentHomeChooseChallengeBinding binding;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home_mychallenges, container, false);
        View view = binding.getRoot();

        return view;
    }

    public ChallengesFragment() {

    }


    public static HomeChallengesFragment newInstance(String name) {
        HomeChallengesFragment fragment = new HomeChallengesFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();

    }


}











