package com.app.furoapp.fragment.profileSection;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.ImagePickerActivity;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.adapter.MyFancyCoverFlowAdapter;
import com.app.furoapp.adapter.MyFancyCoverWhiteFlowAdapter;
import com.app.furoapp.adapter.ProfileAllTimeAdapter;
import com.app.furoapp.adapter.ProfileMonthlyAdapter;
import com.app.furoapp.adapter.ProfileWeeklyAdapter;
import com.app.furoapp.databinding.FragmentProfileSectionBinding;
import com.app.furoapp.model.Items;
import com.app.furoapp.model.chooseChallange.Challenge;
import com.app.furoapp.model.chooseChallange.SelectChallangeResponse;
import com.app.furoapp.model.chooseChallange.Subcategory;
import com.app.furoapp.model.getImage.UserImageRequest;
import com.app.furoapp.model.getImage.UserImageResponse;
import com.app.furoapp.model.profile.AddProfile;
import com.app.furoapp.model.profile.ClubsAllTime;
import com.app.furoapp.model.profile.ClubsMonthly;
import com.app.furoapp.model.profile.ClubsWeekly;
import com.app.furoapp.model.profile.GraphProfile;
import com.app.furoapp.model.profile.ItemProfile;
import com.app.furoapp.model.profile.ProfileModel;
import com.app.furoapp.model.updateimage.UpdateImageResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FileUtils1;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.widget.francyconverflow.FancyCoverFlow;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.furoapp.activity.SignUpActivity.REQUEST_IMAGE;


/**
 * @author bhawa
 */
public class ProfileHomeNewActivity extends AppCompatActivity {

    FragmentProfileSectionBinding binding;
    LinearLayout linearLayout;
    TextView textViewtvActivityValue;
    ProgressBar progressBar;
    MultipartBody.Part image;
    File imageFile;
    private String imageUrl;

    TextView textViewprofile, tvfqscore;
    BarChart barChart;
    ProgressBar progressBarImage;
    LinearLayout linearLayoutimagechange;
    private ImageView userProfile;
    private TextView userProfileName, logoutUser;
    private FancyCoverFlow fancyCoverFlow, fancyCoverFlowClub;
    private TabLayout tabLayout;
    private HashMap<String, List<Subcategory>> hashMap;
    private SelectChallangeResponse selectChallangeResponse;
    private RecyclerView recyclerView;
    private List<ItemProfile> mFancyCoverFlows;
    private Switch sw_weekly, sw_monthly, sw_all_time;
    private String str_act, userImageUpdated;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_profile_section);
        View view = binding.getRoot();

        hashMap = new HashMap<>();
        progressBar = binding.progressBar;
        progressBarImage = binding.progressbar;
        textViewtvActivityValue = binding.tvActivityValue;

        linearLayout = binding.linearlayout;
        userProfile = binding.ivUserNewProfile;
        logoutUser = binding.logout;
        linearLayoutimagechange = binding.linearprofilechange;
        textViewprofile = binding.textnoactivity;
        userProfileName = binding.tvTitleUserName;
        tabLayout = binding.tabProfileTab;
        fancyCoverFlow = binding.viewpager;
        recyclerView = binding.recyclerview;
        fancyCoverFlowClub = binding.viewpager2;
        sw_weekly = binding.swWeekly;
        sw_monthly = binding.swMonthly;
        sw_all_time = binding.swAllTime;
        barChart = binding.barchart;
        /*userImageUpdated = FuroPrefs.getString(getApplicationContext(), "userImage");*/
       /* if (TextUtils.isEmpty(userImageUpdated)) {

        } else {
            Picasso.with(getApplicationContext()).load(userImageUpdated).into(userProfile);
        }*/


        userId = FuroPrefs.getString(getApplication(), "loginUserId");

        linearLayoutimagechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerOptions();
            }
        });


        getUserImage();

        mFancyCoverFlows = new ArrayList<>();

        String image = FuroPrefs.getString(getApplicationContext(), "userImage");
        String uniqueName = FuroPrefs.getString(getApplicationContext(), "loginUserNameNew");

        /*GlideApp.with(this).load(image).error(R.drawable.user_icon).into(userProfile);*/

        userProfileName.setText(uniqueName);
        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        sw_weekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    sw_monthly.setChecked(false);
                    sw_all_time.setChecked(false);
                    getchooseClubData(str_act, "week");
                }
            }
        });

        sw_monthly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    sw_weekly.setChecked(false);
                    sw_all_time.setChecked(false);
                    getchooseClubData(str_act, "monthly");
                }
            }
        });


        sw_all_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    sw_monthly.setChecked(false);
                    sw_weekly.setChecked(false);
                    getchooseClubData(str_act, "all");
                }
            }
        });

        getChooseChallengeData();
//        getchooseClubData(str_act, "all");


    }


    private void setListAdapter(String type, ProfileModel profileModel) {

        if (type.equals("all")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ProfileAllTimeAdapter profileAllTimeAdapter = new ProfileAllTimeAdapter(profileModel.getProfile().getAllTime(), getApplicationContext());
            recyclerView.setAdapter(profileAllTimeAdapter);
            profileAllTimeAdapter.notifyDataSetChanged();



        }

        if (type.equals("week")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ProfileWeeklyAdapter profileWeeklyAdapter = new ProfileWeeklyAdapter(profileModel.getProfile().getWeekly(), getApplicationContext());
            recyclerView.setAdapter(profileWeeklyAdapter);

            profileWeeklyAdapter.notifyDataSetChanged();



        }

        if (type.equals("monthly")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            ProfileMonthlyAdapter profileMonthlyAdapter = new ProfileMonthlyAdapter(profileModel.getProfile().getMonthly(), getApplicationContext());
            recyclerView.setAdapter(profileMonthlyAdapter);
            profileMonthlyAdapter.notifyDataSetChanged();


        }


    }

//    private ArrayList<ChallengeItemModel> getListOfChallenges() {
//        ArrayList<ChallengeItemModel> listOfChallenges = new ArrayList<ChallengeItemModel>();
//
//        Integer arrayOfChallenges[] = new Integer[]{R.drawable.ic_running,
//                R.drawable.ic_cycling,
//                R.drawable.ic_squads,
//                R.drawable.ic_pushups,
//                R.drawable.ic_running2,
//        };
//
//        for (int i = 0; i < 4; i++) {
//
//            for (int j = 0; j < arrayOfChallenges.length; j++) {
//
//                ChallengeItemModel model = new ChallengeItemModel();
//
//                model.imageName = arrayOfChallenges[j];
//                listOfChallenges.add(model);
//
//
//            }
//            return listOfChallenges;
//
//
//        }
//
//
//        return null;
//    }

    private void setClubViewPagerView() {

//        final FancyCoverFlow fancyCoverFlow = view.findViewById(R.id.viewpager2);
//
//        List<Integer> imgRes = new ArrayList<Integer>();
//        imgRes.add(R.drawable.badge_star);
//        imgRes.add(R.drawable.badge_ribben);
//        imgRes.add(R.drawable.badge_yellow);
//        List<Items> mFancyCoverFlows = new ArrayList<>();
//
//        for (int i = 0; i < 12; i++) {
//            for (int j = 0; j < imgRes.size(); j++) {
//
//                Items item = new Items();
//                item.imageName = imgRes.get(j);
//                item.setName(i + 1 + "K ");
//                item.setSelected(false);
//                mFancyCoverFlows.add(item);
//            }
//        }
        MyFancyCoverFlowAdapter mMyFancyCoverFlowAdapter = new MyFancyCoverFlowAdapter(this, mFancyCoverFlows);
        fancyCoverFlowClub.setAdapter(mMyFancyCoverFlowAdapter);
        mMyFancyCoverFlowAdapter.notifyDataSetChanged();
        fancyCoverFlowClub.setUnselectedAlpha(0.5f);
        fancyCoverFlowClub.setUnselectedSaturation(0.5f);
        fancyCoverFlowClub.setUnselectedScale(0.3f);
        fancyCoverFlowClub.setSpacing(0);
        fancyCoverFlowClub.setMaxRotation(0);
        fancyCoverFlowClub.setScaleDownGravity(0.5f);
        fancyCoverFlowClub.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        int num = Integer.MAX_VALUE / 2 % mFancyCoverFlows.size();
        int selectPosition = Integer.MAX_VALUE / 2 - num;
        fancyCoverFlowClub.setSelection(selectPosition);
        fancyCoverFlowClub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ItemProfile homeFancyCoverFlow = (ItemProfile) fancyCoverFlowClub.getSelectedItem();
                if (homeFancyCoverFlow != null) {
//                    Toast.makeText(getContext(), homeFancyCoverFlow.getName(), Toast.LENGTH_SHORT).show();
//                    getchooseClubData(homeFancyCoverFlow.getName(),"all");

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
//                str_act = tab.getText().toString();
//                getchooseClubData(str_act, "all");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setChartView(List<GraphProfile> graph_monthly) {

//        HIChartView chartView = view.findViewById(R.id.hiChartView);

//        HIOptions options = new HIOptions();
//
//        HITitle title = new HITitle();
//        title.setText("com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.User Progress");
//        options.setTitle(title);
//
//        HIYAxis yAxis1 = new HIYAxis();
//        yAxis1.setClassName("highcharts-color-0");
//        yAxis1.setTitle(new HITitle());
//        yAxis1.setVisible(false);
//        yAxis1.getTitle().setText("Primary axis");
//
//        HIYAxis yAxis2 = new HIYAxis();
//        yAxis2.setClassName("highcharts-color-1");
//        yAxis2.setOpposite(true);
//        yAxis2.setTitle(new HITitle());
//        yAxis2.setVisible(false);
//        yAxis2.getTitle().setText("Secondary axis");
//
//        options.setYAxis(new ArrayList<>(Arrays.asList(yAxis1, yAxis2)));
//
//        HIPlotOptions plotOptions = new HIPlotOptions();
//        plotOptions.setColumn(new HIColumn());
//        plotOptions.getColumn().setBorderRadius(10);
//        plotOptions.getColumn().setColors(new ArrayList<>(Arrays.asList("#818282", "#19CFE6")));
//        options.setPlotOptions(plotOptions);
//
//        HISeries series1 = new HIColumn();
//        Number[] series1_data = new Number[] {1, 3, 2, 4};
//        series1.setData(new ArrayList<>(Arrays.asList(series1_data)));
//
//        HIColumn series2 = new HIColumn();
//        Number[] series2_data = new Number[] {324, 124, 547, 221};
//        series2.setData(new ArrayList<>(Arrays.asList(series2_data)));
//        series2.setYAxis(1);
//
///*
//        HIColumn series3 = new HIColumn();
//        series3.setColor(HIColor.initWithHexValue("#000"));
//        Number[] series3_data = new Number[] {324, 124, 547, 221};
//        series2.setData(new ArrayList<>(Arrays.asList(series3_data)));
//        series3.setYAxis(1);
//*/
//
//        options.setSeries(new ArrayList<>(Arrays.asList(series1, series2)));
//
//        chartView.setOptions(options);

//        HIOptions options = new HIOptions();
//
//        HIChart chart = new HIChart();
//        chart.setType("column");
//        options.setChart(chart);
//
//        HITitle title = new HITitle();
//        title.setText("Demo chart");
//
//        options.setTitle(title);
//
//        HIColumn series = new HIColumn();
//        series.setData(new ArrayList<>(Arrays.asList(49.9, 71.5, 106.4, 129.2, 144, 176, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4)));
//        options.setSeries(new ArrayList<HISeries>(Collections.singletonList(series)));
//
//        chartView.setOptions(options);

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        int index = 0;

        for (GraphProfile graphProfile : graph_monthly) {
            entries.add(new BarEntry(Float.parseFloat(graphProfile.getCount()), index));
            labels.add(graphProfile.getG_date());
            index++;
        }

        BarDataSet bardataset = new BarDataSet(entries, "Cells");
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        // set the data and list of labels into chart
        barChart.setDescription("");  // set the description
        bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.light_blue)));

        barChart.animateY(5000);

    }

    private void getChooseChallengeData() {
        if (Util.isInternetConnected(getApplicationContext())) {
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            RestClient.userSelectChallange(new retrofit2.Callback<SelectChallangeResponse>() {
                @Override
                public void onResponse(Call<SelectChallangeResponse> call, Response<SelectChallangeResponse> response) {
                    linearLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        if (response.body().getChallenges().size() > 0) {
                            selectChallangeResponse = response.body();
                            setTab(selectChallangeResponse.getChallenges());

//                            Toast.makeText(LeaderBoardActivity.this,selectChallangeResponse.getChallenges().get(0).getChallenge(),Toast.LENGTH_SHORT).show();
                        } else {
                            textViewprofile.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<SelectChallangeResponse> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Util.dismissProgressDialog();
            Toast.makeText(getApplicationContext(), "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getchooseClubData(String act, String type) {

        String userIdProfile = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        if (Util.isInternetConnected(getApplicationContext())) {
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            RestClient.addProfile(new AddProfile(userIdProfile, act), new retrofit2.Callback<ProfileModel>() {
                @Override
                public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                    linearLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    if (response.body() != null) {

                        ProfileModel profileModel = response.body();
//                        for(ClubsWeekly clubsWeekly : profileModel.getProfile().getClubsWeekly()){
//                                ItemProfile item = new ItemProfile();
//                                item.imageName = clubsWeekly.getImage();
//                                item.name = clubsWeekly.getFinished();
//                                item.setSelected(false);
//                                 mFancyCoverFlows.add(item);
//                        }


                        if (type.equals("week")) {
                            setListAdapter("week", profileModel);
                            setChartView(profileModel.getProfile().getGraph_weekly());
                            if (profileModel.getProfile().getClubsWeekly().size() > 0) {

                                for (ClubsWeekly clubsWeekly : profileModel.getProfile().getClubsWeekly()) {
                                    ItemProfile item = new ItemProfile();
                                    item.imageName = clubsWeekly.getImage();
                                    item.name = clubsWeekly.getFinished();

                                    item.setSelected(false);
                                    mFancyCoverFlows.add(item);
                                }
                                setClubViewPagerView();

                            }
                        } else if (type.equals("all")) {
                            setListAdapter("all", profileModel);
                            setChartView(profileModel.getProfile().getGraph_alltime());
                            if (profileModel.getProfile().getClubsAllTime().size() > 0) {

                                for (ClubsAllTime clubsAllTime : profileModel.getProfile().getClubsAllTime()) {
                                    ItemProfile item = new ItemProfile();
                                    item.imageName = clubsAllTime.getImage();
                                    item.name = clubsAllTime.getFinished();
                                    item.setSelected(false);
                                    mFancyCoverFlows.add(item);
                                }
                                setClubViewPagerView();

                            }
                        } else if (type.equals("monthly")) {
                            setListAdapter("monthly", profileModel);
                            setChartView(profileModel.getProfile().getGraph_monthly());
                            if (profileModel.getProfile().getClubsMonthly().size() > 0) {

                                for (ClubsMonthly clubsMonthly : profileModel.getProfile().getClubsMonthly()) {
                                    ItemProfile item = new ItemProfile();
                                    item.imageName = clubsMonthly.getImage();
                                    item.name = clubsMonthly.getFinished();
                                    item.setSelected(false);
                                    mFancyCoverFlows.add(item);
                                }
                                setClubViewPagerView();

                            }
                        }


//                        for (int i = 0; i < 12; i++) {
//                            for (int j = 0; j < imgRes.size(); j++) {
//
//                                Items item = new Items();
//                                item.imageName = imgRes.get(j);
//                                item.setName(i + 1 + "K ");
//                                item.setSelected(false);
//                                mFancyCoverFlows.add(item);
//                            }
//                        }
//                        Toast.makeText(getActivity(),profileModel.getProfile().getAllTime().get(0).getUserId(),Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ProfileModel> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Util.dismissProgressDialog();
            Toast.makeText(getApplicationContext(), "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setFancyView(List<Subcategory> list) {

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
//                    tvActivityValue.setText(homeFancyCoverFlow.categoryName);
//                     Toast.makeText(getActivity(), homeFancyCoverFlow.categoryName, Toast.LENGTH_SHORT).show();
//                    addfragment(homeFancyCoverFlow.categoryName);
//                    getLeaderData(homeFancyCoverFlow.categoryName);
                    str_act = homeFancyCoverFlow.categoryName;
                    getchooseClubData(str_act, "all");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        profileUpdate();
    }

    private void logout() {

        FuroPrefs.clear(getApplicationContext());
        new androidx.appcompat.app.AlertDialog.Builder(getApplicationContext())
                .setMessage("Are you sure you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), LoginTutorialScreen.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getApplicationContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getApplicationContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void loadProfile(String url) {


        Picasso.with(this).load(url)
                .into(userProfile);

        userProfile.setColorFilter(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));


        imageUrl = url;

        profileUpdate();


    }

    public void profileUpdate() {

        String userid = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        if (!TextUtils.isEmpty(imageUrl)) {

            String path = FileUtils1.getPath(getApplicationContext(), Uri.parse(imageUrl));


            imageFile = new File(path);

            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), userid);
            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            // Create MultipartBody.Part using file request-body,file name and part name
            image = MultipartBody.Part.createFormData("image", imageFile.getName(), fileReqBody);

            progressBarImage.setVisibility(View.VISIBLE);
            RestClient.userImageUpdate(userId, image, new retrofit2.Callback<UpdateImageResponse>() {
                @Override
                public void onResponse(Call<UpdateImageResponse> call, Response<UpdateImageResponse> response) {
                    progressBarImage.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            String userImage = response.body().getUser().getImage();
                            FuroPrefs.putString(getApplicationContext(), "userImage", userImage);
                            Toast.makeText(getApplicationContext(), "Profile picture updated successfully.", Toast.LENGTH_SHORT).show();
                            Picasso.with(getApplicationContext()).load(userImage).into(userProfile);

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Profile picture updated failed", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<UpdateImageResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
                }
            });


        }


    }

    public void getUserImage() {

        UserImageRequest userImageRequest = new UserImageRequest();
        userImageRequest.setUserId(userId);

        progressBarImage.setVisibility(View.VISIBLE);
        RestClient.userimageGet(userImageRequest, new Callback<UserImageResponse>() {
            @Override
            public void onResponse(Call<UserImageResponse> call, Response<UserImageResponse> response) {
                if (response != null) {
                    progressBarImage.setVisibility(View.GONE);
                    if (response.body() != null) {

                        String image = response.body().getImage();
                        Picasso.with(getApplicationContext()).load(image).into(userProfile);

                    }

                }

            }

            @Override
            public void onFailure(Call<UserImageResponse> call, Throwable t) {
                Toast.makeText(ProfileHomeNewActivity.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();

            }
        });


    }

}