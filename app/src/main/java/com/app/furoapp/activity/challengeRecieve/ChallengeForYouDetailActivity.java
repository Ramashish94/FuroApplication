package com.app.furoapp.activity.challengeRecieve;

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
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.adapter.ChallengeForYouDetailAdapter;
import com.app.furoapp.model.challengeAccepted.ChallengeAcceptedRequest;
import com.app.furoapp.model.challengeAccepted.ChallengeAcceptedResponse;
import com.app.furoapp.model.challengeReject.ChallangeRejectRequest;
import com.app.furoapp.model.challengeReject.ChallangeRejectResponse;
import com.app.furoapp.model.challengeforyoudetail.ChallengeDetails;
import com.app.furoapp.model.challengeforyoudetail.ChallengeForYouDetailRequest;
import com.app.furoapp.model.challengeforyoudetail.ChallengeForYouDetailResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeForYouDetailActivity extends AppCompatActivity {
    private TextView acceptChallange, rejectTextView;
    RecyclerView recyclerViewNew;
    ChallengeForYouDetailAdapter challengeByYouDetailAdapter;
    String challenegeDetailId;
    String userid, senderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_for_you_detail);
        Intent intent = getIntent();
        challenegeDetailId = intent.getStringExtra("userIdChallenge");
        userid = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        recyclerViewNew = findViewById(R.id.challengeForyou);
        challenegeForYouDetail();

        rejectTextView = findViewById(R.id.reject);

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
    }

    public void challenegeForYouDetail() {
        ChallengeForYouDetailRequest challengeForYouDetailRequest = new ChallengeForYouDetailRequest();
        challengeForYouDetailRequest.setChallengeId(challenegeDetailId);
        challengeForYouDetailRequest.setUserId(userid);

        Util.showProgressDialog(getApplicationContext());
        RestClient.userChallengeForDetail(challengeForYouDetailRequest, new Callback<ChallengeForYouDetailResponse>() {
            @Override
            public void onResponse(Call<ChallengeForYouDetailResponse> call, Response<ChallengeForYouDetailResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {

                    if (response.body().getStatus().equalsIgnoreCase("200")) {

                        List<ChallengeDetails> challengeDetails = Collections.singletonList(response.body().getChallengeDetails());
                        senderId = response.body().getChallengeDetails().getUserId();
                        challengeByYouDetailAdapter = new ChallengeForYouDetailAdapter(challengeDetails, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewNew.setLayoutManager(layoutManager);
                        recyclerViewNew.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewNew.setAdapter(challengeByYouDetailAdapter);
                        if (response.body().getChallengeDetails().getAccepted().equalsIgnoreCase("1") || (response.body().getChallengeDetails().getRejected().equalsIgnoreCase("1"))) {
                            rejectTextView.setVisibility(View.GONE);
                            acceptChallange.setVisibility(View.GONE);
                        } else if (response.body().getChallengeDetails().getAccepted().equalsIgnoreCase("0") &&
                                (response.body().getChallengeDetails().getRejected().equalsIgnoreCase("0"))) {
                            rejectTextView.setVisibility(View.VISIBLE);
                            acceptChallange.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(ChallengeForYouDetailActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChallengeForYouDetailResponse> call, Throwable t) {
                Toast.makeText(ChallengeForYouDetailActivity.this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void acceptChallenge() {

        ChallengeAcceptedRequest challengeAcceptedRequest = new ChallengeAcceptedRequest();
        challengeAcceptedRequest.setChallengeId(String.valueOf(challenegeDetailId));
        challengeAcceptedRequest.setUserId(userid);
        challengeAcceptedRequest.setSenderId(senderId);

        Util.showProgressDialog(this);
        RestClient.ChallangeAcceptByUser(challengeAcceptedRequest, new Callback<ChallengeAcceptedResponse>() {
            @Override
            public void onResponse(Call<ChallengeAcceptedResponse> call, Response<ChallengeAcceptedResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            Intent intent = new Intent(ChallengeForYouDetailActivity.this, AcceptChallangeActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(ChallengeForYouDetailActivity.this, "Challenge Accepted !", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ChallengeForYouDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ChallengeForYouDetailActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChallengeAcceptedResponse> call, Throwable t) {
                Toast.makeText(ChallengeForYouDetailActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void rejectChallenge() {

        ChallangeRejectRequest challangeRejectRequest = new ChallangeRejectRequest();
        challangeRejectRequest.setChallengeId(String.valueOf(challenegeDetailId));
        challangeRejectRequest.setUserId(userid);

        Util.showProgressDialog(this);
        RestClient.ChallangeRejectByUser(challangeRejectRequest, new Callback<ChallangeRejectResponse>() {
            @Override
            public void onResponse(Call<ChallangeRejectResponse> call, Response<ChallangeRejectResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            Intent intent = new Intent(ChallengeForYouDetailActivity.this, HomeMainActivity.class);
                            intent.putExtra("contestpage", "");
                            startActivity(intent);
                            finish();
                            Toast.makeText(ChallengeForYouDetailActivity.this, "Challenge Rejected !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChallengeForYouDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ChallangeRejectResponse> call, Throwable t) {
                Toast.makeText(ChallengeForYouDetailActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
