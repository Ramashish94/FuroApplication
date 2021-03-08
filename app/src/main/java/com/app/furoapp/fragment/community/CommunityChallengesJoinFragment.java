package com.app.furoapp.fragment.community;

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
import com.app.furoapp.databinding.FragmentCommunityJoinChallengeDetailsBinding;

public class CommunityChallengesJoinFragment extends Fragment {
    HomeMainActivity homeMainActivity;
    FragmentCommunityJoinChallengeDetailsBinding binding;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_community_join_challenge_details, container, false);
        View view = binding.getRoot();

        return view;
    }

    public CommunityChallengesJoinFragment() {

    }

    public static CommunityChallengesJoinFragment newInstance(String name) {
        CommunityChallengesJoinFragment fragment = new CommunityChallengesJoinFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }











}
