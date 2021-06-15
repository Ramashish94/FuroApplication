package com.app.furoapp.fragment.content_feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.AboutContestChallengeActivity;
import com.app.furoapp.activity.WelcomeActivityContentFeed;
import com.app.furoapp.activity.newFeature.notification.NotificationSectionActivity;
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.DailyFeedNotification;
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.NotificationResponse;
import com.app.furoapp.adapter.BannerImageeAdapter;
import com.app.furoapp.databinding.FragmentContentFeedMainBinding;
import com.app.furoapp.model.Bannersecond.Banner;
import com.app.furoapp.model.Bannersecond.BannerSecondResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.GifImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContentFeedMainFragment extends Fragment {
    FragmentContentFeedMainBinding binding;
    GifView gifView;
    ImageView imageView;
    BannerImageeAdapter bannerImageAdapter;
    GifImageView gifImageView;
    RecyclerView recyclerView;
    ImageView imageViewcontentquestionmark, contestChallenge;
    public LinearLayout llNotificationSection;
    BottomNavigationView navigationFeed;
    public String getAccessToken;
    TextView tvCountNotification;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public ContentFeedMainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_feed_main, container, false);
        View view = binding.getRoot();
        recyclerView = binding.bannerSecond;
        imageView = binding.imagebanner;
        navigationFeed = binding.navigationfeed;

        imageViewcontentquestionmark = view.findViewById(R.id.contentfeed);
        llNotificationSection = view.findViewById(R.id.llNotificationSection);
        tvCountNotification = view.findViewById(R.id.tvCountNotification);
        navigationFeed.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Fragment contentFeedHomeFragment = ContentFeedHomeFragment.newInstance(ContentFeedHomeFragment.class.getSimpleName());
        replaceFragmentWithStack(R.id.container_feed, contentFeedHomeFragment, ContentFeedHomeFragment.class.getSimpleName());


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAccessToken = FuroPrefs.getString(getActivity(), Constants.Get_ACCESS_TOKEN);
        setOnClickListeners();
        callNotificationApi();
    }

    public static ContentFeedMainFragment newInstance(String name) {
        ContentFeedMainFragment fragment = new ContentFeedMainFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            binding.navigationfeed.getSelectedItemId();
            int id = item.getItemId();


            switch (id) {

                case R.id.allFeeds:

                    replaceFragmentWithStack(R.id.container_feed, ContentFeedHomeFragment.newInstance(null), ContentFeedHomeFragment.class.getSimpleName());


                    break;
                case R.id.tabActivity:
                    replaceFragmentWithStack(R.id.container_feed, ContentActivityFragment.newInstance(null), ContentActivityFragment.class.getSimpleName());

                    break;

                case R.id.tabFood:
                    replaceFragmentWithStack(R.id.container_feed, ContentFoodFragment.newInstance(null), ContentFoodFragment.class.getSimpleName());


                    break;
                case R.id.tabHealth:
                    replaceFragmentWithStack(R.id.container_feed, ContentHealthFragment.newInstance(null), ContentHealthFragment.class.getSimpleName());


                    break;

                case R.id.tabMotivation:

                    replaceFragmentWithStack(R.id.container_feed, ContentMotivationFragment.newInstance(null), ContentMotivationFragment.class.getSimpleName());


                    break;
            }
            return true;
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.container_feed || super.onOptionsItemSelected(item);

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

    public void bannerImage() {

        RestClient.bannerSecondImage(new Callback<BannerSecondResponse>() {
            @Override
            public void onResponse(Call<BannerSecondResponse> call, Response<BannerSecondResponse> response) {
                if (response.body().getStatus().equalsIgnoreCase("200")) {
                    List<Banner> banners = response.body().getData().getBanner();
                    bannerImageAdapter = new BannerImageeAdapter(banners, getContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(bannerImageAdapter);


                } else {
                    Toast.makeText(getActivity(), "Something Went Wrong !!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BannerSecondResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Something Went Wrong !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickListeners() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutContestChallengeActivity.class);
                startActivity(intent);
            }
        });

        imageViewcontentquestionmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WelcomeActivityContentFeed.class);
                startActivity(intent);
            }
        });

        llNotificationSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotificationSectionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void callNotificationApi() {
        RestClient.getNotificationData(getAccessToken, new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        setNotificationCount(response.body().getDailyFeedNotification());
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Some thing went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setNotificationCount(DailyFeedNotification dailyFeedNotification) {
        tvCountNotification.setText("" + dailyFeedNotification.getTo());
    }


}












