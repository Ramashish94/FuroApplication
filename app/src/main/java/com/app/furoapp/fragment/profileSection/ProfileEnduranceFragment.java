package com.app.furoapp.fragment.profileSection;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.adapter.MyFancyCoverWhiteFlowAdapter;
import com.app.furoapp.base.BaseFragment;
import com.app.furoapp.fragment.challenges.OpenAndCloseChallengeFragment;
import com.app.furoapp.model.Items;
import com.app.furoapp.widget.francyconverflow.FancyCoverFlow;
import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ProfileEnduranceFragment extends BaseFragment {


    public ProfileEnduranceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_endurance_challenge,container,false);
    }

    @Override
    protected void setUp(View view) {
        initView(view);
        setFancyView(view);
        setChartView(view);




    }

    TextView tvActivityValue;
    private void initView(View view) {
        tvActivityValue = view.findViewById(R.id.tvActivityValue);

    }

    private void setFancyView(View view) {


        final FancyCoverFlow fancyCoverFlow=view.findViewById(R.id.vpFacnyView);

        Integer arrayOfChallenges[] = new Integer[]{
                R.drawable.ic_running,
                R.drawable.ic_cycling,
                R.drawable.ic_squads,
                R.drawable.ic_pushups,
                R.drawable.ic_running2,
        };
        String arrayOfCategoryName[] = new String[]{
                getString(R.string.running),
                getString(R.string.cycling),
                getString(R.string.squats),
                getString(R.string.pushups),
                getString(R.string.running),
        };



        List<Items> mFancyCoverFlows=new ArrayList<>();
        for(int i=0;i<5;i++){
            Items item=new Items();
            item.imageName=arrayOfChallenges[i];
            item.categoryName=arrayOfCategoryName[i];
            item.setName("FQ Score :77");
            item.setSelected(false);
            mFancyCoverFlows.add(item);
        }
        MyFancyCoverWhiteFlowAdapter mMyFancyCoverFlowAdapter = new MyFancyCoverWhiteFlowAdapter(getContext(), mFancyCoverFlows);
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
                    tvActivityValue.setText(homeFancyCoverFlow.categoryName);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });







    }

    private void setChartView(View view) {



        HIChartView chartView = view.findViewById(R.id.hiChartView);

        HIOptions options = new HIOptions();

        HITitle title = new HITitle();
        title.setText("com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.User Progress");
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

    public static ProfileEnduranceFragment newInstance(String name) {
        ProfileEnduranceFragment fragment = new ProfileEnduranceFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

}
