package com.app.furoapp.activity.newFeature.StepsTracker;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.adapter.FetchAllSlotAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.WeeklyDataList;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.filterAdater.MonthFilterAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.filterAdater.WeekFilterAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.filterAdater.YearFilterAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.MonthlyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.WeeklyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.YearlyHistoryAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.WeeklyMonthlyYearlyRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.monthlyResponse.MonthStepCounter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.monthlyResponse.MonthlyData;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.monthlyResponse.MonthlyResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.weeklyResponse.CurrentWeekStepCounter;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.weeklyResponse.WeeklyData;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.weeklyResponse.WeeklyResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.yearResponse.YearlyData;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.yearResponse.YearlyDataList;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.yearResponse.YearlyResponse;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StepCounterHistoryActivity extends AppCompatActivity implements YearFilterAdapter.YearClickCallBack,
        MonthFilterAdapter.MonthlyClickCallBack, WeekFilterAdapter.WeekClickCallBack {
    RecyclerView rvFilterByWeek, rvFilterByMonth, rvFilterByYear, rvHistory;
    LinearLayout llFilterByWeek, llFilterByMonth, llFilterByYear, llYearRecy, llMonthRecy, llWeekRecy, llWeekMonthYearFilter;
    ImageView ivFilterWeekDownArrow, ivFilterWeekUpArrow, ivFilterMonthDownArrow, ivFilterMonthUpArrow, ivFilterYearDownArrow, ivFilterYearUpArrow;
    YearFilterAdapter yearFilterAdapter;
    MonthFilterAdapter monthFilterAdapter;
    WeekFilterAdapter weekFilterAdapter;
    MonthlyHistoryAdapter monthlyHistoryAdapter;
    WeeklyHistoryAdapter weeklyHistoryAdapter;
    YearlyHistoryAdapter yearlyHistoryAdapter;
    List<YearlyDataList> yearlyDataListList = new ArrayList<>();
    List<CurrentWeekStepCounter> currentWeekStepCounterList = new ArrayList<>();
    List<MonthStepCounter> monthStepCounterList = new ArrayList<>();
    public List<AgeModelTest> ageModelTests = new ArrayList<>();
    private boolean isYearSelected;
    private String getYear;
    TextView tvYearMonthWeek, tvYearMonthWeekValue, tvTotStep, tvDailyAverage, tvTotStepssss, tvDailyAveragessss, tvTime, tvCalories, tvDateWithDays;
    public int currentMonth;
    public Date date;
    List<String> weekFilterModelList = new ArrayList<>();
    List<String> allDates = new ArrayList<>();
    private String getFilterType, filterGettingVal;
    private int weekPosition;
    private String type;
    private String getMonthDate;
    public String getAcessToken;
    private WeeklyMonthlyYearlyRequest weeklyMonthlyYearlyRequest;
    public LineChart mpLineChart;
    LineChart lineChart;
    LineData lineData;
    List<Entry> entryList = new ArrayList<>();
    private int getCountSteps;
    public Date getDate, date1, date2, date3, date4, date5, date6, date7;
    public TextView tvDays1, tvDays2, tvDays3, tvDays4, tvDays5, tvDays6, tvDays7;

    public LinearLayout llHistoryDetailsLayout, llWeeklyDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter_history);

        getAcessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        intViews();
        clickEvent();
        setYearFilterAdapter();
        setMonthFiltrAdapter();
        setWeeklyFilterAdapter();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");/*MMM-yyyy*/
        Date date = new Date();
        System.out.println(formatter.format(date));
        filterGettingVal = formatter.format(date);
        Log.d("getMonthDate", filterGettingVal);

        tvYearMonthWeek.setText("Year");
        tvYearMonthWeekValue.setText("" + filterGettingVal);


        callFilterApi("year", filterGettingVal);
        // call1stMonthlyApi("month");
        //setMonthlyHistoryAdapter();
    }


    private void intViews() {
        llFilterByWeek = findViewById(R.id.llFilterByWeek);
        llFilterByMonth = findViewById(R.id.llFilterByMonth);
        llFilterByYear = findViewById(R.id.llFilterByYear);
        rvFilterByWeek = findViewById(R.id.rvFilterByWeek);
        rvFilterByMonth = findViewById(R.id.rvFilterByMonth);
        rvFilterByYear = findViewById(R.id.rvFilterByYear);
        ivFilterWeekDownArrow = findViewById(R.id.ivFilterWeekDownArrow);
        ivFilterWeekUpArrow = findViewById(R.id.ivFilterWeekUpArrow);
        ivFilterMonthDownArrow = findViewById(R.id.ivFilterMonthDownArrow);
        ivFilterMonthUpArrow = findViewById(R.id.ivFilterMonthUpArrow);
        ivFilterYearDownArrow = findViewById(R.id.ivFilterYearDownArrow);
        ivFilterYearUpArrow = findViewById(R.id.ivFilterYearUpArrow);
        llWeekRecy = findViewById(R.id.llWeekRecy);
        llMonthRecy = findViewById(R.id.llMonthRecy);
        llYearRecy = findViewById(R.id.llYearRecy);
        llWeekMonthYearFilter = findViewById(R.id.llWeekMonthYearFilter);
        tvYearMonthWeek = findViewById(R.id.tvYearMonthWeek);
        tvYearMonthWeekValue = findViewById(R.id.tvYearMonthWeekValue);
        rvHistory = findViewById(R.id.rvHistory);
        tvTotStep = findViewById(R.id.tvTotStep);
        tvDailyAverage = findViewById(R.id.tvDailyAverage);
        mpLineChart = findViewById(R.id.LineChart);

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
        llFilterByWeek.setOnClickListener(v -> {
            rvFilterByWeek.setVisibility(View.VISIBLE);
            rvFilterByMonth.setVisibility(View.GONE);
            rvFilterByYear.setVisibility(View.GONE);
            llWeekMonthYearFilter.setVisibility(View.VISIBLE);
            llWeekRecy.setVisibility(View.VISIBLE);
            llMonthRecy.setVisibility(View.INVISIBLE);
            llYearRecy.setVisibility(View.INVISIBLE);
            ivFilterWeekUpArrow.setVisibility(View.VISIBLE);
            ivFilterMonthDownArrow.setVisibility(View.VISIBLE);
            ivFilterYearDownArrow.setVisibility(View.VISIBLE);
            ivFilterWeekDownArrow.setVisibility(View.GONE);
            ivFilterMonthUpArrow.setVisibility(View.GONE);
            ivFilterYearUpArrow.setVisibility(View.GONE);
        });
        llFilterByMonth.setOnClickListener(v -> {
            rvFilterByWeek.setVisibility(View.GONE);
            rvFilterByMonth.setVisibility(View.VISIBLE);
            rvFilterByYear.setVisibility(View.GONE);
            llWeekRecy.setVisibility(View.INVISIBLE);
            llMonthRecy.setVisibility(View.VISIBLE);
            llYearRecy.setVisibility(View.INVISIBLE);
            llWeekMonthYearFilter.setVisibility(View.VISIBLE);
            ivFilterMonthDownArrow.setVisibility(View.GONE);
            ivFilterMonthUpArrow.setVisibility(View.VISIBLE);
            ivFilterWeekDownArrow.setVisibility(View.VISIBLE);
            ivFilterWeekUpArrow.setVisibility(View.GONE);
            ivFilterYearDownArrow.setVisibility(View.VISIBLE);
            ivFilterYearUpArrow.setVisibility(View.GONE);
        });
        llFilterByYear.setOnClickListener(v -> {
            rvFilterByWeek.setVisibility(View.GONE);
            rvFilterByMonth.setVisibility(View.GONE);
            rvFilterByYear.setVisibility(View.VISIBLE);
            llWeekRecy.setVisibility(View.INVISIBLE);
            llMonthRecy.setVisibility(View.INVISIBLE);
            llYearRecy.setVisibility(View.VISIBLE);
            llWeekMonthYearFilter.setVisibility(View.VISIBLE);
            ivFilterYearDownArrow.setVisibility(View.GONE);
            ivFilterYearUpArrow.setVisibility(View.VISIBLE);
            ivFilterMonthDownArrow.setVisibility(View.VISIBLE);
            ivFilterMonthUpArrow.setVisibility(View.GONE);
            ivFilterWeekDownArrow.setVisibility(View.VISIBLE);
            ivFilterWeekUpArrow.setVisibility(View.GONE);
        });
    }

    private void setWeeklyFilterAdapter() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");
        Date date = new Date();
        String dateString = formatter.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -6);

        System.out.println(dateString);
        for (int week = 0; week < 4; week++) {
            String newDateValue = formatter.format(calendar.getTime());
            weekFilterModelList.add(dateString + " - " + newDateValue);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            String nextDateValue = formatter.format(calendar.getTime());
            System.out.println(nextDateValue);
            calendar.setTime(calendar.getTime());
            dateString = nextDateValue;
            calendar.add(Calendar.DAY_OF_YEAR, -6);
        }
        System.out.println(weekFilterModelList);

        weekFilterAdapter = new WeekFilterAdapter(getApplicationContext(), weekFilterModelList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvFilterByWeek.setLayoutManager(layoutManager);
        rvFilterByWeek.setItemAnimator(new DefaultItemAnimator());
        rvFilterByWeek.setAdapter(weekFilterAdapter);
    }

    @Override
    public void weekSelectItem(String position, String week) {
        getFilterType = "week";
        weekPosition = Integer.parseInt(position);
        filterGettingVal = week;
        Log.d(" weekPosition", String.valueOf(weekPosition + 1));
        Log.d(" getWeek", filterGettingVal);
        rvFilterByMonth.setVisibility(View.GONE);
        llWeekMonthYearFilter.setVisibility(View.GONE);
        //tvYearMonthWeek.setText(" Week " + (1 + weekPosition));
        //   tvYearMonthWeekValue.setText("" + filterGettingVal);
        ivFilterWeekUpArrow.setVisibility(View.GONE);
        ivFilterWeekDownArrow.setVisibility(View.VISIBLE);
        callFilterApi(getFilterType, filterGettingVal);
    }

    private void setMonthFiltrAdapter() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
        Date date = new Date();
        String maxDate = formatter.format(date);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(formatter.parse(maxDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 12; i++) {
            String month_name1 = formatter.format(cal.getTime());
            allDates.add(month_name1);
            cal.add(Calendar.MONTH, -1);
        }
        System.out.println(allDates);
        monthFilterAdapter = new MonthFilterAdapter(getApplicationContext(), allDates, this);
        rvFilterByMonth.setItemAnimator(new DefaultItemAnimator());
        rvFilterByMonth.setAdapter(monthFilterAdapter);
    }

    @Override
    public void monthlySelectItem(String month) {
        getFilterType = "month";
        filterGettingVal = month;
        Log.d(" getMonth", filterGettingVal);
        rvFilterByMonth.setVisibility(View.GONE);
        llWeekMonthYearFilter.setVisibility(View.GONE);
//        tvYearMonthWeek.setText("Month");
//        tvYearMonthWeekValue.setText("" + filterGettingVal);
        ivFilterMonthUpArrow.setVisibility(View.GONE);
        ivFilterMonthDownArrow.setVisibility(View.VISIBLE);
        callFilterApi(getFilterType, filterGettingVal);
        // setMonthlyHistoryAdapter();
    }


    private void setYearFilterAdapter() {
        yearFilterAdapter = new YearFilterAdapter(getApplicationContext(), ageModelTests, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvFilterByYear.setLayoutManager(layoutManager);
        rvFilterByYear.setItemAnimator(new DefaultItemAnimator());
        rvFilterByYear.setAdapter(yearFilterAdapter);
        List<AgeModelTest> ageModelTestArrayList = new ArrayList<>();
        for (int i = 2021; i <= 2030; i++) {
            AgeModelTest ageModelTest = new AgeModelTest();
            ageModelTest.setAge("" + i);
            ageModelTestArrayList.add(ageModelTest);
        }
        YearFilterAdapter yearFilterAdapter = new YearFilterAdapter(getApplicationContext(), ageModelTestArrayList, this);
        rvFilterByYear.setAdapter(yearFilterAdapter);
    }

    @Override
    public void yearSelectItem(int year) {
        isYearSelected = true;
        getFilterType = "year";
        filterGettingVal = String.valueOf(year);
        Log.d(" getYear", filterGettingVal);

        rvFilterByYear.setVisibility(View.GONE);
        llWeekMonthYearFilter.setVisibility(View.GONE);
//        tvYearMonthWeek.setText("Year");
//        tvYearMonthWeekValue.setText("" + filterGettingVal);
        ivFilterYearUpArrow.setVisibility(View.GONE);
        ivFilterYearDownArrow.setVisibility(View.VISIBLE);
        callFilterApi(getFilterType, filterGettingVal);
        //setYearlyHistoryAdapter();
    }

    /*  private void call1stMonthlyApi(String month) {

     *//* SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
        Date date = new Date();
        System.out.println(formatter.format(date));
        filterGettingVal = formatter.format(date);
        Log.d("getMonthDate", filterGettingVal);

        tvYearMonthWeek.setText("Month");
        tvYearMonthWeekValue.setText("" + filterGettingVal);*//*


        WeeklyMonthlyYearlyRequest weeklyMonthlyYearlyRequest = new WeeklyMonthlyYearlyRequest();
        weeklyMonthlyYearlyRequest.setType(month);
        weeklyMonthlyYearlyRequest.setMonth(getMonthDate);
        if (Util.isInternetConnected(getApplicationContext())) {
            Util.showProgressDialog(getApplicationContext());
            RestClient.getMonthlyData(getAcessToken, weeklyMonthlyYearlyRequest, new Callback<MonthlyResponse>() {
                @Override
                public void onResponse(Call<MonthlyResponse> call, Response<MonthlyResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null && response.body().getData() != null) {
                            getCountSteps = Integer.parseInt(response.body().getData().getMonthlyData().getCountSteps());
                            if (getCountSteps != 0) {
                                // monthly steps and daily average
                                setMonthlyAndDailyAverage("month", response.body().getData().getMonthlyData());
                                // notify history details
                                notifyMonthlyHistoryAdapter(response.body().getData().getMonthStepCounter());
                                //set line chart
                                setMonthlyChart(response.body().getData().getMonthStepCounter());
                            } else {
                                Toast.makeText(StepCounterHistoryActivity.this, "No more data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(StepCounterHistoryActivity.this, "Internal server Error !", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(StepCounterHistoryActivity.this, response.code() + " Session Expired Please login again !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MonthlyResponse> call, Throwable t) {
                    Toast.makeText(StepCounterHistoryActivity.this, "Failure Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }*/

    private void callFilterApi(String getFilterType, String filterGettingVal) {
        weeklyMonthlyYearlyRequest = new WeeklyMonthlyYearlyRequest();
        if (getFilterType.equalsIgnoreCase("week")) {
            weeklyMonthlyYearlyRequest.setType(getFilterType);
            weeklyMonthlyYearlyRequest.setDate_range(filterGettingVal);

            if (Util.isInternetConnected(getApplicationContext())) {
                Util.showProgressDialog(getApplicationContext());

                RestClient.getWeeklyData(getAcessToken, weeklyMonthlyYearlyRequest, new Callback<WeeklyResponse>() {
                    @Override
                    public void onResponse(Call<WeeklyResponse> call, Response<WeeklyResponse> response) {
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                if (response.body().getData() != null) {
                                    getCountSteps = Integer.parseInt(response.body().getData().getWeeklyData().getCountSteps());
                                    if (getCountSteps != 0) {
                                        llWeeklyDays.setVisibility(View.VISIBLE);
                                        //  steps and daily average Data
                                        setWeekStepsDailyAvarageData("week", response.body().getData().getWeeklyData());
                                        // notify weekly details
                                        notifyWeekListAdapter(response.body().getData().getCurrentWeekStepCounter());
                                        // set weekly line chart
                                        setWeeklyLineChart(response.body().getData().getCurrentWeekStepCounter());
                                        // weekly days name
                                        setWeeklyDaysName("week", response.body().getData().getCurrentWeekStepCounter());
                                    } else {
                                        getAlertDialog();
                                        // Toast.makeText(StepCounterHistoryActivity.this, "No more data !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        } else if (response.code() == 500) {
                            Toast.makeText(StepCounterHistoryActivity.this, "Internal server Error !", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 403) {
                            Toast.makeText(StepCounterHistoryActivity.this, response.code() + " Session Expired Please login again !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeeklyResponse> call, Throwable t) {
                        Toast.makeText(StepCounterHistoryActivity.this, "Failure Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (getFilterType.equalsIgnoreCase("month")) {
            weeklyMonthlyYearlyRequest.setType(getFilterType);
            weeklyMonthlyYearlyRequest.setMonth(filterGettingVal);
            if (Util.isInternetConnected(getApplicationContext())) {
                Util.showProgressDialog(getApplicationContext());
                RestClient.getMonthlyData(getAcessToken, weeklyMonthlyYearlyRequest, new Callback<MonthlyResponse>() {
                    @Override
                    public void onResponse(Call<MonthlyResponse> call, Response<MonthlyResponse> response) {
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                if (response.body().getData() != null) {
                                    getCountSteps = Integer.parseInt(response.body().getData().getMonthlyData().getCountSteps());
                                    if (getCountSteps != 0) {
                                        llWeeklyDays.setVisibility(View.INVISIBLE);
                                        //  steps and daily average Data
                                        setMonthlyAndDailyAverage("month", response.body().getData().getMonthlyData());
                                        // notify weekly details
                                        notifyMonthlyHistoryAdapter(response.body().getData().getMonthStepCounter());
                                        //set line chart
                                        setMonthlyChart(response.body().getData().getMonthStepCounter());
                                    } else {
                                        //Toast.makeText(StepCounterHistoryActivity.this, "No more data !", Toast.LENGTH_SHORT).show();
                                        getAlertDialog();
                                    }
                                }
                            }
                        } else if (response.code() == 500) {
                            Toast.makeText(StepCounterHistoryActivity.this, "Internal server Error !", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 403) {
                            Toast.makeText(StepCounterHistoryActivity.this, response.code() + " Session Expired Please login again !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MonthlyResponse> call, Throwable t) {
                        Toast.makeText(StepCounterHistoryActivity.this, "Failure Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } else if (getFilterType.equalsIgnoreCase("year")) {
            weeklyMonthlyYearlyRequest.setType(getFilterType);
            weeklyMonthlyYearlyRequest.setYear(filterGettingVal);
            if (Util.isInternetConnected(getApplicationContext())) {
                Util.showProgressDialog(getApplicationContext());
                RestClient.getYearlyData(getAcessToken, weeklyMonthlyYearlyRequest, new Callback<YearlyResponse>() {
                    @Override
                    public void onResponse(Call<YearlyResponse> call, Response<YearlyResponse> response) {
                        if (response.code() == 200) {

                            if (response.body() != null) {
                                if (response.body().getData() != null) {
                                    getCountSteps = Integer.parseInt(response.body().getData().getYearlyData().getCountSteps());
                                    if (getCountSteps != 0) {
                                        llWeeklyDays.setVisibility(View.INVISIBLE);
                                        //  steps and daily average Data
                                        setYearStepsDailyAvarageData("year", response.body().getData().getYearlyData());
                                        // notify weekly details
                                        notifyYearListAdapter(response.body().getData().getYearlyDataLists());
                                        // setYear line chart
                                        setYearLineChart(response.body().getData().getYearlyDataLists());
                                    } else {
                                        //  Toast.makeText(StepCounterHistoryActivity.this, "No more data !", Toast.LENGTH_SHORT).show();
                                        getAlertDialog();
                                    }
                                }
                            }
                        } else if (response.code() == 500) {
                            Toast.makeText(StepCounterHistoryActivity.this, "Internal server Error !", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 403) {
                            Toast.makeText(StepCounterHistoryActivity.this, response.code() + " Session Expired Please login again !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<YearlyResponse> call, Throwable t) {
                        Toast.makeText(StepCounterHistoryActivity.this, "Failure Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

    private void setWeekStepsDailyAvarageData(String type, WeeklyData weeklyData) {
        if (type.equalsIgnoreCase("week")) {
            if (weeklyData != null) {
                tvYearMonthWeek.setText(" Week " + (1 + weekPosition));
                tvYearMonthWeekValue.setText("" + filterGettingVal);
                tvTotStep.setText("" + weeklyData.getCountSteps());
                tvDailyAverage.setText("" + weeklyData.getDailyAverage() + " m");
            }
        }
    }

    private void setMonthlyAndDailyAverage(String type, MonthlyData monthlyData) {
        if (type.equalsIgnoreCase("month")) {
            if (monthlyData != null) {
                tvYearMonthWeek.setText("Month");
                tvYearMonthWeekValue.setText("" + filterGettingVal);
                tvTotStep.setText("" + monthlyData.getCountSteps());
                tvDailyAverage.setText("" + monthlyData.getDailyAverage() + " m");
            }
        }
    }

    private void setYearStepsDailyAvarageData(String type, YearlyData yearlyData) {
        if (type.equalsIgnoreCase("year")) {
            if (yearlyData != null) {
                tvYearMonthWeek.setText("Year");
                tvYearMonthWeekValue.setText("" + filterGettingVal);
                tvTotStep.setText("" + yearlyData.getCountSteps());
                tvDailyAverage.setText("" + yearlyData.getDailyAverage() + " m");
            }
        }
    }

    private void notifyWeekListAdapter(List<CurrentWeekStepCounter> currentWeekStepCounter) {
        if (currentWeekStepCounter != null && currentWeekStepCounter.size() > 0) {
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            weeklyHistoryAdapter = new WeeklyHistoryAdapter(getApplicationContext(), currentWeekStepCounter);
            rvHistory.setAdapter(weeklyHistoryAdapter);
            weeklyHistoryAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No records fond in this week ! ", Toast.LENGTH_SHORT).show();
        }
    }

    private void notifyMonthlyHistoryAdapter(List<MonthStepCounter> monthStepCounter) {
        if (monthStepCounter != null && monthStepCounter.size() > 0) {
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            monthlyHistoryAdapter = new MonthlyHistoryAdapter(getApplicationContext(), monthStepCounter);
            rvHistory.setAdapter(monthlyHistoryAdapter);
            monthlyHistoryAdapter.notifyDataSetChanged();
        }
    }

    private void notifyYearListAdapter(List<YearlyDataList> yearlyDataLists) {
        if (yearlyDataLists != null && yearlyDataLists.size() > 0) {
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            yearlyHistoryAdapter = new YearlyHistoryAdapter(getApplicationContext(), yearlyDataLists);
            rvHistory.setAdapter(yearlyHistoryAdapter);
            yearlyHistoryAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No records fond in this years ! ", Toast.LENGTH_SHORT).show();
        }
    }

    private void setWeeklyLineChart(List<CurrentWeekStepCounter> currentWeekStepCounter) {
        if (currentWeekStepCounter != null && currentWeekStepCounter.size() > 0) {
            List<Entry> lineEntries = getWeekDataSet(currentWeekStepCounter);
            LineDataSet lineDataSet = new LineDataSet(lineEntries, filterGettingVal);
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setHighlightEnabled(true);
            lineDataSet.setLineWidth(2);
            lineDataSet.setColor(Color.parseColor("#19CFE6"));/*ColorTemplate.JOYFUL_COLORS*/
            lineDataSet.setCircleColor(Color.parseColor("#19CFE6"));
            lineDataSet.setCircleRadius(6);
            lineDataSet.setCircleHoleRadius(3);
            lineDataSet.setDrawHighlightIndicators(true);
            lineDataSet.setHighLightColor(Color.parseColor("#19CFE6"));
            lineDataSet.setValueTextSize(12);
            lineDataSet.setValueTextColor(Color.parseColor("#19CFE6"));


            LineData lineData = new LineData(lineDataSet);
            mpLineChart.getDescription().setText("Total Steps");
            mpLineChart.getDescription().setTextSize(12);
            mpLineChart.setDrawMarkers(true);
            mpLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//*BOTH_SIDED*//*
            mpLineChart.animateY(1000);
            mpLineChart.getXAxis().setGranularityEnabled(true);
            mpLineChart.getXAxis().setGranularity(1.0f);
            mpLineChart.getXAxis().setAxisMinimum(1);
            mpLineChart.getXAxis().setGranularity(100f);
            mpLineChart.getXAxis().setLabelRotationAngle(-20);
            mpLineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
            mpLineChart.setData(lineData);
            mpLineChart.invalidate();


        }
    }

    private List<Entry> getWeekDataSet(List<CurrentWeekStepCounter> currentWeekStepCounters) {
        int WeekIndex = 1;
        List<Entry> lineEntries = new ArrayList<Entry>();
        for (CurrentWeekStepCounter currentWeekStepCounter : currentWeekStepCounters) {
            lineEntries.add(new Entry(WeekIndex, currentWeekStepCounter.getCountSteps()));
            WeekIndex++;
        }
        return lineEntries;
    }

    private void setMonthlyChart(List<MonthStepCounter> monthStepCounter) {
        if (monthStepCounter != null && monthStepCounter.size() > 0) {
            List<Entry> lineEntries = getMonthlyDataSet(monthStepCounter);
            LineDataSet lineDataSet = new LineDataSet(lineEntries, filterGettingVal);
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setHighlightEnabled(true);
            lineDataSet.setLineWidth(2);
            lineDataSet.setColor(Color.parseColor("#19CFE6"));/*ColorTemplate.JOYFUL_COLORS*/
            lineDataSet.setCircleColor(Color.parseColor("#19CFE6"));
            lineDataSet.setCircleRadius(6);
            lineDataSet.setCircleHoleRadius(3);
            lineDataSet.setDrawHighlightIndicators(true);
            lineDataSet.setHighLightColor(Color.parseColor("#19CFE6"));
            lineDataSet.setValueTextSize(12);
            lineDataSet.setValueTextColor(Color.parseColor("#19CFE6"));


            LineData lineData = new LineData(lineDataSet);
            mpLineChart.getDescription().setText("Total Steps");
            mpLineChart.getDescription().setTextSize(12);
            mpLineChart.setDrawMarkers(true);
            mpLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//*BOTH_SIDED*//*
            mpLineChart.animateY(1000);
            mpLineChart.getXAxis().setGranularityEnabled(true);
            mpLineChart.getXAxis().setGranularity(1.0f);
            mpLineChart.getXAxis().setAxisMinimum(1);
            mpLineChart.getXAxis().setGranularity(100f);
            mpLineChart.getXAxis().setLabelRotationAngle(-20);
            mpLineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
            mpLineChart.setData(lineData);
            mpLineChart.invalidate();
        }
    }

    private List<Entry> getMonthlyDataSet(List<MonthStepCounter> monthStepCounter) {
        int monthIndex = 1;
        List<Entry> lineEntries = new ArrayList<Entry>();
        for (MonthStepCounter monthStepCounter1 : monthStepCounter) {
            lineEntries.add(new Entry(monthIndex, monthStepCounter1.getCountSteps()));
            monthIndex++;
        }
        return lineEntries;
    }

    private void setYearLineChart(List<YearlyDataList> yearlyDataLists) {
        if (yearlyDataLists != null && yearlyDataLists.size() > 0) {
            List<Entry> lineEntries = getYearDataSet(yearlyDataLists);
            LineDataSet lineDataSet = new LineDataSet(lineEntries, filterGettingVal);
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setHighlightEnabled(true);
            lineDataSet.setLineWidth(2);
            lineDataSet.setColor(Color.parseColor("#19CFE6"));/*ColorTemplate.JOYFUL_COLORS*/
            lineDataSet.setCircleColor(Color.parseColor("#19CFE6"));
            lineDataSet.setCircleRadius(6);
            lineDataSet.setCircleHoleRadius(3);
            lineDataSet.setDrawHighlightIndicators(true);
            lineDataSet.setHighLightColor(Color.parseColor("#19CFE6"));
            lineDataSet.setValueTextSize(12);
            lineDataSet.setValueTextColor(Color.parseColor("#19CFE6"));


            LineData lineData = new LineData(lineDataSet);
            mpLineChart.getDescription().setText("Total Steps");
            mpLineChart.getDescription().setTextSize(12);
            mpLineChart.setDrawMarkers(true);
            mpLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//*BOTH_SIDED*//*
            mpLineChart.animateY(1000);
            mpLineChart.getXAxis().setGranularityEnabled(true);
            mpLineChart.getXAxis().setGranularity(1.0f);
            mpLineChart.getXAxis().setAxisMinimum(1);
            mpLineChart.getXAxis().setGranularity(100f);
            mpLineChart.getXAxis().setLabelRotationAngle(-20);
            mpLineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
            mpLineChart.setData(lineData);
            mpLineChart.invalidate();

        }
    }

    private List<Entry> getYearDataSet(List<YearlyDataList> yearlyDataLists) {
        int yearIndex = 1;
        List<Entry> lineEntries = new ArrayList<Entry>();
        for (YearlyDataList yearlyDataList : yearlyDataLists) {
            lineEntries.add(new Entry(yearIndex, yearlyDataList.getCountSteps()));
            yearIndex++;
        }
        return lineEntries;
    }

    private void setWeeklyDaysName(String type, List<CurrentWeekStepCounter> currentWeekStepCounter) {
        if (type.equalsIgnoreCase("week")) {
            if (currentWeekStepCounter != null && currentWeekStepCounter.size() > 0) {
                llWeeklyDays.setVisibility(View.VISIBLE);
                DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
                try {
                    date1 = dateFormat.parse(currentWeekStepCounter.get(0).getCreatedAt());
                    date2 = dateFormat.parse(currentWeekStepCounter.get(1).getCreatedAt());
                    date3 = dateFormat.parse(currentWeekStepCounter.get(2).getCreatedAt());
                    date4 = dateFormat.parse(currentWeekStepCounter.get(3).getCreatedAt());
                    date5 = dateFormat.parse(currentWeekStepCounter.get(4).getCreatedAt());
                    date6 = dateFormat.parse(currentWeekStepCounter.get(5).getCreatedAt());
                    date7 = dateFormat.parse(currentWeekStepCounter.get(6).getCreatedAt());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat dateFormat1 = new SimpleDateFormat("EEE");/*dd MMM,*/
                String getDate1 = dateFormat1.format(date1);
                Log.d("setWeeklyDaysName: ", getDate1);
                String getDate2 = dateFormat1.format(date2);
                Log.d("setWeeklyDaysName: ", getDate1);
                String getDate3 = dateFormat1.format(date3);
                Log.d("setWeeklyDaysName: ", getDate1);
                String getDate4 = dateFormat1.format(date4);
                Log.d("setWeeklyDaysName: ", getDate1);
                String getDate5 = dateFormat1.format(date5);
                Log.d("setWeeklyDaysName: ", getDate1);
                String getDate6 = dateFormat1.format(date6);
                Log.d("setWeeklyDaysName: ", getDate1);
                String getDate7 = dateFormat1.format(date7);
                Log.d("setWeeklyDaysName: ", getDate1);

                tvDays1.setText("" + getDate1);
                tvDays2.setText("" + getDate2);
                tvDays3.setText("" + getDate3);
                tvDays4.setText("" + getDate4);
                tvDays5.setText("" + getDate5);
                tvDays6.setText("" + getDate6);
                tvDays7.setText("" + getDate7);

            }
        }
    }


    private void getAlertDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.item_no_records_founds, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();

        ImageView ivBtnCancel = dialogView.findViewById(R.id.ivBtnCancel);
        TextView tvChooseAnyFilter = dialogView.findViewById(R.id.tvChooseAnyFilter);
        ivBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        tvChooseAnyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();

    }


}