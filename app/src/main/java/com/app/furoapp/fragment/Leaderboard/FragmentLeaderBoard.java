package com.app.furoapp.fragment.Leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.adapter.LeaderboardAdapter;
import com.app.furoapp.adapter.MonthlyLeaderBoardAdapter;
import com.app.furoapp.adapter.WeeklyLeaderBoardAdapter;
import com.app.furoapp.model.LeaderBoard.AllTime;
import com.app.furoapp.model.LeaderBoard.LeaderModel;
import com.app.furoapp.model.LeaderBoard.Monthly;
import com.app.furoapp.model.LeaderBoard.Weekly;
import com.app.furoapp.model.Leaderboard;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLeaderBoard extends Fragment {

    private RecyclerView recyclerView;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    private Switch sw_weekly, sw_monthly, sw_all_time;
    private TextView tv_norecord;

    public static FragmentLeaderBoard newInstance(String act) {
        FragmentLeaderBoard fragmentLeaderBoard = new FragmentLeaderBoard();
        Bundle args = new Bundle();
        args.putString("act", act);
        fragmentLeaderBoard.setArguments(args);
        return fragmentLeaderBoard;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String act = getArguments().getString("act");
        View leadView = inflater.inflate(R.layout.fragment_leaderboard_strength, container, false);
        recyclerView = leadView.findViewById(R.id.recycler_view);
        sw_weekly = leadView.findViewById(R.id.sw_weekly);
        progressBar = leadView.findViewById(R.id.progressBarnew);
        linearLayout = leadView.findViewById(R.id.linearLayoutnew);
        sw_monthly = leadView.findViewById(R.id.sw_monthly);
        sw_all_time = leadView.findViewById(R.id.sw_all_time);
        tv_norecord = leadView.findViewById(R.id.tv_nodata);


        sw_weekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    sw_monthly.setChecked(false);
                    sw_all_time.setChecked(false);
                    getLeaderData(act, "week");
                }
            }
        });

        sw_monthly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    sw_weekly.setChecked(false);
                    sw_all_time.setChecked(false);
                    getLeaderData(act, "monthly");
                }
            }
        });


        sw_all_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    sw_monthly.setChecked(false);
                    sw_weekly.setChecked(false);
                    getLeaderData(act, "all");
                }
            }
        });

        getLeaderData(act, "all");

        return leadView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void getLeaderData(String act, String type) {
        if (Util.isInternetConnected(getActivity())) {
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            RestClient.userLeaderData(new Leaderboard(act), new Callback<LeaderModel>() {
                @Override
                public void onResponse(Call<LeaderModel> call, Response<LeaderModel> response) {
                    linearLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    LeaderModel leaderModel = response.body();
                    if (type.equals("week")) {
                        tv_norecord.setVisibility(View.GONE);
                        if (leaderModel.getLeadership().getWeekly().size() > 0) {

                            setAdapterWeekly(leaderModel.getLeadership().getWeekly());
                        } else {
                            tv_norecord.setVisibility(View.VISIBLE);
                        }
                    } else if (type.equals("all")) {
                        tv_norecord.setVisibility(View.GONE);
                        if (leaderModel.getLeadership().getAllTime().size() > 0) {
                            setAdapter(leaderModel.getLeadership().getAllTime());
                        } else {
                            tv_norecord.setVisibility(View.VISIBLE);
                        }
                    } else if (type.equals("monthly")) {
                        tv_norecord.setVisibility(View.GONE);
                        if (leaderModel.getLeadership().getMonthly().size() > 0) {

                            setAdapterMonthly(leaderModel.getLeadership().getMonthly());
                        } else {
                            tv_norecord.setVisibility(View.VISIBLE);
                        }
                    }


//                    Toast.makeText(getActivity(), String.valueOf(leaderModel.getLeadership().getAllTime().get(0).getActivityCount()), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<LeaderModel> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(getContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Util.dismissProgressDialog();
            Toast.makeText(getContext(), "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter(List<AllTime> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LeaderboardAdapter adapter = new LeaderboardAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void setAdapterWeekly(List<Weekly> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        WeeklyLeaderBoardAdapter adapter = new WeeklyLeaderBoardAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void setAdapterMonthly(List<Monthly> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MonthlyLeaderBoardAdapter adapter = new MonthlyLeaderBoardAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}
