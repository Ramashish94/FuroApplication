
package com.app.furoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.app.furoapp.R;
import com.app.furoapp.fragment.FriendsSection.FragmentFriend;
import com.app.furoapp.fragment.FriendsSection.FragmentFriendInvite;
import com.app.furoapp.fragment.FriendsSection.FragmentFriendPending;
import com.google.android.material.tabs.TabLayout;

public class FriendsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TextView tvinviteFriends;
    ImageView imageViewFriends;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tvinviteFriends = findViewById(R.id.inviteFriends);
        imageViewFriends = findViewById(R.id.friends);
        imageViewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this,WewlcomeFriendsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tvinviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this,InviteFriendsActivity.class);
                startActivity(intent);
            }
        });

        init();
        setTab();

    }

    private void init() {
        tabLayout = findViewById(R.id.tabProfileTab);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentFriend fragmentFriend = new FragmentFriend();
        ft.add(R.id.frame, fragmentFriend);
        ft.commit();
    }

    private void setTab()
    {
        tabLayout.addTab(tabLayout.newTab().setText("Friend List"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Friends"));



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Friend List"))
                {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    FragmentFriend fragmentFriend = new FragmentFriend();
                    ft.replace(R.id.frame, fragmentFriend);
                    ft.commit();
                }else if(tab.getText().equals("Pending"))
                {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    FragmentFriendPending fragmentFriendPending = new FragmentFriendPending();
                    ft.replace(R.id.frame, fragmentFriendPending);
                    ft.commit();
                }else if(tab.getText().equals("Add Friends"))
                {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    FragmentFriendInvite fragmentFriendInvite = new FragmentFriendInvite();
                    ft.replace(R.id.frame, fragmentFriendInvite);
                    ft.commit();
                }
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