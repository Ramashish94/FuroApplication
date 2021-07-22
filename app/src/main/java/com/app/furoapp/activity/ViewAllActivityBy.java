package com.app.furoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.challengeRecieve.ChallengeByUserandChallengeForUserDetailActivity;
import com.app.furoapp.adapter.OpenAndChallengeCloseAdapter;
import com.app.furoapp.model.challengeByUser.ChallengeByUser;
import com.app.furoapp.model.challengeByUser.ChallengeByUserResponse;
import com.app.furoapp.model.challengeByUser.SendChallenge;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewAllActivityBy extends AppCompatActivity {

    HomeMainActivity homeMainActivity;

    ProgressBar progressBar;
    LinearLayout linearLayout;
    RecyclerView recyclerView, recyclerView1;
    OpenAndChallengeCloseAdapter openAndChallengeCloseAdapter;
    TextView youHaveNotChallange, Youhavechallange;
    int userIdChallenge;
    GifView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_open_close_loaderby);

        recyclerView = findViewById(R.id.ChallengeByYou);
        gifView = findViewById(R.id.progressBarJumpingJacks);
        gifView.setImageResource(R.drawable.jumpingjacks);

        linearLayout = findViewById(R.id.linearLayoutALLCommunity);
        Youhavechallange = findViewById(R.id.youhavechallange);
        recyclerView1 = findViewById(R.id.ChallengeByYouViewall);
        youHaveNotChallange = findViewById(R.id.youhavechallangeFOR);

        userIdChallenge = FuroPrefs.getInt(getApplicationContext(), "challengefuroid", 0);


        loadMore();

    }


    public void loadMore() {

        String challengeByYou = FuroPrefs.getString(getApplicationContext(), "loginUserId");

        ChallengeByUser challengeByUser = new ChallengeByUser();
        challengeByUser.setUserId(challengeByYou);


        Util.isInternetConnected(getApplicationContext());
        gifView.setVisibility(View.VISIBLE);
        RestClient.userChallangeByUser(challengeByUser, new Callback<ChallengeByUserResponse>() {
            @Override
            public void onResponse(Call<ChallengeByUserResponse> call, Response<ChallengeByUserResponse> response) {
                gifView.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null && response.body().getSendChallenge() != null && response.body().getSendChallenge().size() > 0) {

                        List<SendChallenge> sendChallenges = response.body().getSendChallenge();
                        if (sendChallenges!=null && sendChallenges.size()>0) {
                            openAndChallengeCloseAdapter = new OpenAndChallengeCloseAdapter(sendChallenges, getApplicationContext());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView1.setLayoutManager(layoutManager);
                            recyclerView1.setAdapter(openAndChallengeCloseAdapter);
                        }else {
                            Toast.makeText(homeMainActivity, "No records found", Toast.LENGTH_SHORT).show();
                        }

                        openAndChallengeCloseAdapter.setChallengeByYouItemList(new OpenAndChallengeCloseAdapter.ChallengeByYouInterface() {
                            @Override
                            public void challengeForYouItem(String challengeId, int position) {

                                String challTypeby = response.body().getSendChallenge().get(position).getChallengeType();
                                if (challTypeby.equalsIgnoreCase("map")) {
                                    Intent intent = new Intent(getApplicationContext(), MapChallengeActivityBy.class);
                                    String Challengeid = challengeId;
                                    intent.putExtra("userIdChallengeByMap", Challengeid);
                                    startActivity(intent);
                                } else if (challTypeby.equalsIgnoreCase("video")) {
                                    Intent intent = new Intent(getApplicationContext(), ChallengeByUserandChallengeForUserDetailActivity.class);
                                    String Challengeid = challengeId;
                                    intent.putExtra("userIdChallengeByVideo", Challengeid);
                                    startActivity(intent);


                                }


                            }
                        });


                    } else {

                        youHaveNotChallange.setVisibility(View.VISIBLE);


                    }

                }

            }

            @Override
            public void onFailure(Call<ChallengeByUserResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, "Something went wrong !", Toast.LENGTH_SHORT).show();

            }
        });


    }


}




