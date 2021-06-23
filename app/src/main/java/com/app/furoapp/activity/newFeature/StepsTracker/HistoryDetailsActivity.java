package com.app.furoapp.activity.newFeature.StepsTracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.newFeature.StepsTracker.adapter.LeadBoardAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyAdapter.AllTimeHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyAdapter.MonthlyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyAdapter.WeeklyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeCounter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeData;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.Data;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.HistoryResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.MonthlyData;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.MonthlyDataList;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.WeeklyDataList;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailsActivity extends AppCompatActivity {

    private BarChart barChart;
    public SwitchCompat switchBtnWeekly, switchBtnMontly, switchBtnAllType;
    ImageView ivHistoryCross;
    private String type;
    AllTimeHistoryAdapter allTimeHistoryAdapter;
    MonthlyHistoryAdapter monthlyHistoryAdapter;
    WeeklyHistoryAdapter weeklyHistoryAdapter;
    private String str_act, userImageUpdated;
    private String getAccessToken;
    TextView tvTotStep, tvDailyAverage, tvTotStepssss, tvDailyAveragessss, tvTime, tvCalories, tvDateWithDays;
    TextView tvSun, tvMon, tvTue, tvWed, tvThu, tvFri, tvSat;
    RecyclerView rvHistory, rvDays;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    public Date date;
    public LinearLayout llHistoryDetailsLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);


        initViews();
        clickEvent();


        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
        callHistoryApi();

        getDays();

    }

    private void initViews() {
        barChart = findViewById(R.id.barChart);
        switchBtnWeekly = findViewById(R.id.switchBtnWeekly);
        switchBtnMontly = findViewById(R.id.switchBtnMonthly);
        switchBtnAllType = findViewById(R.id.switchBtnAlType);
        ivHistoryCross = findViewById(R.id.ivHistoryCross);
        tvTotStep = findViewById(R.id.tvTotStep);
        tvDailyAverage = findViewById(R.id.tvDailyAverage);
        rvHistory = findViewById(R.id.rvHistory);
        //rvDays = findViewById(R.id.rvDays);

        tvTotStepssss = findViewById(R.id.tvTotStepssss);
        tvDailyAveragessss = findViewById(R.id.tvDailyAveragessss);
        tvTime = findViewById(R.id.tvTime);
        tvCalories = findViewById(R.id.tvCalories);
        tvDateWithDays = findViewById(R.id.tvDateWithDays);
        llHistoryDetailsLayout = findViewById(R.id.llHistoryDetailsLayout);

        tvSun = findViewById(R.id.tvSun);
        tvMon = findViewById(R.id.tvMon);
        tvTue = findViewById(R.id.tvTue);
        tvWed = findViewById(R.id.tvWed);
        tvThu = findViewById(R.id.tvThu);
        tvFri = findViewById(R.id.tvFri);
        tvSat = findViewById(R.id.tvSat);

    }

    private void getDays() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String days = new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());
        //System.out.println(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));

        if ("SUN".equalsIgnoreCase(days)) {
            tvSun.setTextColor(Color.parseColor("#19CFE6"));
        } else if ("MON".equalsIgnoreCase(days)) {
            tvMon.setTextColor(Color.parseColor("#19CFE6"));
        } else if ("TUE".equalsIgnoreCase(days)) {
            tvTue.setTextColor(Color.parseColor("#19CFE6"));
        } else if ("WED".equalsIgnoreCase(days)) {
            tvWed.setTextColor(Color.parseColor("#19CFE6"));
        } else if ("THU".equalsIgnoreCase(days)) {
            tvThu.setTextColor(Color.parseColor("#19CFE6"));
        } else if ("FRI".equalsIgnoreCase(days)) {
            tvFri.setTextColor(Color.parseColor("#19CFE6"));
        } else if ("SAT".equalsIgnoreCase(days)) {
            tvSat.setTextColor(Color.parseColor("#19CFE6"));
        }

    }

    private void clickEvent() {
        ivHistoryCross.setOnClickListener(v -> {
            finish();
        });


        switchBtnWeekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Weekly";
                    switchBtnAllType.setChecked(false);
                    switchBtnMontly.setChecked(false);
                    callHistoryApi("Weekly");/*str_act,*/
                }

            }
        });

        switchBtnMontly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Monthly";
                    switchBtnWeekly.setChecked(false);
                    switchBtnAllType.setChecked(false);
                    callHistoryApi("Monthly");/*str_act,*/


                }
            }
        });

        switchBtnAllType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "All Time";
                    switchBtnWeekly.setChecked(false);
                    switchBtnMontly.setChecked(false);
                    callHistoryApi("All Time");/*/*str_act,*/

                }
            }
        });
        callHistoryApi();

    }


    private void callApi() {
       /* switchBtnWeekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Weekly";
                    switchBtnAllType.setChecked(false);
                    switchBtnMontly.setChecked(false);
                    callHistoryApi("Weekly");*//*str_act,*//*
                }

            }
        });

        switchBtnMontly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Monthly";
                    switchBtnWeekly.setChecked(false);
                    switchBtnAllType.setChecked(false);
                    callHistoryApi("Monthly");*//*str_act,*//*

                }
            }
        });

        switchBtnAllType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "All Time";
                    switchBtnWeekly.setChecked(false);
                    switchBtnMontly.setChecked(false);
                    callHistoryApi("All Time");*//*//**str_act,*//*

                }
            }
        });
        callHistoryApi();*/
    }

    private void callHistoryApi() {
        Utils.showProgressDialogBar(getApplicationContext());
        RestClient.getHistoryData(getAccessToken, new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                Utils.dismissProgressDialogBar();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getData().getAllTimeCounterList() != null) {
                            setDateData(response.body().getData().getAllTimeCounterList().get(0));
                            setstepsAndDailyAvaragedata(response.body().getData().getAllTimeData());
                            //setChartView(response.body().getData().getAllTimeCounterList());
                            setAllTimeChartView(response.body().getData().getAllTimeCounterList());
                            // setAllTimedata(response.body().getData().getAllTimeData());
                            //setAllTimeAdapter(response.body().getData().getAllTimeCounterList());
                            setGraphMonthlyAndAllTimeDelails("All Time", response.body().getData());
                        }
                    }

                } else if (response.code() == 500) {
                    Toast.makeText(HistoryDetailsActivity.this, "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    //  Toast.makeText(HistoryDetailsActivity.this, response.code() + " Session expire please login again", Toast.LENGTH_SHORT).show();
                    // getTokenExpireDialog();
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                Toast.makeText(HistoryDetailsActivity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setstepsAndDailyAvaragedata(AllTimeData allTimeData) {
        if (allTimeData != null) {
            tvTotStep.setText("" + allTimeData.getCountSteps());
            tvDailyAverage.setText("" + allTimeData.getDailyAverage() + " m");
        }
    }

    private void setDateData(AllTimeCounter allTimeCounterList) {
        if (allTimeCounterList != null) {

            DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
            try {
                date = dateFormat.parse(allTimeCounterList.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
            String getDate = dateFormat1.format(date);
            tvDateWithDays.setText(getDate);
            //}
        } else {
            Toast.makeText(this, "No records found", Toast.LENGTH_SHORT).show();
        }
    }

    private void callHistoryApi(/*String str_act,*/ String type) {
        if (Util.isInternetConnected(getApplicationContext())) {
            Utils.showProgressDialogBar(getApplicationContext());
            RestClient.getHistoryData(getAccessToken, new Callback<HistoryResponse>() {
                @Override
                public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                    Utils.dismissProgressDialogBar();
                    if (response.code() == 200) {
                        //  Data data = response.body().getData();
                        if (response.body() != null) {
                            if (type.equalsIgnoreCase("Weekly")) {
                                setData("Weekly", response.body().getData());
                                setWeeklyChartView(response.body().getData().getWeeklyDataLists());
                                setListAdapter("Weekly", response.body().getData());

                            } else if (type.equalsIgnoreCase("Monthly")) {
                                setData("Monthly", response.body().getData());
                                setGraphMonthlyAndAllTimeDelails("Monthly", response.body().getData());
                                setMonthlyChartView(response.body().getData().getMonthlyDataLists());
                                // setListAdapter("Monthly", response.body().getData());
//                                setGraphChartMontly(response.body().getData().getMonthlyData());

                            } else if (type.equalsIgnoreCase("All Time")) {
                                setData("All Time", response.body().getData());
                                setGraphMonthlyAndAllTimeDelails("All Time", response.body().getData());
                                setAllTimeChartView(response.body().getData().getAllTimeCounterList());
                                //setListAdapter("All Time", response.body().getData());
//                                setGraphChartAllTime(response.body().getData().getAllTimeData());

                            }
                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(HistoryDetailsActivity.this, "Internal server error", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(HistoryDetailsActivity.this, response.code() + " Session expire please login again", Toast.LENGTH_SHORT).show();
                        //  getTokenExpireDialog();
                    }
                }

                @Override
                public void onFailure(Call<HistoryResponse> call, Throwable t) {
                    Toast.makeText(HistoryDetailsActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

   /* private void setWeeklyAdapter() {
        daysGraphAdapter = new DaysGraphAdapter(getApplicationContext(), weeklyDataListList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        rvDays.setLayoutManager(layoutManager);
        rvDays.setItemAnimator(new DefaultItemAnimator());
        rvDays.setAdapter(daysGraphAdapter);
    }

    private void notifyDaysAdapter(List<WeeklyDataList> weeklyDataLists) {
        weeklyDataListList.clear();
        weeklyDataListList.addAll(weeklyDataLists);
        if (weeklyDataListList != null && weeklyDataListList.size() > 0) {
            daysGraphAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No record found !", Toast.LENGTH_SHORT).show();
        }
    }
*/

    private void setWeeklyChartView(List<WeeklyDataList> weeklyDataLists) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        int index = 0;

        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            date = dateFormat.parse(weeklyDataLists.get(0).getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
        String getDate = dateFormat1.format(date);



        for (WeeklyDataList weeklyDataList : weeklyDataLists) {
            entries.add(new BarEntry(Float.parseFloat(String.valueOf(weeklyDataList.getCountSteps())), index));
            labels.add(String.valueOf(weeklyDataList.getCountSteps()));
            index++;
        }

        BarDataSet bardataset = new BarDataSet(entries, getDate);
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        // set the data and list of labels into chart
        barChart.setDescription("");  // set the description
        //      bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.light_blue)));

        barChart.animateY(2000);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setDrawAxisLine(false);

        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawLabels(false);
        barChart.getXAxis().setDrawAxisLine(false);

        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawAxisLine(false);
    }


    private void setMonthlyChartView(List<MonthlyDataList> monthlyDataLists) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        int index = 0;

        for (MonthlyDataList monthlyDataList : monthlyDataLists) {
            entries.add(new BarEntry(Float.parseFloat(String.valueOf(monthlyDataList.getCountSteps())), index));
            labels.add(String.valueOf(monthlyDataList.getCountSteps()));
            index++;
        }

        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            date = dateFormat.parse(monthlyDataLists.get(0).getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
        String getDate = dateFormat1.format(date);


        BarDataSet bardataset = new BarDataSet(entries, getDate);
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        // set the data and list of labels into chart
        barChart.setDescription("");  // set the description
        //       bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.light_blue)));

        barChart.animateY(2000);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setDrawAxisLine(false);

        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawLabels(false);
        barChart.getXAxis().setDrawAxisLine(false);

        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawAxisLine(false);

    }


    private void setAllTimeChartView(List<AllTimeCounter> allTimeCounterList) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        int index = 0;

        for (AllTimeCounter allTimeCounter : allTimeCounterList) {
            entries.add(new BarEntry(Float.parseFloat(String.valueOf(allTimeCounter.getCountSteps())), index));
            labels.add(String.valueOf(allTimeCounter.getCountSteps()));
            index++;
        }

        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            date = dateFormat.parse(allTimeCounterList.get(0).getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
        String getDate = dateFormat1.format(date);



        BarDataSet bardataset = new BarDataSet(entries, getDate);
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        // set the data and list of labels into chart
        barChart.setDescription("");  // set the description
        //     bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.light_blue)));

        barChart.animateY(2000);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setDrawAxisLine(false);

        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawLabels(false);
        barChart.getXAxis().setDrawAxisLine(false);

        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawAxisLine(false);
    }

    private void setListAdapter(String type, Data data) {
        if (type.equalsIgnoreCase("Weekly")) {
            llHistoryDetailsLayout.setVisibility(View.GONE);
            rvHistory.setVisibility(View.VISIBLE);
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            weeklyHistoryAdapter = new WeeklyHistoryAdapter(getApplicationContext(), data.getWeeklyDataLists());
            rvHistory.setAdapter(weeklyHistoryAdapter);
            weeklyHistoryAdapter.notifyDataSetChanged();
        }

        if (type.equalsIgnoreCase("Monthly")) {
            llHistoryDetailsLayout.setVisibility(View.VISIBLE);
            rvHistory.setVisibility(View.GONE);
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            monthlyHistoryAdapter = new MonthlyHistoryAdapter(getApplicationContext(), data.getMonthlyDataLists());
            rvHistory.setAdapter(monthlyHistoryAdapter);
            monthlyHistoryAdapter.notifyDataSetChanged();
        }

        if (type.equalsIgnoreCase("All Time")) {
            llHistoryDetailsLayout.setVisibility(View.VISIBLE);
            rvHistory.setVisibility(View.GONE);
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            allTimeHistoryAdapter = new AllTimeHistoryAdapter(getApplicationContext(), data.getAllTimeCounterList());
            rvHistory.setAdapter(allTimeHistoryAdapter);
            allTimeHistoryAdapter.notifyDataSetChanged();
        }
    }

    private void setData(String type, Data data) {
        if (type.equalsIgnoreCase("Weekly")) {
            tvTotStep.setText("" + data.getWeeklyData().getCountSteps() + " ");
            tvDailyAverage.setText("" + data.getWeeklyData().getDailyAverage() + " ");
        }

        if (type.equalsIgnoreCase("Monthly")) {
            tvTotStep.setText("" + data.getMonthlyData().getCountSteps() + " ");
            tvDailyAverage.setText("" + data.getMonthlyData().getDailyAverage() + " ");
        }

        if (type.equalsIgnoreCase("All Time")) {
            tvTotStep.setText("" + data.getAllTimeData().getCountSteps() + " ");
            tvDailyAverage.setText("" + data.getAllTimeData().getDailyAverage() + " ");
        }

    }

    private void setAllTimeAdapter(List<AllTimeCounter> allTimeCounterList) {
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        allTimeHistoryAdapter = new AllTimeHistoryAdapter(getApplicationContext(), allTimeCounterList);
        rvHistory.setAdapter(allTimeHistoryAdapter);
        allTimeHistoryAdapter.notifyDataSetChanged();
    }


    private void setGraphMonthlyAndAllTimeDelails(String type, Data data) {
        if (type.equalsIgnoreCase("Monthly")) {
            rvHistory.setVisibility(View.GONE);
            llHistoryDetailsLayout.setVisibility(View.VISIBLE);

            tvTotStep.setText("" + data.getMonthlyData().getCountSteps() + " ");
            tvDailyAverage.setText("" + data.getMonthlyData().getDailyAverage() + " ");

            tvTotStepssss.setText("" + data.getMonthlyData().getCountSteps() + " ");
            tvDailyAveragessss.setText("" + data.getMonthlyData().getDailyAverage() + " m");
            tvTime.setText("" + data.getMonthlyData().getTime() + " Minutes");
            tvCalories.setText("" + data.getMonthlyData().getCalories() + " Cal");

        }

        if (type.equalsIgnoreCase("All Time")) {
            rvHistory.setVisibility(View.GONE);
            llHistoryDetailsLayout.setVisibility(View.VISIBLE);

            tvTotStep.setText("" + data.getMonthlyData().getCountSteps() + " ");
            tvDailyAverage.setText("" + data.getMonthlyData().getDailyAverage() + " ");

            tvTotStepssss.setText("" + data.getAllTimeData().getCountSteps() + " ");
            tvDailyAveragessss.setText("" + data.getAllTimeData().getDailyAverage() + " m");
            tvTime.setText("" + data.getAllTimeData().getTime() + " Minutes");
            tvCalories.setText("" + data.getAllTimeData().getCalories() + " Cal");
        }
    }


    private void getTokenExpireDialog() {
        if (getAccessToken != null) {
            dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.profile_alertdialog_logoutt_new, null);
            dialogBuilder.setView(dialogView);
            dialog = dialogBuilder.create();
            ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
            TextView text_logout = dialogView.findViewById(R.id.text_logout);
            TextView noiwanttocontinue = dialogView.findViewById(R.id.noiwanttocontinuee);
            LinearLayout llLogOut = dialogView.findViewById(R.id.llLogOut);

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
                    FuroPrefs.clear(getApplicationContext());
                    googleSignOut();
                    Intent intent = new Intent(getApplicationContext(), LoginTutorialScreen.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });
            dialog.show();
        } else {

        }

    }

    public void googleSignOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Toast.makeText(ActivityMain.this, "Google Sign Out done.", Toast.LENGTH_SHORT).show();
                        revokeAccess();
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Toast.makeText(ActivityMain.this, "Google access revoked.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}