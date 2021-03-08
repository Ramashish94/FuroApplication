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
import com.app.furoapp.adapter.MapActivityAcceptChallengeAdapter;
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

public class AcceptChallengeMapActivity extends AppCompatActivity {
    MapActivityAcceptChallengeAdapter mapActivityAcceptChallengeAdapter;
    RecyclerView recyclerViewAccept;
    TextView textView;
    int userchallangeidAccept;
    private TextView startcamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_challenge_map);
        startcamera = findViewById(R.id.tvStartCamera);
        textView = findViewById(R.id.tvStartmap);
        recyclerViewAccept = findViewById(R.id.acceptchallengeRecycle);


        userchallangeidAccept = FuroPrefs.getInt(getApplicationContext(), "challengefuroid", 0);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcceptChallengeMapActivity.this, MapEnduranceActivityAfterChallenge.class);
                startActivity(intent);
                finish();
            }
        });
        recieveMapChallange();
    }


    public void recieveMapChallange() {

        MapChallengeRecieveRequest challangeDetailRequest = new MapChallengeRecieveRequest();
        challangeDetailRequest.setChallengeId(String.valueOf(userchallangeidAccept));
        Util.isInternetConnected(this);
        Util.showProgressDialog(getApplicationContext());
        RestClient.userMapReciveChallenge(challangeDetailRequest, new Callback<MapChallengeRecieveResponse>() {
            @Override
            public void onResponse(Call<MapChallengeRecieveResponse> call, Response<MapChallengeRecieveResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        String mapActivity = response.body().getChallengeDetails().getChallengeActivity();
                        FuroPrefs.putString(getApplicationContext(), "mapActivity", mapActivity);
                        List<ChallengeDetails> challengeDetails = Collections.singletonList(response.body().getChallengeDetails());
                        mapActivityAcceptChallengeAdapter = new MapActivityAcceptChallengeAdapter(challengeDetails, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewAccept.setLayoutManager(layoutManager);
                        recyclerViewAccept.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewAccept.setAdapter(mapActivityAcceptChallengeAdapter);

                    }

                }


            }

            @Override
            public void onFailure(Call<MapChallengeRecieveResponse> call, Throwable t) {
                Toast.makeText(AcceptChallengeMapActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }

}




