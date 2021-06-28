package com.app.furoapp.activity.newFeature.StepsTracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.filterAdater.MonthFilterAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.filterAdater.WeekFilterAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.filterAdater.YearFilterAdapter;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StepCounterHistoryActivity extends AppCompatActivity implements YearFilterAdapter.YearClickCallBack,
        MonthFilterAdapter.MonthlyClickCallBack, WeekFilterAdapter.WeekClickCallBack {
    RecyclerView rvFilterByWeek, rvFilterByMonth, rvFilterByYear;
    LinearLayout llFilterByWeek, llFilterByMonth, llFilterByYear, llYearRecy, llMonthRecy, llWeekRecy, llWeekMonthYearFilter;
    ImageView ivFilterWeekDownArrow, ivFilterWeekUpArrow, ivFilterMonthDownArrow, ivFilterMonthUpArrow, ivFilterYearDownArrow, ivFilterYearUpArrow;
    YearFilterAdapter yearFilterAdapter;
    MonthFilterAdapter monthFilterAdapter;
    WeekFilterAdapter weekFilterAdapter;
    public List<AgeModelTest> ageModelTests = new ArrayList<>();
    private boolean isYearSelected;
    private String getYear;
    TextView tvYearMonthWeek, tvYearMonthWeekValue;
    public int currentMonth;
    public Date date;
    String maxDate = "Jan-2016";
    List<String> weekFilterModelList = new ArrayList<>();
    List<String> allDates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter_history);
        intViews();
        clickEvent();
        setYearAdapter();
        setMonthAdapter();
        setWeeklyFilterAdapter();

        // getMonth();


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

    private void setYearAdapter() {
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
//        if (isYearSelected = true) {
        getYear = String.valueOf(year);
        Log.d(" getYear", getYear);
        rvFilterByYear.setVisibility(View.GONE);
        llWeekMonthYearFilter.setVisibility(View.GONE);
        tvYearMonthWeek.setText(" Year ");
        tvYearMonthWeekValue.setText("" + getYear);
        ivFilterYearUpArrow.setVisibility(View.GONE);
        ivFilterYearDownArrow.setVisibility(View.VISIBLE);

//        }
    }

    private void setMonthAdapter() {
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
//        }
        rvFilterByMonth.setItemAnimator(new DefaultItemAnimator());
        rvFilterByMonth.setAdapter(monthFilterAdapter);


       /* monthFilterAdapter = new MonthFilterAdapter(getApplicationContext(), ageModelTests, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvFilterByMonth.setLayoutManager(layoutManager);
        rvFilterByMonth.setItemAnimator(new DefaultItemAnimator());
        rvFilterByMonth.setAdapter(monthFilterAdapter);*/

        /*List<AgeModelTest> ageModelTestArrayList = new ArrayList<>();
        for (int i = 2021; i <= 2030; i++) {
            AgeModelTest ageModelTest = new AgeModelTest();
            ageModelTest.setAge("" + i);
            ageModelTestArrayList.add(ageModelTest);
        }
        MonthFilterAdapter monthFilterAdapter = new MonthFilterAdapter(getApplicationContext(), ageModelTestArrayList, this);
        rvFilterByYear.setAdapter(monthFilterAdapter);*/
    }

    @Override
    public void monthlySelectItem(String month) {

    }

    private void setWeeklyFilterAdapter() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");
        Date date = new Date();
        String dateString = formatter.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -6);

        System.out.println(dateString);
        for (int w = 0; w < 4; w++) {
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
    public void weekSelectItem(String week) {

    }


}