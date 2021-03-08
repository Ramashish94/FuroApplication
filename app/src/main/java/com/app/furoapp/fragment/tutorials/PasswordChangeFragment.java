package com.app.furoapp.fragment.tutorials;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.FragmentPasswordChangedBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.model.passwordChanged.PasswordChangedRequest;
import com.app.furoapp.model.passwordChanged.PasswordChangedResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PasswordChangeFragment extends Fragment {

    FragmentPasswordChangedBinding binding;
    TutorialScreen tutorialScreen;
    private EditText otpEt, emailEt, passwordEt, confirmPasswordEt;
    private TextView passwordChanged;

    String otpForUser;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tutorialScreen = (TutorialScreen) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_password_changed, container, false);
        View view = binding.getRoot();

        otpForUser = FuroPrefs.getString(getContext(), "user_otp");

        otpEt = view.findViewById(R.id.user_otp);
        emailEt = view.findViewById(R.id.email_id_Forget);
        passwordEt = view.findViewById(R.id.password_forget);
        confirmPasswordEt = view.findViewById(R.id.confirm_password);
        passwordChanged = view.findViewById(R.id.changedPassword);
        otpForUser = FuroPrefs.getString(getContext(), "user_otp");
        String email = FuroPrefs.getString(getContext(), "user_mail");

        otpEt.setText(otpForUser);
        emailEt.setText(email);

        passwordChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changedPasswordVerification();
            }
        });


        return view;
    }

    public PasswordChangeFragment() {

    }

    public static PasswordChangeFragment newInstance(String name) {
        PasswordChangeFragment fragment = new PasswordChangeFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    private void changedPasswordVerification() {

        Boolean check = true;
        String otp = otpEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String confirmPassword = confirmPasswordEt.getText().toString().trim();

        if (TextUtils.isEmpty(otp.trim()) || otp.length() == 0) {
            Util.displayToast(getActivity(), "Please enter valid otp ");
            check = false;
        }
        if (TextUtils.isEmpty(email.trim()) || emailEt.length() == 0) {
            Util.displayToast(getActivity(), "Please enter valid emailId");
            check = false;
        }

        if (TextUtils.isEmpty(password.trim()) || passwordEt.length() == 0) {
            Util.displayToast(getActivity(), "Please enter valid Password ");
            check = false;
        }
        if (TextUtils.isEmpty(confirmPassword.trim()) || confirmPasswordEt.length() == 0) {
            Util.displayToast(getActivity(), "Please enter valid emailId");
            check = false;
        }
        if (passwordEt.getText().toString().equals(confirmPasswordEt.getText().toString())) {


        } else if (passwordEt.getText().toString() != (confirmPasswordEt.getText().toString())) {

            Toast.makeText(getApplicationContext(), "Password does not match!",
                    Toast.LENGTH_LONG).show();
            check = false;

        }
        if (check) {
            PasswordChangedRequest passwordChangedRequest = new PasswordChangedRequest();
            passwordChangedRequest.setEmail(email);
            passwordChangedRequest.setNewPassword(password);
            passwordChangedRequest.setOtp(otp);

            Util.isInternetConnected(getContext());
            Util.showProgressDialog(getContext());
            RestClient.userPasswordChanged(passwordChangedRequest, new Callback<PasswordChangedResponse>() {
                @Override
                public void onResponse(Call<PasswordChangedResponse> call, Response<PasswordChangedResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        tutorialScreen.setDisplayFragment(EnumConstants.LOGIN_WITH_EMAIL, null);

                        Toast.makeText(tutorialScreen, "password Reset Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(tutorialScreen, "password not Changed", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<PasswordChangedResponse> call, Throwable t) {

                }
            });


        }

    }


}







