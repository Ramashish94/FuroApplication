package com.app.furoapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.databinding.FragmentYoutubeBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.utils.FuroPrefs;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

public class YoutubePlayerFragment extends Fragment {

    HomeMainActivity homeMainActivity;
    FragmentYoutubeBinding binding;
    YouTubePlayerView youTubePlayerView;
    String videoId;
    ImageView imageCrossButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_youtube, container, false);
        View view = binding.getRoot();
        youTubePlayerView = view.findViewById(R.id.youtubePlayer);
        imageCrossButton = binding.crossButtonYoutube;
        videoId = FuroPrefs.getString(getContext(), "Youtube_Video_Id");

        setOnclickListnerCrossButton();

        youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f);
                youTubePlayerView.exitFullScreen();
            }

            @Override
            public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState playerState) {

            }

            @Override
            public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackQuality playbackQuality) {

            }

            @Override
            public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackRate playbackRate) {

            }

            @Override
            public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError playerError) {

            }

            @Override
            public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

            }

            @Override
            public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

            }
        });


        return view;
    }

    public YoutubePlayerFragment() {

    }

    public static YoutubePlayerFragment newInstance(String name) {
        YoutubePlayerFragment fragment = new YoutubePlayerFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnclickListnerCrossButton() {
        imageCrossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeMainActivity.setDisplayFragment(EnumConstants.CONTENT_FEED_MAIN_FRAGMENT, null);

            }
        });


    }


}







