package com.app.furoapp.fragment.tutorials;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.activity.GlideApp;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.FragmentwelcomeuseryouareinBinding;
import com.app.furoapp.utils.FuroPrefs;
import com.squareup.picasso.Picasso;

import static com.app.furoapp.enums.EnumConstants.WHAT_BRINGS_YOU_TO_FURO;

public class WelcomeUserYouAreIn extends Fragment  {
    TutorialScreen tutorialScreen;
    private TextView welcomeName ;
    private ImageView welcomeImage;

    FragmentwelcomeuseryouareinBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tutorialScreen = (TutorialScreen) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragmentwelcomeuseryouarein, container, false);
        View view = binding.getRoot();
        welcomeImage = binding.ivWelcon;

        welcomeName = binding.tvTextUserName;
        String image = FuroPrefs.getString(getContext(),"userImage");

        GlideApp.with(this).load(image).error(R.drawable.user_icon)
                .into(welcomeImage);


        String welcome_user_name =FuroPrefs.getString(getContext(),"loginUserNameNew");
        welcomeName.setText(welcome_user_name);
        setOnClickListeners();



        return view;

    }

    public WelcomeUserYouAreIn() {

    }


    public static WelcomeUserYouAreIn newInstance(String name) {
        WelcomeUserYouAreIn fragment = new WelcomeUserYouAreIn();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOnClickListeners() {
        binding.etSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                tutorialScreen.setDisplayFragment(WHAT_BRINGS_YOU_TO_FURO, bundle);
            }
        });


    }


}
