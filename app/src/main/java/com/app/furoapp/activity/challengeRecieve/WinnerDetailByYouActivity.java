package com.app.furoapp.activity.challengeRecieve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.adapter.WinnerByYouDetailActivityAdapter;
import com.app.furoapp.model.challengedetail.ChallangeDetailRequest;
import com.app.furoapp.model.challengedetail.ChallangeDetailResponse;
import com.app.furoapp.model.challengedetail.ChallengeDetails;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WinnerDetailByYouActivity extends AppCompatActivity {

    WinnerByYouDetailActivityAdapter activityAcceptChallengeAdapter;
    RecyclerView recyclerView;
    String challengebyid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_detail_by_you);
        recyclerView = findViewById(R.id.winnerdetailbyyou);
        acceptChallange();
    }

    public void acceptChallange() {
        challengebyid = FuroPrefs.getString(getApplicationContext(), "bychallengeid");
        if (challengebyid != null) {
            ChallangeDetailRequest challangeDetailRequest = new ChallangeDetailRequest();
            challangeDetailRequest.setChallengeId(String.valueOf(challengebyid));
            Util.isInternetConnected(this);
            Util.showProgressDialog(this);
            RestClient.userChallangeDetail(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), challangeDetailRequest, new Callback<ChallangeDetailResponse>() {
                @Override
                public void onResponse(Call<ChallangeDetailResponse> call, Response<ChallangeDetailResponse> response) {
                    Util.dismissProgressDialog();
                    if (response != null) {
                        if (response.body().getChallengeDetails() != null) {
                            List<ChallengeDetails> challengeDetails = Collections.singletonList(response.body().getChallengeDetails());
                            activityAcceptChallengeAdapter = new WinnerByYouDetailActivityAdapter(challengeDetails, getApplicationContext());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(activityAcceptChallengeAdapter);
                        } else {

                        }

                    } else {

                    }


                }

                @Override
                public void onFailure(Call<ChallangeDetailResponse> call, Throwable t) {
                    Toast.makeText(WinnerDetailByYouActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

}
