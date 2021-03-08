package com.app.furoapp.fragment.tutorials;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.FragmentContentMotivationBinding;
import com.app.furoapp.databinding.FragmentLocationMovementBinding;

import static com.app.furoapp.enums.EnumConstants.HOME_TUTORIAL_PAGE;
import static com.app.furoapp.enums.EnumConstants.TUTORIAL_LOCATION;
import static com.app.furoapp.enums.EnumConstants.TUTORIAL_NOTIFICATIONS;
import static com.app.furoapp.enums.EnumConstants.TUTORIAL_PROFILE;

public class LocationAndMovement extends Fragment {
    TutorialScreen tutorialScreen;
    FragmentLocationMovementBinding binding;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tutorialScreen= (TutorialScreen) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_location_movement, container, false);
        View view = binding.getRoot();
        setOnClickListeners();
        setOnClickListenersNew();
        setOnClickListenersBack();

        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    public LocationAndMovement() {

    }

    public static LocationAndMovement newInstance(String name) {
        LocationAndMovement fragment = new LocationAndMovement();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }
    private void setOnClickListeners() {
        binding.etSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle =  new Bundle();
                tutorialScreen.setDisplayFragment(TUTORIAL_PROFILE,bundle);
            }
        });


    }

    private void setOnClickListenersNew() {
        binding.etSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                tutorialScreen.setDisplayFragment(TUTORIAL_NOTIFICATIONS, bundle);
            }
        });


    }
    private void setOnClickListenersBack() {
        binding.ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                tutorialScreen.setDisplayFragment(HOME_TUTORIAL_PAGE, bundle);
            }
        });


    }
}