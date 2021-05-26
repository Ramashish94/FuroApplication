package com.app.furoapp.activity.newFeature.bmiCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.furoapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecordedScoreActivity extends AppCompatActivity {
    public ImageView ivBackArrow;
    public RecyclerView rvRecordedBmi;
    RecordedScoreAdapter recordedScoreAdapter;
    List<RecordedBmiModel> recordedBmiModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_score);

        rvRecordedBmi = findViewById(R.id.rvRecordedBmi);
        ivBackArrow = findViewById(R.id.ivBackArrow);

        setSavedRecyAdapter();
        clickEvent();

    }


    private void setSavedRecyAdapter() {
        recordedScoreAdapter = new RecordedScoreAdapter(getApplicationContext(), recordedBmiModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvRecordedBmi.setLayoutManager(layoutManager);
        rvRecordedBmi.setItemAnimator(new DefaultItemAnimator());
        rvRecordedBmi.setAdapter(recordedScoreAdapter);
        List<RecordedBmiModel> recordedBmiModelArrayList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            RecordedBmiModel recordedBmiModel = new RecordedBmiModel();
            recordedBmiModel.setBmi("24.5" + i);
            recordedBmiModel.setGender("Male");
            recordedBmiModel.setHeight("5'4" + i);
            recordedBmiModel.setWeight("78" + "kg" + i);
            recordedBmiModelArrayList.add(recordedBmiModel);
        }
        RecordedScoreAdapter recordedScoreAdapter = new RecordedScoreAdapter(getApplicationContext(), recordedBmiModelArrayList);
        rvRecordedBmi.setAdapter(recordedScoreAdapter);
    }

    private void clickEvent() {
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}