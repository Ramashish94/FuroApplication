package com.app.furoapp.activity.challengeRecieve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.databinding.ActivityAfterSubmitReportBinding;

public class AfterSubmitReportActivity extends AppCompatActivity {
    ActivityAfterSubmitReportBinding binding;

    TextView textViewDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_after_submit_report);
        textViewDone =  binding.Done;
        textViewDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSubmitReportActivity.this, HomeMainActivity.class);
                intent.putExtra("contestpage","");
                startActivity(intent);

            }
        });
    }
}
