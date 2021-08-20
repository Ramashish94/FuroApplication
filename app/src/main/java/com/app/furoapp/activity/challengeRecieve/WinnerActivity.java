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
import com.app.furoapp.adapter.LoserDetailAdapter;
import com.app.furoapp.adapter.WinnerActivityAdapter;
import com.app.furoapp.model.winnerApi.Loser;
import com.app.furoapp.model.winnerApi.Winner;
import com.app.furoapp.model.winnerApi.WinnerRequest;
import com.app.furoapp.model.winnerApi.WinnerResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WinnerActivity extends AppCompatActivity {
    int subChallengeId;
    String userId;
    RecyclerView recyclerViewWinnerVideo, recyclerViewLoserVideo;
    WinnerActivityAdapter winnerActivityAdapter;
    LoserDetailAdapter loserDetailAdapter;
    TextView textViewReportThesubmiison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        recyclerViewWinnerVideo = findViewById(R.id.winnerDetailsvideonew);
        recyclerViewLoserVideo = findViewById(R.id.losserDetailsvideonew);
        textViewReportThesubmiison = findViewById(R.id.ReportThesubmiison);
        textViewReportThesubmiison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WinnerActivity.this, ReportSubmissionActivity.class);
                startActivity(intent);
            }
        });


        winnerDetail();
    }

    public void winnerDetail() {


        subChallengeId = FuroPrefs.getInt(getApplicationContext(), "SubmissionChallenegeId", 0);
        userId = FuroPrefs.getString(getApplicationContext(), "userIdLogin");
        WinnerRequest winnerRequest = new WinnerRequest();
        winnerRequest.setChallengeId(String.valueOf(subChallengeId));
        winnerRequest.setUserId(userId);
        Util.showProgressDialog(this);
        RestClient.winnerUserResponse(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN),winnerRequest, new Callback<WinnerResponse>() {
            @Override
            public void onResponse(Call<WinnerResponse> call, Response<WinnerResponse> response) {
                Util.dismissProgressDialog();

                if (response.body() != null) {

                    List<Winner> challengeDetails = Collections.singletonList(response.body().getDataWinner().getWinner());
                    winnerActivityAdapter = new WinnerActivityAdapter(challengeDetails, getApplicationContext());
                    RecyclerView.LayoutManager layoutManagernew = new LinearLayoutManager(getApplicationContext());
                    recyclerViewWinnerVideo.setLayoutManager(layoutManagernew);
                    recyclerViewWinnerVideo.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewWinnerVideo.setAdapter(winnerActivityAdapter);

                    List<Loser> losers = Collections.singletonList(response.body().getDataLoser().getLoser());
                    loserDetailAdapter = new LoserDetailAdapter(losers, getApplicationContext());
                    RecyclerView.LayoutManager layoutManager1new = new LinearLayoutManager(getApplicationContext());
                    recyclerViewLoserVideo.setLayoutManager(layoutManager1new);
                    recyclerViewLoserVideo.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewLoserVideo.setAdapter(loserDetailAdapter);


                } else {
                    Toast.makeText(WinnerActivity.this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<WinnerResponse> call, Throwable t) {
                Toast.makeText(WinnerActivity.this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();

            }
        });


    }


}
