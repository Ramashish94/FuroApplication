package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.app.furoapp.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private WebView webView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        imageView = findViewById(R.id.ivBackBtnnew);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        webView = (WebView) findViewById(R.id.webView1);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://api.fitnessquotient.in/privacy-policy");/*https://furoapi.tk/privacy-policy*/

        }

    }

