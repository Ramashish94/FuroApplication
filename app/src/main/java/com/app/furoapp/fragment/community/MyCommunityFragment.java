package com.app.furoapp.fragment.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.adapter.MyCommunityAdapter;
import com.app.furoapp.databinding.FragmentMyCommunitiesBinding;
import com.app.furoapp.model.AllCommunityModel;
import com.app.furoapp.model.myCommunities.Community;
import com.app.furoapp.model.myCommunities.MyCommunitiesRequest;
import com.app.furoapp.model.myCommunities.MyCommunitiesResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCommunityFragment extends Fragment {

    RecyclerView recyclerView;
    List<AllCommunityModel> allCommunityModelList = new ArrayList<>();
    MyCommunityAdapter myCommunityAdapter;
    String user_id;
    HomeMainActivity homeMainActivity;
    FragmentMyCommunitiesBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_communities, container, false);
        View view = binding.getRoot();


        recyclerView = view.findViewById(R.id.rvMyCommunitiesChallenges);
        setOnClickListeners();

        user_id = FuroPrefs.getString(getContext(), "loginUserId");
        myCommunitiesApi();

        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    public MyCommunityFragment() {

    }

    public static MyCommunityFragment newInstance(String name) {
        MyCommunityFragment fragment = new MyCommunityFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOnClickListeners() {

    }

    public void myCommunitiesApi() {


        MyCommunitiesRequest myCommunitiesRequest = new MyCommunitiesRequest();
        myCommunitiesRequest.setUserId(user_id);


        Util.isInternetConnected(getContext());
        Util.showProgressDialog(getContext());
        RestClient.userMyCommunities(FuroPrefs.getString(getActivity(), Constants.Get_ACCESS_TOKEN), myCommunitiesRequest, new Callback<MyCommunitiesResponse>() {
            @Override
            public void onResponse(Call<MyCommunitiesResponse> call, Response<MyCommunitiesResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        List<Community> myCommunities = response.body().getCommunities();
                        if (myCommunities != null) {
                            List<Community> communities = response.body().getCommunities();
                            myCommunityAdapter = new MyCommunityAdapter(communities, getContext());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(myCommunityAdapter);

                            myCommunityAdapter.setMyCommunity(new MyCommunityAdapter.MyCommunityInterface() {
                                @Override
                                public void MyCommunityItem(int id, String communityName) {
                                    FuroPrefs.putString(getActivity(), "community_id", String.valueOf(id));
                                    FuroPrefs.putString(getActivity(), "communityName", communityName);
                                    Intent intent = new Intent(getContext(), CommunityChallengeDetailActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }


                    }


                }


            }

            @Override
            public void onFailure(Call<MyCommunitiesResponse> call, Throwable t) {

            }
        });


    }
}





