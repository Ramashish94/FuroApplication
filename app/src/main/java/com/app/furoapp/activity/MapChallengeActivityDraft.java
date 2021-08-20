package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.adapter.ChallengeDraftMapAdapter;
import com.app.furoapp.model.mapchallenge.ChallengeDetails;
import com.app.furoapp.model.mapchallenge.MapChallengeRecieveRequest;
import com.app.furoapp.model.mapchallenge.MapChallengeRecieveResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapChallengeActivityDraft extends AppCompatActivity {
    RecyclerView recyclerViewNew;
    ChallengeDraftMapAdapter challengeDraftMapAdapter;
    String challenegeDetailId;
    String userid;
    TextView textViewexpired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_challenge_draft);
        Intent intent = getIntent();
        textViewexpired = findViewById(R.id.sendChallangeexpired);
        challenegeDetailId = intent.getStringExtra("userIdChallenge");
        userid = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        recyclerViewNew = findViewById(R.id.challengeDraft);
        textViewexpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MapChallengeActivityDraft.this,VideoChallangeFriendsActivity.class);
                startActivity(intent1);
            }
        });
        recieveMapChallange();
        ;
    }

    public void recieveMapChallange() {

        MapChallengeRecieveRequest challangeDetailRequest = new MapChallengeRecieveRequest();
        challangeDetailRequest.setChallengeId(String.valueOf(challenegeDetailId));

        Util.isInternetConnected(this);
        Util.showProgressDialog(getApplicationContext());
        RestClient.userMapReciveChallenge(challangeDetailRequest, new Callback<MapChallengeRecieveResponse>() {
            @Override
            public void onResponse(Call<MapChallengeRecieveResponse> call, Response<MapChallengeRecieveResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body().getStatus().equalsIgnoreCase("200")) {

                        List<ChallengeDetails> challengeDetails = Collections.singletonList(response.body().getChallengeDetails());
                        challengeDraftMapAdapter = new ChallengeDraftMapAdapter(challengeDetails, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewNew.setLayoutManager(layoutManager);
                        recyclerViewNew.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewNew.setAdapter(challengeDraftMapAdapter);
                        String expire = response.body().getChallengeDetails().getExpired();
                        if (expire.equalsIgnoreCase("0")) {
                            textViewexpired.setVisibility(View.VISIBLE);

                        }else if(expire.equalsIgnoreCase("1")){
                            textViewexpired.setVisibility(View.VISIBLE);
                            textViewexpired.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
                            textViewexpired.setText("Expired");
                            textViewexpired.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(MapChallengeActivityDraft.this, "Challenge expired !!", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }


                    }else{

                    }

                }


            }

            @Override
            public void onFailure(Call<MapChallengeRecieveResponse> call, Throwable t) {

                Toast.makeText(MapChallengeActivityDraft.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }


}







