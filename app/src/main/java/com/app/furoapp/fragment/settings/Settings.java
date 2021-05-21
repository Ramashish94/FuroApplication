package com.app.furoapp.fragment.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.app.furoapp.BuildConfig;
import com.app.furoapp.R;
import com.app.furoapp.activity.FaqActivity;
import com.app.furoapp.activity.HelpandSupportActivity;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.SettingsActivity;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.fragment.profileSection.ProfileHomeNewActivity;
import com.app.furoapp.fragment.profileSection.ProfilesHomeNewFragment;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.widget.SwitchButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Settings extends Fragment {
    HomeMainActivity homeMainActivity;
    TextView logoutTv, tvAppVersion;
    SwitchCompat mySwitch;
    String on, of;
    RelativeLayout relativeLayoutlogout, relativeLayouthelpandsupport, relativeLayoutProfile, relativeAccountSetting, faq;
    SwitchButton switchButtonVideolow, switchButtonVideoHigh;
    View leadView;
    public GoogleSignInClient mGoogleSignInClient;


    public static Settings newInstance(String act) {
        Settings fragmentLeaderBoard = new Settings();
        Bundle args = new Bundle();
        args.putString("act", act);
        fragmentLeaderBoard.setArguments(args);
        return fragmentLeaderBoard;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*View*/ leadView = inflater.inflate(R.layout.fragment_settings, container, false);
        relativeLayouthelpandsupport = leadView.findViewById(R.id.helpandsupport);
        relativeLayoutlogout = leadView.findViewById(R.id.logoutrelative);
        logoutTv = leadView.findViewById(R.id.logouttextview);
        faq = leadView.findViewById(R.id.relativemyfaq);

        mySwitch = leadView.findViewById(R.id.switch1);
        relativeLayoutProfile = leadView.findViewById(R.id.relativemyProfile);
        relativeAccountSetting = leadView.findViewById(R.id.accountsetting);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        mySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mySwitch.isChecked()) {
                    on = "VideoLow";
                    FuroPrefs.putString(getApplicationContext(), "true", on);
                    mySwitch.setChecked(true);
                    onResume();

                } else {
                    on = "Videohigh";
                    FuroPrefs.putString(getApplicationContext(), "true", on);
                    mySwitch.setChecked(false);
                    onPause();
                }

            }
        });

        of = FuroPrefs.getString(getApplicationContext(), "true");
        if (of.equalsIgnoreCase("VideoLow")) {
            mySwitch.setChecked(true);
        } else if (of.equalsIgnoreCase("Videohigh")) {
            mySwitch.setChecked(false);
        }


        relativeAccountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        relativeLayouthelpandsupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HelpandSupportActivity.class);
                startActivity(intent);
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FaqActivity.class);
                startActivity(intent);
            }
        });

        relativeLayoutlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        relativeLayoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileHomeNewActivity.class);
                startActivity(intent);
                // homeMainActivity.replaceFragmentWithStack(R.id.container_main, ProfilesHomeNewFragment.newInstance(null), ProfilesHomeNewFragment.class.getSimpleName());

            }
        });

        setVersionName();

        return leadView;
    }

    private void setVersionName() {
        tvAppVersion = leadView.findViewById(R.id.tvAppVersion);
        tvAppVersion.setText("Version : " + BuildConfig.VERSION_NAME);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

   /* private void logout() {
        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setMessage("Are you sure you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        FuroPrefs.clear(getApplicationContext());

                        Intent intent = new Intent(getApplicationContext(), LoginTutorialScreen.class);

                      *//*  String onContentFeedClick = "contentfeed";
                        String onContentFeedClickfriend = "friends";
                        String onContentFeedClickChallenge = "newchallenge";
                        String onContentFeedClickleader = "leaderboard";
                        String onContentFeedClickClub = "club";
                        String onContentFeedClickopen = "openFinishChallenge";
                        String onContentFeedClickCommunity = "community";


                        FuroPrefs.putString(getApplicationContext(), "tutorial", onContentFeedClick);
                        FuroPrefs.putString(getApplicationContext(), "tutorialopenfinishedchallenge", onContentFeedClickopen);
                        FuroPrefs.putString(getApplicationContext(), "tutorialleaderboard", onContentFeedClickleader);
                        FuroPrefs.putString(getApplicationContext(), "tutorialFriends", onContentFeedClickfriend);
                        getActivity().finish();
                        FuroPrefs.putString(getApplicationContext(), "tutorialchallenge", onContentFeedClickChallenge);
                        FuroPrefs.putString(getApplicationContext(), "tutorialClub", onContentFeedClickClub);
                        FuroPrefs.putString(getApplicationContext(), "tutorialcommunity", onContentFeedClickCommunity);
*//*
                          startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }*/

    public void logout() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_alertdialog_logoutt_new, null);


        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();


        ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
        TextView text_logout = dialogView.findViewById(R.id.text_logout);
        TextView noiwanttocontinue = dialogView.findViewById(R.id.noiwanttocontinuee);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        noiwanttocontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FuroPrefs.clear(getApplicationContext());
                googleSignOut();
                Intent intent = new Intent(getApplicationContext(), LoginTutorialScreen.class);
                startActivity(intent);
                getActivity().finishAffinity();


            }
        });

        dialog.show();


    }

    public void googleSignOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Toast.makeText(ActivityMain.this, "Google Sign Out done.", Toast.LENGTH_SHORT).show();
                        revokeAccess();
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Toast.makeText(ActivityMain.this, "Google access revoked.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
