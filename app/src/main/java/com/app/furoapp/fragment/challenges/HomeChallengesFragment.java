package com.app.furoapp.fragment.challenges;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.WelcomeActivityChallenges;
import com.app.furoapp.activity.WelcomeActivityClubChallenge;
import com.app.furoapp.activity.WelcomeActivityClubOpenChoose;
import com.app.furoapp.activity.WelcomeActivityCommunity;
import com.app.furoapp.activity.WelcomeActivityOpenFinishChallenge;
import com.app.furoapp.activity.tutorialScreens.WelcomeUserYouAreInActivity;
import com.app.furoapp.databinding.FragmentHomeNewBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.fragment.community.CommunitiesHomeFragment;
import com.app.furoapp.fragment.profileSection.ProfilesHomeNewFragment;
import com.app.furoapp.utils.FuroPrefs;
import com.google.android.material.tabs.TabLayout;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeChallengesFragment extends Fragment {

    HomeMainActivity homeMainActivity;
    FragmentHomeNewBinding binding;
    String openQestionActivity;
    String onClickClub, onclickopenfinshed;
    private ChooseChallengeFragment chooseChallengeFragment;
    private ClubChallengeFragment clubChallengeFragment;
    private OpenAndCloseChallengeFragment openAndCloseChallengeFragment;
    ImageView questionChallenge;
    public static final int REQUEST_CODE = 303;
    public static final int REQUEST_CODE_1 = 304;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home_new, container, false);
        View view = binding.getRoot();

        questionChallenge = view.findViewById(R.id.Challenges);

        questionChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), WelcomeActivityClubOpenChoose.class);
                startActivity(intent);

            }
        });
        bindWidgetsWithAnEvent();
        setupTabLayout();
        return view;
    }

    public HomeChallengesFragment() {

    }


    public static HomeChallengesFragment newInstance(String name) {
        HomeChallengesFragment fragment = new HomeChallengesFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    private void setupTabLayout() {

        chooseChallengeFragment = new ChooseChallengeFragment();
        clubChallengeFragment = new ClubChallengeFragment();
        openAndCloseChallengeFragment = new OpenAndCloseChallengeFragment();
        binding.tabChallenges.addTab(binding.tabChallenges.newTab().setText("NEW CHALLENGES"), true);
        binding.tabChallenges.addTab(binding.tabChallenges.newTab().setText("CLUB CHALLENGES"));
        binding.tabChallenges.addTab(binding.tabChallenges.newTab().setText("HISTORY"));/*/*OPEN/FINISHED*/
    }

    private void bindWidgetsWithAnEvent() {
        binding.tabChallenges.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public  void setCurrentTabFragment(int tabPosition) {

        switch (tabPosition) {
            case 0:
                replaceFragmentWithStack(R.id.fl_inside_home, ChooseChallengeFragment.newInstance(null), ChooseChallengeFragment.class.getSimpleName());
                break;

            case 1:
                onClickClub = FuroPrefs.getString(getApplicationContext(), "tutorialClub");
                if (onClickClub.equalsIgnoreCase("club")) {
                    replaceFragmentWithStack(R.id.fl_inside_home, ClubChallengeFragment.newInstance(null), ClubChallengeFragment.class.getSimpleName());

                } else {
                    Intent intent = new Intent(getContext(), WelcomeActivityClubChallenge.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                break;

            case 2:
                onclickopenfinshed = FuroPrefs.getString(getApplicationContext(), "tutorialopenfinishedchallenge");
                if (onclickopenfinshed.equalsIgnoreCase("openFinishChallenge")) {
                    replaceFragmentWithStack(R.id.fl_inside_home, OpenAndCloseChallengeFragment.newInstance(null), OpenAndCloseChallengeFragment.class.getSimpleName());

                } else {
                    Intent intent = new Intent(getContext(), WelcomeActivityOpenFinishChallenge.class);
                    startActivityForResult(intent, REQUEST_CODE_1);
                }
                break;


        }
    }

    public void replaceFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {
            FragmentManager fragmentManager = getChildFragmentManager();
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

    public void addFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {

            if (!fragment.isAdded()) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(containerId, fragment, name);
                fragmentTransaction.addToBackStack(name);
                fragmentTransaction.commitAllowingStateLoss();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            replaceFragmentWithStack(R.id.fl_inside_home, ClubChallengeFragment.newInstance(null), ClubChallengeFragment.class.getSimpleName());
        }
        else if (requestCode == REQUEST_CODE_1) {
            replaceFragmentWithStack(R.id.fl_inside_home, OpenAndCloseChallengeFragment.newInstance(null), OpenAndCloseChallengeFragment.class.getSimpleName());

        }


    }

}








