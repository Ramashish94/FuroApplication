package com.app.furoapp.fragment.challenges;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.app.furoapp.adapter.ClubChallengesAdapter;
import com.app.furoapp.adapter.ClubDetailAdapter;
import com.app.furoapp.databinding.FragmentClubChallengesDetailsBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.model.clubChallenge.ClubChallengeResponse;
import com.app.furoapp.model.clubDetails.Club;
import com.app.furoapp.model.clubDetails.ClubDetailRequest;
import com.app.furoapp.model.clubDetails.ClubDetailResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClubChallengeDetailsFragment extends Fragment {
    HomeMainActivity homeMainActivity;
    ImageView imageView, backButton;
    ClubDetailAdapter clubDetailAdapter;
    RecyclerView recyclerView;
    FragmentClubChallengesDetailsBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_club_challenges_details, container, false);
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.clubDetailRecycler);
        imageView = binding.ivBackBtnnewComminity;
        clubChallengesDetail();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeMainActivity.setDisplayFragment(EnumConstants.HOME_CLUB_CHALLENGES, null);

            }
        });


        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    public ClubChallengeDetailsFragment() {

    }

    public static ClubChallengeDetailsFragment newInstance(String name) {
        ClubChallengeDetailsFragment fragment = new ClubChallengeDetailsFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    public void clubChallengesDetail() {

        String ClubId = FuroPrefs.getString(getContext(), "clubId");

        ClubDetailRequest clubDetailRequest = new ClubDetailRequest();
        clubDetailRequest.setClubId(ClubId);
        Util.isInternetConnected(getContext());
        Util.showProgressDialog(getContext());
        RestClient.myClubDetails(clubDetailRequest, new Callback<ClubDetailResponse>() {
            @Override
            public void onResponse(Call<ClubDetailResponse> call, Response<ClubDetailResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {
                    if (response.body() != null) {

                        List<Club> clubList = response.body().getClubs();
                        clubDetailAdapter = new ClubDetailAdapter(clubList, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(clubDetailAdapter);


                    }

                }

            }

            @Override
            public void onFailure(Call<ClubDetailResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, " Something went wrong !!", Toast.LENGTH_SHORT).show();

            }
        });


    }

}





