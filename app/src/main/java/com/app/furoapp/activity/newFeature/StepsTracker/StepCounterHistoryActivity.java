package com.app.furoapp.activity.newFeature.StepsTracker;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
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

        call1stMonthlyApi("month");
        setMonthlyHistoryAdapter();
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
//        tvYearMonthWeek.setText(" Week " + (1 + weekPosition));
//        tvYearMonthWeekValue.setText("" + filterGettingVal);
        ivFilterWeekUpArrow.setVisibility(View.GONE);
        ivFilterWeekDownArrow.setVisibility(View.VISIBLE);
        callFilterApi(getFilterType, filterGettingVal);
        setWeeklyHistoryAdapter();
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
        setMonthlyHistoryAdapter();
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
        setYearlyHistoryAdapter();
    }

    private void call1stMonthlyApi(String month) {

        SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
        Date date = new Date();
        System.out.println(formatter.format(date));
        getMonthDate = formatter.format(date);
        Log.d("getMonthDate", getMonthDate);

        tvYearMonthWeek.setText("Month");
        tvYearMonthWeekValue.setText("" + getMonthDate);


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
    }

    private void setMonthlyAndDailyAverage(String type, MonthlyData monthlyData) {
        if (type.equalsIgnoreCase("month")) {
            if (monthlyData != null) {
                tvTotStep.setText("" + monthlyData.getCountSteps());
                tvDailyAverage.setText("" + monthlyData.getDailyAverage() + " m");
            }
        }
    }

    private void setMonthlyChart(List<MonthStepCounter> monthStepCounter) {
        if (monthStepCounter != null && monthStepCounter.size() > 0) {
            List<Entry> lineEntries = getMonthlyDataSet(monthStepCounter);
            LineDataSet lineDataSet = new LineDataSet(lineEntries, "LineChart");
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setHighlightEnabled(true);
            lineDataSet.setLineWidth(2);
            lineDataSet.setColor(Color.RED);
            lineDataSet.setCircleColor(Color.YELLOW);
            lineDataSet.setCircleRadius(6);
            lineDataSet.setCircleHoleRadius(3);
            lineDataSet.setDrawHighlightIndicators(true);
            lineDataSet.setHighLightColor(Color.RED);
            lineDataSet.setValueTextSize(12);
            lineDataSet.setValueTextColor(Color.DKGRAY);

            LineData lineData = new LineData(lineDataSet);
            mpLineChart.getDescription().setText("Total Steps");
            mpLineChart.getDescription().setTextSize(12);
            mpLineChart.setDrawMarkers(true);
            mpLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            mpLineChart.animateY(1000);
            mpLineChart.getXAxis().setGranularityEnabled(true);
            mpLineChart.getXAxis().setGranularity(1.0f);
            mpLineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
            mpLineChart.setData(lineData);
            mpLineChart.invalidate();
        }
    }

    private List<Entry> getMonthlyDataSet(List<MonthStepCounter> monthStepCounter) {
        int monthIndex = 0;
        List<Entry> lineEntries = new ArrayList<Entry>();
        for (MonthStepCounter monthStepCounter1 : monthStepCounter) {
            lineEntries.add(new Entry(monthIndex, monthStepCounter1.getCountSteps()));
            monthIndex++;
        }
        return lineEntries;
    }

    private void setMonthlyHistoryAdapter() {
        monthlyHistoryAdapter = new MonthlyHistoryAdapter(getApplicationContext(), monthStepCounterList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvHistory.setLayoutManager(layoutManager);
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.setAdapter(monthlyHistoryAdapter);
    }

    private void notifyMonthlyHistoryAdapter(List<MonthStepCounter> monthStepCounter) {
        if (monthStepCounter != null && monthStepCounter.size() > 0) {
            monthStepCounterList.clear();
            monthStepCounterList.addAll(monthStepCounter);
            if (monthStepCounterList != null && monthStepCounterList.size() > 0) {
                monthlyHistoryAdapter.notifyDataSetChanged();
            }
        }
    }

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
                                        //  steps and daily average Data
                                        setWeekStepsDailyAvarageData("week", response.body().getData().getWeeklyData());
                                        // notify weekly details
                                        notifyWeekListAdapter(response.body().getData().getCurrentWeekStepCounter());
                                        // set weekly line chart
                                        setWeeklyLineChart(response.body().getData().getCurrentWeekStepCounter());
                                    } else {
                                        Toast.makeText(StepCounterHistoryActivity.this, "No more data !", Toast.LENGTH_SHORT).show();
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
                                        //  steps and daily average Data
                                        setMonthlyAndDailyAverage("month", response.body().getData().getMonthlyData());
                                        // notify weekly details
                                        notifyMonthlyHistoryAdapter(response.body().getData().getMonthStepCounter());
                                        //set line chart
                                        setMonthlyChart(response.body().getData().getMonthStepCounter());
                                    } else {
                                        Toast.makeText(StepCounterHistoryActivity.this, "No more data !", Toast.LENGTH_SHORT).show();
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
                                        //  steps and daily average Data
                                        setYearStepsDailyAvarageData("year", response.body().getData().getYearlyData());
                                        // notify weekly details
                                        notifyYearListAdapter(response.body().getData().getYearlyDataLists());
                                        // setYear line chart
                                        setYearLineChart(response.body().getData().getYearlyDataLists());
                                    } else {
                                        Toast.makeText(StepCounterHistoryActivity.this, "No more data !", Toast.LENGTH_SHORT).show();
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
//                tvYearMonthWeek.setText(" Week " + (1 + weekPosition));
//                tvYearMonthWeekValue.setText("" + filterGettingVal);
                tvTotStep.setText("" + weeklyData.getCountSteps());
                tvDailyAverage.setText("" + weeklyData.getDailyAverage() + " m");
            }
        }
    }

    private void setYearStepsDailyAvarageData(String type, YearlyData yearlyData) {
        if (type.equalsIgnoreCase("year")) {
            if (yearlyData != null) {
//                tvYearMonthWeek.setText("Year");
//                tvYearMonthWeekValue.setText("" + filterGettingVal);
                tvTotStep.setText("" + yearlyData.getCountSteps());
                tvDailyAverage.setText("" + yearlyData.getDailyAverage() + " m");
            }
        }
    }

    private void setWeeklyHistoryAdapter() {
        weeklyHistoryAdapter = new WeeklyHistoryAdapter(getApplicationContext(), currentWeekStepCounterList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvHistory.setLayoutManager(layoutManager);
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.setAdapter(weeklyHistoryAdapter);
    }

    private void notifyWeekListAdapter(List<CurrentWeekStepCounter> currentWeekStepCounter) {
        currentWeekStepCounterList.clear();
        currentWeekStepCounterList.addAll(currentWeekStepCounter);
        if (currentWeekStepCounterList != null && currentWeekStepCounterList.size() > 0) {
            weeklyHistoryAdapter.notifyDataSetChanged();
        }
    }

    private void setYearlyHistoryAdapter() {
        yearlyHistoryAdapter = new YearlyHistoryAdapter(getApplicationContext(), yearlyDataListList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvHistory.setLayoutManager(layoutManager);
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.setAdapter(yearlyHistoryAdapter);
    }

    private void notifyYearListAdapter(List<YearlyDataList> yearlyDataLists) {
        yearlyDataListList.clear();
        yearlyDataListList.addAll(yearlyDataLists);
        if (yearlyDataListList != null && yearlyDataListList.size() > 0) {
            yearlyHistoryAdapter.notifyDataSetChanged();
        }
    }

    private void setWeeklyLineChart(List<CurrentWeekStepCounter> currentWeekStepCounter) {
        if (currentWeekStepCounter != null && currentWeekStepCounter.size() > 0) {
            List<Entry> lineEntries = getWeekDataSet(currentWeekStepCounter);
            LineDataSet lineDataSet = new LineDataSet(lineEntries, "LineChart");
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
            mpLineChart.getXAxis().setGranularity(10f);
            mpLineChart.getXAxis().setLabelRotationAngle(-20);
            mpLineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
            mpLineChart.setData(lineData);
            mpLineChart.invalidate();

//            XAxis xAxis = mpLineChart.getXAxis();
//            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//            xAxis.setAxisMinimum(0f);
//            xAxis.setGranularity(10f);
//            xAxis.setLabelRotationAngle(-20);
//            xAxis.setValueFormatter(new IndexAxisValueFormatter(year));

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


    private void setYearLineChart(List<YearlyDataList> yearlyDataLists) {
        if (yearlyDataLists != null && yearlyDataLists.size() > 0) {
            List<Entry> lineEntries = getYearDataSet(yearlyDataLists);
            LineDataSet lineDataSet = new LineDataSet(lineEntries, "LineChart");
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setHighlightEnabled(true);
            lineDataSet.setLineWidth(2);
            lineDataSet.setColor(Color.BLUE);
            lineDataSet.setCircleColor(Color.BLUE);
            lineDataSet.setCircleRadius(6);
            lineDataSet.setCircleHoleRadius(3);
            lineDataSet.setDrawHighlightIndicators(true);
            lineDataSet.setHighLightColor(Color.RED);
            lineDataSet.setValueTextSize(12);
            lineDataSet.setValueTextColor(Color.DKGRAY);

            LineData lineData = new LineData(lineDataSet);
            mpLineChart.getDescription().setText("Total Steps");
            mpLineChart.getDescription().setTextSize(12);
            mpLineChart.setDrawMarkers(true);
            mpLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            mpLineChart.animateY(1000);
            mpLineChart.getXAxis().setGranularityEnabled(true);
            //mpLineChart.getXAxis().setGranularity(1.0f);
            mpLineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
            mpLineChart.setData(lineData);

            XAxis xAxis = mpLineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAxisMinimum(0f);
            xAxis.setGranularity(10f);
            xAxis.setLabelRotationAngle(-20);
            //xAxis.setValueFormatter(new IndexAxisValueFormatter(year));

            mpLineChart.invalidate();

        }
    }

    private List<Entry> getYearDataSet(List<YearlyDataList> yearlyDataLists) {
        int yearIndex = 0;
        List<Entry> lineEntries = new ArrayList<Entry>();
        for (YearlyDataList yearlyDataList : yearlyDataLists) {
            lineEntries.add(new Entry(yearIndex, yearlyDataList.getCountSteps()));
            yearIndex++;
        }
        return lineEntries;
    }

}