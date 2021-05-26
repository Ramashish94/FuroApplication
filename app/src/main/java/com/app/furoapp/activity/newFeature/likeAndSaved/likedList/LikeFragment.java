package com.app.furoapp.activity.newFeature.likeAndSaved.likedList;

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
import com.app.furoapp.activity.newFeature.likeAndSaved.likedList.likeOnPost.LikeListRequest;
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

public class LikeFragment extends Fragment {
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
        getApiCalling();
        return view;
    }


    private void getApiCalling() {

        activityId = FuroPrefs.getString(getApplicationContext(), "id");
        LikeListRequest likeListRequest = new LikeListRequest();
        likeListRequest.setActivityId(activityId);

        Util.isInternetConnected(getContext());
        Util.showProgressDialog(getActivity());

        RestClient.likeList(getAccessToken, likeListRequest,new Callback<LikeListResponse>() {
            @Override
            public void onResponse(Call<LikeListResponse> call, Response<LikeListResponse> response) {
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
        likeListAdapter = new LikeListAdapter(getApplicationContext(), likeOnPostList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvLikeRecy.setLayoutManager(layoutManager);
        rvLikeRecy.setItemAnimator(new DefaultItemAnimator());
        rvLikeRecy.setAdapter(likeListAdapter);
    }

    private void notifyLikeListAdapter(List<LikeOnPost> saved) {
        likeOnPostList.clear();
        likeOnPostList.addAll(saved);
        if (likeListResponses != null && likeListResponses.size() > 0) {
            likeListAdapter.notifyDataSetChanged();
        }
    }
}