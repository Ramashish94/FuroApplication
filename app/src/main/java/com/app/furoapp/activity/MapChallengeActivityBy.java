package com.app.furoapp.activity;

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
import com.app.furoapp.activity.MapChallengeActivity;
import com.app.furoapp.activity.challengeRecieve.ChallengeByUserandChallengeForUserDetailActivity;
import com.app.furoapp.activity.challengeRecieve.WinnerDetailByYouActivity;
import com.app.furoapp.activity.challengeRecieveMap.WinnerActivityMap;
import com.app.furoapp.adapter.ChallengeByUserandChallengeForUserDetailActivityAdapter;
import com.app.furoapp.adapter.ChallengeByUserandChallengeForUserDetailActivityAdapterBy;
import com.app.furoapp.adapter.ChallengeByYouDetailAdapter;
import com.app.furoapp.databinding.SocialFragmentBinding;
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

import static com.facebook.FacebookSdk.getApplicationContext;

public class MapChallengeActivityBy extends AppCompatActivity {
    String durationShare, activityShare, distanceShare;

    RecyclerView recyclerViewNew, recyclerView1;
    ChallengeByYouDetailAdapter challengeByYouDetailAdapter;
    String challenegeDetailId;
    String userid, pubLink, challengeId;
    TextView challengeMoreFriends;
    ChallengeByUserandChallengeForUserDetailActivityAdapterBy challengeByUserandChallengeForUserDetailActivityAdapter;
    TextView textViewshareHint, textViewAgreeAndContinue, textViewPublicLinkOff;
    ImageView whatsAppIcon, fbIcon,linkcopy;
    LinearLayout linearLayoutImageIcon, imageIconName, shareImageIconName,iconName;
    TextView duration, distance, activity_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_by_userand_challenge_for_user_detail_map);

        duration = findViewById(R.id.duration_open_map);
        linkcopy = findViewById(R.id.copylinkmap);
        iconName = findViewById(R.id.share_icons_name_open);
        distance = findViewById(R.id.distance_open_map);
        activity_map = findViewById(R.id.activity_open_map);
        textViewAgreeAndContinue = findViewById(R.id.agree_and_continue_open);
        linearLayoutImageIcon = findViewById(R.id.activity_details_ll_open);
        imageIconName = findViewById(R.id.share_icons_ll_open);
        whatsAppIcon = findViewById(R.id.whatsappicon_open);
        fbIcon = findViewById(R.id.facebook_con_open);

        shareImageIconName = findViewById(R.id.share_icons_ll_open);
        textViewPublicLinkOff = findViewById(R.id.turn_off_public_tv_open);

        Intent intent = getIntent();
        challenegeDetailId = intent.getStringExtra("userIdChallengeByMap");
        userid = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        recyclerViewNew = findViewById(R.id.challengeByYouandChallengeForYouRecycler);
        challengeMoreFriends = findViewById(R.id.challengemorefriends);
        recyclerView1 = findViewById(R.id.challengedetailby);
        pubLink = FuroPrefs.getString(getApplicationContext(), "publiclink");
        copymapdata();
        challengeMoreFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String openChallToSocial ="ChallToSocial";
                FuroPrefs.putString(getApplicationContext(),"publiclink",openChallToSocial);
                Intent intent1 = new Intent(MapChallengeActivityBy.this, VideoChallangeFriendsActivity.class);
                startActivity(intent1);
            }
        });
        challengeDetails();
        agreeAndContinue();
        turnOffPublicLink();
        whatsAppShare();
        fbShare();

    }

    public void agreeAndContinue() {
        textViewAgreeAndContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String agreeNew = "publicklinkonn";
                textViewPublicLinkOff.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
                textViewAgreeAndContinue.setVisibility(View.GONE);
                iconName.setVisibility(View.VISIBLE);
                textViewPublicLinkOff.setVisibility(View.VISIBLE);
                shareImageIconName.setVisibility(View.VISIBLE);
                linearLayoutImageIcon.setVisibility(View.VISIBLE);
                imageIconName.setVisibility(View.VISIBLE);
                FuroPrefs.putString(getApplicationContext(), "publiclinkonmap", agreeNew);


            }
        });


    }

    public void turnOffPublicLink() {
        textViewPublicLinkOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String agreeNew = "publiclinkofff";
                iconName.setVisibility(View.GONE);
                shareImageIconName.setVisibility(View.GONE);
                textViewAgreeAndContinue.setVisibility(View.VISIBLE);
                textViewPublicLinkOff.setVisibility(View.GONE);
                linearLayoutImageIcon.setVisibility(View.GONE);
                imageIconName.setVisibility(View.GONE);
                FuroPrefs.putString(getApplicationContext(), "publiclinkonmap", agreeNew);
            }
        });
        String on = FuroPrefs.getString(getApplicationContext(), "publiclinkonmap");

        if (on.equalsIgnoreCase("publicklinkonn")) {
            textViewAgreeAndContinue.setVisibility(View.GONE);
            iconName.setVisibility(View.VISIBLE);
            textViewPublicLinkOff.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
            textViewPublicLinkOff.setVisibility(View.VISIBLE);
            shareImageIconName.setVisibility(View.VISIBLE);
            linearLayoutImageIcon.setVisibility(View.VISIBLE);
            imageIconName.setVisibility(View.VISIBLE);


        } else if(on.equalsIgnoreCase("publiclinkofff")) {

            shareImageIconName.setVisibility(View.GONE);
            iconName.setVisibility(View.GONE);
            textViewPublicLinkOff.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);

            textViewAgreeAndContinue.setVisibility(View.VISIBLE);
            textViewPublicLinkOff.setVisibility(View.GONE);
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
                if (response != null) {

                    if (response.body().getStatus().equalsIgnoreCase("200")) {

                        List<ChallengeDetails> challengeDetails = Collections.singletonList(response.body().getChallengeDetails());
                        durationShare = response.body().getChallengeDetails().getAcitivityDuration();
                        activityShare = response.body().getChallengeDetails().getChallengeActivity();
                        distanceShare = response.body().getChallengeDetails().getDistance();

                        duration.setText(durationShare);
                        distance.setText(distanceShare);
                        activity_map.setText(activityShare);
                        challengeByUserandChallengeForUserDetailActivityAdapter = new ChallengeByUserandChallengeForUserDetailActivityAdapterBy(challengeDetails, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewNew.setLayoutManager(layoutManager);
                        recyclerViewNew.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewNew.setAdapter(challengeByUserandChallengeForUserDetailActivityAdapter);

                        challengeId = String.valueOf(response.body().getChallengeDetails().getId());
                        FuroPrefs.putString(getApplicationContext(), "challengeidforshare", challengeId);


                        List<Receiver> receivers = (response.body().getChallengeDetails().getReceivers());


                        challengeByYouDetailAdapter = new ChallengeByYouDetailAdapter(receivers, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
                        recyclerView1.setLayoutManager(layoutManager1);
                        recyclerView1.setItemAnimator(new DefaultItemAnimator());
                        recyclerView1.setAdapter(challengeByYouDetailAdapter);

                            challengeByYouDetailAdapter.setContentFeedActivityList(new ChallengeByYouDetailAdapter.ChallengeByYouDetailInterface() {
                                @Override
                                public void contentByYouDetailItem(int id, String user_id,int position) {
                                    int challengeid = id;
                                    String useridmap = user_id;

                                    FuroPrefs.putInt(getApplicationContext(), "SubmissionChallenegeIdmap", challengeid);
                                    FuroPrefs.putString(getApplicationContext(), "userIdLoginmap", useridmap);
                                    FuroPrefs.putString(getApplicationContext(), "bychallengeid", String.valueOf(challengeid));
                                    Intent intent = new Intent(MapChallengeActivityBy.this, WinnerActivityMap.class);
                                    startActivity(intent);

                                }
                            });


                        }
                    }


            }


            @Override
            public void onFailure(Call<ChallenegeByYouDetailResponse> call, Throwable t) {

                Toast.makeText(MapChallengeActivityBy.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void whatsAppShare() {
        whatsAppIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mapActivity = "map";
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I have taken a challenge on the FQ app. To watch and beat my score, download the app");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT,

                        "I just Finished "+distanceShare+"m"+" "+activityShare+" "+"in"+" "+durationShare+""+"H/M/S"+" "+"can you beat my Score?"+"  "+"Click here to challenge"+" "+ " https://furofq.app.link/map/" + challengeId+ "/" + userid);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {


                    Toast.makeText(getApplicationContext(), "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void copymapdata(){

        linkcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", "I just Finished "+distanceShare+"m"+" "+activityShare+" "+"in"+" "+durationShare+""+"H/M/S"+" "+"can you beat my Score?" +" "+"Click here to challenge"+" "+ " https://furofq.app.link/map/" + challengeId+ "/" + userid);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MapChallengeActivityBy.this, " Link Copied now you can share !!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void fbShare() {
        fbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doFBShareLink("I just Finished "+distanceShare+"m"+" "+activityShare+" "+"in"+" "+durationShare+""+"H/M/S"+" "+"can you beat my Score?" +" "+"Click here to challenge"+" "+ " https://furofq.app.link/map/" + challengeId+ "/" + userid);

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
