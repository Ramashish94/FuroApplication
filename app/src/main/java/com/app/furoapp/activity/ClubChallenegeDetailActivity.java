package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.adapter.ClubDetailAdapter;
import com.app.furoapp.databinding.ActivityClubChallenegeDetailBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.fragment.challenges.ClubChallengeDetailsFragment;
import com.app.furoapp.model.clubDetails.Club;
import com.app.furoapp.model.clubDetails.ClubDetailRequest;
import com.app.furoapp.model.clubDetails.ClubDetailResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubChallenegeDetailActivity extends AppCompatActivity {
    ImageView imageView, backButton;
    ClubDetailAdapter clubDetailAdapter;
    GifView gifView;
    RecyclerView recyclerView;
    ActivityClubChallenegeDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_club_challenege_detail);
        recyclerView = findViewById(R.id.clubDetailRecycler);
        gifView = findViewById(R.id.progressBarJumpingJacks);
        gifView.setImageResource(R.drawable.jumpingjacks);


        clubChallengesDetail();


    }

    public void clubChallengesDetail() {

        String ClubId = FuroPrefs.getString(getApplicationContext(), "clubId");

        ClubDetailRequest clubDetailRequest = new ClubDetailRequest();
        clubDetailRequest.setClubId(ClubId);
        Util.isInternetConnected(getApplicationContext());
        gifView.setVisibility(View.VISIBLE);
        RestClient.myClubDetails(clubDetailRequest, new Callback<ClubDetailResponse>() {
            @Override
            public void onResponse(Call<ClubDetailResponse> call, Response<ClubDetailResponse> response) {
                gifView.setVisibility(View.GONE);

                if (response != null) {
                    if (response.body() != null) {

                        List<Club> clubList = response.body().getClubs();
                        clubDetailAdapter = new ClubDetailAdapter(clubList, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(clubDetailAdapter);


                    } else {
                        Toast.makeText(ClubChallenegeDetailActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<ClubDetailResponse> call, Throwable t) {
                Toast.makeText(ClubChallenegeDetailActivity.this, " Something went wrong !!", Toast.LENGTH_SHORT).show();

            }
        });


    }

}





