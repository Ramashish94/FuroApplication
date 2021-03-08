package com.app.furoapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.challengeRecieve.AfterSubmitReportActivity;
import com.app.furoapp.databinding.ActivityInviteFriendsBinding;
import com.app.furoapp.databinding.FragmentBoardInviteFriendsBinding;
import com.app.furoapp.fragment.challenges.LeaderBoardInviteFragment;
import com.app.furoapp.utils.FuroPrefs;
 /*https://furofq.app.link/d/CgzDqX2uY2"+sharechallengeid*/
public class InviteFriendsActivity extends AppCompatActivity {
    ActivityInviteFriendsBinding binding;
    String sharechallengeid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invite_friends);
        sharechallengeid =   FuroPrefs.getString(getApplicationContext(),"challengeidforshare");
        setOnClickListenersFacebook();
        setOnClickListnerWhatsapp();
    }


    private void setOnClickListenersFacebook() {
        binding.challengeFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doFBShareLink("Do you know your Fitness Quotient?\n" +
                        "I’m inviting you to the Fitness Quotient by Furo Sports App. Click on the link below & install the FQ App. \n" +
                        "Know your fitness quotient, challenge yourself & #StayFitEveryday! \n https://play.google.com/store/apps/details?id=com.app.furoapp&ah=6k4dewAj7xLuYXgtSTZmJToGouQ");
            }
        });


    }

    private void setOnClickListnerWhatsapp() {
        binding.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I have taken a challenge on the FQ app. To watch and beat my score, download the app");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, " Do you know your Fitness Quotient?\n" +
                        "I’m inviting you to the Fitness Quotient by Furo Sports App. Click on the link below & install the FQ App. \n" +
                        "Know your fitness quotient, challenge yourself & #StayFitEveryday! \n https://play.google.com/store/apps/details?id=com.app.furoapp&ah=6k4dewAj7xLuYXgtSTZmJToGouQ");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {                                                                                   https://example.app.link/fzmLEhobLD?$custom_data=123&hello=world
                    Toast.makeText(InviteFriendsActivity.this, "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();

                }

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
