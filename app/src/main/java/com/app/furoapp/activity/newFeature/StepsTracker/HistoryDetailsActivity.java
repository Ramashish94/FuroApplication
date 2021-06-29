package com.app.furoapp.activity.newFeature.StepsTracker;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.AllTimeHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.DaysGraphAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.MonthlyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.WeeklyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeCounter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeData;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.Data;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.HistoryResponse;
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
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailsActivity extends AppCompatActivity implements MonthlyHistoryAdapter.TimeSlotClickCallBack ,WeeklyHistoryAdapter.WeekClickCallBack{

    private BarChart barChart;
    public SwitchCompat switchBtnWeekly, switchBtnMontly, switchBtnAllType;
    ImageView ivHistoryCross;
    public String type;
    AllTimeHistoryAdapter allTimeHistoryAdapter;
    MonthlyHistoryAdapter monthlyHistoryAdapter;
    WeeklyHistoryAdapter weeklyHistoryAdapter;
    DaysGraphAdapter daysGraphAdapter;
    private String str_act, userImageUpdated;
    private String getAccessToken;
    TextView tvTotStep, tvDailyAverage, tvTotStepssss, tvDailyAveragessss, tvTime, tvCalories, tvDateWithDays;
    TextView tvDays1, tvDays2, tvDays3, tvDays4, tvDays5, tvDays6, tvDays7;
    RecyclerView rvHistory, rvDays;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    public Date date, date1, date2, date3, date4, date5, date6, date7;
    public LinearLayout llHistoryDetailsLayout, llWeeklyDays;
    private ArrayList getWeekDays;


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


        if (type != null/* && type.equalsIgnoreCase("Weekly")
                || type.equalsIgnoreCase("Monthly") || type.equalsIgnoreCase("All Time")*/) {
            callHistoryApi(type);
        } else {
            callHistoryApi();
        }
        //getDays();

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
        rvDays = findViewById(R.id.rvDays);

        tvTotStepssss = findViewById(R.id.tvTotStepssss);
        tvDailyAveragessss = findViewById(R.id.tvDailyAveragessss);
        tvTime = findViewById(R.id.tvTime);
        tvCalories = findViewById(R.id.tvCalories);
        tvDateWithDays = findViewById(R.id.tvDateWithDays);
        llHistoryDetailsLayout = findViewById(R.id.llHistoryDetailsLayout);
        llWeeklyDays = findViewById(R.id.llWeeklyDays);
        tvDays1 = findViewById(R.id.tvDays1);
        tvDays2 = findViewById(R.id.tvDays2);
        tvDays3 = findViewById(R.id.tvDays3);
        tvDays4 = findViewById(R.id.tvDays4);
        tvDays5 = findViewById(R.id.tvDays5);
        tvDays6 = findViewById(R.id.tvDays6);
        tvDays7 = findViewById(R.id.tvDays7);

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

        if (type != null) {
            callHistoryApi(type);
        } else {
            callHistoryApi();
        }

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
                            setDateData(response.body().getData().getAllTimeCounterList());
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

    private void setDateData(List<AllTimeCounter> allTimeCounterList) {
        if (allTimeCounterList != null && allTimeCounterList.size() > 0) {

            DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
            try {
                date = dateFormat.parse(allTimeCounterList.get(0).getCreatedAt());
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

    public void callHistoryApi(/*String str_act,*/ String type) {
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
                                setWeeklyDaysName("weekly", response.body().getData().getWeeklyDataLists());
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
                    Toast.makeText(HistoryDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void setWeeklyChartView(List<WeeklyDataList> weeklyDataLists) {
        if (weeklyDataLists != null && weeklyDataLists.size() > 0) {
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
    }


    private void setMonthlyChartView(List<MonthlyDataList> monthlyDataLists) {
        if (monthlyDataLists != null && monthlyDataLists.size() > 0) {
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


            BarDataSet bardataset = new BarDataSet(entries, "Monthly Data");
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
    }


    private void setAllTimeChartView(List<AllTimeCounter> allTimeCounterList) {
        if (allTimeCounterList != null && allTimeCounterList.size() > 0) {
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


            BarDataSet bardataset = new BarDataSet(entries, "All Time");
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
    }

    private void setListAdapter(String type, Data data) {
        if (type.equalsIgnoreCase("Weekly")) {
            if (data != null && data.getWeeklyDataLists() != null && data.getWeeklyDataLists().size() > 0) {
                llHistoryDetailsLayout.setVisibility(View.GONE);
                llWeeklyDays.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.VISIBLE);
                // rvDays.setVisibility(View.VISIBLE);
                rvHistory.setLayoutManager(new LinearLayoutManager(this));
                weeklyHistoryAdapter = new WeeklyHistoryAdapter(getApplicationContext(), data.getWeeklyDataLists(),this);
                rvHistory.setAdapter(weeklyHistoryAdapter);
                weeklyHistoryAdapter.notifyDataSetChanged();
            }
        }

        if (type.equalsIgnoreCase("Monthly")) {
            if (data != null && data.getMonthlyDataLists() != null && data.getAllTimeCounterList().size() > 0) {
                llHistoryDetailsLayout.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.GONE);
                rvDays.setVisibility(View.GONE);
                llWeeklyDays.setVisibility(View.GONE);
                rvHistory.setLayoutManager(new LinearLayoutManager(this));
                monthlyHistoryAdapter = new MonthlyHistoryAdapter(getApplicationContext(), data.getMonthlyDataLists(),this);
                rvHistory.setAdapter(monthlyHistoryAdapter);
                monthlyHistoryAdapter.notifyDataSetChanged();
            }
        }

        if (type.equalsIgnoreCase("All Time")) {
            if (data != null && data.getAllTimeCounterList() != null && data.getAllTimeCounterList().size() > 0) {
                llHistoryDetailsLayout.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.GONE);
                rvDays.setVisibility(View.GONE);
                llWeeklyDays.setVisibility(View.GONE);
                rvHistory.setLayoutManager(new LinearLayoutManager(this));
                allTimeHistoryAdapter = new AllTimeHistoryAdapter(getApplicationContext(), data.getAllTimeCounterList());
                rvHistory.setAdapter(allTimeHistoryAdapter);
                allTimeHistoryAdapter.notifyDataSetChanged();
            }
        }
    }


    private void setWeeklyDaysName(String type, List<WeeklyDataList> weeklyDataLists) {
        if (type.equalsIgnoreCase("Weekly")) {
            if (weeklyDataLists != null && weeklyDataLists.size() > 0) {

                DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
                try {
                    date1 = dateFormat.parse(weeklyDataLists.get(0).getCreatedAt());
                    date2 = dateFormat.parse(weeklyDataLists.get(1).getCreatedAt());
                    date3 = dateFormat.parse(weeklyDataLists.get(2).getCreatedAt());
                    date4 = dateFormat.parse(weeklyDataLists.get(3).getCreatedAt());
                    date5 = dateFormat.parse(weeklyDataLists.get(4).getCreatedAt());
                    date6 = dateFormat.parse(weeklyDataLists.get(5).getCreatedAt());
                    date7 = dateFormat.parse(weeklyDataLists.get(6).getCreatedAt());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
               DateFormat dateFormat1 = new SimpleDateFormat("EEE");/*dd MMM,*/
                String getDate1 = dateFormat1.format(date1);
                String getDate2 = dateFormat1.format(date2);
                String getDate3 = dateFormat1.format(date3);
                String getDate4 = dateFormat1.format(date4);
                String getDate5 = dateFormat1.format(date5);
                String getDate6 = dateFormat1.format(date6);
                String getDate7 = dateFormat1.format(date7);

                tvDays1.setText("" + getDate1);
                tvDays2.setText("" + getDate2);
                tvDays3.setText("" + getDate3);
                tvDays4.setText("" + getDate4);
                tvDays5.setText("" + getDate5);
                tvDays6.setText("" + getDate6);
                tvDays7.setText("" + getDate7);


/////////////////////
       /*          DateFormat dateFormat2 = new SimpleDateFormat(("yyyy-MM-dd"));
                try {
                    date2 = dateFormat2.parse(weeklyDataLists.get(1).getCreatedAt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat dateFormat2_ = new SimpleDateFormat("EEE");*//*dd MMM,*//*
                String getDate2 = dateFormat2_.format(date2).trim();
                tvDays2.setText("" + getDate2.trim());
                ////////////////////

                DateFormat dateFormat3 = new SimpleDateFormat(("yyyy-MM-dd"));
                try {
                    date3 = dateFormat3.parse(weeklyDataLists.get(2).getCreatedAt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat dateFormat3_ = new SimpleDateFormat("EEE");*//*dd MMM,*//*
                String getDate3 = dateFormat3_.format(date3);
                tvDays3.setText("" + getDate3);
                ////////////////////

                DateFormat dateFormat4 = new SimpleDateFormat(("yyyy-MM-dd"));
                try {
                    date4 = dateFormat4.parse(weeklyDataLists.get(3).getCreatedAt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat dateFormat4_ = new SimpleDateFormat("EEE");*//*dd MMM,*//*
                String getDate4 = dateFormat4_.format(date4);
                tvDays4.setText("" + getDate4);
                ////////////////////


                DateFormat dateFormat5 = new SimpleDateFormat(("yyyy-MM-dd"));
                try {
                    date5 = dateFormat5.parse(weeklyDataLists.get(4).getCreatedAt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat dateFormat5_ = new SimpleDateFormat("EEE");*//*dd MMM,*//*
                String getDate5 = dateFormat5_.format(date5);
                tvDays5.setText("" + getDate5);
                ////////////////////

                DateFormat dateFormat6 = new SimpleDateFormat(("yyyy-MM-dd"));
                try {
                    date6 = dateFormat6.parse(weeklyDataLists.get(5).getCreatedAt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat dateFormat6_ = new SimpleDateFormat("EEE");*//*dd MMM,*//*
                String getDate6 = dateFormat6_.format(date6);
                tvDays6.setText("" + getDate6);
                ////////////////////
                DateFormat dateFormat7 = new SimpleDateFormat(("yyyy-MM-dd"));
                try {
                    date7 = dateFormat7.parse(weeklyDataLists.get(6).getCreatedAt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat dateFormat7_ = new SimpleDateFormat("EEE");*//*dd MMM,*//*
                String getDate7 = dateFormat7_.format(date7);
                tvDays7.setText("" + getDate7);
                ////////////////////

*/
            }
        }
    }


    private void setData(String type, Data data) {
        if (type.equalsIgnoreCase("Weekly")) {
            if (data != null && data.getWeeklyData() != null) {
                tvTotStep.setText("" + data.getWeeklyData().getCountSteps() + " ");
                tvDailyAverage.setText("" + data.getWeeklyData().getDailyAverage() + " ");
            }
        }

        if (type.equalsIgnoreCase("Monthly")) {
            if (data != null && data.getMonthlyData() != null) {
                tvTotStep.setText("" + data.getMonthlyData().getCountSteps() + " ");
                tvDailyAverage.setText("" + data.getMonthlyData().getDailyAverage() + " ");
            }
        }

        if (type.equalsIgnoreCase("All Time")) {
            if (data != null && data.getAllTimeData() != null) {
                tvTotStep.setText("" + data.getAllTimeData().getCountSteps() + " ");
                tvDailyAverage.setText("" + data.getAllTimeData().getDailyAverage() + " ");
            }
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
            if (data != null && data.getMonthlyData() != null) {
                rvHistory.setVisibility(View.GONE);
                rvDays.setVisibility(View.GONE);
                llHistoryDetailsLayout.setVisibility(View.VISIBLE);
                llWeeklyDays.setVisibility(View.GONE);

                tvTotStep.setText("" + data.getMonthlyData().getCountSteps() + " ");
                tvDailyAverage.setText("" + data.getMonthlyData().getDailyAverage() + " ");

                tvTotStepssss.setText("" + data.getMonthlyData().getCountSteps() + " ");
                tvDailyAveragessss.setText("" + data.getMonthlyData().getDailyAverage() + " m");
                tvTime.setText("" + data.getMonthlyData().getTime() + " Minutes");
                tvCalories.setText("" + data.getMonthlyData().getCalories() + " Cal");
            }
        }

        if (type.equalsIgnoreCase("All Time")) {
            if (data != null && data.getAllTimeData() != null) {
                rvHistory.setVisibility(View.GONE);
                rvDays.setVisibility(View.GONE);
                llHistoryDetailsLayout.setVisibility(View.VISIBLE);
                llWeeklyDays.setVisibility(View.GONE);

                tvTotStep.setText("" + data.getMonthlyData().getCountSteps() + " ");
                tvDailyAverage.setText("" + data.getMonthlyData().getDailyAverage() + " ");

                tvTotStepssss.setText("" + data.getAllTimeData().getCountSteps() + " ");
                tvDailyAveragessss.setText("" + data.getAllTimeData().getDailyAverage() + " m");
                tvTime.setText("" + data.getAllTimeData().getTime() + " Minutes");
                tvCalories.setText("" + data.getAllTimeData().getCalories() + " Cal");
            }
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

    @Override
    public void timeSlotClick(Integer id, String timeslot) {

    }

    @Override
    public void weekClick(Integer id, String timeslot) {

    }
}