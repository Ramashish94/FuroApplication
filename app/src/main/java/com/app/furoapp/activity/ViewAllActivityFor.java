package com.app.furoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.challengeRecieve.ChallengeForYouDetailActivity;
import com.app.furoapp.adapter.ChallangesForYouAdapter;
import com.app.furoapp.model.challengeforyouRecieve.ChallenegeForYouRecieve;
import com.app.furoapp.model.challengeforyouRecieve.ChallenegeForYouRecieveRequest;
import com.app.furoapp.model.challengeforyouRecieve.ReceiveChallenge;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ViewAllActivityFor extends AppCompatActivity {

    LinearLayout linearLayout;
    RecyclerView recyclerView, recyclerView1;
    ChallangesForYouAdapter challangesForYouAdapter;
    TextView youHaveNotChallange, Youhavechallange;
    int userIdChallenge;
    GifView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_for);

        recyclerView = findViewById(R.id.ChallengeByYou);

        gifView = findViewById(R.id.progressBarJumpingJacks);
        gifView.setImageResource(R.drawable.pushup);

        Youhavechallange = findViewById(R.id.youhavechallange);
        recyclerView1 = findViewById(R.id.challengeForyouView);
        youHaveNotChallange = findViewById(R.id.youhavenotchallange);

         userIdChallenge = FuroPrefs.getInt(getApplicationContext(), "challengefuroid", 0);



        getDChallengeForYou();

    }






    public void getDChallengeForYou() {
        String challengeForYou = FuroPrefs.getString(getApplicationContext(), "loginUserId");

        ChallenegeForYouRecieveRequest challenegeForYouRecieveRequest = new ChallenegeForYouRecieveRequest();
        challenegeForYouRecieveRequest.setUserId(challengeForYou);


        Util.isInternetConnected(getApplicationContext());

        gifView.setVisibility(View.VISIBLE);

        RestClient.challenegeForYouRecieve(challenegeForYouRecieveRequest, new Callback<ChallenegeForYouRecieve>() {
            @Override
            public void onResponse(Call<ChallenegeForYouRecieve> call, Response<ChallenegeForYouRecieve> response) {
                gifView.setVisibility(View.GONE);

                if (response != null) {

                    if (response.body() != null && response.body().getReceiveChallenge().size() > 0) {
                        List<ReceiveChallenge> receiveChallenges = response.body().getReceiveChallenge();


                        challangesForYouAdapter = new ChallangesForYouAdapter(receiveChallenges   , getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView1.setLayoutManager(layoutManager);
                        recyclerView1.setItemAnimator(new DefaultItemAnimator());
                        recyclerView1.setAdapter(challangesForYouAdapter);
                        challangesForYouAdapter.setChallengeForYouItemList(new ChallangesForYouAdapter.ChallengeForYouInterface() {
                            @Override
                            public void challengeForYouItem(String idChallenge, int position) {

                                String challTypenew = response.body().getReceiveChallenge().get(position).getChallengeType();
                                if (challTypenew.equalsIgnoreCase("map")) {
                                    Intent intent = new Intent(getApplicationContext(), MapChallengeActivity.class);
                                    String Challengeidnew = idChallenge;
                                    intent.putExtra("userIdChallenge", Challengeidnew);
                                    startActivity(intent);
                                } else if (challTypenew.equalsIgnoreCase("video")) {
                                    Intent intent = new Intent(getApplicationContext(), ChallengeForYouDetailActivity.class);
                                    String Challengeidnew = idChallenge;
                                    intent.putExtra("userIdChallenge", Challengeidnew);
                                    startActivity(intent);


                                }
                            }


                        });
                    } else {
                        Youhavechallange.setVisibility(View.VISIBLE);
                    }


                }
            }

            @Override
            public void onFailure(Call<ChallenegeForYouRecieve> call, Throwable t) {
                Toast.makeText(ViewAllActivityFor.this, "No Internet connection !!", Toast.LENGTH_SHORT).show();

            }
        });


    }


}




