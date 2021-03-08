package com.app.furoapp.fragment.challenges;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.MapOverViewActivity;
import com.app.furoapp.activity.VideoOverViewActivity;
import com.app.furoapp.adapter.CustomPagerWithoutTitleAdapter;
import com.app.furoapp.databinding.FragmentHomeStartChallengeBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class CreateChallengeFragment extends Fragment {

    HomeMainActivity homeMainActivity;
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


    FragmentHomeStartChallengeBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home_new, container, false);
        View view = binding.getRoot();
        subCategoryActivity = view.findViewById(R.id.activityChallange);
        createChallanges = view.findViewById(R.id.challangescreate);


        SubCategory = FuroPrefs.getString(getContext(), "subCategory");
        challanges = FuroPrefs.getString(getContext(), "challange");

        Log.i("challenge 1",challanges);
        subCategoryActivity.setText(SubCategory);
        createChallanges.setText(challanges);

        viewPager = binding.vpImages;
        binding.startChallengeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("challenge 2",challanges);
                if (challanges.equalsIgnoreCase("STRENGTH")) {
                    Intent intent = new Intent(getContext(), VideoOverViewActivity.class);
                    startActivity(intent);

                }else if (challanges.equalsIgnoreCase("FLEXIBLITY")) {

                    Intent intent = new Intent(getContext(), VideoOverViewActivity.class);
                    startActivity(intent);

                }else if (challanges.equalsIgnoreCase("ENDURANCE") && (SubCategory.equalsIgnoreCase("WALKING")) || (SubCategory.equalsIgnoreCase("RUNNING")) || (SubCategory.equalsIgnoreCase("CYCLING"))) {

                    Intent intent = new Intent(getContext(), MapOverViewActivity.class);
                    startActivity(intent);

                    // homeMainActivity.setDisplayFragment(EnumConstants.CREATE_CHALLANGE_ENDURANCE_FRAGMENT, bundle);

                } else if (challanges.equalsIgnoreCase("ENDURANCE") && (SubCategory.equalsIgnoreCase("SPOT JOGGING")) || (SubCategory.equalsIgnoreCase("SKIPPING"))) {
                    Intent intent = new Intent(getContext(), VideoOverViewActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getContext(), "not found", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //setOnClickListen();

        setPagerAdapter();


        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    private void setPagerAdapter() {
        adapter = new CustomPagerWithoutTitleAdapter(getContext(), getImages());
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


    public CreateChallengeFragment() {

    }

    public static CreateChallengeFragment newInstance(String name) {
        CreateChallengeFragment fragment = new CreateChallengeFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOnClickListen() {


    }

    public void replaceFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerId, fragment, name);

            if (!fragment.isAdded()) {
                fragmentTransaction.addToBackStack(name);
                fragmentTransaction.commitAllowingStateLoss();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


}




