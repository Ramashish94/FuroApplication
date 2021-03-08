package com.app.furoapp.fragment.challenges;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.databinding.FragmentBoardInviteFriendsBinding;

import java.util.List;


public class  LeaderBoardInviteFragment extends Fragment {

    FragmentBoardInviteFriendsBinding binding;

    public static LeaderBoardInviteFragment newInstance(String name) {
        LeaderBoardInviteFragment fragment = new LeaderBoardInviteFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_invite_friends, container, false);
        View view = binding.getRoot();
        setOnClickListenersFacebook();
        setOnClickListnerWhatsapp();


        return view;
    }

    private void setOnClickListenersFacebook() {
        binding.challengeFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doFBShareLink("Do you know your Fitness Quotient? I’m inviting you to the Fitness Quotient by Furo Sports App. Click on the link below & install the FQ App. Know your fitness quotient, challenge yourself StayFitEveryday!\n https://play.google.com/store/apps/details?id=com.app.furoapp&ah=6k4dewAj7xLuYXgtSTZmJToGouQ");
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
                whatsappIntent.putExtra(Intent.EXTRA_TEXT,"Do you know your Fitness Quotient? I’m inviting you to the Fitness Quotient by Furo Sports App. Click on the link below & install the FQ App. Know your fitness quotient, challenge yourself StayFitEveryday!\n https://play.google.com/store/apps/details?id=com.app.furoapp&ah=6k4dewAj7xLuYXgtSTZmJToGouQ");
                try {
                    getActivity().startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }



    private void doFBShareLink(String link) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        Intent chooserIntent = Intent.createChooser(shareIntent, getString(R.string.share_via));

        // for 21+, we can use EXTRA_REPLACEMENT_EXTRAS to support the specific case of Facebook
        // (only supports a link)
        // >=21: facebook=link, other=text+link
        // <=20: all=link
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