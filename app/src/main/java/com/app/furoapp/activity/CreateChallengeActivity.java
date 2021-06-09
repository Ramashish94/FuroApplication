package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.adapter.CustomPagerWithoutTitleAdapter;
import com.app.furoapp.databinding.ActivityCreateChallengeBinding;
import com.app.furoapp.fragment.challenges.CreateChallengeFragment;
import com.app.furoapp.utils.FuroPrefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class CreateChallengeActivity extends AppCompatActivity {
    ActivityCreateChallengeBinding binding;

    private DatePickerDialog dp;
    private Calendar c;
    private TextView subCategoryActivity, createChallanges;
    ViewPager viewPager;
    String SubCategory, challanges;
    CustomPagerWithoutTitleAdapter adapter;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_challenge);

        subCategoryActivity = findViewById(R.id.activityChallange);
        createChallanges = findViewById(R.id.challangescreate);


        SubCategory = FuroPrefs.getString(getApplicationContext(), "subCategory");
        challanges = FuroPrefs.getString(getApplicationContext(), "challange");

        subCategoryActivity.setText(SubCategory);
        createChallanges.setText(challanges);

        viewPager = binding.vpImages;
        binding.startChallengeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (challanges.equalsIgnoreCase("FLEXIBLITY")) {
                    Intent intent = new Intent(getApplicationContext(), VideoOverViewActivity.class);
                    startActivity(intent);
                    finish();
                }
                 else if (challanges.equalsIgnoreCase("STRENGTH")) {

                    Intent intent = new Intent(getApplicationContext(), VideoOverViewActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (challanges.equalsIgnoreCase("ENDURANCE") && (SubCategory.equalsIgnoreCase("WALKING"))
                        || (SubCategory.equalsIgnoreCase("RUNNING")) || (SubCategory.equalsIgnoreCase("CYCLING"))) {

                    Intent intent = new Intent(getApplicationContext(), MapOverViewActivity.class);
                    startActivity(intent);
                   finish();
                    // homeMainActivity.setDisplayFragment(EnumConstants.CREATE_CHALLANGE_ENDURANCE_FRAGMENT, bundle);


                }
                else if (challanges.equalsIgnoreCase("ENDURANCE") && (SubCategory.equalsIgnoreCase("SPOT JOGGING"))
                        || (SubCategory.equalsIgnoreCase("SKIPPING"))) {
                    Intent intent = new Intent(getApplicationContext(), VideoOverViewActivity.class);
                    startActivity(intent);
                    //finish();
                }
            }
        });
        //setOnClickListen();

        setPagerAdapter();


        //here data must be an instance of the class MarsDataProvider

    }

    private void setPagerAdapter() {
        adapter = new CustomPagerWithoutTitleAdapter(getApplicationContext(), getImages());
        binding.vpImages.setAdapter(adapter);
        binding.vpImages.setCurrentItem(0);
        binding.vpImages.setClipToPadding(false);
        binding.vpImages.setOffscreenPageLimit(3);
        binding.vpImages.setPadding(100, 0, 100, 0);

        binding.vpImages.setPageMargin(90);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                int NUM_PAGES = 3;
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                binding.vpImages.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    private ArrayList<Integer> getImages() {

        ArrayList imagesList = new ArrayList<Integer>();
        imagesList.add(R.drawable.image1);
        imagesList.add(R.drawable.furoimage2);
        imagesList.add(R.drawable.image_4);
        return imagesList;
    }





}




