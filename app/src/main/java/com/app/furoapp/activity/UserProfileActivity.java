package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {
    ImageView imageView,backButton;
    TextView textView,noprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        imageView= findViewById(R.id.userImage);
        backButton= findViewById(R.id.BackButton);
        noprofile = findViewById(R.id.noProfile);
        textView = findViewById(R.id.usernameprofile);

        Bundle bundle = getIntent().getExtras();
        final String name = bundle.getString("name");

        String image = bundle.getString("image");

        textView.setText(name);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        if (!TextUtils.isEmpty(image)){
            Picasso.with(this).load(image).error(R.drawable.sample_image)
                    .into(imageView);
        }else if(TextUtils.isEmpty(image)){
            noprofile.setVisibility(View.VISIBLE);
        }

    }
}
