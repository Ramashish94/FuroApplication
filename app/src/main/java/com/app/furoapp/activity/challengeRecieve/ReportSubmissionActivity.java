package com.app.furoapp.activity.challengeRecieve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.databinding.ActivityReportSubmissionBinding;
import com.app.furoapp.model.report.ReportIssueRequest;
import com.app.furoapp.model.report.ReportIssueResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportSubmissionActivity extends AppCompatActivity {
    ActivityReportSubmissionBinding submissionBinding;
    RelativeLayout relativeLayoutillegal, relativeLayouterotic, relativeLayoutwrongdata, relativeLayoutviolates, relativeLayoutirrelevant, relativeLayoutjustdontlike;
    TextView tvIllegal, tvErotic, tvWrong, tvViolates, tvIrelevat, tvJustdontlike;
    ImageView ivIllegal, ivErotic, ivWrong, ivViolates, ivIrelevat, ivJustdontlike,tvivBackBtnnew;
    List<String> hash_set = new ArrayList<String>();
    TextView buttonReport;
    String something_elseIllegal, something_Violates, something_elseErotic, something_elseWrongData, something_Irrelevant, something_Justdontlikeit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        submissionBinding = DataBindingUtil.setContentView(this, R.layout.activity_report_submission);
        buttonReport = submissionBinding.report;
        relativeLayoutillegal = submissionBinding.Illegalrelative;
        relativeLayouterotic = submissionBinding.Eroticrelative;
        relativeLayoutwrongdata = submissionBinding.WrongDatarelative;
        relativeLayoutirrelevant = submissionBinding.Irrelevantrelative;
        relativeLayoutjustdontlike = submissionBinding.Justdontlikeitrelative;
        relativeLayoutviolates = submissionBinding.Violatesrelative;
        tvivBackBtnnew = submissionBinding.ivBackBtnnew;

        tvIllegal = submissionBinding.Illegal;
        tvErotic = submissionBinding.Erotic;
        tvWrong = submissionBinding.WrongData;
        tvIrelevat = submissionBinding.Irrelevant;
        tvJustdontlike = submissionBinding.Justdontlikeit;
        tvViolates = submissionBinding.Violates;


        ivErotic = submissionBinding.illegalErotic;
        ivWrong = submissionBinding.illegalWrongDatatick;
        ivViolates = submissionBinding.tickViolates;
        ivIrelevat = submissionBinding.tickIrrelevant;
        ivJustdontlike = submissionBinding.tickJustdontlikeit;
        ivIllegal = submissionBinding.illegaltick;


        setOnClickListeners1();
        setOnClickListeners2();
        setOnClickListeners3();
        setOnClickListeners4();
        setOnClickListeners5();
        setOnClickListeners6();
        tvivBackBtnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportIssue();
            }
        });


    }

    private void setOnClickListeners1() {
        submissionBinding.Illegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                something_elseIllegal = tvIllegal.getText().toString().trim();

                if (isSelected(something_elseIllegal)) {

                    submissionBinding.report.setVisibility(View.VISIBLE);
                    submissionBinding.illegaltick.setVisibility(View.VISIBLE);
                } else {
                    // submissionBinding.report.setVisibility(View.GONE);
                    submissionBinding.illegaltick.setVisibility(View.GONE);
                }
            }

        });


    }

    private void setOnClickListeners2() {
        submissionBinding.Erotic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                something_elseErotic = tvErotic.getText().toString().trim();

                if (isSelected(something_elseErotic)) {
                    submissionBinding.report.setVisibility(View.VISIBLE);
                    submissionBinding.illegalErotic.setVisibility(View.VISIBLE);
                } else {
                    // submissionBinding.report.setVisibility(View.GONE);
                    submissionBinding.illegalErotic.setVisibility(View.GONE);
                }
            }

        });


    }

    private void setOnClickListeners3() {
        submissionBinding.WrongData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                something_elseWrongData = tvWrong.getText().toString().trim();

                if (isSelected(something_elseWrongData)) {
                    submissionBinding.report.setVisibility(View.VISIBLE);
                    submissionBinding.illegalWrongDatatick.setVisibility(View.VISIBLE);
                } else {
                    // submissionBinding.report.setVisibility(View.GONE);
                    submissionBinding.illegalWrongDatatick.setVisibility(View.GONE);
                }
            }

        });


    }

    private void setOnClickListeners4() {
        submissionBinding.Irrelevant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                something_Irrelevant = tvIrelevat.getText().toString().trim();

                if (isSelected(something_Irrelevant)) {
                    submissionBinding.report.setVisibility(View.VISIBLE);
                    submissionBinding.tickIrrelevant.setVisibility(View.VISIBLE);
                } else {
                    //  submissionBinding.report.setVisibility(View.GONE);
                    submissionBinding.tickIrrelevant.setVisibility(View.GONE);
                }
            }

        });


    }

    private void setOnClickListeners5() {
        submissionBinding.Justdontlikeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                something_Justdontlikeit = tvJustdontlike.getText().toString().trim();

                if (isSelected(something_Justdontlikeit)) {
                    submissionBinding.report.setVisibility(View.VISIBLE);
                    submissionBinding.tickJustdontlikeit.setVisibility(View.VISIBLE);
                } else {
                    // submissionBinding.report.setVisibility(View.GONE);
                    submissionBinding.tickJustdontlikeit.setVisibility(View.GONE);
                }
            }

        });


    }

    private void setOnClickListeners6() {
        submissionBinding.Violates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                something_Violates = tvViolates.getText().toString().trim();

                if (isSelected(something_Violates)) {
                    submissionBinding.report.setVisibility(View.VISIBLE);
                    submissionBinding.tickViolates.setVisibility(View.VISIBLE);
                } else {
                    //  submissionBinding.report.setVisibility(View.GONE);
                    submissionBinding.tickViolates.setVisibility(View.GONE);
                }
            }

        });


    }


    public boolean isSelected(String text) {
        for (String string : hash_set) {
            if (string.equalsIgnoreCase(text)) {
                hash_set.remove(text);
                return false;
            }
        }
        hash_set.add(text);
        Log.i("hasghsat", String.valueOf(hash_set));
        return true;

    }


    public void reportIssue() {

        if(hash_set.size() > 0){
            String userIdReport = FuroPrefs.getString(getApplicationContext(), "loginUserId");
            int challengeidReport = FuroPrefs.getInt(getApplicationContext(),"SubmissionChallenegeId",0);


            ReportIssueRequest reportIssueRequest = new ReportIssueRequest();
            reportIssueRequest.setReport(String.valueOf(hash_set));
            reportIssueRequest.setUserId(userIdReport);
            reportIssueRequest.setChallengeid(String.valueOf(challengeidReport));

            Util.showProgressDialog(this);

            RestClient.userReportIssue(reportIssueRequest, new Callback<ReportIssueResponse>() {
                @Override
                public void onResponse(Call<ReportIssueResponse> call, Response<ReportIssueResponse> response) {
                    Util.dismissProgressDialog();
                    if (response != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {

                            Intent intent = new Intent(ReportSubmissionActivity.this, AfterSubmitReportActivity.class);
                            startActivity(intent);


                        }


                    }


                }

                @Override
                public void onFailure(Call<ReportIssueResponse> call, Throwable t) {
                    Toast.makeText(ReportSubmissionActivity.this, "Check ", Toast.LENGTH_SHORT).show();

                }
            });


        }else{
            Toast.makeText(this, "Please select  atleast one point", Toast.LENGTH_SHORT).show();

        }

        }


}
