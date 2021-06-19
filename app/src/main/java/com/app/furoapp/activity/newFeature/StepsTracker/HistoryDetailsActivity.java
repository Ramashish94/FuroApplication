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
import com.app.furoapp.activity.newFeature.StepsTracker.historyAdapter.AllTimeHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyAdapter.MonthlyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyAdapter.WeeklyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeCounter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeData;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.Data;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.HistoryResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.MonthlyData;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.MonthlyDataList;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.WeeklyData;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    RecyclerView rvHistory;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    public Date date;


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

        tvTotStepssss = findViewById(R.id.tvTotStepssss);
        tvDailyAveragessss = findViewById(R.id.tvDailyAveragessss);
        tvTime = findViewById(R.id.tvTime);
        tvCalories = findViewById(R.id.tvCalories);
        tvDateWithDays = findViewById(R.id.tvDateWithDays);
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
                    //setWeeklyDataAdapter();
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
                    //  setMonthlyDataAdapter();
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
                    // setAllTimeDataAdapter();
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
                            setChartView(response.body().getData().getAllTimeCounterList());
                            // setAllTimedata(response.body().getData().getAllTimeData());
                            setAllTimeAdapter(response.body().getData().getAllTimeCounterList());
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


    private void setAllTimedata(AllTimeData allTimeData) {
        tvTotStep.setText("" + allTimeData.getCountSteps());
        tvDailyAverage.setText("" + allTimeData.getDailyAverage() + " m");

        tvTotStepssss.setText("" + allTimeData.getCountSteps() + " ");
        tvDailyAveragessss.setText("" + allTimeData.getDailyAverage() + " m");
        tvTime.setText("" + allTimeData.getTime() + " Minutes");
        tvCalories.setText("" + allTimeData.getCalories() + " Cal");
    }

    private void setDateData(AllTimeCounter allTimeCounterList) {
        if (allTimeCounterList != null) {
            //if (allTimeCounterList.get(0).getId() == 1) {
            tvTotStep.setText("" + allTimeCounterList.getCountSteps());
            tvDailyAverage.setText("" + allTimeCounterList.getDailyAverage() + " m");

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
                            if (type.equals("Weekly")) {
                                setData("Weekly", response.body().getData());
                                setWeeklyChartView(response.body().getData().getWeeklyDataLists());
                                //setDate(response.body().getData().getAllTimeCounterList());
                                setListAdapter("Weekly", response.body().getData());

                            } else if (type.equals("All Time")) {
                                setData("Weekly", response.body().getData());
                                // setAllTimeData("All Time", response.body().getData().getAllTimeData());
                                setAllTimeChartView(response.body().getData().getAllTimeCounterList());
                                //setDate(response.body().getData().getAllTimeCounterList());
                                setListAdapter("All Time", response.body().getData());

                            } else if (type.equals("Monthly")) {
                                setData("Weekly", response.body().getData());
                                //setMonthlyData("Monthly", response.body().getData().getMonthlyData());
                                setMonthlyChartView(response.body().getData().getMonthlyDataLists());
                                //setDate(response.body().getData().getAllTimeCounterList());
                                setListAdapter("Monthly", response.body().getData());
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


    private void setDate(List<AllTimeCounter> allTimeCounterList) {
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
        }
    }


    private void setWeeklyData(String weekly, WeeklyData weeklyData) {
        if (weeklyData != null) {
            if (type.equalsIgnoreCase("Weekly")) {
                tvTotStep.setText("" + weeklyData.getCountSteps() + " ");
                tvDailyAverage.setText("" + weeklyData.getDailyAverage() + " ");

               /* tvTime.setText("" + weeklyData.getTime() + " Minutes");
                tvTotStepssss.setText("" + weeklyData.getCountSteps() + " ");
                tvDailyAveragessss.setText("" + weeklyData.getDailyAverage() + " m");
                tvCalories.setText("" + weeklyData.getCalories() + " Cal");*/
            }
        }
    }


    private void setMonthlyData(String monthly, MonthlyData monthlyData) {
        if (monthlyData != null) {
            if (type.equalsIgnoreCase("Monthly")) {
                tvTotStep.setText("" + monthlyData.getCountSteps() + " ");
                tvDailyAverage.setText("" + monthlyData.getDailyAverage() + " ");

              /*  tvTime.setText("" + monthlyData.getTime() + " Minutes");
                tvTotStepssss.setText("" + monthlyData.getCountSteps() + " ");
                tvDailyAveragessss.setText("" + monthlyData.getDailyAverage() + " m");
                tvCalories.setText("" + monthlyData.getCalories() + " Cal");*/
            }
        }
    }


    private void setAllTimeData(String all_time, AllTimeData allTimeData) {
        if (allTimeData != null) {
            if (type.equalsIgnoreCase("All Time")) {
                tvTotStep.setText("" + allTimeData.getCountSteps() + " ");
                tvDailyAverage.setText("" + allTimeData.getDailyAverage() + " ");

                /*tvTime.setText("" + allTimeData.getTime() + " Minutes");
                tvTotStepssss.setText("" + allTimeData.getCountSteps() + " ");
                tvDailyAveragessss.setText("" + allTimeData.getDailyAverage() + " m");
                tvCalories.setText("" + allTimeData.getCalories() + " Cal");*/
            }
        }
    }


    private void setWeeklyChartView(List<WeeklyDataList> weeklyDataLists) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        int index = 0;

        for (WeeklyDataList weeklyDataList : weeklyDataLists) {
            entries.add(new BarEntry(Float.parseFloat(String.valueOf(weeklyDataList.getCountSteps())), index));
            labels.add(String.valueOf(weeklyDataList.getCountSteps()));
            index++;
        }

        BarDataSet bardataset = new BarDataSet(entries, "Cells");
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        // set the data and list of labels into chart
        barChart.setDescription("");  // set the description
        bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.light_blue)));

        barChart.animateY(2000);
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

        BarDataSet bardataset = new BarDataSet(entries, "Cells");
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        // set the data and list of labels into chart
        barChart.setDescription("");  // set the description
        bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.light_blue)));

        barChart.animateY(2000);
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

        BarDataSet bardataset = new BarDataSet(entries, "Cells");
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        // set the data and list of labels into chart
        barChart.setDescription("");  // set the description
        bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.light_blue)));

        barChart.animateY(2000);
    }


    private void setChartView(List<AllTimeCounter> allTimeCounterList) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        int index = 0;

        for (AllTimeCounter allTimeCounter : allTimeCounterList) {
            entries.add(new BarEntry(Float.parseFloat(String.valueOf(allTimeCounter.getCountSteps())), index));
            labels.add(String.valueOf(allTimeCounter.getCountSteps()));
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

    private void setListAdapter(String type, Data data) {
        if (type.equals("Weekly")) {
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            weeklyHistoryAdapter = new WeeklyHistoryAdapter(getApplicationContext(), data.getWeeklyDataLists());
            rvHistory.setAdapter(weeklyHistoryAdapter);
            weeklyHistoryAdapter.notifyDataSetChanged();
        }
        if (type.equals("All Time")) {
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            allTimeHistoryAdapter = new AllTimeHistoryAdapter(getApplicationContext(), data.getAllTimeCounterList());
            rvHistory.setAdapter(allTimeHistoryAdapter);
            allTimeHistoryAdapter.notifyDataSetChanged();
        }
        if (type.equals("Monthly")) {
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            monthlyHistoryAdapter = new MonthlyHistoryAdapter(getApplicationContext(), data.getMonthlyDataLists());
            rvHistory.setAdapter(monthlyHistoryAdapter);
            monthlyHistoryAdapter.notifyDataSetChanged();
        }
    }

    private void setData(String type, Data data) {
        if (type.equals("Weekly")) {
            tvTotStep.setText("" + data.getWeeklyData().getCountSteps() + " ");
            tvDailyAverage.setText("" + data.getWeeklyData().getDailyAverage() + " ");
        }
        if (type.equals("All Time")) {
            tvTotStep.setText("" + data.getAllTimeData().getCountSteps() + " ");
            tvDailyAverage.setText("" + data.getAllTimeData().getDailyAverage() + " ");
        }
        if (type.equals("Monthly")) {
            tvTotStep.setText("" + data.getMonthlyData().getCountSteps() + " ");
            tvDailyAverage.setText("" + data.getMonthlyData().getDailyAverage() + " ");
        }
    }

    private void setAllTimeAdapter(List<AllTimeCounter> allTimeCounterList) {
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        allTimeHistoryAdapter = new AllTimeHistoryAdapter(getApplicationContext(), allTimeCounterList);
        rvHistory.setAdapter(allTimeHistoryAdapter);
        allTimeHistoryAdapter.notifyDataSetChanged();
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