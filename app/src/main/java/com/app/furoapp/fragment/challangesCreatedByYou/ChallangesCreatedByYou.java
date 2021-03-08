package com.app.furoapp.fragment.challangesCreatedByYou;

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
import com.app.furoapp.fragment.challenges.HomeChallengesFragment;

public class ChallangesCreatedByYou extends Fragment {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_challange_created_by_you, container, false);
        View view = binding.getRoot();

        return view;
    }

    public ChallangesCreatedByYou() {

    }


    public static ChallangesCreatedByYou newInstance(String name) {
        ChallangesCreatedByYou fragment = new ChallangesCreatedByYou();
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














