
package com.app.furoapp.activity.challengeRecieveMap;

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
import com.app.furoapp.activity.challengeRecieve.ReportSubmissionActivity;
import com.app.furoapp.adapter.LoserDetailAdapter;
import com.app.furoapp.adapter.LoserDetailMapAdapter;
import com.app.furoapp.adapter.WinnerActivityAdapter;
import com.app.furoapp.adapter.WinnerMapActivityAdapter;
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

public class WinnerActivityMap extends AppCompatActivity {
    int subChallengeIdmap;
    String userIdmap;
    RecyclerView recyclerViewWinnerMap, recyclerViewLoserMap;
    WinnerMapActivityAdapter winnerActivityAdapter;
    LoserDetailMapAdapter loserDetailAdapter;
    TextView textViewReportThesubmiison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_map);

        recyclerViewWinnerMap = findViewById(R.id.winnerDetailsmap);
        recyclerViewLoserMap = findViewById(R.id.losserDetailsmap);
        textViewReportThesubmiison = findViewById(R.id.ReportThesubmiison);
        textViewReportThesubmiison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WinnerActivityMap.this, ReportSubmissionActivity.class);
                startActivity(intent);
            }
        });


        winnerDetail();
    }

    public void winnerDetail() {
        subChallengeIdmap = FuroPrefs.getInt(getApplicationContext(), "SubmissionChallenegeIdmap", 0);
        userIdmap = FuroPrefs.getString(getApplicationContext(), "userIdLoginmap");
        WinnerRequest winnerRequest = new WinnerRequest();
        winnerRequest.setChallengeId(String.valueOf(subChallengeIdmap));
        winnerRequest.setUserId(userIdmap);

        Util.showProgressDialog(this);

        RestClient.winnerUserResponse(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), winnerRequest, new Callback<WinnerResponse>() {
            @Override
            public void onResponse(Call<WinnerResponse> call, Response<WinnerResponse> response) {
                Util.dismissProgressDialog();
                if (response.body() != null) {

                    List<Winner> challengeDetails = Collections.singletonList(response.body().getDataWinner().getWinner());
                    winnerActivityAdapter = new WinnerMapActivityAdapter(challengeDetails, getApplicationContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewWinnerMap.setLayoutManager(layoutManager);
                    recyclerViewWinnerMap.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewWinnerMap.setAdapter(winnerActivityAdapter);

                    List<Loser> losers = Collections.singletonList(response.body().getDataLoser().getLoser());
                    loserDetailAdapter = new LoserDetailMapAdapter(losers, getApplicationContext());
                    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
                    recyclerViewLoserMap.setLayoutManager(layoutManager1);
                    recyclerViewLoserMap.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewLoserMap.setAdapter(loserDetailAdapter);


                } else {
                    Toast.makeText(WinnerActivityMap.this, "error", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<WinnerResponse> call, Throwable t) {
                Toast.makeText(WinnerActivityMap.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();

            }
        });


    }


}
