package com.app.furoapp.fragment.challenges;

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
import com.app.furoapp.activity.ChallengeActivityVideoDraft;
import com.app.furoapp.activity.DraftViewAllActivity;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.MapChallengeActivity;
import com.app.furoapp.activity.MapChallengeActivityBy;
import com.app.furoapp.activity.MapChallengeActivityDraft;
import com.app.furoapp.activity.ViewAllActivityBy;
import com.app.furoapp.activity.ViewAllActivityFor;
import com.app.furoapp.activity.challengeRecieve.ChallengeByUserandChallengeForUserDetailActivity;
import com.app.furoapp.activity.challengeRecieve.ChallengeForYouDetailActivity;
import com.app.furoapp.adapter.ChallangesForYouAdapter;
import com.app.furoapp.adapter.DraftAdapter;
import com.app.furoapp.adapter.OpenAndChallengeCloseAdapter;
import com.app.furoapp.databinding.RowOpenCloseLoaderBinding;
import com.app.furoapp.model.challengeByUser.ChallengeByUser;
import com.app.furoapp.model.challengeByUser.ChallengeByUserResponse;
import com.app.furoapp.model.challengeByUser.SendChallenge;
import com.app.furoapp.model.challengeforyouRecieve.ChallenegeForYouRecieve;
import com.app.furoapp.model.challengeforyouRecieve.ChallenegeForYouRecieveRequest;
import com.app.furoapp.model.challengeforyouRecieve.ReceiveChallenge;
import com.app.furoapp.model.draft.DraftChallenge;
import com.app.furoapp.model.draft.DraftRequest;
import com.app.furoapp.model.draft.DraftResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class OpenAndCloseChallengeFragment extends Fragment {
    String useridopenandclose;
    HomeMainActivity homeMainActivity;
    TextView textViewloadmoreby, textViewloadmorefor, draftloadmore;
    GifView pGif;
    LinearLayout linearLayout;
    DraftAdapter draftAdapter;
    RowOpenCloseLoaderBinding binding;
    RecyclerView recyclerView, recyclerView1, recyclerViewdraft;
    ChallangesForYouAdapter challangesForYouAdapter;
    OpenAndChallengeCloseAdapter openAndChallengeCloseAdapter;
    TextView youHaveNotChallange, Youhavechallange, draft;
    int userIdChallenge;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.row_open_close_loader, container, false);
        View view = binding.getRoot();


        useridopenandclose = FuroPrefs.getString(getContext(), "loginUserId");
        recyclerView = view.findViewById(R.id.ChallengeByYou);
        recyclerViewdraft = view.findViewById(R.id.challengeDraftrecycler);
        draftloadmore = view.findViewById(R.id.loadmorefordraft);
        textViewloadmoreby = view.findViewById(R.id.loadmoreBy);
        textViewloadmorefor = view.findViewById(R.id.loadmorefor);
        linearLayout = view.findViewById(R.id.linearLayoutALLCommunity);
        Youhavechallange = view.findViewById(R.id.youhavechallange);
        recyclerView1 = view.findViewById(R.id.challengeForyou);
        draft = view.findViewById(R.id.youhavechallangedraft);
        youHaveNotChallange = view.findViewById(R.id.youhavenotchallange);
        pGif = view.findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.sqats);


        userIdChallenge = FuroPrefs.getInt(getApplicationContext(), "challengefuroid", 0);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        draftloadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DraftViewAllActivity.class);
                startActivity(intent);
            }
        });

        textViewloadmoreby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewAllActivityBy.class);
                startActivity(intent);
            }
        });

        textViewloadmorefor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewAllActivityFor.class);
                startActivity(intent);
            }
        });
        getDataChallenge();
        getDChallengeForYou();
        getDChallengeDraft();
    }

    public OpenAndCloseChallengeFragment() {

    }

    public static OpenAndCloseChallengeFragment newInstance(String name) {
        OpenAndCloseChallengeFragment fragment = new OpenAndCloseChallengeFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    public void getDChallengeForYou() {


        ChallenegeForYouRecieveRequest challenegeForYouRecieveRequest = new ChallenegeForYouRecieveRequest();
        challenegeForYouRecieveRequest.setUserId(useridopenandclose);


        Util.isInternetConnected(getContext());
        linearLayout.setVisibility(View.VISIBLE);
        pGif.setVisibility(View.VISIBLE);

        RestClient.challenegeForYouRecieve(challenegeForYouRecieveRequest, new Callback<ChallenegeForYouRecieve>() {
            @Override
            public void onResponse(Call<ChallenegeForYouRecieve> call, Response<ChallenegeForYouRecieve> response) {
                pGif.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                if (response != null && response.body() != null) {

                    if (response.body().getReceiveChallenge() != null && response.body().getReceiveChallenge().size() >= 2) {

                        List<ReceiveChallenge> sreceiveChallenges1 = response.body().getReceiveChallenge().subList(0, 2);


                        challangesForYouAdapter = new ChallangesForYouAdapter(sreceiveChallenges1, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView1.setLayoutManager(layoutManager);
                        recyclerView1.setItemAnimator(new DefaultItemAnimator());
                        recyclerView1.setAdapter(challangesForYouAdapter);
                        challangesForYouAdapter.setChallengeForYouItemList(new ChallangesForYouAdapter.ChallengeForYouInterface() {
                            @Override
                            public void challengeForYouItem(String idChallenge, int position) {

                                String challTypenew = response.body().getReceiveChallenge().get(position).getChallengeType();
                                if (challTypenew.equalsIgnoreCase("map")) {
                                    Intent intent = new Intent(getActivity(), MapChallengeActivity.class);
                                    //  String Challengeidnew = idChallenge;
                                    intent.putExtra("userIdChallenge", idChallenge);
                                    startActivity(intent);
                                } else if (challTypenew.equalsIgnoreCase("video")) {
                                    Intent intent = new Intent(getActivity(), ChallengeForYouDetailActivity.class);
                                    // String Challengeidnew = idChallenge;
                                    intent.putExtra("userIdChallenge", idChallenge);
                                    startActivity(intent);


                                }
                            }


                        });
                    } else {

                    }


                } else {
                    Youhavechallange.setVisibility(View.VISIBLE);
                }


            }


            @Override
            public void onFailure(Call<ChallenegeForYouRecieve> call, Throwable t) {
                Toast.makeText(homeMainActivity, "No Internet connection !!", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void getDataChallenge() {
        String challengeByYou = FuroPrefs.getString(getContext(), "loginUserId");

        ChallengeByUser challengeByUser = new ChallengeByUser();
        challengeByUser.setUserId(challengeByYou);


        Util.isInternetConnected(getContext());
        pGif.setVisibility(View.VISIBLE);
        RestClient.userChallangeByUser(challengeByUser, new Callback<ChallengeByUserResponse>() {
            @Override
            public void onResponse(Call<ChallengeByUserResponse> call, Response<ChallengeByUserResponse> response) {
                pGif.setVisibility(View.GONE);
                if (response != null && response.body() != null) {
                    if (response.body().getSendChallenge() != null && response.body().getSendChallenge().size() >= 2) {


                        List<SendChallenge> sendChallenges1 = response.body().getSendChallenge().subList(0, 2);

                        openAndChallengeCloseAdapter = new OpenAndChallengeCloseAdapter(sendChallenges1, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(openAndChallengeCloseAdapter);
                        openAndChallengeCloseAdapter.setChallengeByYouItemList(new OpenAndChallengeCloseAdapter.ChallengeByYouInterface() {
                            @Override
                            public void challengeForYouItem(String challengeId, int position) {

                                String challTypeby = response.body().getSendChallenge().get(position).getChallengeType();
                                if (challTypeby.equalsIgnoreCase("map")) {
                                    Intent intent = new Intent(getActivity(), MapChallengeActivityBy.class);
                                    //  String Challengeid = challengeId;
                                    FuroPrefs.putString(getApplicationContext(), "challengeidforshare", challengeId);
                                    intent.putExtra("userIdChallengeByMap", challengeId);
                                    startActivity(intent);
                                } else if (challTypeby.equalsIgnoreCase("video")) {
                                    Intent intent = new Intent(getActivity(), ChallengeByUserandChallengeForUserDetailActivity.class);
                                    // String Challengeid = challengeId;
                                    FuroPrefs.putString(getApplicationContext(), "challengeidforshare", challengeId);
                                    intent.putExtra("userIdChallengeByVideo", challengeId);
                                    startActivity(intent);


                                }


                            }
                        });


                    } else {


                    }

                } else {
                    youHaveNotChallange.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<ChallengeByUserResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, "Something went wrong !", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void getDChallengeDraft() {


        DraftRequest draftRequest = new DraftRequest();
        draftRequest.setUserId(useridopenandclose);


        Util.isInternetConnected(getContext());
        linearLayout.setVisibility(View.VISIBLE);
        pGif.setVisibility(View.VISIBLE);

        RestClient.userdraft(draftRequest, new Callback<DraftResponse>() {
            @Override
            public void onResponse(Call<DraftResponse> call, Response<DraftResponse> response) {
                pGif.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                if (response != null && response.body() != null) {

                    if (response.body().getDraftChallenges() != null && response.body().getDraftChallenges().size() >= 2) {
                        List<DraftChallenge> draftChallenges = response.body().getDraftChallenges().subList(0, 2);

                        draftAdapter = new DraftAdapter(draftChallenges, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerViewdraft.setLayoutManager(layoutManager);
                        recyclerViewdraft.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewdraft.setAdapter(draftAdapter);

                        draftAdapter.setDraftItemList(new DraftAdapter.DraftInterface() {
                            @Override
                            public void draftItem(String IdChallengedraft, int position) {

                                String challTypedraft = response.body().getDraftChallenges().get(position).getChallengeType();
                                if (challTypedraft.equalsIgnoreCase("map")) {
                                    String Challengeiddraft = IdChallengedraft;
                                    FuroPrefs.putString(getApplicationContext(), "challengeidforshare", Challengeiddraft);
                                    Intent intent = new Intent(getActivity(), MapChallengeActivityDraft.class);


                                    intent.putExtra("userIdChallenge", IdChallengedraft);
                                    startActivity(intent);
                                } else if (challTypedraft.equalsIgnoreCase("video")) {
                                    String Challengeiddraft = IdChallengedraft;
                                    FuroPrefs.putString(getApplicationContext(), "challengeidforshare", Challengeiddraft);
                                    Intent intent = new Intent(getActivity(), ChallengeActivityVideoDraft.class);


                                    intent.putExtra("userIdChallenge", IdChallengedraft);
                                    startActivity(intent);


                                }

                            }
                        });


                    } else {

                    }

                } else {
                    draft.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<DraftResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, "Something went wrong  !!", Toast.LENGTH_SHORT).show();

            }
        });


    }

}




