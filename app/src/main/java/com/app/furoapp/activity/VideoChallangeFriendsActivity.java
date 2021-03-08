package com.app.furoapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.adapter.ViewPagerAdapter;
import com.app.furoapp.databinding.ActivityVideoChallangeFriendsBinding;
import com.app.furoapp.fragment.challenges.LeaderBoardFriendFragment;
import com.app.furoapp.fragment.challenges.LeaderBoardInviteFragment;
import com.app.furoapp.fragment.socialFragment.Social;
import com.google.android.material.tabs.TabLayout;

public class VideoChallangeFriendsActivity extends AppCompatActivity {

    ActivityVideoChallangeFriendsBinding binding;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    LeaderBoardInviteFragment leaderBoardInviteFragment;
    LeaderBoardFriendFragment leaderBoardFriendFragment;
    Social socialFragment;
    ImageView imageViewBackLeader;
    TextView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_challange_friends);

        viewPager = binding.vpLeaderBoardCalll;
        tabLayout = binding.tabLeaderboardd;
        backButton = binding.tvTitle;
        imageViewBackLeader = binding.ivBackBtnnewComminity;

        setupTabLayout();
        bindWidgetsWithAnEvent();

        viewPagerAdapter.addFrag(leaderBoardFriendFragment, "");
        viewPagerAdapter.addFrag(socialFragment, "");
        viewPagerAdapter.addFrag(leaderBoardInviteFragment, "");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoChallangeFriendsActivity.this, HomeMainActivity.class);
                startActivity(intent);

            }
        });


    }

    private void setupTabLayout() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        leaderBoardFriendFragment = new LeaderBoardFriendFragment();
        socialFragment = new Social();
        leaderBoardInviteFragment = new LeaderBoardInviteFragment();

        binding.tabLeaderboardd.addTab(binding.tabLeaderboardd.newTab().setText("Friends"), true);
        binding.tabLeaderboardd.addTab(binding.tabLeaderboardd.newTab().setText("Social"));
        binding.tabLeaderboardd.addTab(binding.tabLeaderboardd.newTab().setText("Invite"));

    }


    private void bindWidgetsWithAnEvent() {

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLeaderboardd));
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

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_alertdialognew, null);


        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();


        ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
        TextView text_logout = dialogView.findViewById(R.id.text_logout);
        TextView noiwanttocontinue = dialogView.findViewById(R.id.noiwanttocontinuee);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        noiwanttocontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoChallangeFriendsActivity.this, CreateChallengeActivity  .class);
                startActivity(intent);
                finish();


            }
        });

        dialog.show();

    }
}



