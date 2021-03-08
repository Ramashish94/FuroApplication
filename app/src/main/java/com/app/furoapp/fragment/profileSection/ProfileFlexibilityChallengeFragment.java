package com.app.furoapp.fragment.profileSection;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.adapter.ProfileFlexibityChallangeAadapter;
import com.app.furoapp.base.BaseFragment;
import com.app.furoapp.model.ChallengeItemModel;

import java.util.ArrayList;



public class ProfileFlexibilityChallengeFragment extends BaseFragment {


    public ProfileFlexibilityChallengeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flexibility_challenges, container, false);
    }

    @Override
    protected void setUp(View view) {
        setListAdapter(view);


    }

    private void setListAdapter(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ProfileFlexibityChallangeAadapter( getListOfChallenges(),getContext()));



    }

    private ArrayList<ChallengeItemModel> getListOfChallenges() {
        ArrayList<ChallengeItemModel> listOfChallenges = new ArrayList<ChallengeItemModel>();

        Integer arrayOfChallenges[] = new Integer[]{R.drawable.ic_running,
                R.drawable.ic_cycling,
                R.drawable.ic_squads,
                R.drawable.ic_pushups,
                R.drawable.ic_running2,
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

    public static ProfileFlexibilityChallengeFragment newInstance(String name) {

        ProfileFlexibilityChallengeFragment fragment = new ProfileFlexibilityChallengeFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }
}
