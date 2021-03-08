package com.app.furoapp.activity.tutorialScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.furoapp.R;
import com.app.furoapp.activity.GlideApp;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.ActivityWelcomeUserYouAreInBinding;
import com.app.furoapp.utils.FuroPrefs;



public class WelcomeUserYouAreInActivity extends AppCompatActivity {
    ActivityWelcomeUserYouAreInBinding binding;
    TutorialScreen tutorialScreen;
    private TextView welcomeName ;
    private ImageView welcomeImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_user_you_are_in);
        welcomeImage = binding.ivWelcon;

        welcomeName = binding.tvTextUserName;
        String image = FuroPrefs.getString(getApplicationContext(),"userImage");

        GlideApp.with(this).load(image).error(R.drawable.user_icon)
                .into(welcomeImage);
        String welcome_user_name =FuroPrefs.getString(getApplicationContext(),"loginUserNameNew");
        welcomeName.setText(welcome_user_name);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        binding.etSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeUserYouAreInActivity.this,WhatBringsYouToFuroActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                intent.putExtra("contestpage","");
                startActivity(intent);
                finish();
            }
        });
    }


}
