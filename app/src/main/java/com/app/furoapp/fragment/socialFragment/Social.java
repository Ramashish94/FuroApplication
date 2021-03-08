package com.app.furoapp.fragment.socialFragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.databinding.SocialFragmentBinding;
import com.app.furoapp.utils.FuroPrefs;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class Social extends Fragment {
    TextView textViewshareHint, textViewAgreeAndContinue, textViewPublicLinkOff;
    ImageView whatsAppIconSocial, fbIcon, copyTextSocial;
    LinearLayout linearLayoutImageIcon, imageIconName, shareImageIconName, linearLayoutmap, linear_Map, linear_Video;
    SocialFragmentBinding binding;
    String sharechallengeid, agreeAndContinueVisible, actOpen, actDuration, actCount, actActivity;
    TextView textViewVideoDuration, textViewVideoActivity, textViewVideoReps;
    TextView textViewMapDuration, textViewMapActivity, textViewMapDistance;
    String linkText, senderId, agree;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.social_fragment, container, false);
        View view = binding.getRoot();

        linearLayoutmap = binding.activityDetailsLlMap;
        textViewMapDuration = binding.durationMap;
        copyTextSocial = binding.copysocial;

        textViewMapActivity = binding.activityNameMap;
        textViewMapDistance = binding.activityDistanceMap;

        linearLayoutImageIcon = binding.activityDetailsLlVideo;
        textViewVideoActivity = binding.activityNameVideo;
        textViewVideoDuration = binding.durationVideo;
        textViewVideoReps = binding.activityRepsVideo;


        textViewshareHint = binding.hintText;
        textViewAgreeAndContinue = binding.agreeAndContinue;


        imageIconName = binding.shareIconsLl;
        whatsAppIconSocial = binding.whatsappiconsocial;
        fbIcon = binding.facebookCon;

        shareImageIconName = binding.shareIconsName;
        textViewPublicLinkOff = binding.turnOffPublicTv;
        sharechallengeid = FuroPrefs.getString(getContext(), "challengeidforshare");
        agreeAndContinueVisible = FuroPrefs.getString(getContext(), "publiclinkon");
        actOpen = FuroPrefs.getString(getApplicationContext(), "activity_open");
        senderId = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        actDuration = FuroPrefs.getString(getApplicationContext(), "getAcitivityDuration");
        actCount = FuroPrefs.getString(getApplicationContext(), "getActivityCount");
        actActivity = FuroPrefs.getString(getApplicationContext(), "getChallengeActivity");
        copysoialtext();

        if (actOpen.equalsIgnoreCase("video")) {
            linearLayoutmap.setVisibility(View.GONE);
            linearLayoutImageIcon.setVisibility(View.VISIBLE);
            textViewVideoActivity.setText(actActivity);
            textViewVideoDuration.setText(actDuration);
            textViewVideoReps.setText(actCount);
            linkText = "I just Finished " +actCount +" "+actActivity + " " + "in" + " " + actDuration +" "+ "H/M/S" + " " + "can you beat my Score ?" + " " + "Click here to challenge.";

        } else  if(actOpen.equalsIgnoreCase("map")){

            linearLayoutmap.setVisibility(View.VISIBLE);
            linearLayoutImageIcon.setVisibility(View.GONE);
            textViewMapDuration.setText(actDuration);
            textViewMapActivity.setText(actActivity);
            textViewMapDistance.setText(actCount);

            linkText = "I just Finished " + actCount + "m" + " " + actActivity + " " + "in" + " " + actDuration +" "+ "H/M/S" + " " + "can you beat my Score?" + " " + "Click here to challenge.";

        }
        agreeAndContinue();
        fbShare();
        whatsAppShare();
        turnOffPublicLink();
        return view;


    }

    public static Social newInstance(String name) {
        Social fragment = new Social();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    public void agreeAndContinue() {
        textViewAgreeAndContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                agree = "publicklinkon";
                textViewAgreeAndContinue.setVisibility(View.GONE);
                textViewPublicLinkOff.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
                textViewPublicLinkOff.setVisibility(View.VISIBLE);
                shareImageIconName.setVisibility(View.VISIBLE);
                linearLayoutImageIcon.setVisibility(View.VISIBLE);
                imageIconName.setVisibility(View.VISIBLE);
                textViewshareHint.setVisibility(View.VISIBLE);
                FuroPrefs.putString(getApplicationContext(), "publiclink", agree);


                if (actOpen.equalsIgnoreCase("video")) {
                    linearLayoutmap.setVisibility(View.GONE);
                    linearLayoutImageIcon.setVisibility(View.VISIBLE);
                    textViewVideoActivity.setText(actActivity);
                    textViewVideoDuration.setText(actDuration);
                    textViewVideoReps.setText(actCount);
                    linkText = "I just Finished " + actCount + "  " + actActivity + " " + "in" + " " + actDuration + " "+ "H/M/S" + " " + "can you beat my Score ?" + " " + "Click here to challenge.";

                } else  if(actOpen.equalsIgnoreCase("map")){

                    linearLayoutmap.setVisibility(View.VISIBLE);
                    linearLayoutImageIcon.setVisibility(View.GONE);
                    textViewMapDuration.setText(actDuration);
                    textViewMapActivity.setText(actActivity);
                    textViewMapDistance.setText(actCount);

                    linkText = "I just Finished " + actCount + "m" + " " + actActivity + " " + "in" + " " + actDuration +" "+"H/M/S" + " " + "can you beat my Score?" + " " + "Click here to challenge.";

                }


            }
        });


    }



    public void turnOffPublicLink() {
        textViewPublicLinkOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (actOpen.equalsIgnoreCase("video")) {
                    linearLayoutmap.setVisibility(View.GONE);
                    linearLayoutImageIcon.setVisibility(View.VISIBLE);
                    textViewVideoActivity.setText(actActivity);
                    textViewVideoDuration.setText(actDuration);
                    textViewVideoReps.setText(actCount);
                    linkText = "I just Finished " + actCount + "  " + actActivity + " " + "in" + " " + actDuration + "sec" + " " + "can you beat my Score ?" + " " + "Click here to challenge";

                } else  if(actOpen.equalsIgnoreCase("map")){

                    linearLayoutmap.setVisibility(View.VISIBLE);
                    linearLayoutImageIcon.setVisibility(View.GONE);
                    textViewMapDuration.setText(actDuration);
                    textViewMapActivity.setText(actActivity);
                    textViewMapDistance.setText(actCount);

                    linkText = "I just Finished " + actCount + "m" + " " + actActivity + " " + "in" + " " + actDuration + "sec" + " " + "can you beat my Score?" + " " + "Click here to challenge";

                 }


                String agree = "publiclinkoff";
                shareImageIconName.setVisibility(View.GONE);
                textViewAgreeAndContinue.setVisibility(View.VISIBLE);
                textViewPublicLinkOff.setVisibility(View.GONE);
                linearLayoutImageIcon.setVisibility(View.GONE);
                linearLayoutmap.setVisibility(View.GONE);
                imageIconName.setVisibility(View.GONE);
                textViewshareHint.setVisibility(View.GONE);
                FuroPrefs.putString(getApplicationContext(), "publiclink", agree);
            }
        });


        String of = FuroPrefs.getString(getApplicationContext(), "publiclink");
        if (of.equalsIgnoreCase("publicklinkon")) {

            textViewAgreeAndContinue.setVisibility(View.GONE);
            textViewPublicLinkOff.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
            textViewPublicLinkOff.setVisibility(View.VISIBLE);
            shareImageIconName.setVisibility(View.VISIBLE);
            linearLayoutImageIcon.setVisibility(View.VISIBLE);
            imageIconName.setVisibility(View.VISIBLE);

            textViewshareHint.setVisibility(View.VISIBLE);
        } else if (of.equalsIgnoreCase("publiclinkoff")) {

            shareImageIconName.setVisibility(View.GONE);
            textViewPublicLinkOff.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
            textViewAgreeAndContinue.setVisibility(View.VISIBLE);
            textViewPublicLinkOff.setVisibility(View.GONE);
            linearLayoutImageIcon.setVisibility(View.GONE);
            imageIconName.setVisibility(View.GONE);
            textViewshareHint.setVisibility(View.GONE);





        }/*else if(of.equalsIgnoreCase("ChallToSocial")){
            linearLayoutImageIcon.setVisibility(View.GONE);
            linearLayoutmap.setVisibility(View.GONE);


        }else {
            linearLayoutImageIcon.setVisibility(View.VISIBLE);
            linearLayoutmap.setVisibility(View.VISIBLE);
        }*/


    }


    public void whatsAppShare() {
        whatsAppIconSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mapActivity = "map";
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I have taken a challenge on the FQ app. To watch and beat my score, download the app");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, linkText + "\n https://furofq.app.link/" + actOpen + "/" + sharechallengeid + "/" + senderId);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void fbShare() {
        fbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doFBShareLink(linkText + "\n https://furofq.app.link/" + actOpen + "/" + sharechallengeid + "/" + senderId);

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

    public void copysoialtext() {

        copyTextSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", linkText + "\n https://furofq.app.link/" + actOpen + "/" + sharechallengeid + "/" + senderId);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), " Link Copied now you can share !!", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
