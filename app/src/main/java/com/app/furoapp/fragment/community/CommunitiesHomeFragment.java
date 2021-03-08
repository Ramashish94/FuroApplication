package com.app.furoapp.fragment.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.WelcomeActivityCommunity;
import com.app.furoapp.adapter.ViewPagerAdapter;
import com.app.furoapp.databinding.FragmentCommunityHomeBinding;
import com.google.android.material.tabs.TabLayout;


public class CommunitiesHomeFragment extends Fragment {

    HomeMainActivity homeMainActivity;
    FragmentCommunityHomeBinding binding;
    ImageView imageViewquestion;

    AllCommunitiesFragment allCommunitiesFragment;
    MyCommunityFragment myCommunityFragment;
    CommunityChallengesSelectChallangeListFragmen communityChallengesSelectChallangeListFragmen;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_community_home, container, false);
        View view = binding.getRoot();
        viewPager = binding.vpLeaderBoardCal;
        imageViewquestion= view.findViewById(R.id.Community);
        imageViewquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WelcomeActivityCommunity.class);
                startActivity(intent);
            }
        });
        setupTabLayout();

        viewPagerAdapter.addFrag(allCommunitiesFragment, "");
        viewPagerAdapter.addFrag(myCommunityFragment, "");

        viewPager.setAdapter(viewPagerAdapter);
        bindWidgetsWithAnEvent();




        return view;
    }

    public CommunitiesHomeFragment() {


    }


    public static CommunitiesHomeFragment newInstance(String name) {
        CommunitiesHomeFragment fragment = new CommunitiesHomeFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    private void setupTabLayout() {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());


        allCommunitiesFragment = new AllCommunitiesFragment();
        myCommunityFragment = new MyCommunityFragment();
        communityChallengesSelectChallangeListFragmen = new CommunityChallengesSelectChallangeListFragmen();
        binding.tabLeaderboard.addTab(binding.tabLeaderboard.newTab().setText("All Community"), true);
        binding.tabLeaderboard.addTab(binding.tabLeaderboard.newTab().setText("My Community"));

    }


    private void bindWidgetsWithAnEvent() {

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLeaderboard));
        binding.tabLeaderboard.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


}












