package com.app.furoapp.fragment.community;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.app.furoapp.adapter.CommunityChallangeSelectChallangeAdapter;
import com.app.furoapp.databinding.FragmentCommunitySelectedChallengeListBinding;
import com.app.furoapp.model.communityChallange.Challenge;
import com.app.furoapp.model.communityChallange.CommunityChallangeRequest;
import com.app.furoapp.model.communityChallange.CommunityChallangeResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityChallengesSelectChallangeListFragmen extends Fragment {

    HomeMainActivity homeMainActivity;
    RecyclerView recyclerView;
    List<Challenge> challenges;
    private String userId, communityId;
    private TextView comunityName, noChallanges;
    FragmentCommunitySelectedChallengeListBinding binding;
    CommunityChallangeSelectChallangeAdapter communityChallangeSelectChallangeAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_community_selected_challenge_list, container, false);
        View view = binding.getRoot();
        comunityName = view.findViewById(R.id.tvToolbarTitle);
        noChallanges = view.findViewById(R.id.nochallanges);
        recyclerView = binding.rvCommunitiesChallengesList;
        userId = FuroPrefs.getString(getContext(), "loginUserId");
        communityId = FuroPrefs.getString(getContext(), "community_id");

        String community = FuroPrefs.getString(getContext(), "communityName");
        comunityName.setText(community);
        communityChallange();

        return view;
    }

    public CommunityChallengesSelectChallangeListFragmen() {

    }

    public static CommunityChallengesSelectChallangeListFragmen newInstance(String name) {
        CommunityChallengesSelectChallangeListFragmen fragment = new CommunityChallengesSelectChallangeListFragmen();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    public void communityChallange() {


        if (userId != null && communityId != null) {
            CommunityChallangeRequest communityChallangeRequest = new CommunityChallangeRequest();
            communityChallangeRequest.setCommunityId(communityId);
            communityChallangeRequest.setUserId(userId);

            Util.isInternetConnected(getContext());
            Util.showProgressDialog(getContext());
            RestClient.userCommunityChallange(FuroPrefs.getString(getActivity(), Constants.Get_ACCESS_TOKEN), communityChallangeRequest, new Callback<CommunityChallangeResponse>() {
                @Override
                public void onResponse(Call<CommunityChallangeResponse> call, Response<CommunityChallangeResponse> response) {
                    Util.dismissProgressDialog();
                    if (response != null) {
                        if (response.body() != null && response.body().getChallenges().size() > 0) {
                            challenges = response.body().getChallenges();
                            communityChallangeSelectChallangeAdapter = new CommunityChallangeSelectChallangeAdapter(challenges, getContext());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(communityChallangeSelectChallangeAdapter);


                        } else {

                            noChallanges.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<CommunityChallangeResponse> call, Throwable t) {
                    Toast.makeText(homeMainActivity, "Something went wrong !!", Toast.LENGTH_SHORT).show();

                }
            });


        }


    }


}





/*

    private ArrayList<ChallengeItemModel> getListOfChallenges() {
        ArrayList<ChallengeItemModel> listOfChallenges = new ArrayList<ChallengeItemModel>();

        Integer arrayOfChallenges[] = new Integer[]{R.drawable.ic_running,
                R.drawable.rate_compete,
                R.drawable.roaddistance,
                R.drawable.time_taken,
                R.drawable.rate_compete,
                R.drawable.roaddistance,
                R.drawable.time_taken,


        };

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < arrayOfChallenges.length; j++) {

                ChallengeItemModel model = new ChallengeItemModel();

                model.imageName = arrayOfChallenges[j];
                listOfChallenges.add(model);


            }
            return listOfChallenges;


        }


        return null;
    }

*/

   /* private void setListAdapter(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.rvCommunitiesChallengesList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CommunityChallangeSelectChallangeAdapter(getListOfChallenges(), getContext()));
     *//*communityChallangeSelectChallangeAdapter.setAllCommunityChallangeSelectChallange(new CommunityChallangeSelectChallangeAdapter.CommunityChallangeSelectChallangeInterface() {
            @Override
            public void allCommunityChallangeSelectChallangeItem(int position) {
                homeMainActivity.setDisplayFragment(EnumConstants.COMMUNITY_CHALLENGE_JOIN_FRAGMENT,    null);
            }

        });*//*
    }*/



