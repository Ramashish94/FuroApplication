package com.app.furoapp.fragment.tutorials;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.SplashActivity;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.FragmentLoginEmailBinding;
import com.app.furoapp.enums.EnumConstants;
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

public class LoginWithEmail extends Fragment {

    FragmentLoginEmailBinding binding;
    TutorialScreen tutorialScreen;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    private ImageView loginTextView;
    private EditText edtPassword, edtGmail;
    private TextView forgetTextView;
    CheckBox loginCheck;
    boolean check = false;
    private String fcm_token;
    private String androidDeviceId;
     public  static  String  accessToken;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tutorialScreen = (TutorialScreen) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login_email, container, false);
        View view = binding.getRoot();

        loginTextView = binding.etLoginButton;
        linearLayout = binding.linearLayout;
        progressBar = binding.progressBar;
        edtPassword = binding.etPasswordLogin;
        edtGmail = binding.etEmailLogin;
        loginCheck = binding.loginCheckbox;
        forgetTextView = binding.forgetPassword;

       /* androidDeviceId = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);*/

        String user_name = FuroPrefs.getString(getContext(), "user_name");
        FuroPrefs.putString(getActivity(), "welcome_user_name", user_name);

        fcm_token = FuroPrefs.getString(getActivity(), "token");

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        forgetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tutorialScreen.setDisplayFragment(FORGET_PASSWORD_FRAGMENT, null);
            }
        });


        return view;

    }

    public LoginWithEmail() {

    }

    public static LoginWithEmail newInstance(String name) {
        LoginWithEmail fragment = new LoginWithEmail();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
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
            Util.displayToast(getActivity(), "Please enter valid email ID ");
            return;
        }
        if (TextUtils.isEmpty(pass_str.trim()) || pass_str.length() == 0) {
            Util.displayToast(getActivity(), "Please enter valid password");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_str).matches()) {
            Util.displayToast(getActivity(), "Please enter valid email");
            return;
        }
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email_str);
        loginRequest.setFcmToken(fcm_token);
        loginRequest.setPassword(pass_str);

        if (Util.isInternetConnected(getContext())) {
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            RestClient.loginNewUser(loginRequest, new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    linearLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getReasons().equalsIgnoreCase("0")) {
                            String loginUserId = String.valueOf(response.body().getUser().getId());
                            String userNameee = response.body().getUser().getName();
                            String Image = response.body().getUser().getImage();
                            accessToken = response.body().getAccessToken();
                            FuroPrefs.putString(getContext(), "accessToken", accessToken);
                            FuroPrefs.putString(getContext(), "loginUserId", loginUserId);
                            FuroPrefs.putString(getContext(), "userimage", Image);
                            FuroPrefs.putString(getContext(), "loginUserNameNew", userNameee);
                            tutorialScreen.setDisplayFragment(EnumConstants.HOME_TUTORIAL_PAGE, null);
                            Toast.makeText(tutorialScreen, "Login Successful", Toast.LENGTH_SHORT).show();

                        } else if (response.body().getReasons().equalsIgnoreCase("1")) {
                            FuroPrefs.putBoolean(getContext(), Constants.LOGGEDIN, true);
                            String loginUserId = String.valueOf(response.body().getUser().getId());
                            accessToken = response.body().getAccessToken();
                            FuroPrefs.putString(getContext(), "accessToken", accessToken);

                            String userNameNew = response.body().getUser().getName();
                            String Image = response.body().getUser().getImage();
                            FuroPrefs.putString(getContext(), "loginUserId", loginUserId);
                            FuroPrefs.putString(getContext(), "userimage", Image);
                            FuroPrefs.putString(getContext(), "loginUserNameNew", userNameNew);
                            Intent intent = new Intent(getActivity(), HomeMainActivity.class);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(tutorialScreen, "  Email or Password is Incorrect", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(tutorialScreen, "SomeThing went wrong !", Toast.LENGTH_SHORT).show();

                }
            });


        }


    }

}











