package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.adapter.ActivityChallangeRecieveAdapter;
import com.app.furoapp.adapter.ChallengeDraftVideoAdapter;
import com.app.furoapp.model.challengedetail.ChallangeDetailRequest;
import com.app.furoapp.model.challengedetail.ChallangeDetailResponse;
import com.app.furoapp.model.challengedetail.ChallengeDetails;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeActivityVideoDraft extends AppCompatActivity {
    TextView textViewexpired;
    RecyclerView recyclerView;
    String challenegeDetailId,userid;

    ChallengeDraftVideoAdapter challengeDraftVideoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_video_draft);

        textViewexpired = findViewById(R.id.sendChallangeexpired);
        recyclerView = findViewById(R.id.recieveChallangedraft);
        Intent intent = getIntent();
        challenegeDetailId = intent.getStringExtra("userIdChallenge");
        userid = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        textViewexpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ChallengeActivityVideoDraft.this,VideoChallangeFriendsActivity.class);
                startActivity(intent1);

            }
        });

        recieveChallange();


    }

    public void recieveChallange() {

        ChallangeDetailRequest challangeDetailRequest = new ChallangeDetailRequest();
        challangeDetailRequest.setChallengeId(String.valueOf(challenegeDetailId));

        Util.isInternetConnected(this);
        Util.showProgressDialog(getApplicationContext());
        RestClient.userChallangeDetail(challangeDetailRequest, new Callback<ChallangeDetailResponse>() {
            @Override
            public void onResponse(Call<ChallangeDetailResponse> call, Response<ChallangeDetailResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        List<ChallengeDetails> challengeDetails = Collections.singletonList(response.body().getChallengeDetails());
                        challengeDraftVideoAdapter = new ChallengeDraftVideoAdapter(challengeDetails, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(challengeDraftVideoAdapter);
                        String expire = response.body().getChallengeDetails().getExpired();

                        if (expire.equalsIgnoreCase("0")) {
                            textViewexpired.setVisibility(View.VISIBLE);

                        } else if (expire.equalsIgnoreCase("1")) {
                            textViewexpired.setVisibility(View.VISIBLE);
                            textViewexpired.setText("Expired");
                            textViewexpired.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);

                            textViewexpired.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(ChallengeActivityVideoDraft.this, "Challenge expired !!", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {
                            Toast.makeText(ChallengeActivityVideoDraft.this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else {
                        Toast.makeText(ChallengeActivityVideoDraft.this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<ChallangeDetailResponse> call, Throwable t) {
                Toast.makeText(ChallengeActivityVideoDraft.this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
