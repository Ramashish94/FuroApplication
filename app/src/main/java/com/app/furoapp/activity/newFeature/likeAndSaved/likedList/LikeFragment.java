package com.app.furoapp.activity.newFeature.likeAndSaved.likedList;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.ContentFeedDetailActivity;
import com.app.furoapp.activity.YoutubePlayerActivity;
import com.app.furoapp.activity.newFeature.likeAndSaved.likedList.likeOnPost.LikeListResponse;
import com.app.furoapp.activity.newFeature.likeAndSaved.likedList.likeOnPost.LikeOnPost;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class LikeFragment extends Fragment implements LikeListAdapter.ContentLikeCallback {
    RecyclerView rvLikeRecy;
    LikeListAdapter likeListAdapter;
    List<LikeOnPost> likeOnPostList = new ArrayList<>();
    private String getAccessToken;
    List<LikeListResponse> likeListResponses = new ArrayList<>();
    private String activityId;

    public LikeFragment() {
        // Required empty public constructor
    }


    public static LikeFragment newInstance(String param1, String param2) {
        LikeFragment fragment = new LikeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        rvLikeRecy = view.findViewById(R.id.rvLikeRecy);
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        setLikeListAdapter();
        getLikeApiCalling();
        return view;
    }


    private void getLikeApiCalling() {
        Util.isInternetConnected(getContext());
        Util.showProgressDialog(getActivity());
        RestClient.likeList(getAccessToken, new Callback<LikeListResponse>() {
            @Override
            public void onResponse(Call<LikeListResponse> call, Response<LikeListResponse> response) {
                Util.dismissProgressDialog();
                if (response != null && response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus() != null) {
                        notifyLikeListAdapter(response.body().getLikeOnPost());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failure" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LikeListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLikeListAdapter() {
        likeListAdapter = new LikeListAdapter(getApplicationContext(), likeOnPostList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvLikeRecy.setLayoutManager(layoutManager);
        rvLikeRecy.setItemAnimator(new DefaultItemAnimator());
        rvLikeRecy.setAdapter(likeListAdapter);
    }

    private void notifyLikeListAdapter(List<LikeOnPost> saved) {
        likeOnPostList.clear();
        likeOnPostList.addAll(saved);
        if (likeOnPostList != null && likeOnPostList.size() > 0) {
            likeListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void contentLikeItem(int videoId) {
        Intent intent = new Intent(getContext(), YoutubePlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void contentLikeItem2(int id) {
        Intent intent = new Intent(getContext(), ContentFeedDetailActivity.class);
        FuroPrefs.putString(getActivity(), "id", String.valueOf(id));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLikeApiCalling();
        setLikeListAdapter();
    }
}