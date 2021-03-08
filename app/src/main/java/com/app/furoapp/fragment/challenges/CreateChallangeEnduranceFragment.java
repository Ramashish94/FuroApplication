package com.app.furoapp.fragment.challenges;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.adapter.CustomPagerWithoutTitleAdapter;
import com.app.furoapp.databinding.FragmentHomeChallangeEnduranceBinding;
import com.app.furoapp.fragment.enduranceMapFragments.EnduranceMapFragmentStart;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateChallangeEnduranceFragment extends Fragment {

    HomeMainActivity homeMainActivity;
    private DatePickerDialog dp;
    private Calendar c;
    ViewPager viewPager;
    CustomPagerWithoutTitleAdapter adapter;

    FragmentHomeChallangeEnduranceBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home_challange_endurance, container, false);
        View view = binding.getRoot();

        viewPager = binding.vpImages;
      setOnClickListeners();
        setOnStartDate();
        setPagerAdapter();
        setOnEndDate();



        //here data must be an instance of the class MarsDataProvider
        return view;
    }
    private void setPagerAdapter() {
        adapter = new CustomPagerWithoutTitleAdapter(getContext(), getImages());
        binding.vpImages.setAdapter(adapter);
        binding.vpImages.setCurrentItem(0);
        binding.vpImages.setClipToPadding(false);
        binding.vpImages.setOffscreenPageLimit(3);
        binding.vpImages.setPadding(250, 0, 250, 20);
        binding.vpImages.setPageMargin(40);
    }

    private ArrayList<Integer> getImages() {

        ArrayList imagesList= new ArrayList<Integer>();
        imagesList.add(R.drawable.image1);
        imagesList.add(R.drawable.image_2);
        imagesList.add(R.drawable.image_4);
        return imagesList;
    }


    public CreateChallangeEnduranceFragment() {

    }

    public static CreateChallangeEnduranceFragment newInstance(String name) {
        CreateChallangeEnduranceFragment fragment = new CreateChallangeEnduranceFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

  private void setOnClickListeners() {
        binding.startChallengeNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               homeMainActivity.replaceFragmentWithStack(R.id.container_main, EnduranceMapFragmentStart.newInstance(null), EnduranceMapFragmentStart.class.getSimpleName());
            }
        });


    }

    private void setOnStartDate() {
        binding.startTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dp = new DatePickerDialog(homeMainActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        binding.startTimeDate.setText(year + "/" + (month + 1) + "/" + dayOfMonth);


                    }
                }, day, year, month);
                dp.show();


            }

        });
    }

    private void setOnEndDate() {
        binding.endTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dp = new DatePickerDialog(homeMainActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        binding.endTimeDate.setText(year + "/" + (month + 1) + "/" + dayOfMonth);

                    }
                }, day, year, month);
                dp.show();

            }
        });
    }

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            view.setMinDate(System.currentTimeMillis() - 1000);
        }
    };
}





