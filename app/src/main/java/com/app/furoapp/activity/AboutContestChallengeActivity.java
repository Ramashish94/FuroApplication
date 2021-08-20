package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.adapter.BannerImageAdapter;
import com.app.furoapp.adapter.ContentFeedFoodAdapter;
import com.app.furoapp.fragment.challenges.HomeChallengesFragment;
import com.app.furoapp.model.bannerresponse.Banner;
import com.app.furoapp.model.bannerresponse.BannerResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutContestChallengeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BannerImageAdapter bannerImageAdapter;
    TextView textViewStartChallenege;
    GifView pGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_contest_challenge);
        recyclerView = findViewById(R.id.bannerRecycler);
        textViewStartChallenege = findViewById(R.id.StartChallenege);
        pGif = findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.run);
        bannerImage();
        textViewStartChallenege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomeScreen();
            }
        });

    }

    private void launchHomeScreen() {
        String ContestPAGE = "contestpageee";
        Intent intent = new Intent(AboutContestChallengeActivity.this, HomeMainActivity.class);
        intent.putExtra("contestpage", ContestPAGE);
        startActivity(intent);
    }


    public void bannerImage() {


       pGif.setVisibility(View.VISIBLE);
        RestClient.bannerImage(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN),new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                pGif.setVisibility(View.GONE);
                if (response.body().getStatus().equalsIgnoreCase("200")) {
                    List<Banner> banners = response.body().getData().getBanner();
                    bannerImageAdapter = new BannerImageAdapter(banners, getApplicationContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(bannerImageAdapter);


                } else {
                    Toast.makeText(AboutContestChallengeActivity.this, "Something Went Wrong !!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                Toast.makeText(AboutContestChallengeActivity.this, "Something Went Wrong !!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void replaceFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
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
