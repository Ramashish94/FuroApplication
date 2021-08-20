package com.app.furoapp.fragment.community;

import android.content.Context;
import android.content.Intent;
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
import com.app.furoapp.adapter.CommunitiesListAdapter;
import com.app.furoapp.databinding.FragmentAllCommunitiesBinding;
import com.app.furoapp.model.AllCommunityModel;
import com.app.furoapp.model.allCommunity.AllCommunitiesResponse;
import com.app.furoapp.model.allCommunity.Community;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.furoapp.enums.EnumConstants.HOME_COMMUNITIES_CHALLENGE_DETAILS;

public class AllCommunitiesFragment extends Fragment {
    RecyclerView recyclerView;
    List<AllCommunityModel> allCommunityModelList = new ArrayList<>();
    CommunitiesListAdapter communitiesListAdapter;

    TextView textView;
    GifView pGif;
    LinearLayout linearLayout;
    HomeMainActivity homeMainActivity;
    FragmentAllCommunitiesBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_communities, container, false);
        View view = binding.getRoot();
        setOnClickListeners();
        boolean mUserVisibleHint = true;
        setUserVisibleHint(false);

        linearLayout = view.findViewById(R.id.linearLayoutALLCommunity);
        textView = view.findViewById(R.id.pbText);
        recyclerView = view.findViewById(R.id.rvAllCommunitiesChallenges);
        pGif = view.findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.run);

        pGif.setVisibility(View.VISIBLE);
        allCommunity();
        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    public AllCommunitiesFragment() {

    }

    public static AllCommunitiesFragment newInstance(String name) {
        AllCommunitiesFragment fragment = new AllCommunitiesFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOnClickListeners() {

    }

    public void allCommunity() {
        Util.isInternetConnected(getContext());
        linearLayout.setVisibility(View.VISIBLE);
        pGif.setVisibility(View.VISIBLE);
        /*  textView.setVisibility(View.VISIBLE);*/
        RestClient.userAllCommunities(FuroPrefs.getString(getActivity(), Constants.Get_ACCESS_TOKEN), new Callback<AllCommunitiesResponse>() {
            @Override
            public void onResponse(Call<AllCommunitiesResponse> call, Response<AllCommunitiesResponse> response) {
                linearLayout.setVisibility(View.GONE);
                pGif.setVisibility(View.GONE);
                /*textView.setVisibility(View.GONE);*/
                if (response != null) {
                    if (response.body() != null) {


                        List<Community> communities = response.body().getCommunities();

                        communitiesListAdapter = new CommunitiesListAdapter(communities, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(communitiesListAdapter);
                        communitiesListAdapter.setAllCommunityModels(new CommunitiesListAdapter.AllCommunityInterface() {
                            @Override
                            public void allCommunityItem(int id, String communityName) {

                                Bundle bundle = new Bundle();
                                FuroPrefs.putString(getActivity(), "community_id", String.valueOf(id));
                                FuroPrefs.putString(getActivity(), "communityName", communityName);
                                Intent intent = new Intent(getContext(), CommunityChallengeDetailActivity.class);
                                startActivity(intent);
                                //  homeMainActivity.setDisplayFragment(HOME_COMMUNITIES_CHALLENGE_DETAILS, bundle);
                            }
                        });


                    }

                }


            }

            @Override
            public void onFailure(Call<AllCommunitiesResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });


    }
}




