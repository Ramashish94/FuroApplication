package com.app.furoapp.activity.newFeature.likeAndSaved.SavedList;

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
import com.app.furoapp.activity.newFeature.likeAndSaved.SavedList.saveOnPost.SavedListResponse;
import com.app.furoapp.activity.newFeature.likeAndSaved.SavedList.saveOnPost.SavedOnPost;
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

public class SavedFragment extends Fragment implements SavedListAdapter.ContentSavedCallback {
    public RecyclerView rvSavedRecycler;
    SavedListAdapter savedListAdapter;
    List<SavedOnPost> savedOnPostList = new ArrayList<>();
    public String getAccessToken;
    public String activityId;
   List<SavedListResponse >savedListResponseList=new ArrayList<>();
    int getAdapterCurrentPos=0;



    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance() {
        SavedFragment fragment = new SavedFragment();
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
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        rvSavedRecycler = view.findViewById(R.id.rvSavedRecycler);
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        setSavedListAdapter();
        getSavedApiCalling();
        return view;
    }

    private void getSavedApiCalling() {
        Util.isInternetConnected(getContext());
        Util.showProgressDialog(getActivity());
        RestClient.saveList(getAccessToken, new Callback<SavedListResponse>() {
            @Override
            public void onResponse(Call<SavedListResponse> call, Response<SavedListResponse> response) {
                Util.dismissProgressDialog();
                if (response != null && response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus()!=null){
                        notifySavedListAdapter(response.body().getSavedOnPost());
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Failure" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SavedListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSavedListAdapter() {
        savedListAdapter = new SavedListAdapter(getApplicationContext(), savedOnPostList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSavedRecycler.setLayoutManager(layoutManager);
        rvSavedRecycler.setItemAnimator(new DefaultItemAnimator());
        rvSavedRecycler.setAdapter(savedListAdapter);

        rvSavedRecycler.scrollToPosition(getAdapterCurrentPos);
    }
    private void notifySavedListAdapter(List<SavedOnPost> savedOnPost) {
        savedOnPostList.clear();
        savedOnPostList.addAll(savedOnPost);
        if (savedOnPostList != null && savedOnPostList.size() > 0) {
            savedListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void contentSavedItem(int pos,int videoId) {
        getAdapterCurrentPos=pos;
        Intent intent = new Intent(getContext(), YoutubePlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void contentSavedItem2(int pos,int id) {
        getAdapterCurrentPos=pos;
        Intent intent = new Intent(getContext(), ContentFeedDetailActivity.class);
        FuroPrefs.putString(getActivity(), "id", String.valueOf(id));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSavedApiCalling();
        setSavedListAdapter();

    }
}