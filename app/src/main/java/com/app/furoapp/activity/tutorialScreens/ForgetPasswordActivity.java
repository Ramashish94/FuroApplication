package com.app.furoapp.activity.tutorialScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.databinding.ActivityForgetPasswordBinding;
import com.app.furoapp.model.emailVerified.EmailVerifiedRequest;
import com.app.furoapp.model.emailVerified.EmailVerifiedResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    ActivityForgetPasswordBinding binding;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    private TextView emailSendTv;
    private EditText emailVerifyEt;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        emailSendTv = binding.sendEmailVerified;
        progressBar = binding.progressBar;
        linearLayout = binding.linearLayout;
        emailVerifyEt = binding.verifiedEmail;
        emailSendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailVerified();

            }
        });


    }




    private void emailVerified() {

        Boolean check = true;

        String email_verified = emailVerifyEt.getText().toString().trim();
        if (TextUtils.isEmpty(email_verified.trim()) || emailVerifyEt.length() == 0) {
            Util.displayToast(getApplicationContext(), "Please enter valid email ID ");
            check = false;
        }
        if (check) {

            if (Util.isInternetConnected(getApplicationContext())) {
                linearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                EmailVerifiedRequest emailVerifiedRequest = new EmailVerifiedRequest();
                emailVerifiedRequest.setEmail(email_verified);

                RestClient.userEmailVerified(emailVerifiedRequest, new Callback<EmailVerifiedResponse>() {

                    @Override
                    public void onResponse(Call<EmailVerifiedResponse> call, Response<EmailVerifiedResponse> response) {
                        linearLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        if (response.code() == 200) {
                            if (response != null) {
                                if (response.body() != null) {
                                    if (response.body().getStatus().equalsIgnoreCase("3")) {
                                        Intent intent = new Intent(ForgetPasswordActivity.this, PasswordChangeActivity.class);
                                        startActivity(intent);
                                        finish();
                                        /*tutorialScreen.setDisplayFragment(EnumConstants.PASSWORD_CHANGE_FRAGMENT, null);*/
                                        Toast.makeText(ForgetPasswordActivity.this, "OTP is sent to the registered email address ", Toast.LENGTH_SHORT).show();

                                    /*String userOtp = String.valueOf(response.body().getOtp());
                                    FuroPrefs.putString(getApplicationContext(), "user_otp", userOtp);*/
                                    FuroPrefs.putString(getApplicationContext(), "user_mail", email_verified);

                                    } else if (response.body().getStatus().equalsIgnoreCase("0")) {
                                        Toast.makeText(ForgetPasswordActivity.this, " email doesn't exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else if (response.code() == 500) {
                            Toast.makeText(ForgetPasswordActivity.this,response.code() +" Internal Server Error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EmailVerifiedResponse> call, Throwable t) {
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });


            }


        }


    }


}






