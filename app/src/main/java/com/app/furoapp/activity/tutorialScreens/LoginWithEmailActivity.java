package com.app.furoapp.activity.tutorialScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.ActivityLoginWithEmailBinding;
import com.app.furoapp.model.loginUser.LoginRequest;
import com.app.furoapp.model.loginUser.LoginResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.furoapp.enums.EnumConstants.FORGET_PASSWORD_FRAGMENT;

public class LoginWithEmailActivity extends AppCompatActivity {
    ActivityLoginWithEmailBinding binding;
    TutorialScreen tutorialScreen;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    private ImageView loginTextView;
    private EditText edtPassword, edtGmail;
    private TextView forgetTextView;
    CheckBox loginCheck;
    boolean check = false;
    private String fcm_token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_with_email);

        loginTextView = binding.etLoginButton;
        linearLayout = binding.linearLayout;
        progressBar = binding.progressBar;
        edtPassword = binding.etPasswordLogin;
        edtGmail = binding.etEmailLogin;
        loginCheck = binding.loginCheckbox;
        forgetTextView = binding.forgetPassword;

        String user_name = FuroPrefs.getString(getApplicationContext(), "user_name");

        FuroPrefs.putString(getApplicationContext(), "welcome_user_name", user_name);
        fcm_token = "";

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        forgetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginWithEmailActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                // tutorialScreen.setDisplayFragment(FORGET_PASSWORD_FRAGMENT, null);
            }
        });


    }


    private void validation() {

        String email_str = edtGmail.getText().toString().trim();
        String pass_str = edtPassword.getText().toString().trim();

        if (loginCheck.isChecked()) {
            check = true;
            FuroPrefs.putBoolean(tutorialScreen, Constants.LoginCheck, check);
        } else {
            check = false;
            FuroPrefs.putBoolean(tutorialScreen, Constants.LoginCheck, check);
        }

        if (TextUtils.isEmpty(email_str.trim()) || edtGmail.length() == 0) {
            Util.displayToast(getApplicationContext(), "Please enter valid email ID ");
            return;
        }
        if (TextUtils.isEmpty(pass_str.trim()) || pass_str.length() == 0) {
            Util.displayToast(getApplicationContext(), "Please enter valid password");
            return;
        }

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email_str);
        loginRequest.setFcmToken(fcm_token);
        loginRequest.setPassword(pass_str);

        if (Util.isInternetConnected(getApplicationContext())) {
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            RestClient.loginNewUser(loginRequest, new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    linearLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getReasons().equalsIgnoreCase("0")) {
                            FuroPrefs.putBoolean(getApplicationContext(), Constants.LOGGEDALERADYIN, true);
                            String loginUserId = String.valueOf(response.body().getUser().getId());
//                            Toast.makeText(LoginWithEmailActivity.this, ""+loginUserId, Toast.LENGTH_SHORT).show();
                            Log.e("userId",loginUserId);
                            String userNameee = response.body().getUser().getName();
                            String Image = response.body().getUser().getImage();
                            String accessToken = response.body().getAccessToken();
                            FuroPrefs.putString(getApplicationContext(), "accessToken", accessToken);
                            FuroPrefs.putString(getApplicationContext(), "loginUserId", loginUserId);
                            FuroPrefs.putString(getApplicationContext(), "userimage", Image);
                            FuroPrefs.putString(getApplicationContext(), "loginUserNameNew", userNameee);

                            Intent intent = new Intent(getApplicationContext(), WelcomeUserYouAreInActivity.class);
                            startActivity(intent);
                            finish();
                            //tutorialScreen.setDisplayFragment(EnumConstants.HOME_TUTORIAL_PAGE, null);
                            Toast.makeText(LoginWithEmailActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        } else if (response.body().getReasons().equalsIgnoreCase("1")) {
                            FuroPrefs.putBoolean(getApplicationContext(), Constants.LOGGEDIN, true);
                            String loginUserId = String.valueOf(response.body().getUser().getId());
                            Log.e("userId",loginUserId);
                          
                            String userNameNew = response.body().getUser().getName();
                            String Image = response.body().getUser().getImage();
                            FuroPrefs.putString(getApplicationContext(), "loginUserId", loginUserId);
                            FuroPrefs.putString(getApplicationContext(), "userimage", Image);
                            FuroPrefs.putString(getApplicationContext(), "loginUserNameNew", userNameNew);
                            Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                            intent.putExtra("contestpage","");
                            startActivity(intent);

                        }

                    } else if (response.code() == 402) {
                        Toast.makeText(LoginWithEmailActivity.this, "Username doesnot exist ", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(LoginWithEmailActivity.this, "Email doesnot exist ", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(LoginWithEmailActivity.this, "Password is incorrect ", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    Toast.makeText(LoginWithEmailActivity.this, "SomeThing went wrong !", Toast.LENGTH_SHORT).show();

                }
            });


        }


    }

}

