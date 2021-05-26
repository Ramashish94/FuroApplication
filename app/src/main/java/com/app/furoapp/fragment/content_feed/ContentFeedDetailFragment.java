/*
package com.app.furoapp.fragment.content_feed;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.adapter.ContentFeedDetailAdapter;
import com.app.furoapp.databinding.FragmentContentFeedDetailsBinding;
import com.app.furoapp.model.contentFeedDetail.com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.Body;
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

public class ContentFeedDetailFragment extends Fragment {
    FragmentContentFeedDetailsBinding binding;
    HomeMainActivity homeMainActivity;
    YouTubePlayerView youTubePlayerView;
    ContentFeedDetailAdapter contentFeedDetailAdapter;
    RecyclerView recyclerView;
    String videoId = "";
    ProgressBar progressBarr;
    TextView textView;
    LinearLayout linearLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_feed_details, container, false);
        View view = binding.getRoot();
        youTubePlayerView = binding.youtubePlayerView;

        progressBarr = view.findViewById(R.id.progressBar);
        linearLayout=view.findViewById(R.id.linearLayoutContentfeeddetail);
        textView = view.findViewById(R.id.pbText);
        recyclerView = view.findViewById(R.id.contentDetailRecycler);

        contentFeedApi();
        return view;
    }

    public ContentFeedDetailFragment() {

    }

    public static ContentFeedDetailFragment newInstance(String name) {
        ContentFeedDetailFragment fragment = new ContentFeedDetailFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    public void contentFeedApi() {

        String activityId = FuroPrefs.getString(getContext(), "id");
        ContentFeedDetailRequest contentFeedDetailRequest = new ContentFeedDetailRequest();
        contentFeedDetailRequest.setActivityId(activityId);

        Util.isInternetConnected(getActivity());
        linearLayout.setVisibility(View.VISIBLE);
        progressBarr.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        RestClient.activityDetail(contentFeedDetailRequest, new Callback<ContentFeedDetailResponse>() {
            @Override
            public void onResponse(Call<ContentFeedDetailResponse> call, Response<ContentFeedDetailResponse> response) {
                linearLayout.setVisibility(View.GONE);
                progressBarr.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);

                if (response != null) {
                    if (response.body() != null) {
                        List<com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.Body> bodyList = response.body().getActivityDetails().get(0).getBody();
                        contentFeedDetailAdapter = new ContentFeedDetailAdapter(bodyList, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(contentFeedDetailAdapter);


                    }


                }


            }

            @Override
            public void onFailure(Call<ContentFeedDetailResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, "", Toast.LENGTH_SHORT).show();

            }
        });


    }


}
*/
