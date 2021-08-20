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
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityChallengesDetailFragment extends Fragment {
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_community_challenges_details, container, false);
        View view = binding.getRoot();
        coomunityName = view.findViewById(R.id.tvToolbarTitle);
        textViewRecord = view.findViewById(R.id.recordsfound);

        String community = FuroPrefs.getString(getContext(), "communityName");
        usserCommunityId = FuroPrefs.getString(getContext(), "community_id");
        userId = FuroPrefs.getString(getContext(), "loginUserId");
        setOnClickListeners();
        setOnClickListenersTwo();

        coomunityName.setText(community);
        recyclerView = view.findViewById(R.id.challengeLeaderBoard);
        recyclerView1 = view.findViewById(R.id.communityDetailRecycler);


        communityDetail();
        return view;

    }

    public CommunityChallengesDetailFragment() {

    }

    public static CommunityChallengesDetailFragment newInstance(String name) {
        CommunityChallengesDetailFragment fragment = new CommunityChallengesDetailFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOnClickListeners() {
        binding.tvChallengesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Toast.makeText(homeMainActivity, "Coming soon", Toast.LENGTH_SHORT).show();
                // homeMainActivity.setDisplayFragment(COMMUNITY_CHALLENGES_SELECT_CHALLENGES_LIST_FRAGMENT, bundle);

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

                Util.isInternetConnected(getActivity());
                Util.showProgressDialog(getContext());
                RestClient.userCommunitiesJoin(FuroPrefs.getString(getActivity(), Constants.Get_ACCESS_TOKEN), communitiesJoinRequest, new Callback<CommunitiesJoinResponse>() {
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
                                            Toast.makeText(getActivity(), " Already join", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            } else {
                                Toast.makeText(homeMainActivity, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CommunitiesJoinResponse> call, Throwable t) {
                        Toast.makeText(homeMainActivity, "Check your Network", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }

    public void communityDetail() {
        CommunityDetailRequest communityDetailRequest = new CommunityDetailRequest();
        communityDetailRequest.setCommunityId(usserCommunityId);
        communityDetailRequest.setUserId(userId);
        Util.isInternetConnected(getContext());
        Util.showProgressDialog(getContext());
        RestClient.userCommunityDetail(FuroPrefs.getString(getActivity(), Constants.Get_ACCESS_TOKEN), communityDetailRequest, new Callback<CommunityDetailResponse>() {
            @Override
            public void onResponse(Call<CommunityDetailResponse> call, Response<CommunityDetailResponse> response) {
                Util.dismissProgressDialog();

                if (response != null) {
                    if (response.body() != null) {
                        String joined = response.body().getCommunities().getIsJoined();

                        List<Communities> communitiesList = Collections.singletonList(response.body().getCommunities());
                        myCommunityDetailAdapter = new MyCommunityDetailAdapter(communitiesList, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
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
                                    Toast.makeText(homeMainActivity, "you are already join", Toast.LENGTH_SHORT).show();

                                }
                            });


                        } else if (joined.equalsIgnoreCase("0")) {
                            binding.tvJoinBtn.setText("+Join");
                        }

                    }
                    if (response.body().getCommunities().getLeadership() != null) {
                        List<Leadership> leaderships = response.body().getCommunities().getLeadership();
                        communityLeaderBoardAdapter = new CommunityLeaderBoardAdapter(leaderships, getContext());
                        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
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
                Toast.makeText(homeMainActivity, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void leaderBoardCommunity() {


    }


}








