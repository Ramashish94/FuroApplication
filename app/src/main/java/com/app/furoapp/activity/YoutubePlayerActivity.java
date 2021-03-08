package com.app.furoapp.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.furoapp.R;
import com.app.furoapp.databinding.ActivityYoutubePlayerBinding;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.YouTubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class YoutubePlayerActivity extends YouTubeBaseActivity {

    ActivityYoutubePlayerBinding binding;
    private static final String TAG = "MainActivity";
    YouTubePlayerView mYouTubePlayerView;

    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    String videoId;
    ImageView imageCrossButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);


        imageCrossButton = findViewById(R.id.crossButton_youtube_new);

        videoId = FuroPrefs.getString(getApplicationContext(), "Youtube_Video_Id");

        setOnclickListnerCrossButton();


        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlay);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onClick: Done initializing.");
                //List<String>videoList=new ArrayList<>();
                // videoList.add("");
                //videoList.add("");
                //youTubePlayer.loadVideo(videoList);

/*
                youTubePlayer.loadVideo("sNQzqjrk1yI");
*/

                //youTubePlayer.loadVideo("zzCWCPK1grY");

                youTubePlayer.loadVideo(videoId);


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onClick: Failed to initialize.");

            }
        };

        mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
    }


    public void setOnclickListnerCrossButton() {
        imageCrossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();


            }
        });


    }


}



