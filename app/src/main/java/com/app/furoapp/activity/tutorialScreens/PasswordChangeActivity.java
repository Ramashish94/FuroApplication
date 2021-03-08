package com.app.furoapp.activity.tutorialScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.ActivityPasswordChangeBinding;
import com.app.furoapp.model.passwordChanged.PasswordChangedRequest;
import com.app.furoapp.model.passwordChanged.PasswordChangedResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PasswordChangeActivity extends AppCompatActivity {
    ActivityPasswordChangeBinding binding;
    TutorialScreen tutorialScreen;
    private EditText otpEt, emailEt, passwordEt, confirmPasswordEt;
    private TextView passwordChanged,emailTv;

    String otpForUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_change);

      /*  otpForUser = FuroPrefs.getString(getApplicationContext(), "user_otp");*/

        otpEt = findViewById(R.id.user_otp);
        emailTv = findViewById(R.id.emailtextview);
        emailEt = findViewById(R.id.email_id_Forget);
        passwordEt = findViewById(R.id.password_forget);
        confirmPasswordEt = findViewById(R.id.confirm_password);
        passwordChanged = findViewById(R.id.changedPassword);
       /* otpForUser = FuroPrefs.getString(getApplicationContext(), "user_otp");*/
        String email = FuroPrefs.getString(getApplicationContext(), "user_mail");

       /* otpEt.setText(otpForUser);*/
        emailTv.setText(email);

        passwordChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changedPasswordVerification();
            }
        });



    }



    private void changedPasswordVerification() {

        Boolean check = true;
        String otp = otpEt.getText().toString().trim();
        String email = emailTv.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String confirmPassword = confirmPasswordEt.getText().toString().trim();

        if (TextUtils.isEmpty(otp.trim()) || otp.length() == 0) {
            Util.displayToast(getApplicationContext(), "Please enter  otp ");
            check = false;
        }


        if (TextUtils.isEmpty(password.trim()) || passwordEt.length() == 0) {
            Util.displayToast(getApplicationContext(), "Please enter a valid Password ");
            check = false;
        }
        if (TextUtils.isEmpty(confirmPassword.trim()) || confirmPasswordEt.length() == 0) {
            Util.displayToast(getApplicationContext(), "Please enter a valid password");
            check = false;
        }
        if (passwordEt.getText().toString().equals(confirmPasswordEt.getText().toString())) {


        } else if (passwordEt.getText().toString() != (confirmPasswordEt.getText().toString())) {

            Toast.makeText(PasswordChangeActivity.this, "Wrong username or password.!",
                    Toast.LENGTH_LONG).show();
            check = false;

        }
        if (check) {
            PasswordChangedRequest passwordChangedRequest = new PasswordChangedRequest();
            passwordChangedRequest.setEmail(email);
            passwordChangedRequest.setNewPassword(password);
            passwordChangedRequest.setOtp(otp);

            Util.isInternetConnected(getApplicationContext());
            Util.showProgressDialog(this);
            RestClient.userPasswordChanged(passwordChangedRequest, new Callback<PasswordChangedResponse>() {
                @Override
                public void onResponse(Call<PasswordChangedResponse> call, Response<PasswordChangedResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(PasswordChangeActivity.this,LoginWithEmailActivity.class);
                        startActivity(intent);
                        finish();
                       // tutorialScreen.setDisplayFragment(EnumConstants.LOGIN_WITH_EMAIL, null);

                        Toast.makeText(PasswordChangeActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(PasswordChangeActivity.this, " The OTP you have entered is incorrect.Please enter a valid OTP. !!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<PasswordChangedResponse> call, Throwable t) {
                    Toast.makeText(tutorialScreen, "Something went wrong !!", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }


}








