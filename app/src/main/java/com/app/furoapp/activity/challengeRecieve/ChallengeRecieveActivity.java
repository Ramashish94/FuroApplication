package com.app.furoapp.activity.challengeRecieve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.challengeRecieveMap.ChallengeRecieveMapActivity;
import com.app.furoapp.adapter.ActivityChallangeRecieveAdapter;
import com.app.furoapp.model.challengeAccepted.ChallengeAcceptedRequest;
import com.app.furoapp.model.challengeAccepted.ChallengeAcceptedResponse;
import com.app.furoapp.model.challengeReject.ChallangeRejectRequest;
import com.app.furoapp.model.challengeReject.ChallangeRejectResponse;
import com.app.furoapp.model.challengedetail.ChallangeDetailRequest;
import com.app.furoapp.model.challengedetail.ChallangeDetailResponse;
import com.app.furoapp.model.challengedetail.ChallengeDetails;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.Collections;
import java.util.List;

import okhttp3.Challenge;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeRecieveActivity extends AppCompatActivity {


    private TextView acceptChallange, rejectTextView;

    RecyclerView recyclerView;
    String mapOpen,shareUserId;
    String userId;
    int userchallangeid;
    TextView report;
    ActivityChallangeRecieveAdapter activityChallangeRecieveAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_recieve);
        report = findViewById(R.id.ReportThesubmiison);

        // userchallangeid = FuroPrefs.getInt(getApplicationContext(), "challengefuroid", 0);
        Intent intent = getIntent();
        userchallangeid = intent.getIntExtra("challenegid", 0);





        if (userchallangeid == 0) {
            Intent appLinkIntent = getIntent();
            String appLinkData = appLinkIntent.getData().toString();
            Log.d("Data", "" + appLinkData);


            if (!TextUtils.isEmpty(appLinkData)) {
                String data[] = appLinkData.split("/");
                userchallangeid = Integer.parseInt(data[4]);
                shareUserId = data[5];
                mapOpen = data[3];

                if (mapOpen.equalsIgnoreCase("map")) {
                    FuroPrefs.putInt(getApplicationContext(), "challengefuroid", userchallangeid);
                    FuroPrefs.putString(getApplicationContext(),"",shareUserId);
                    Intent intent1 = new Intent(ChallengeRecieveActivity.this, ChallengeRecieveMapActivity.class);
                    startActivity(intent1);
                    finish();

                } else if (mapOpen.equalsIgnoreCase("video")) {
                    userchallangeid = Integer.parseInt(data[4]);
                    FuroPrefs.putInt(getApplicationContext(), "challengefuroid", userchallangeid);

                }


            }

        } else {
            userchallangeid = intent.getIntExtra("challenegid", 0);
        }


        recyclerView = findViewById(R.id.recieveChallange);

        rejectTextView = findViewById(R.id.reject);
        recieveChallange();

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
                Intent intent1 = new Intent(ChallengeRecieveActivity.this,ReportSubmissionActivity.class);
                startActivity(intent1);
            }
        });


    }

    public void recieveChallange() {

        ChallangeDetailRequest challangeDetailRequest = new ChallangeDetailRequest();
        challangeDetailRequest.setChallengeId(String.valueOf(userchallangeid));
        Util.isInternetConnected(this);
        Util.showProgressDialog(getApplicationContext());
        RestClient.userChallangeDetail(challangeDetailRequest, new Callback<ChallangeDetailResponse>() {
            @Override
            public void onResponse(Call<ChallangeDetailResponse> call, Response<ChallangeDetailResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        Log.i("id", String.valueOf(userchallangeid));
                        List<ChallengeDetails> challengeDetails = Collections.singletonList(response.body().getChallengeDetails());
                        activityChallangeRecieveAdapter = new ActivityChallangeRecieveAdapter(challengeDetails, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(activityChallangeRecieveAdapter);

                    }

                }


            }

            @Override
            public void onFailure(Call<ChallangeDetailResponse> call, Throwable t) {

                Toast.makeText(ChallengeRecieveActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void acceptChallenge() {

        ChallengeAcceptedRequest challengeAcceptedRequest = new ChallengeAcceptedRequest();
        challengeAcceptedRequest.setChallengeId(String.valueOf(userchallangeid));
        challengeAcceptedRequest.setUserId(userId);
        challengeAcceptedRequest.setSenderId(shareUserId);


        Util.showProgressDialog(this);
        RestClient.ChallangeAcceptByUser(challengeAcceptedRequest, new Callback<ChallengeAcceptedResponse>() {
            @Override
            public void onResponse(Call<ChallengeAcceptedResponse> call, Response<ChallengeAcceptedResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            Intent intent = new Intent(ChallengeRecieveActivity.this, AcceptChallangeActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(ChallengeRecieveActivity.this, "Challenge Accepted !", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ChallengeRecieveActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ChallengeRecieveActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<ChallengeAcceptedResponse> call, Throwable t) {
                Toast.makeText(ChallengeRecieveActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void rejectChallenge() {

        ChallangeRejectRequest challangeRejectRequest = new ChallangeRejectRequest();
        challangeRejectRequest.setChallengeId(String.valueOf(userchallangeid));
        challangeRejectRequest.setUserId(userId);

        Util.showProgressDialog(this);
        RestClient.ChallangeRejectByUser(challangeRejectRequest, new Callback<ChallangeRejectResponse>() {
            @Override
            public void onResponse(Call<ChallangeRejectResponse> call, Response<ChallangeRejectResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {

                            Intent intent = new Intent(ChallengeRecieveActivity.this, HomeMainActivity.class);
                            intent.putExtra("contestpage","");
                            startActivity(intent);
                            finish();
                            Toast.makeText(ChallengeRecieveActivity.this, "Challenge Rejected !", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ChallengeRecieveActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<ChallangeRejectResponse> call, Throwable t) {
                Toast.makeText(ChallengeRecieveActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
