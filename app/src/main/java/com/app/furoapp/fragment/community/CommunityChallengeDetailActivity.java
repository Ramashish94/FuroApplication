package com.app.furoapp.fragment.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.CommunityMembers;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.adapter.CommunityLeaderBoardAdapter;
import com.app.furoapp.adapter.FriendLeaderBoardAdapter;
import com.app.furoapp.adapter.MyCommunityDetailAdapter;
import com.app.furoapp.databinding.FragmentCommunityChallengesDetailsBinding;
import com.app.furoapp.model.LeaderFriendModel;
import com.app.furoapp.model.communityJoin.CommunitiesJoinRequest;
import com.app.furoapp.model.communityJoin.CommunitiesJoinResponse;
import com.app.furoapp.model.communitydetail.Communities;
import com.app.furoapp.model.communitydetail.CommunityDetailRequest;
import com.app.furoapp.model.communitydetail.CommunityDetailResponse;
import com.app.furoapp.model.communitydetail.Leadership;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityChallengeDetailActivity extends AppCompatActivity {
    List<LeaderFriendModel> leaderFriendModelList = new ArrayList<>();
    String usserCommunityId;
    TextView textViewRecord;
    private String userId;
    HomeMainActivity homeMainActivity;
    FragmentCommunityChallengesDetailsBinding binding;
    RecyclerView recyclerView, recyclerView1;
    FriendLeaderBoardAdapter friendLeaderBoardAdapter;
    MyCommunityDetailAdapter myCommunityDetailAdapter;
    CommunityLeaderBoardAdapter communityLeaderBoardAdapter;
    private TextView coomunityName;
    ImageView imageViewBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_community_challenges_details);
        View view = binding.getRoot();
        coomunityName = view.findViewById(R.id.tvToolbarTitle);
        textViewRecord = view.findViewById(R.id.recordsfound);
        imageViewBack = view.findViewById(R.id.BackButton);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String community = FuroPrefs.getString(getApplicationContext(), "communityName");
        usserCommunityId = FuroPrefs.getString(getApplicationContext(), "community_id");
        userId = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        setOnClickListeners();
        setOnClickListenersTwo();

        coomunityName.setText(community);
        recyclerView = view.findViewById(R.id.challengeLeaderBoard);
        recyclerView1 = view.findViewById(R.id.communityDetailRecycler);
        communityDetail();

    }


    private void setOnClickListeners() {
        binding.tvChallengesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Toast.makeText(CommunityChallengeDetailActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void setOnClickListenersTwo() {
        binding.tvJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CommunitiesJoinRequest communitiesJoinRequest = new CommunitiesJoinRequest();
                communitiesJoinRequest.setCommunityId(usserCommunityId);
                communitiesJoinRequest.setUserId(userId);

                Util.isInternetConnected(CommunityChallengeDetailActivity.this);
                Util.showProgressDialog(CommunityChallengeDetailActivity.this);
                RestClient.userCommunitiesJoin(communitiesJoinRequest, new Callback<CommunitiesJoinResponse>() {
                    @Override
                    public void onResponse(Call<CommunitiesJoinResponse> call, Response<CommunitiesJoinResponse> response) {
                        Util.dismissProgressDialog();
                        if (response != null) {
                            if (response.body() != null) {
                                if (response.body().getCommunity().getIsJoined().equalsIgnoreCase("0")) {
                                    binding.tvJoinBtn.setText("+Join");

                                } else if (response.body().getCommunity().getIsJoined().equalsIgnoreCase("1")) {
                                    binding.tvJoinBtn.setText("Joined");
                                    binding.tvJoinBtn.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
                                    binding.tvJoinBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(getApplicationContext(), " You have already joined the community.", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            } else {
                                Toast.makeText(
                                        CommunityChallengeDetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CommunitiesJoinResponse> call, Throwable t) {
                        Toast.makeText(CommunityChallengeDetailActivity.this, "Check your Network", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }

    public void communityDetail() {
        CommunityDetailRequest communityDetailRequest = new CommunityDetailRequest();
        communityDetailRequest.setCommunityId(usserCommunityId);
        communityDetailRequest.setUserId(userId);
        Util.isInternetConnected(CommunityChallengeDetailActivity.this);
        Util.showProgressDialog(CommunityChallengeDetailActivity.this);
        RestClient.userCommunityDetail(communityDetailRequest, new Callback<CommunityDetailResponse>() {
            @Override
            public void onResponse(Call<CommunityDetailResponse> call, Response<CommunityDetailResponse> response) {
                Util.dismissProgressDialog();

                if (response != null) {
                    if (response.body() != null) {
                        String joined = response.body().getCommunities().getIsJoined();

                        List<Communities> communitiesList = Collections.singletonList(response.body().getCommunities());
                        myCommunityDetailAdapter = new MyCommunityDetailAdapter(communitiesList, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView1.setLayoutManager(layoutManager);
                        recyclerView1.setItemAnimator(new DefaultItemAnimator());
                        recyclerView1.setAdapter(myCommunityDetailAdapter);
                        if (joined.equalsIgnoreCase("1")) {
                            binding.tvJoinBtn.setText("Already Joined");
                            //  textView.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
                            binding.tvJoinBtn.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
                            binding.tvJoinBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(CommunityChallengeDetailActivity.this, "You are already joined", Toast.LENGTH_SHORT).show();

                                }
                            });




                        } else if (joined.equalsIgnoreCase("0")) {
                            binding.tvJoinBtn.setText("+Join");
                        }

                    }
                    if (response.body().getCommunities().getLeadership() != null) {
                        List<Leadership> leaderships = response.body().getCommunities().getLeadership();
                        communityLeaderBoardAdapter = new CommunityLeaderBoardAdapter(leaderships, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager1);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(communityLeaderBoardAdapter);
                    } else {
                        textViewRecord.setVisibility(View.VISIBLE);

                    }

                }


            }


            @Override
            public void onFailure(Call<CommunityDetailResponse> call, Throwable t) {
                Toast.makeText(CommunityChallengeDetailActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });


    }


}








