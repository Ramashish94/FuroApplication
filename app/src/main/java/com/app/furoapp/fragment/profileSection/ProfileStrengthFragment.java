package com.app.furoapp.fragment.profileSection;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.GlideApp;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.adapter.MyFancyCoverFlowAdapter;
import com.app.furoapp.adapter.MyFancyCoverWhiteFlowAdapter;
import com.app.furoapp.adapter.ProfileAllTimeAdapter;
import com.app.furoapp.adapter.ProfileMonthlyAdapter;
import com.app.furoapp.adapter.ProfileWeeklyAdapter;
import com.app.furoapp.base.BaseFragment;
import com.app.furoapp.databinding.FragmentProfileSectionBinding;
import com.app.furoapp.model.Items;
import com.app.furoapp.model.chooseChallange.Challenge;
import com.app.furoapp.model.chooseChallange.SelectChallangeResponse;
import com.app.furoapp.model.chooseChallange.Subcategory;
import com.app.furoapp.model.profile.AddProfile;
import com.app.furoapp.model.profile.ClubsAllTime;
import com.app.furoapp.model.profile.ClubsMonthly;
import com.app.furoapp.model.profile.ClubsWeekly;
import com.app.furoapp.model.profile.ItemProfile;
import com.app.furoapp.model.profile.ProfileModel;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.widget.francyconverflow.FancyCoverFlow;
import com.google.android.material.tabs.TabLayout;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * @author avdhesh
 */
public class ProfileStrengthFragment extends BaseFragment {

    FragmentProfileSectionBinding binding;
    HomeMainActivity mActivity;
    private ImageView userProfile;
    private TextView userProfileName, logoutUser;
    private FancyCoverFlow fancyCoverFlow, fancyCoverFlowClub;
    private TabLayout tabLayout;
    private HashMap<String, List<Subcategory>> hashMap;
    private SelectChallangeResponse selectChallangeResponse;
    private RecyclerView recyclerView;
    private List<ItemProfile> mFancyCoverFlows;
    private Switch sw_weekly, sw_monthly, sw_all_time;
    private String str_act;


    public ProfileStrengthFragment() {
        // Required empty public constructor
    }

    public static ProfilesHomeNewFragment newInstance(String name) {
        ProfilesHomeNewFragment fragment = new ProfilesHomeNewFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_section, container, false);
        View view = binding.getRoot();

        userProfile = binding.ivUserNewProfile;
        logoutUser = binding.logout;

        tabLayout = binding.tabProfileTab;
        fancyCoverFlow = binding.viewpager;
        recyclerView = binding.recyclerview;
        fancyCoverFlowClub = binding.viewpager2;
        sw_weekly = binding.swWeekly;
        sw_monthly = binding.swMonthly;
        sw_all_time = binding.swAllTime;

        mFancyCoverFlows = new ArrayList<>();

        String image = FuroPrefs.getString(getContext(), "userImage");
        String user_name = FuroPrefs.getString(getContext(), "loginUserName");

        GlideApp.with(this).load(image).error(R.drawable.user_icon)
                .into(userProfile);


        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showLogoutAlert();
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
        getchooseClubData(str_act, "all");

        return view;
    }

    @Override
    protected void setUp(View view) {
        initView();
        setChartView(view);
    }

    /*  predefined properties*/
    private void initView() {
        mActivity = (HomeMainActivity) getActivity();
        hashMap = new HashMap<>();
    }

    private void setListAdapter(String type, ProfileModel profileModel) {

        if (type.equals("all")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ProfileAllTimeAdapter profileAllTimeAdapter = new ProfileAllTimeAdapter(profileModel.getProfile().getAllTime(), getContext());
            recyclerView.setAdapter(profileAllTimeAdapter);
            profileAllTimeAdapter.notifyDataSetChanged();

        }

        if (type.equals("week")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ProfileWeeklyAdapter profileWeeklyAdapter = new ProfileWeeklyAdapter(profileModel.getProfile().getWeekly(), getContext());
            recyclerView.setAdapter(profileWeeklyAdapter);
            profileWeeklyAdapter.notifyDataSetChanged();

        }

        if (type.equals("monthly")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ProfileMonthlyAdapter profileMonthlyAdapter = new ProfileMonthlyAdapter(profileModel.getProfile().getMonthly(), getContext());
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
        MyFancyCoverFlowAdapter mMyFancyCoverFlowAdapter = new MyFancyCoverFlowAdapter(getContext(), mFancyCoverFlows);
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
                str_act = tab.getText().toString();
                getchooseClubData(str_act, "all");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setChartView(View view) {

        HIChartView chartView = view.findViewById(R.id.hiChartView);

        HIOptions options = new HIOptions();

        HITitle title = new HITitle();
        title.setText("User Progress");
        options.setTitle(title);

        HIYAxis yAxis1 = new HIYAxis();
        yAxis1.setClassName("highcharts-color-0");
        yAxis1.setTitle(new HITitle());
        yAxis1.setVisible(false);
        yAxis1.getTitle().setText("Primary axis");

        HIYAxis yAxis2 = new HIYAxis();
        yAxis2.setClassName("highcharts-color-1");
        yAxis2.setOpposite(true);
        yAxis2.setTitle(new HITitle());
        yAxis2.setVisible(false);
        yAxis2.getTitle().setText("Secondary axis");

        options.setYAxis(new ArrayList<>(Arrays.asList(yAxis1, yAxis2)));

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setColumn(new HIColumn());
        plotOptions.getColumn().setBorderRadius(10);
        plotOptions.getColumn().setColors(new ArrayList<>(Arrays.asList("#818282", "#19CFE6")));
        options.setPlotOptions(plotOptions);

        HISeries series1 = new HIColumn();
        Number[] series1_data = new Number[] {1, 3, 2, 4};
        series1.setData(new ArrayList<>(Arrays.asList(series1_data)));

        HIColumn series2 = new HIColumn();
        Number[] series2_data = new Number[] {324, 124, 547, 221};
        series2.setData(new ArrayList<>(Arrays.asList(series2_data)));
        series2.setYAxis(1);

/*
        HIColumn series3 = new HIColumn();
        series3.setColor(HIColor.initWithHexValue("#000"));
        Number[] series3_data = new Number[] {324, 124, 547, 221};
        series2.setData(new ArrayList<>(Arrays.asList(series3_data)));
        series3.setYAxis(1);
*/

        options.setSeries(new ArrayList<>(Arrays.asList(series1, series2)));

        chartView.setOptions(options);
    }

    private void getChooseChallengeData() {
        if (Util.isInternetConnected(getActivity())) {
            Util.showProgressDialog(getActivity());
            RestClient.userSelectChallange(new retrofit2.Callback<SelectChallangeResponse>() {
                @Override
                public void onResponse(Call<SelectChallangeResponse> call, Response<SelectChallangeResponse> response) {
                    Util.dismissProgressDialog();
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
                    Toast.makeText(getActivity(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Util.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getchooseClubData(String act, String type) {
        if (Util.isInternetConnected(getActivity())) {
            Util.showProgressDialog(getActivity());
            RestClient.addProfile(new AddProfile("24", "Jumping Jacks"), new retrofit2.Callback<ProfileModel>() {
                @Override
                public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                    Util.dismissProgressDialog();
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
                            if (profileModel.getProfile().getClubsWeekly().size() > 0) {

                                for (ClubsWeekly clubsWeekly : profileModel.getProfile().getClubsWeekly()) {
                                    ItemProfile item = new ItemProfile();
                                    item.imageName = (clubsWeekly.getImage());
                                    item.name = clubsWeekly.getFinished();
                                    item.setSelected(false);
                                    mFancyCoverFlows.add(item);
                                }
                                setClubViewPagerView();
                                setListAdapter("week", profileModel);
                            }
                        } else if (type.equals("all")) {
                            if (profileModel.getProfile().getClubsAllTime().size() > 0) {

                                for (ClubsAllTime clubsAllTime : profileModel.getProfile().getClubsAllTime()) {
                                    ItemProfile item = new ItemProfile();
                                    item.imageName = (clubsAllTime.getImage());
                                    item.name = clubsAllTime.getFinished();
                                    item.setSelected(false);
                                    mFancyCoverFlows.add(item);
                                }
                                setClubViewPagerView();
                                setListAdapter("all", profileModel);
                            }
                        } else if (type.equals("monthly")) {
                            if (profileModel.getProfile().getClubsMonthly().size() > 0) {

                                for (ClubsMonthly clubsMonthly : profileModel.getProfile().getClubsMonthly()) {
                                    ItemProfile item = new ItemProfile();
                                    item.imageName = (clubsMonthly.getImage());
                                    item.name = clubsMonthly.getFinished();
                                    item.setSelected(false);
                                    mFancyCoverFlows.add(item);
                                }
                                setClubViewPagerView();
                                setListAdapter("monthly", profileModel);
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
                    Toast.makeText(getActivity(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Util.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
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
        MyFancyCoverWhiteFlowAdapter mMyFancyCoverFlowAdapter = new MyFancyCoverWhiteFlowAdapter(mActivity, mFancyCoverFlows);
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
//                    tvActivityValue.setText(homeFancyCoverFlow.categoryName);
//                    Toast.makeText(LeaderBoardActivity.this, homeFancyCoverFlow.categoryName, Toast.LENGTH_SHORT).show();
//                    addfragment(homeFancyCoverFlow.categoryName);
//                    getLeaderData(homeFancyCoverFlow.categoryName);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void showLogoutAlert() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(getApplicationContext());
        builder2.setMessage("Please confirm to logout?");
        builder2.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                logout();
            }

        });

        builder2.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        builder2.show();
    }

    private void logout() {

        FuroPrefs.clear(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), LoginTutorialScreen.class);
        startActivity(intent);
        mActivity.finish();

//        FuroPrefs.putBoolean(getContext(), Constants.LOGGEDIN, false);
//        Intent intent = new Intent(getApplicationContext(),LoginTutorialScreen.class);
//        startActivity(intent);

    }


}
