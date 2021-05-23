package com.app.furoapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.likeAndSaved.ComentsModelTest;
import com.app.furoapp.activity.newFeature.newFeatureModelByM.activityDetailsNew.ActivityDetailResponse;
import com.app.furoapp.activity.newFeature.newFeatureModelByM.activityDetailsNew.ActivityDetailsItem;
import com.app.furoapp.activity.newFeature.newFeatureModelByM.addComments.AddCommentRequest;
import com.app.furoapp.activity.newFeature.newFeatureModelByM.addComments.AddCommentResponse;
import com.app.furoapp.activity.newFeature.newFeatureModelByM.activityDetailsNew.BodyItem;
import com.app.furoapp.activity.newFeature.newFeatureModelByM.like.LikeRequest;
import com.app.furoapp.activity.newFeature.newFeatureModelByM.like.LikeResponse;
import com.app.furoapp.adapter.ActivityDetailAdapter;
import com.app.furoapp.adapter.LikeShareCommentsAdapter;
import com.app.furoapp.model.contentFeedDetail.ContentFeedDetailRequest;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentFeedDetailActivity extends AppCompatActivity {

    YouTubePlayerView youTubePlayerView;
    ActivityDetailAdapter contentFeedDetailAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBarr;
    TextView textView;
    LinearLayout linearLayout,llLikeksSection, llPostBtn;
    RecyclerView rvComment;
    ImageView ivlikeAndUnlike, ivShare;
    TextView tvLikeCounts, tvLiksTxt, tvCmntsCount, tvCmntsTxt, tvViewsCounts, tvViewsTxt, tvTotNosOfComments;
    EditText etAddComments;
    public String activityId;
    public Boolean clicked = true;
    private String accessToken;
    private ActivityDetailsItem detailsItem;
    LikeShareCommentsAdapter likeShareCommentsAdapter;
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
        llLikeksSection = findViewById(R.id.llLikeksSection);
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
        llPostBtn.setOnClickListener(v -> {
            inputValidation();
        });
        contentFeedApi();
        setCommentsAdapter();
    }

    private void inputValidation() {
        String comment=etAddComments.getText().toString().trim();
        if (!comment.isEmpty()){
            callCommentApi( getCommentApiParams(Integer.parseInt(activityId),comment));
            etAddComments.setText("");
        }else{
            showToast("Please enter comments");
        }

    }

    public void contentFeedApi() {

        activityId = FuroPrefs.getString(getApplicationContext(), "id");
        ContentFeedDetailRequest contentFeedDetailRequest = new ContentFeedDetailRequest();
        contentFeedDetailRequest.setActivityId(activityId);

        Util.isInternetConnected(this);
        linearLayout.setVisibility(View.VISIBLE);
        progressBarr.setVisibility(View.VISIBLE);
        accessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        RestClient.activityDetailNew(accessToken, contentFeedDetailRequest, new Callback<ActivityDetailResponse>() {
            @Override
            public void onResponse(Call<ActivityDetailResponse> call, Response<ActivityDetailResponse> response) {
                linearLayout.setVisibility(View.GONE);
                progressBarr.setVisibility(View.GONE);

                if (response != null) {
                    if (response.body() != null) {
                        setData(response.body().getActivityDetails().get(0));
                        List<BodyItem> bodyList = response.body().getActivityDetails().get(0).getBody();
                        contentFeedDetailAdapter = new ActivityDetailAdapter(bodyList, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(contentFeedDetailAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ActivityDetailResponse> call, Throwable t) {
                Toast.makeText(ContentFeedDetailActivity.this, " Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLikeShareAndCmntsData(String userLike) {
        if (userLike.equals("0")) {
            clicked = false;
            ivlikeAndUnlike.setImageResource(R.drawable.thumb_unlike);
            tvLikeCounts.setTextColor(Color.BLACK);
            tvLiksTxt.setTextColor(Color.BLACK);
        } else {
            clicked = true;
            ivlikeAndUnlike.setImageResource(R.drawable.thumb_like);
            tvLikeCounts.setTextColor(Color.parseColor("#19CFE6"));
            tvLiksTxt.setTextColor(Color.parseColor("#19CFE6"));
        }

        llLikeksSection.setOnClickListener(v -> {
            if (clicked) {
                clicked = false;
                ivlikeAndUnlike.setImageResource(R.drawable.thumb_unlike);
                tvLikeCounts.setTextColor(Color.BLACK);
                tvLiksTxt.setTextColor(Color.BLACK);
            } else {
                clicked = true;
                ivlikeAndUnlike.setImageResource(R.drawable.thumb_like);
                tvLikeCounts.setTextColor(Color.parseColor("#19CFE6"));
                tvLiksTxt.setTextColor(Color.parseColor("#19CFE6"));
            }
            int dataUserLike;
            if (clicked) {
                dataUserLike = 1;
               // tvLikeCounts.setText(Integer.toString(detailsItem.getTotalLikes())+1);
            } else {
                dataUserLike = 0;
               // tvLikeCounts.setText(detailsItem.getTotalLikes()-1);
            }
            callLikeApi(getLikeApiParams(Integer.parseInt(activityId), dataUserLike));

        });

        ivShare.setOnClickListener(v -> {
            shareData();
        });
    }

    private void setCommentsAdapter() {
        likeShareCommentsAdapter = new LikeShareCommentsAdapter(getApplicationContext(), comentsModelTestList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvComment.setLayoutManager(layoutManager);
        rvComment.setItemAnimator(new DefaultItemAnimator());
        rvComment.setAdapter(likeShareCommentsAdapter);
        List<ComentsModelTest> comentsModelTestLists = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            ComentsModelTest comentsModelTestList = new ComentsModelTest();
            comentsModelTestList.setName("kamal" + i);
            comentsModelTestList.setComments("fantastic" + i + "Nice");
            comentsModelTestLists.add(comentsModelTestList);
        }
        LikeShareCommentsAdapter likeShareCommentsAdapter1 = new LikeShareCommentsAdapter(getApplicationContext(), comentsModelTestLists);
        rvComment.setAdapter(likeShareCommentsAdapter1);
    }

    private AddCommentRequest getCommentApiParams(int postId, String comment){
        AddCommentRequest request=new AddCommentRequest();
        request.setComment(comment);
        request.setPostId(postId);
        return request;

    }

    private void callCommentApi(AddCommentRequest request) {

        RestClient.userCommentOnPost(accessToken, request, new Callback<AddCommentResponse>() {
            @Override
            public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {

                if (response.body() != null) {
                    contentFeedApi();
                }
            }

            @Override
            public void onFailure(Call<AddCommentResponse> call, Throwable t) {
                Toast.makeText(ContentFeedDetailActivity.this, " Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(ActivityDetailsItem data) {
        detailsItem = data;
        tvLikeCounts.setText("" + data.getTotalLikes());
        tvCmntsCount.setText("" + data.getTotalComments());
        tvTotNosOfComments.setText("" + data.getTotalComments());
        tvViewsCounts.setText("" + data.getTotalViews());
        getLikeShareAndCmntsData(data.getUserLike());
    }

    private void callLikeApi(LikeRequest data) {
//        Util.isInternetConnected(getContext());
//        Util.showProgressDialog(getActivity());
        RestClient.userPostLike(accessToken, data, new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
//                Util.dismissProgressDialog();

                if (response.body() != null) {
                    LikeResponse likeResponse = response.body();
                    showToast(likeResponse.getStatus());
                }
            }
            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                showToast("No internet connection!");
            }
        });

    }

    private LikeRequest getLikeApiParams(int postId, int flag) {
        LikeRequest request = new LikeRequest();
        request.setLikeFlag(flag);
        request.setPostId(postId);
        return request;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void shareData() {
        String title = detailsItem.getDescription();
        String url;
        String videoId = null;
        if (detailsItem.getVideo() != null)
            try {
                videoId = Util.extractYoutubeId(detailsItem.getVideo());
            } catch (
                    MalformedURLException e) {
                e.printStackTrace();
            }
        if (!TextUtils.isEmpty(videoId)) {
            url = "http://www.youtube.com/watch?v=" + videoId;
        } else {
            url = detailsItem.getImage();
        }
        String fullMessage = title + "\n" + url;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Furo FQ");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, fullMessage);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
