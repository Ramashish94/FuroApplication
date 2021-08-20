package com.app.furoapp.fragment.challenges;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.ClubChallenegeDetailActivity;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.adapter.ClubChallengesAdapter;
import com.app.furoapp.databinding.FragmentHomeOpenCloseChallengesBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.model.ClubChallengeModel;
import com.app.furoapp.model.clubChallenge.Club;
import com.app.furoapp.model.clubChallenge.ClubChallengeResponse;
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

import static com.app.furoapp.enums.EnumConstants.HOME_CHALLENGE_DETAILS_FRAGMENT;

public class ClubChallengeFragment extends Fragment {
    RecyclerView recyclerView;
    private ImageView imageView;
    List<ClubChallengeModel> clubChallengeModelList = new ArrayList<>();
    GifView pGif;
    TextView textView;
    LinearLayout linearLayout;
    HomeMainActivity homeMainActivity;
    ClubChallengesAdapter clubChallengesAdapter;

    FragmentHomeOpenCloseChallengesBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home_open_close_challenges, container, false);
        View view = binding.getRoot();
        setOnClickListeners();

        imageView = binding.ivBackBtnnewComminity;
        pGif = view.findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.sqats);

        pGif.setVisibility(View.VISIBLE);
        linearLayout = view.findViewById(R.id.linearLayouttOpenclose);
        textView = view.findViewById(R.id.pbText);
        recyclerView = view.findViewById(R.id.rvOpenCloseChallenges);

        clubChallenges();

       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeMainActivity.setDisplayFragment(EnumConstants.HOME_CHALLENGE_CHOOSE,null);
            }
        });*/

       /* for (int i = 0; i <= 20; i++) {
            ClubChallengeModel clubChallengeModel = new ClubChallengeModel();
            clubChallengeModel.setHeading("The 100km Running club" + i);
            clubChallengeModel.setTitle("The 100km Running club" + i);
            clubChallengeModelList.add(clubChallengeModel);



        }*/
        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    public ClubChallengeFragment() {

    }


    public static ClubChallengeFragment newInstance(String name) {
        ClubChallengeFragment fragment = new ClubChallengeFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOnClickListeners() {


    }

    public void clubChallenges() {
        Util.isInternetConnected(getContext());
        linearLayout.setVisibility(View.VISIBLE);
        pGif.setVisibility(View.VISIBLE);
        /* textView.setVisibility(View.VISIBLE);*/
        RestClient.myClubChallenges(FuroPrefs.getString(getActivity(), Constants.Get_ACCESS_TOKEN), new Callback<ClubChallengeResponse>() {
            @Override
            public void onResponse(Call<ClubChallengeResponse> call, Response<ClubChallengeResponse> response) {
                linearLayout.setVisibility(View.GONE);
                pGif.setVisibility(View.VISIBLE);
                /* textView.setVisibility(View.VISIBLE);*/
                if (response != null) {
                    if (response.body() != null) {

                        List<Club> clubList = response.body().getClubs();
                        clubChallengesAdapter = new ClubChallengesAdapter(clubList, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(clubChallengesAdapter);
                        clubChallengesAdapter.setClubChallengeInterface(new ClubChallengesAdapter.ClubChallengeInterface() {
                            @Override
                            public void clubChallengeItem(int id) {

                                String clubId = String.valueOf(id);

                                FuroPrefs.putString(getContext(), "clubId", String.valueOf(clubId));
                                Bundle bundle = new Bundle();
                                Intent intent = new Intent(getContext(), ClubChallenegeDetailActivity.class);
                                startActivity(intent);
                            }
                        });


                    }

                }

            }

            @Override
            public void onFailure(Call<ClubChallengeResponse> call, Throwable t) {

            }
        });


    }
}



