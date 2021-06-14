package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.WeeklyData;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.adapter.MonthlyDataAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.adapter.WeeklyDataAdapter;
import com.app.furoapp.adapter.ProfileAllTimeAdapter;
import com.app.furoapp.adapter.ProfileMonthlyAdapter;
import com.app.furoapp.adapter.ProfileWeeklyAdapter;
import com.app.furoapp.model.profile.AllTime;
import com.app.furoapp.model.profile.GraphProfile;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailsActivity extends AppCompatActivity {

    private BarChart barchart;
    public SwitchCompat switchBtnWeekly, switchBtnMontly, switchBtnAllType;
    ImageView ivHistoryCross;
    private String type;
    AllTimeHistoryAdapter allTimeHistoryAdapter;
    MonthlyHistoryAdapter monthlyHistoryAdapter;
    WeeklyHistoryAdapter weeklyHistoryAdapter;
    private String str_act, userImageUpdated;
    private String getAccessToken;
    TextView tvTotSteps, tvDailyAverage;
    RecyclerView rvHistory;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);


        initViews();
        clickEvent();
        callApi();

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

    }


    private void initViews() {
        barchart = findViewById(R.id.barchart);
        switchBtnWeekly = findViewById(R.id.switchBtnWeekly);
        switchBtnMontly = findViewById(R.id.switchBtnMonthly);
        switchBtnAllType = findViewById(R.id.switchBtnAlType);
        ivHistoryCross = findViewById(R.id.ivHistoryCross);
        tvTotSteps = findViewById(R.id.tvTotSteps);
        tvDailyAverage = findViewById(R.id.tvDailyAverage);
        rvHistory = findViewById(R.id.rvHistory);
    }

    private void clickEvent() {
        ivHistoryCross.setOnClickListener(v -> {
            finish();
        });

    }


    private void callApi() {
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

    private void callHistoryApi() {
        Util.showProgressDialog(getApplicationContext());
        RestClient.getHistoryData(getAccessToken, new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getData().getAllTimeCounterList() != null) {
                            setData(response.body().getData().getAllTimeCounterList().get(0));

                        }
                    }

                } else if (response.code() == 500) {
                    Toast.makeText(HistoryDetailsActivity.this, "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(HistoryDetailsActivity.this, "Session expire please login again", Toast.LENGTH_SHORT).show();
                    getTokenExpireDialog();

                }


            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                Toast.makeText(HistoryDetailsActivity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(AllTimeCounter allTimeCounter) {
        if (allTimeCounter != null) {
            tvTotSteps.setText("" + allTimeCounter.getCountSteps());
            tvDailyAverage.setText("" + allTimeCounter.getDailyAverage());
        }
    }

    private void callHistoryApi(/*String str_act,*/ String type) {
        if (Util.isInternetConnected(getApplicationContext())) {
            Util.showProgressDialog(getApplicationContext());
            RestClient.getHistoryData(getAccessToken, new Callback<HistoryResponse>() {
                @Override
                public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        Data data = response.body().getData();
                        if (response.body() != null) {
                            if (type.equals("Weekly")) {
                                setListAdapter("Weekly", data);
                                setChartView(data.getAllTimeCounterList());
                            } else if (type.equals("All Time")) {
                                setListAdapter("All Time", data);
                                setChartView(data.getAllTimeCounterList());

                            } else if (type.equals("Monthly")) {
                                setListAdapter("All Time", data);
                                setChartView(data.getAllTimeCounterList());

                            }
                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(HistoryDetailsActivity.this, "Internal server error", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(HistoryDetailsActivity.this, "Session expire please login again", Toast.LENGTH_SHORT).show();
                        //  getTokenExpireDialog();
                    }

                }

                @Override
                public void onFailure(Call<HistoryResponse> call, Throwable t) {

                }
            });
        }
    }


    private void setListAdapter(String weekly, Data data) {
        if (type.equals("Weekly")) {
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            weeklyHistoryAdapter = new WeeklyHistoryAdapter(getApplicationContext(), (List<WeeklyData>) data.getWeeklyData());
            rvHistory.setAdapter(weeklyHistoryAdapter);
            weeklyHistoryAdapter.notifyDataSetChanged();
        }
        if (type.equals("All Time")) {
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            allTimeHistoryAdapter = new AllTimeHistoryAdapter(getApplicationContext(), (List<AllTimeData>) data.getAllTimeData());
            rvHistory.setAdapter(allTimeHistoryAdapter);
            allTimeHistoryAdapter.notifyDataSetChanged();
        }
        if (type.equals("Monthly")) {
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            monthlyHistoryAdapter = new MonthlyHistoryAdapter(getApplicationContext(), (List<MonthlyData>) data.getMonthlyData());
            rvHistory.setAdapter(monthlyHistoryAdapter);
            monthlyHistoryAdapter.notifyDataSetChanged();


        }
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
        barchart.setData(data);
        // set the data and list of labels into chart
        barchart.setDescription("");  // set the description
        bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.light_blue)));

        barchart.animateY(5000);
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