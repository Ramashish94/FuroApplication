package com.app.furoapp.fragment.challenges;

import android.content.Context;
import android.content.Intent;
import android.media.MicrophoneInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.furoapp.R;
import com.app.furoapp.activity.AboutContestChallengeActivity;
import com.app.furoapp.activity.CreateChallengeActivity;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.adapter.ExpandableAdapter;
import com.app.furoapp.databinding.FragmentHomeChooseChallengeBinding;
import com.app.furoapp.model.chooseChallange.Challenge;
import com.app.furoapp.model.chooseChallange.ChallengeResponse;
import com.app.furoapp.model.chooseChallange.SelectChallangeResponse;
import com.app.furoapp.model.chooseChallange.Subcategory;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseChallengeFragment extends Fragment {

    HomeMainActivity homeMainActivity;
    ProgressBar progressBarr;
    TextView textView;
    LinearLayout linearLayout;
    ImageView imageViewLockdown;
    GifView gifView;

    FragmentHomeChooseChallengeBinding binding;


    ExpandableListView expandableListView;
    SelectChallangeResponse challengeResponse;
    ExpandableAdapter expandableListAdapter;

    List<Subcategory> childModelsList;
    List<Challenge> headerList = new ArrayList<>();
    HashMap<Challenge, List<Subcategory>> childList = new HashMap<>();





    private String TAG = ChooseChallengeFragment.class.getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home_choose_challenge, container, false);

        View view = binding.getRoot();


        progressBarr = view.findViewById(R.id.progressBar);
        linearLayout=view.findViewById(R.id.linearLayoutChoose);
        textView = view.findViewById(R.id.pbText);
        gifView=view.findViewById(R.id.challengeslockdown);
        gifView.setImageResource(R.drawable.revisedfrontbanner);
        expandableListView = view.findViewById(R.id.expandedListItem);
        getChooseChallengeData();


        gifView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutContestChallengeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getChooseChallengeData() {


        if (Util.isInternetConnected(getContext())) {
            linearLayout.setVisibility(View.VISIBLE);
            progressBarr.setVisibility(View.VISIBLE);
           /* textView.setVisibility(View.VISIBLE);*/
            RestClient.userSelectChallange(new Callback<SelectChallangeResponse>() {
                @Override
                public void onResponse(Call<SelectChallangeResponse> call, Response<SelectChallangeResponse> response) {
                    progressBarr.setVisibility(View.GONE);
                    /*textView.setVisibility(View.GONE);*/
                    linearLayout.setVisibility(View.GONE);
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: " + response.body().getStatus());
                        challengeResponse = response.body();
                        if (challengeResponse != null && challengeResponse.getChallenges().size() > 0) {
                            for (int i = 0; i < challengeResponse.getChallenges().size(); i++) {
                                Challenge challenge = new Challenge();
                                challenge.setChallenge(challengeResponse.getChallenges().get(i).getChallenge());
                                challenge.setIcon(challengeResponse.getChallenges().get(i).getIcon());
                                headerList.add(challenge);
                                childModelsList = new ArrayList<>();
                                for (int j = 0; j < challengeResponse.getChallenges().get(i).getSubcategory().size(); j++) {
                                    Subcategory subcategory = new Subcategory();
                                    subcategory.setSubcategory(challengeResponse.getChallenges().get(i).getSubcategory().get(j).getSubcategory());
                                    subcategory.setBlackIcon(challengeResponse.getChallenges().get(i).getSubcategory().get(j).getBlackIcon());
                                    subcategory.setBlueIcon(challengeResponse.getChallenges().get(i).getSubcategory().get(j).getBlueIcon());
                                    subcategory.setWhiteIcon(challengeResponse.getChallenges().get(i).getSubcategory().get(j).getWhiteIcon());
                                    childModelsList.add(subcategory);
                                    if (subcategory.getSubcategory() != null) {
                                        Log.d("API123", "here");
                                        childList.put(challenge, childModelsList);
                                    }
                                }


                                Log.d(TAG, "onResponse: " + headerList);
                                Log.d(TAG, "onResponse: " + childModelsList);
                                Log.d(TAG, "onResponse: " + childList);
                            }

                            expandableListAdapter = new ExpandableAdapter(getContext(), headerList, childList);
                            expandableListView.setAdapter(expandableListAdapter);
                            expandableListAdapter.setChooseChallangeInterface(new ExpandableAdapter.ChooseChallangeInterface() {
                                @Override
                                public void chooseChallangeItem(String subCategory, String challenge) {
                                    FuroPrefs.putString(getContext(), "subCategory", subCategory);
                                    for (int i = 0; i < challengeResponse.getChallenges().size(); i++) {

                                        List<Subcategory> childs = challengeResponse.getChallenges().get(i).getSubcategory();
                                        if (childs != null && childs.size() > 0) {
                                            for (int k = 0; k < childs.size(); k++) {
                                                if (childs.get(k).getSubcategory().equalsIgnoreCase(subCategory)) {
                                                    challenge = challengeResponse.getChallenges().get(i).getChallenge();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    FuroPrefs.putString(getContext(), "challange", challenge);
                                    Log.i("challengenameeeee",challenge);

                                   /* Fragment fragmentHomeChallengeCreate = CreateChallengeFragment.newInstance(CreateChallengeFragment.class.getSimpleName());
                                    replaceFragmentWithStack(R.id.fl_inside_home, fragmentHomeChallengeCreate, CreateChallengeFragment.class.getSimpleName());
*/
                                    Intent intent = new Intent(getContext(), CreateChallengeActivity.class);
                                    startActivity(intent);

                                }
                            });
                        }
                    }
                }




                @Override
                public void onFailure(Call<SelectChallangeResponse> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(homeMainActivity, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Util.dismissProgressDialog();
            Toast.makeText(homeMainActivity, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }


    public ChooseChallengeFragment() {

    }

    public static ChooseChallengeFragment newInstance(String name) {
        ChooseChallengeFragment fragment = new ChooseChallengeFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    public void replaceFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerId, fragment, name);

            if (!fragment.isAdded()) {
                fragmentTransaction.addToBackStack(name);
                fragmentTransaction.commitAllowingStateLoss();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}

