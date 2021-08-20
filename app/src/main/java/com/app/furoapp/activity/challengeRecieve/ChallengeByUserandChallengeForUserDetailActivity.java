package com.app.furoapp.activity.challengeRecieve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.VideoChallangeFriendsActivity;
import com.app.furoapp.adapter.ChallengeByUserandChallengeForUserDetailActivityAdapter;
import com.app.furoapp.adapter.ChallengeByYouDetailAdapter;
import com.app.furoapp.model.challengeByYouDetail.ChallenegeByYouDetailRequest;
import com.app.furoapp.model.challengeByYouDetail.ChallenegeByYouDetailResponse;
import com.app.furoapp.model.challengeByYouDetail.ChallengeDetails;
import com.app.furoapp.model.challengeByYouDetail.Receiver;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeByUserandChallengeForUserDetailActivity extends AppCompatActivity {

    RecyclerView recyclerViewNew, recyclerView1;
    ChallengeByYouDetailAdapter challengeByYouDetailAdapter;
    String challenegeDetailId, durationShare, activityShare, repsShare;

    String userid, challengeId;
    int challengeid;
    TextView challengeMoreFriends;
    ChallengeByUserandChallengeForUserDetailActivityAdapter challengeByUserandChallengeForUserDetailActivityAdapter;
    TextView textViewAgreeAndContinue, textViewPublicLinkOff, duration, reps, activity;
    ImageView whatsAppIcon, fbIcon, copyText;
    LinearLayout linearLayoutImageIcon, imageIconName, shareImageIconName, iconName;
    String pubLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_by_userand_challenge_for_user_detail);
        Intent intent = getIntent();
        challenegeDetailId = intent.getStringExtra("userIdChallengeByVideo");
        userid = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        recyclerViewNew = findViewById(R.id.challengeByYouandChallengeForYouRecycler);
        recyclerView1 = findViewById(R.id.challengedetailby);
        duration = findViewById(R.id.duration_open);
        iconName = findViewById(R.id.share_icons_name_open);
        reps = findViewById(R.id.reps_open);
        copyText = findViewById(R.id.copy_open);
        activity = findViewById(R.id.activity_open);

        challengeMoreFriends = findViewById(R.id.challengemorefriends);
        pubLink = FuroPrefs.getString(getApplicationContext(), "publiclink");

        textViewAgreeAndContinue = findViewById(R.id.agree_and_continue_open);
        linearLayoutImageIcon = findViewById(R.id.activity_details_ll_open);
        imageIconName = findViewById(R.id.share_icons_ll_open);
        whatsAppIcon = findViewById(R.id.whatsappicon_open);
        fbIcon = findViewById(R.id.facebook_con_open);

        shareImageIconName = findViewById(R.id.share_icons_ll_open);
        textViewPublicLinkOff = findViewById(R.id.turn_off_public_tv_open);
        copyText();
        challengeMoreFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String openChallToSocial = "ChallToSocial";
                FuroPrefs.putString(getApplicationContext(), "publiclink", openChallToSocial);
                Intent intent1 = new Intent(ChallengeByUserandChallengeForUserDetailActivity.this, VideoChallangeFriendsActivity.class);
                startActivity(intent1);
            }
        });
        challengeDetails();
        agreeAndContinue();
        turnOffPublicLink();
        fbShare();
        whatsAppShare();
    }


    public void agreeAndContinue() {
        textViewAgreeAndContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String agree = "publicklinkon";
                textViewPublicLinkOff.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
                textViewAgreeAndContinue.setVisibility(View.GONE);
                iconName.setVisibility(View.VISIBLE);
                textViewPublicLinkOff.setVisibility(View.VISIBLE);
                shareImageIconName.setVisibility(View.VISIBLE);
                linearLayoutImageIcon.setVisibility(View.VISIBLE);
                imageIconName.setVisibility(View.VISIBLE);
                FuroPrefs.putString(getApplicationContext(), "publiclinkon", agree);
            }
        });
    }

    public void turnOffPublicLink() {
        textViewPublicLinkOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String agree = "publiclinkoff";
                iconName.setVisibility(View.GONE);
                shareImageIconName.setVisibility(View.GONE);
                textViewAgreeAndContinue.setVisibility(View.VISIBLE);
                textViewPublicLinkOff.setVisibility(View.GONE);
                linearLayoutImageIcon.setVisibility(View.GONE);
                imageIconName.setVisibility(View.GONE);
                FuroPrefs.putString(getApplicationContext(), "publiclinkon", agree);
            }
        });
        String on = FuroPrefs.getString(getApplicationContext(), "publiclinkon");

        if (on.equalsIgnoreCase("publicklinkon")) {
            textViewPublicLinkOff.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
            textViewAgreeAndContinue.setVisibility(View.GONE);
            textViewPublicLinkOff.setVisibility(View.VISIBLE);
            iconName.setVisibility(View.VISIBLE);
            shareImageIconName.setVisibility(View.VISIBLE);
            linearLayoutImageIcon.setVisibility(View.VISIBLE);
            imageIconName.setVisibility(View.VISIBLE);
        } else {
            textViewPublicLinkOff.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
            shareImageIconName.setVisibility(View.GONE);
            textViewAgreeAndContinue.setVisibility(View.VISIBLE);
            textViewPublicLinkOff.setVisibility(View.GONE);
            iconName.setVisibility(View.GONE);
            linearLayoutImageIcon.setVisibility(View.GONE);
            imageIconName.setVisibility(View.GONE);
        }
    }

    public void challengeDetails() {

        ChallenegeByYouDetailRequest challangeDetailRequest = new ChallenegeByYouDetailRequest();
        challangeDetailRequest.setChallengeId(String.valueOf(challenegeDetailId));
        challangeDetailRequest.setUserId(userid);

        Util.isInternetConnected(this);
        Util.showProgressDialog(this);
        RestClient.userChallengeByDetail(challangeDetailRequest, new Callback<ChallenegeByYouDetailResponse>() {
            @Override
            public void onResponse(Call<ChallenegeByYouDetailResponse> call, Response<ChallenegeByYouDetailResponse> response) {
                Util.dismissProgressDialog();
                if (response != null && response.body().getChallengeDetails().getReceivers().size()>0) {

                    if (response.body().getStatus().equalsIgnoreCase("200")) {

                        List<ChallengeDetails> challengeDetails = Collections.singletonList(response.body().getChallengeDetails());

                        challengeId = String.valueOf(response.body().getChallengeDetails().getId());
                        FuroPrefs.putString(getApplicationContext(), "challengeidforshare", challengeId);
                        durationShare = response.body().getChallengeDetails().getAcitivityDuration();
                        activityShare = response.body().getChallengeDetails().getChallengeActivity();
                        repsShare = response.body().getChallengeDetails().getActivityCount();
                        duration.setText(durationShare);
                        reps.setText(repsShare);
                        activity.setText(activityShare);

                        challengeByUserandChallengeForUserDetailActivityAdapter = new ChallengeByUserandChallengeForUserDetailActivityAdapter(challengeDetails, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewNew.setLayoutManager(layoutManager);
                        recyclerViewNew.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewNew.setAdapter(challengeByUserandChallengeForUserDetailActivityAdapter);

                        List<Receiver> receivers = (response.body().getChallengeDetails().getReceivers());
                        challengeByYouDetailAdapter = new ChallengeByYouDetailAdapter(receivers, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
                        recyclerView1.setLayoutManager(layoutManager1);
                        recyclerView1.setItemAnimator(new DefaultItemAnimator());
                        recyclerView1.setAdapter(challengeByYouDetailAdapter);

                            challengeByYouDetailAdapter.setContentFeedActivityList(new ChallengeByYouDetailAdapter.ChallengeByYouDetailInterface() {
                                @Override
                                public void contentByYouDetailItem(int id, String user_id,int position) {

                                    challengeid = id;
                                    String userid = user_id;
                                    FuroPrefs.putInt(getApplicationContext(), "SubmissionChallenegeId", challengeid);
                                    FuroPrefs.putString(getApplicationContext(), "userIdLogin", userid);
                                    Intent intent = new Intent(ChallengeByUserandChallengeForUserDetailActivity.this, WinnerActivity.class);
                                    startActivity(intent);
                                }
                            });

                        } else {
                            Toast.makeText(ChallengeByUserandChallengeForUserDetailActivity.this, "Error  !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            @Override
            public void onFailure(Call<ChallenegeByYouDetailResponse> call, Throwable t) {
                Toast.makeText(ChallengeByUserandChallengeForUserDetailActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                Util.dismissProgressDialog();
            }
        });
    }

    public void whatsAppShare() {
        whatsAppIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I have taken a challenge on the FQ app. To watch and beat my score, download the app");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "I just Finished " + repsShare +" "+"of"+" "+ activityShare +" " +"in"+" " +durationShare +" "+"H/M/S" + " " + "can you beat my Score?" + " " + "Click here to challenge" + " " + " https://furofq.app.link/video/" + challengeId + "/" + userid);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void copyText() {
        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", "I just Finished " + repsShare +" "+"of"+" " +activityShare + " " + "in" + " " + durationShare +" "+ "H/M/S" + " " + "can you beat my Score?" + " " + "Click here to challenge ." + " " + " https://furofq.app.link/video/" + challengeId + "/" + userid);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ChallengeByUserandChallengeForUserDetailActivity.this, " Link Copied now you can share !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fbShare() {
        fbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doFBShareLink("I just Finished " + repsShare +" "+ "of"+" " + activityShare +" " +"in"+" "+ durationShare +" "+ "H/M/S" + " " + "can you beat my Score?" + " " + "Click here to challenge ." + " " + " https://furofq.app.link/video/" + challengeId + "/" + userid);
            }
        });
    }


    private void doFBShareLink(String link) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        Intent chooserIntent = Intent.createChooser(shareIntent, getString(R.string.share_via));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, link);
            Bundle facebookBundle = new Bundle();
            facebookBundle.putString(Intent.EXTRA_TEXT, link);
            Bundle replacement = new Bundle();
            replacement.putBundle("com.facebook.katana", facebookBundle);
            chooserIntent.putExtra(Intent.EXTRA_REPLACEMENT_EXTRAS, replacement);
        } else {
            shareIntent.putExtra(Intent.EXTRA_TEXT, link);
        }
        chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(chooserIntent);
    }


}
