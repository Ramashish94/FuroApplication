package com.app.furoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.app.furoapp.R;
import com.app.furoapp.adapter.MyFancyCoverWhiteFlowAdapter;
import com.app.furoapp.fragment.Leaderboard.FragmentLeaderBoard;
import com.app.furoapp.model.Items;
import com.app.furoapp.model.chooseChallange.Challenge;
import com.app.furoapp.model.chooseChallange.SelectChallangeResponse;
import com.app.furoapp.model.chooseChallange.Subcategory;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.widget.francyconverflow.FancyCoverFlow;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderBoardActivity extends AppCompatActivity {


    private SelectChallangeResponse selectChallangeResponse;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    private TabLayout tabLayout;
    ImageView imageView;
    TextView textViewtvActivityValue;
    private HashMap<String, List<Subcategory>> hashMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        linearLayout = findViewById(R.id.linearLayout);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.leaderboard);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaderBoardActivity.this, WelcomeLeaderBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        textViewtvActivityValue = findViewById(R.id.tvActivityValue);

        init();
        getChooseChallengeData();

    }

    public void init() {
        tabLayout = findViewById(R.id.tabProfileTab);
        hashMap = new HashMap<>();
    }

    private void getChooseChallengeData() {
        if (Util.isInternetConnected(this)) {
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            RestClient.userSelectChallange(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), new Callback<SelectChallangeResponse>() {
                @Override
                public void onResponse(Call<SelectChallangeResponse> call, Response<SelectChallangeResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            selectChallangeResponse = response.body();
                            setTab(selectChallangeResponse.getChallenges());

//                            Toast.makeText(LeaderBoardActivity.this,selectChallangeResponse.getChallenges().get(0).getChallenge(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SelectChallangeResponse> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(LeaderBoardActivity.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Util.dismissProgressDialog();
            Toast.makeText(LeaderBoardActivity.this, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }


    private void setFancyView(List<Subcategory> list) {

        final FancyCoverFlow fancyCoverFlow = findViewById(R.id.vpFacnyView);

        List<Items> mFancyCoverFlows = new ArrayList<>();
        for (Subcategory subcategory : list) {
            if (subcategory.getSubcategory().equals("JUMPING JACKS")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_jumping_jacks;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }
            if (subcategory.getSubcategory().equals("JACKNIFES")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_jackknifes;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }
            if (subcategory.getSubcategory().equals("BURPEES")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_burpees;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);

            }
            if (subcategory.getSubcategory().equals("PILATES")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_pilates;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }
            if (subcategory.getSubcategory().equals("LUNGES")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_lunges;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }
            if (subcategory.getSubcategory().equals("SQUATS")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_squats;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }

            if (subcategory.getSubcategory().equals("PUSH UPS")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_pushups;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }
            if (subcategory.getSubcategory().equals("RUNNING")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_running;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }
            if (subcategory.getSubcategory().equals("WALKING")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_walking;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }
            if (subcategory.getSubcategory().equals("CYCLING")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_cycling;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }
            if (subcategory.getSubcategory().equals("SKIPPING")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_skipping;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }
            if (subcategory.getSubcategory().equals("SPOT JOGGING")) {
                Items item = new Items();
                item.imageName = R.drawable.ic_spot_jogging;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }
            if (subcategory.getSubcategory().equals("SIT UPS")) {
                Items item = new Items();
                item.imageName = R.drawable.situpsnew;
                item.categoryName = subcategory.getSubcategory();
                item.name = subcategory.getPercentile();
                item.setSelected(false);
                mFancyCoverFlows.add(item);
            }

        }

//        Integer arrayOfChallenges[] = new Integer[]{
//                R.drawable.ic_jumping_jacks,
//                R.drawable.ic_cycling,
//                R.drawable.ic_squads,
//                R.drawable.ic_pushups,
//                R.drawable.ic_running2,
//        };
//        String arrayOfCategoryName[] = new String[]{
//                getString(R.string.running),
//                getString(R.string.cycling),
//                getString(R.string.squats),
//                getString(R.string.pushups),
//                getString(R.string.running),
//        };


//        List<Items> mFancyCoverFlows = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            Items item = new Items();
//            item.imageName = arrayOfChallenges[i];
//            item.categoryName = arrayOfCategoryName[i];
//            item.setName("FQ Score :77");
//            item.setSelected(false);
//            mFancyCoverFlows.add(item);
//        }
        MyFancyCoverWhiteFlowAdapter mMyFancyCoverFlowAdapter = new MyFancyCoverWhiteFlowAdapter(this, mFancyCoverFlows);
        fancyCoverFlow.setAdapter(mMyFancyCoverFlowAdapter);
        mMyFancyCoverFlowAdapter.notifyDataSetChanged();
        fancyCoverFlow.setUnselectedAlpha(0.5f);
        fancyCoverFlow.setUnselectedSaturation(0.5f);
        fancyCoverFlow.setUnselectedScale(0.3f);
        fancyCoverFlow.setSpacing(0);
        fancyCoverFlow.setMaxRotation(0);
        fancyCoverFlow.setScaleDownGravity(0.5f);
        fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        int num = Integer.MAX_VALUE / 2 % mFancyCoverFlows.size();
        int selectPosition = Integer.MAX_VALUE / 2 - num;
        fancyCoverFlow.setSelection(selectPosition);
        fancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Items homeFancyCoverFlow = (Items) fancyCoverFlow.getSelectedItem();
                if (homeFancyCoverFlow != null) {
                    textViewtvActivityValue.setText(homeFancyCoverFlow.categoryName);
//                    Toast.makeText(LeaderBoardActivity.this, homeFancyCoverFlow.categoryName, Toast.LENGTH_SHORT).show();
                    addfragment(homeFancyCoverFlow.categoryName);
//                    getLeaderData(homeFancyCoverFlow.categoryName);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void setTab(List<Challenge> challenges) {
        for (Challenge challenge : challenges) {
            tabLayout.addTab(tabLayout.newTab().setText(challenge.getChallenge()));
            hashMap.put(challenge.getChallenge(), challenge.getSubcategory());
        }

        List<Subcategory> list = hashMap.get("FLEXIBLITY");
        setFancyView(list);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<Subcategory> list = hashMap.get(tab.getText().toString());
                setFancyView(list);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void addfragment(String act) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentLeaderBoard fragmentLeaderBoard = FragmentLeaderBoard.newInstance(act);
        ft.replace(R.id.frame, fragmentLeaderBoard);
        ft.commit();
    }

}
