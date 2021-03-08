package com.app.furoapp.fragment.tutorials;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.FragmentForgetPasswordBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.model.emailVerified.EmailVerifiedRequest;
import com.app.furoapp.model.emailVerified.EmailVerifiedResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordFragment extends Fragment {

    FragmentForgetPasswordBinding binding;
    TutorialScreen tutorialScreen;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    private TextView emailSendTv;
    private EditText emailVerifyEt;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tutorialScreen = (TutorialScreen) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_forget_password, container, false);
        View view = binding.getRoot();
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

        return view;
    }

    public ForgetPasswordFragment() {

    }

    public static ForgetPasswordFragment newInstance(String name) {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    private void emailVerified() {

        Boolean check = true;

        String email_verified = emailVerifyEt.getText().toString().trim();
        if (TextUtils.isEmpty(email_verified.trim()) || emailVerifyEt.length() == 0) {
            Util.displayToast(getActivity(), "Please enter valid email ID ");
            check = false;
        }
        if (check) {

            if (Util.isInternetConnected(getContext())) {
                linearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                EmailVerifiedRequest emailVerifiedRequest = new EmailVerifiedRequest();
                emailVerifiedRequest.setEmail(email_verified);

                RestClient.userEmailVerified(emailVerifiedRequest, new Callback<EmailVerifiedResponse>() {

                    @Override
                    public void onResponse(Call<EmailVerifiedResponse> call, Response<EmailVerifiedResponse> response) {
                        linearLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        if (response != null) {
                            if (response.body() != null) {
                                if (response.body().getStatus().equalsIgnoreCase("3")) {
                                    tutorialScreen.setDisplayFragment(EnumConstants.PASSWORD_CHANGE_FRAGMENT, null);
                                    Toast.makeText(tutorialScreen, "email verified", Toast.LENGTH_SHORT).show();

                                    String userOtp = String.valueOf(response.body().getOtp());
                                    FuroPrefs.putString(getContext(), "user_otp", userOtp);
                                    FuroPrefs.putString(getContext(), "user_mail", email_verified);


                                } else if (response.body().getStatus().equalsIgnoreCase("0")) {
                                    Toast.makeText(tutorialScreen, " email doesn't exist", Toast.LENGTH_SHORT).show();


                                }

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EmailVerifiedResponse> call, Throwable t) {
                        Toast.makeText(tutorialScreen, "Something went wrong!!!", Toast.LENGTH_SHORT).show();


                    }
                });


            }


        }


    }


}






