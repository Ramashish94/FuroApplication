package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.furoapp.R;

public class HelpandSupportActivity extends AppCompatActivity {
    RelativeLayout relativeLayoutContactus, relativeLayoutaboutUs, relativeLayoutfeedback, relativeLayoutrateapp, relativeLayoutshareapp, relativeLayouttermuse, relativeLayoutprivacypolicy;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpand_support);
        imageView = findViewById(R.id.ivBackBtnnew);
        relativeLayoutfeedback = findViewById(R.id.Feedbackrelative);
        relativeLayoutContactus = findViewById(R.id.contactus);
        relativeLayoutrateapp = findViewById(R.id.rateapp);
        relativeLayoutaboutUs = findViewById(R.id.shareappaboutus);
        relativeLayoutshareapp = findViewById(R.id.shareapprelative);
        relativeLayouttermuse = findViewById(R.id.termanduserelative);
        relativeLayoutprivacypolicy = findViewById(R.id.privacypolicyrelative);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        relativeLayoutfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpandSupportActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        relativeLayoutaboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpandSupportActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        relativeLayoutprivacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpandSupportActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });

        relativeLayouttermuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpandSupportActivity.this, TermsOfUseActivity.class);
                startActivity(intent);
            }
        });
        relativeLayoutContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpandSupportActivity.this, ContactUsActivity.class);
                startActivity(intent);

            }
        });
        relativeLayoutshareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=com.app.furoapp&ah=6k4dewAj7xLuYXgtSTZmJToGouQ";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "https://play.google.com/store/apps/details?id=com.app.furoapp&ah=6k4dewAj7xLuYXgtSTZmJToGouQ");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });
        relativeLayoutrateapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.app.furoapp&ah=6k4dewAj7xLuYXgtSTZmJToGouQ\"");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=com.app.furoapp&ah=6k4dewAj7xLuYXgtSTZmJToGouQ")));
                }

            }
        });
    }
}
