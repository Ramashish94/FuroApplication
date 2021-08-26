package com.app.furoapp.activity.newFeature.bmiCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.fetchBmiDataModel.Bmi;
import com.app.furoapp.activity.newFeature.bmiCalculator.fetchBmiDataModel.FetchUserWiseDataResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordDataActivity extends AppCompatActivity {
    public ImageView ivBackArrow;
    public RecyclerView rvRecordedBmi;
    FetchRecordAdapter fetchRecordAdapter;
    List<Bmi> bmiArrayList = new ArrayList<>();
    private String getAccessToken;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_score);

        rvRecordedBmi = findViewById(R.id.rvRecordedBmi);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        setFetchRecyAdapter();
        getFetchUserWiseDataApi();
        clickEvent();

    }

    private void getFetchUserWiseDataApi() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        RestClient.getFetchUserWiseData(getAccessToken, new Callback<FetchUserWiseDataResponse>() {
            @Override
            public void onResponse(Call<FetchUserWiseDataResponse> call, Response<FetchUserWiseDataResponse> response) {
                loadingProgressBar.setVisibility(View.GONE);
                if (response.code() == 200 && response.body() != null && response.body().getStatus() != null) {
                    notifyFetchUserWiseData(response.body().getBmi());
                } else {
                    Toast.makeText(RecordDataActivity.this, +response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FetchUserWiseDataResponse> call, Throwable t) {
                Toast.makeText(RecordDataActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFetchRecyAdapter() {
        fetchRecordAdapter = new FetchRecordAdapter(getApplicationContext(), bmiArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvRecordedBmi.setLayoutManager(layoutManager);
        rvRecordedBmi.setItemAnimator(new DefaultItemAnimator());
        rvRecordedBmi.setAdapter(fetchRecordAdapter);
    }

    private void notifyFetchUserWiseData(List<Bmi> bmi) {
        bmiArrayList.clear();
        bmiArrayList.addAll(bmi);
        if (bmiArrayList != null && bmiArrayList.size() > 0) {
            rvRecordedBmi.setVisibility(View.VISIBLE);
            fetchRecordAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No record found !", Toast.LENGTH_SHORT).show();
        }
    }


    private void clickEvent() {
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}