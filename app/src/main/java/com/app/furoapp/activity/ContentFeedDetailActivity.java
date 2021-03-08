package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.adapter.ContentFeedDetailAdapter;
import com.app.furoapp.model.contentFeedDetail.Body;
import com.app.furoapp.model.contentFeedDetail.ContentFeedDetailRequest;
import com.app.furoapp.model.contentFeedDetail.ContentFeedDetailResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentFeedDetailActivity extends AppCompatActivity {

    YouTubePlayerView youTubePlayerView;
    ContentFeedDetailAdapter contentFeedDetailAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBarr;
    TextView textView;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_feed_detail);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        progressBarr = findViewById(R.id.progressBar);
        linearLayout=findViewById(R.id.linearLayoutContentfeeddetail);
        textView = findViewById(R.id.pbText);
        recyclerView = findViewById(R.id.contentDetailRecycler);

        contentFeedApi();
    }

    public void contentFeedApi() {

        String activityId = FuroPrefs.getString(getApplicationContext(), "id");
        ContentFeedDetailRequest contentFeedDetailRequest = new ContentFeedDetailRequest();
        contentFeedDetailRequest.setActivityId(activityId);

        Util.isInternetConnected(this);
        linearLayout.setVisibility(View.VISIBLE);
        progressBarr.setVisibility(View.VISIBLE);

        RestClient.activityDetail(contentFeedDetailRequest, new Callback<ContentFeedDetailResponse>() {
            @Override
            public void onResponse(Call<ContentFeedDetailResponse> call, Response<ContentFeedDetailResponse> response) {
                linearLayout.setVisibility(View.GONE);
                progressBarr.setVisibility(View.GONE);

                if (response != null) {
                    if (response.body() != null) {
                        List<Body> bodyList = response.body().getActivityDetails().get(0).getBody();
                        contentFeedDetailAdapter = new ContentFeedDetailAdapter(bodyList, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(contentFeedDetailAdapter);
                    }else{

                    }
                }
            }

            @Override
            public void onFailure(Call<ContentFeedDetailResponse> call, Throwable t) {
                Toast.makeText(ContentFeedDetailActivity.this, " Something went wrong !!", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
