package com.app.furoapp.fragment.challenges;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.adapter.ViewPagerAdapter;
import com.app.furoapp.databinding.FragmentHomeChallengeLeaderBoardBinding;
import com.app.furoapp.enums.EnumConstants;
import com.google.android.material.tabs.TabLayout;

import static com.app.furoapp.enums.EnumConstants.HOME_CHALLENGES_LEADERBOARD_FRAGMENT;
import static com.app.furoapp.enums.EnumConstants.HOME_CHALLENGE_DETAILS_FRAGMENT;
import static com.app.furoapp.enums.EnumConstants.WHAT_BRINGS_YOU_TO_FURO;

public class HomeChallengeLeaderBoard  extends Fragment {

    HomeMainActivity homeMainActivity;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    LeaderBoardInviteFragment leaderBoardInviteFragment;
    LeaderBoardFriendFragment leaderBoardFriendFragment;
    ImageView imageViewBackLeader;

    FragmentHomeChallengeLeaderBoardBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity= (HomeMainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home_challenge_leader_board, container, false);
        View view = binding.getRoot();
        setOnClickListeners();
        viewPager = binding.vpLeaderBoardCalll;
        tabLayout = binding.tabLeaderboardd;
        imageViewBackLeader = binding.ivBackBtnnewComminity;

        imageViewBackLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeMainActivity.setDisplayFragment(HOME_CHALLENGE_DETAILS_FRAGMENT,null);

            }
        });
        setupTabLayout();
        bindWidgetsWithAnEvent();

        viewPagerAdapter.addFrag(leaderBoardFriendFragment, "");
        viewPagerAdapter.addFrag(leaderBoardInviteFragment, "");

        viewPager.setAdapter(viewPagerAdapter);

        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    public HomeChallengeLeaderBoard() {

    }

    public static HomeChallengeLeaderBoard newInstance(String name) {
        HomeChallengeLeaderBoard fragment = new HomeChallengeLeaderBoard();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }
    private void setOnClickListeners() {



    }
    private void setupTabLayout() {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        leaderBoardFriendFragment = new LeaderBoardFriendFragment();
        leaderBoardInviteFragment = new LeaderBoardInviteFragment();
        binding.tabLeaderboardd.addTab(binding.tabLeaderboardd.newTab().setText("Friends"), true);
        binding.tabLeaderboardd.addTab(binding.tabLeaderboardd.newTab().setText("Invite"));

    }





    private void bindWidgetsWithAnEvent() {

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener( binding.tabLeaderboardd));
        binding.tabLeaderboardd.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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










