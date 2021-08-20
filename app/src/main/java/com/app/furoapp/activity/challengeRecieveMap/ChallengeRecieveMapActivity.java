package com.app.furoapp.activity.challengeRecieveMap;

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
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.challengeRecieve.ChallengeRecieveActivity;
import com.app.furoapp.activity.challengeRecieve.ReportSubmissionActivity;
import com.app.furoapp.adapter.MapActivityChallengeRecieveAdapter;
import com.app.furoapp.model.challengeAccepted.ChallengeAcceptedRequest;
import com.app.furoapp.model.challengeAccepted.ChallengeAcceptedResponse;
import com.app.furoapp.model.challengeReject.ChallangeRejectRequest;
import com.app.furoapp.model.challengeReject.ChallangeRejectResponse;
import com.app.furoapp.model.mapchallenge.ChallengeDetails;
import com.app.furoapp.model.mapchallenge.MapChallengeRecieveRequest;
import com.app.furoapp.model.mapchallenge.MapChallengeRecieveResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeRecieveMapActivity extends AppCompatActivity {


    private TextView acceptChallange, rejectTextView;

    RecyclerView recyclerView;
    String userId, senderId;
    int userchallangeid;
    TextView report;
    MapActivityChallengeRecieveAdapter activityChallangeRecieveAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_challenge_recieve_map);
        report = findViewById(R.id.ReportThesubmiisonmap);
        userchallangeid = FuroPrefs.getInt(getApplicationContext(), "challengefuroid", 0);
        recyclerView = findViewById(R.id.recieveChallange);
        senderId = FuroPrefs.getString(getApplicationContext(), "shareChallengeId");

        rejectTextView = findViewById(R.id.reject);
        recieveMapChallange();

        userId = FuroPrefs.getString(getApplicationContext(), "loginUserId");

        acceptChallange = findViewById(R.id.AcceptChallange);
        acceptChallange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acceptChallenge();
            }
        });

        rejectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectChallenge();
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ChallengeRecieveMapActivity.this, ReportSubmissionActivity.class);
                startActivity(intent1);
            }
        });


    }

    public void recieveMapChallange() {

        MapChallengeRecieveRequest challangeDetailRequest = new MapChallengeRecieveRequest();
        challangeDetailRequest.setChallengeId(String.valueOf(userchallangeid));

        Util.isInternetConnected(this);
        Util.showProgressDialog(getApplicationContext());
        RestClient.userMapReciveChallenge(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN),challangeDetailRequest, new Callback<MapChallengeRecieveResponse>() {
            @Override
            public void onResponse(Call<MapChallengeRecieveResponse> call, Response<MapChallengeRecieveResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        Log.i("id", String.valueOf(userchallangeid));
                        List<ChallengeDetails> challengeDetails = Collections.singletonList(response.body().getChallengeDetails());
                        activityChallangeRecieveAdapter = new MapActivityChallengeRecieveAdapter(challengeDetails, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(activityChallangeRecieveAdapter);

                    }

                }


            }

            @Override
            public void onFailure(Call<MapChallengeRecieveResponse> call, Throwable t) {

                Toast.makeText(ChallengeRecieveMapActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void acceptChallenge() {

        ChallengeAcceptedRequest challengeAcceptedRequest = new ChallengeAcceptedRequest();
        challengeAcceptedRequest.setChallengeId(String.valueOf(userchallangeid));
        challengeAcceptedRequest.setUserId(userId);
        challengeAcceptedRequest.setSenderId(senderId);


        Util.showProgressDialog(this);
        RestClient.ChallangeAcceptByUser(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN),challengeAcceptedRequest, new Callback<ChallengeAcceptedResponse>() {
            @Override
            public void onResponse(Call<ChallengeAcceptedResponse> call, Response<ChallengeAcceptedResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {

                            Intent intent = new Intent(ChallengeRecieveMapActivity.this, AcceptChallengeMapActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(ChallengeRecieveMapActivity.this, "Challenge Accepted !", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ChallengeRecieveMapActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<ChallengeAcceptedResponse> call, Throwable t) {

                Toast.makeText(ChallengeRecieveMapActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void rejectChallenge() {

        ChallangeRejectRequest challangeRejectRequest = new ChallangeRejectRequest();
        challangeRejectRequest.setChallengeId(String.valueOf(userchallangeid));
        challangeRejectRequest.setUserId(userId);

        Util.showProgressDialog(this);
        RestClient.ChallangeRejectByUser(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN),challangeRejectRequest, new Callback<ChallangeRejectResponse>() {
            @Override
            public void onResponse(Call<ChallangeRejectResponse> call, Response<ChallangeRejectResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {

                            Intent intent = new Intent(ChallengeRecieveMapActivity.this, HomeMainActivity.class);
                            intent.putExtra("contestpage","");
                            startActivity(intent);
                            finish();
                            Toast.makeText(ChallengeRecieveMapActivity.this, "Challenge Rejected !", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ChallengeRecieveMapActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<ChallangeRejectResponse> call, Throwable t) {

                Toast.makeText(ChallengeRecieveMapActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
