package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.app.furoapp.R;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.fragment.tutorials.ForgetPasswordFragment;
import com.app.furoapp.fragment.tutorials.LoginWithEmail;
import com.app.furoapp.fragment.tutorials.PasswordChangeFragment;
import com.app.furoapp.fragment.tutorials.WelcomeUserYouAreIn;
import com.app.furoapp.fragment.tutorials.LocationAndMovement;
import com.app.furoapp.fragment.tutorials.Notification;
import com.app.furoapp.fragment.tutorials.WhatBringsYouToFuro;
import com.app.furoapp.fragment.tutorials.TellUsAboutYou;

public class TutorialScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_screen);

        Fragment loginWithEmail = LoginWithEmail.newInstance(LoginWithEmail.class.getSimpleName());
        replaceFragmentWithStack(R.id.tutorialFragmentContainer, loginWithEmail, LoginWithEmail.class.getSimpleName());

    }


    public void setDisplayFragment(int id, Bundle bundle) {
        switch (id) {
            case EnumConstants.HOME_TUTORIAL_PAGE:
                Fragment fragment = WelcomeUserYouAreIn.newInstance(WelcomeUserYouAreIn.class.getSimpleName());
                replaceFragmentWithStack(R.id.tutorialFragmentContainer, fragment, WelcomeUserYouAreIn.class.getSimpleName());
                break;
            case EnumConstants.TUTORIAL_LOCATION:
                Fragment fragment1 = LocationAndMovement.newInstance(LocationAndMovement.class.getSimpleName());
                addFragmentWithStack(R.id.tutorialFragmentContainer, fragment1, LocationAndMovement.class.getSimpleName());

                break;

            case EnumConstants.TUTORIAL_NOTIFICATIONS:
                Fragment fragment3 = Notification.newInstance(Notification.class.getSimpleName());
                addFragmentWithStack(R.id.tutorialFragmentContainer, fragment3, Notification.class.getSimpleName());

                break;
            case EnumConstants.WHAT_BRINGS_YOU_TO_FURO:
                Fragment fragment5 = WhatBringsYouToFuro.newInstance(WhatBringsYouToFuro.class.getSimpleName());
                addFragmentWithStack(R.id.tutorialFragmentContainer, fragment5, WhatBringsYouToFuro.class.getSimpleName());

                break;

            case EnumConstants.TUTORIAL_HEIGHT:
                Fragment fragment6 = TellUsAboutYou.newInstance(TellUsAboutYou.class.getSimpleName());
                addFragmentWithStack(R.id.tutorialFragmentContainer, fragment6, TellUsAboutYou.class.getSimpleName());

                break;
            case EnumConstants.LOGIN_WITH_EMAIL:
                Fragment loginWithEmail = LoginWithEmail.newInstance(LoginWithEmail.class.getSimpleName());
                addFragmentWithStack(R.id.tutorialFragmentContainer, loginWithEmail, LoginWithEmail.class.getSimpleName());

                break;

            case EnumConstants.FORGET_PASSWORD_FRAGMENT:
                Fragment forgetPasswordFragment = ForgetPasswordFragment.newInstance(ForgetPasswordFragment.class.getSimpleName());
                addFragmentWithStack(R.id.tutorialFragmentContainer, forgetPasswordFragment, ForgetPasswordFragment.class.getSimpleName());

                break;

            case EnumConstants.PASSWORD_CHANGE_FRAGMENT:
                Fragment passwordChangeFragment = PasswordChangeFragment.newInstance(PasswordChangeFragment.class.getSimpleName());
                addFragmentWithStack(R.id.tutorialFragmentContainer, passwordChangeFragment, PasswordChangeFragment.class.getSimpleName());

                break;


        }
    }


    private void addFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(containerId, fragment, name);
            fragmentTransaction.addToBackStack(name);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void replaceFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerId, fragment, name);
            fragmentTransaction.addToBackStack(name);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            finish();

        } else {
            getSupportFragmentManager().popBackStack();
        }

    }


}
