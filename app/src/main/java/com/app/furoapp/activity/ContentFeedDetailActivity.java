package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.adapter.ContentFeedDetailAdapter;
import com.app.furoapp.activity.newFeature.likeAndSaved.CommentsAdapter;
import com.app.furoapp.activity.newFeature.likeAndSaved.ComentsModelTest;
import com.app.furoapp.model.contentFeedDetail.Body;
import com.app.furoapp.model.contentFeedDetail.ContentFeedDetailRequest;
import com.app.furoapp.model.contentFeedDetail.ContentFeedDetailResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
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
    LinearLayout linearLayout, llPostBtn;
    RecyclerView rvComment;
    ImageView ivlikeAndUnlike, ivShare;
    TextView tvLikeCounts, tvLiksTxt, tvCmntsCount, tvCmntsTxt, tvViewsCounts, tvViewsTxt, tvTotNosOfComments;
    EditText etAddComments;
    public String activityId;
    public Boolean clicked = true;
    CommentsAdapter commentsAdapter;
    List<ComentsModelTest> comentsModelTestList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_feed_detail);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        progressBarr = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.linearLayoutContentfeeddetail);
        textView = findViewById(R.id.pbText);
        recyclerView = findViewById(R.id.contentDetailRecycler);

        rvComment = findViewById(R.id.rvComment);
        ivlikeAndUnlike = findViewById(R.id.ivlikeAndUnlike);
        tvLikeCounts = findViewById(R.id.tvLikeCounts);
        tvLiksTxt = findViewById(R.id.tvLiksTxt);
        tvCmntsCount = findViewById(R.id.tvCmntsCount);
        tvCmntsTxt = findViewById(R.id.tvCmntsTxt);
        tvViewsCounts = findViewById(R.id.tvViewsCounts);
        tvViewsTxt = findViewById(R.id.tvViewsTxt);
        ivShare = findViewById(R.id.ivShare);
        tvTotNosOfComments = findViewById(R.id.tvTotNosOfComments);
        etAddComments = findViewById(R.id.etAddComments);
        llPostBtn = findViewById(R.id.llPostBtn);

        contentFeedApi();
        getLikeShareAndCmntsData();
        setCommentsAdapter();
    }

    public void contentFeedApi() {

        activityId = FuroPrefs.getString(getApplicationContext(), "id");
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
                    } else {
                    }
                }
            }

            @Override
            public void onFailure(Call<ContentFeedDetailResponse> call, Throwable t) {
                Toast.makeText(ContentFeedDetailActivity.this, " Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLikeShareAndCmntsData() {

        ivlikeAndUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    clicked = false;
                    ivlikeAndUnlike.setImageResource(R.drawable.thumb_like);
                    tvLikeCounts.setTextColor(Color.parseColor("#19CFE6"));
                    tvLiksTxt.setTextColor(Color.parseColor("#19CFE6"));
                } else {
                    clicked = true;
                    ivlikeAndUnlike.setImageResource(R.drawable.thumb_unlike);
                    tvLikeCounts.setTextColor(Color.BLACK);
                    tvLiksTxt.setTextColor(Color.BLACK);
                }
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "http://img.youtube.com/vi/" +"/0.jpg";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }

    private void setCommentsAdapter() {
        commentsAdapter = new CommentsAdapter(getApplicationContext(), comentsModelTestList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvComment.setLayoutManager(layoutManager);
        rvComment.setItemAnimator(new DefaultItemAnimator());
        rvComment.setAdapter(commentsAdapter);
        List<ComentsModelTest> comentsModelTestLists = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            ComentsModelTest comentsModelTestList = new ComentsModelTest();
            comentsModelTestList.setName("kamal" + i);
            comentsModelTestList.setComments("fantastic" + i + "Nice");
            comentsModelTestLists.add(comentsModelTestList);
        }
        CommentsAdapter commentsAdapter1 = new CommentsAdapter(getApplicationContext(), comentsModelTestLists);
        rvComment.setAdapter(commentsAdapter1);

    }


}
