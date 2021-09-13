package com.app.furoapp.activity.newFeature.bmiCalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.newFeature.bmiCalculator.storeBmiModel.BmiStoreDataRequest;
import com.app.furoapp.activity.newFeature.bmiCalculator.storeBmiModel.BmiStoreDataResponse;
import com.app.furoapp.activity.newFeature.fqTips.BmiDatum;
import com.app.furoapp.activity.newFeature.fqTips.TipsResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourScoreActivity extends AppCompatActivity {
    public LinearLayout llDiscard;
    public Button btnSaveData;
    public ImageView ivBackArrow, ivDiscard;
    public TextView tvYourBmiScore, tvShowBmiTxt, tvBtnFindYourBmi, tvPrizmTips;
    public String genderVal, userAge, userHeightInCm, userWeight;
    public double bmiScore;
    public String getAccessToken;
    public double userHeightValInCm;
    public double userWeightValInKg;
    private String findForOthersBmi;
    public String bmiType = "Find yours";
    private String findBmiType;
    private int getBmi;
    private Handler tipsHandler = new Handler();
    private List<BmiDatum> tipsList;
    private int tipsListSize = 0;
    private int tipsStart = 0;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Intent intent;
    private ProgressBar loadingProgressBar, loadingProgressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_score);

        btnSaveData = findViewById(R.id.btnSaveData);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        tvYourBmiScore = findViewById(R.id.tvYourBmiScore);
        tvBtnFindYourBmi = findViewById(R.id.tvBtnFindYourBmi);
        llDiscard = findViewById(R.id.llDiscard);
        ivDiscard = findViewById(R.id.ivDiscard);
        tvShowBmiTxt = findViewById(R.id.tvShowBmiTxt);
        tvPrizmTips = findViewById(R.id.tvPrizmTips);
        findBmiType = getIntent().getStringExtra("getFindBmiType");

        if (bmiType.equalsIgnoreCase(findBmiType)) {
            btnSaveData.setVisibility(View.VISIBLE);
            ivDiscard.setVisibility(View.VISIBLE);
            tvBtnFindYourBmi.setVisibility(View.GONE);
        } else {
            btnSaveData.setVisibility(View.GONE);
            ivDiscard.setVisibility(View.GONE);
            tvBtnFindYourBmi.setVisibility(View.VISIBLE);
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        getManipulateData();
        bmiScoreCalculation();
        callTipsApi();

        clickEvent();

    }


    private void getManipulateData() {
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        findForOthersBmi = FuroPrefs.getString(getApplicationContext(), Constants.FIND_TYPE);
        genderVal = FuroPrefs.getString(getApplicationContext(), Constants.GENDER_VAL);
        userAge = FuroPrefs.getString(getApplicationContext(), Constants.USER_AGE_SELECT);
        userHeightInCm = getIntent().getStringExtra("userHeightInCm");
        userWeight = getIntent().getStringExtra("userWeightInKg");
    }

    private void bmiScoreCalculation() {
        userHeightValInCm = Double.parseDouble(userHeightInCm);
        userWeightValInKg = Double.parseDouble(userWeight);
        //[weight (kg) / height (cm) / height (cm)] x 10,000
        bmiScore = ((userWeightValInKg / userHeightValInCm / userHeightValInCm) * 10000);
        tvYourBmiScore.setText(new DecimalFormat("##.##").format(bmiScore));

        getBmi = (int) bmiScore;
        if (getBmi < 18.5) {
            tvShowBmiTxt.setText("Under Weight BMI");
        } else if (getBmi >= 18.5 && getBmi <= 24.9) {
            tvShowBmiTxt.setText("Normal BMI");
        } else if (getBmi >= 25 && getBmi <= 29.9) {
            tvShowBmiTxt.setText("Over Weight BMI");
        } /*else if (getBmi >= 30 && getBmi <= 39) {
            tvShowBmiTxt.setText("Obese BMI");
        }*/ else if (getBmi >= 30) {
            //tvShowBmiTxt.setText("Extremely Obese BMI");
            tvShowBmiTxt.setText("Obese BMI");
        }


    }

    private void clickEvent() {
        llDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindYourBmiActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToSaveBmiApi();
            }
        });
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindYourBmiActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void callToSaveBmiApi() {
        BmiStoreDataRequest bmiStoreDataRequest = new BmiStoreDataRequest();
        bmiStoreDataRequest.setGender(genderVal);
        bmiStoreDataRequest.setAge(userAge);
        bmiStoreDataRequest.setHeight(String.valueOf(userHeightValInCm));
        bmiStoreDataRequest.setWeight(String.valueOf(userWeightValInKg));
        bmiStoreDataRequest.setBmi(String.valueOf(bmiScore));
        Util.showProgressDialog(getApplicationContext());
        RestClient.getBmiStoreData(getAccessToken, bmiStoreDataRequest, new Callback<BmiStoreDataResponse>() {
            @Override
            public void onResponse(Call<BmiStoreDataResponse> call, Response<BmiStoreDataResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus() != null) {
                        Toast.makeText(YourScoreActivity.this, "Bmi data saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), FindYourBmiActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BmiStoreDataResponse> call, Throwable t) {
                Toast.makeText(YourScoreActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callTipsApi() {
        if (Util.isInternetConnected(getApplicationContext())) {
            Utils.showProgressDialogBar(getApplicationContext());
            RestClient.getAllTipsData(getAccessToken, new Callback<TipsResponse>() {
                @Override
                public void onResponse(Call<TipsResponse> call, Response<TipsResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        //   Log.d(TAG, "onResponse() called with: , response = [" + response.body() + "]");
                        if (response.body() != null) {

                            if (response.body().getData() != null
                                    && response.body().getData().getBmiData() != null
                                    && response.body().getData().getBmiData().size() > 0) {
                                tipsList = response.body().getData().getBmiData();
                                tipsListSize = tipsList.size();
                                tipsHandler.postDelayed(tipsRunnable, 0);

                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "No tips data found ", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        if (response.code() == 500) {
                            Toast.makeText(getApplicationContext(), "Internal server error !", Toast.LENGTH_SHORT).show();
                        }
                        if (response.code() == 403) {
                            Toast.makeText(getApplicationContext(), response.code() + "Session expire Please login again", Toast.LENGTH_SHORT).show();
                            getAlertTokenDialog();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TipsResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private Runnable tipsRunnable = new Runnable() {
        public void run() {
            if (tipsList != null && tipsList.size() > 0) {
                if (tipsStart == (tipsListSize - 1)) {
                    tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph());
                    tipsStart = 0;
                } else {
                    if (tipsList != null && tipsList.size() > 0) {
                        tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph());
                        tipsStart++;
                    }
                }
            }
            tipsHandler.postDelayed(this, 5000);
        }
    };


    private void getAlertTokenDialog() {
        if (getAccessToken != null) {
            dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.session_expired_layout, null);
            dialogBuilder.setView(dialogView);
            dialog = dialogBuilder.create();
            ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
            TextView text_logout = dialogView.findViewById(R.id.text_logout);
            TextView noiwanttocontinue = dialogView.findViewById(R.id.noiwanttocontinuee);
            LinearLayout llLogOut = dialogView.findViewById(R.id.llLogOut);

            btn_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            noiwanttocontinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            text_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FuroPrefs.clear(getApplicationContext());
                    googleSignOut();
                    Intent intent = new Intent(getApplicationContext(), LoginTutorialScreen.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });
            dialog.show();
        } else {

        }
    }

    public void googleSignOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Toast.makeText(ActivityMain.this, "Google Sign Out done.", Toast.LENGTH_SHORT).show();
                        revokeAccess();
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Toast.makeText(ActivityMain.this, "Google access revoked.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}