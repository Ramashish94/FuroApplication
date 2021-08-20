package com.app.furoapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.furoapp.R;
import com.app.furoapp.model.FriendModel.AddFriend;
import com.app.furoapp.model.FriendModel.FriendListModel;
import com.app.furoapp.model.Settings.UpdateUserModel;
import com.app.furoapp.model.Settings.UserDetailsModel;
import com.app.furoapp.model.Settings.UserUpdate;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    private EditText ed_name, ed_email, ed_gender, ed_mobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        getUsersData();

        findViewById(R.id.ivBackBtnnew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString();
                String email = ed_email.getText().toString();
                String gender = ed_gender.getText().toString();

                String mobile = ed_mobile.getText().toString();


                UpdateUsersData(name, email, gender, mobile);
                /*n*/
            }
        });


    }

    private void init() {
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
        ed_gender = findViewById(R.id.ed_gender);
        ed_mobile = findViewById(R.id.ed_mobile);

    }

    private void getUsersData() {
        if (Util.isInternetConnected(this)) {
            Util.showProgressDialog(this);
            RestClient.getUserDetails(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), new AddFriend(FuroPrefs.getString(this, "loginUserId")), new Callback<UserDetailsModel>() {
                @Override
                public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                    Util.dismissProgressDialog();
                    UserDetailsModel userDetailsModel = response.body();
                    setView(userDetailsModel);

                }

                @Override
                public void onFailure(Call<UserDetailsModel> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(SettingsActivity.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Util.dismissProgressDialog();
            Toast.makeText(SettingsActivity.this, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateUsersData(String name, String email, String gender, String mobile) {
        if (Util.isInternetConnected(this)) {
            Util.showProgressDialog(this);
            String user_id = FuroPrefs.getString(this, "loginUserId");
            RestClient.UpdateUserDetails(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), new UpdateUserModel(user_id, name, email, gender, mobile), new Callback<UserUpdate>() {
                @Override
                public void onResponse(Call<UserUpdate> call, Response<UserUpdate> response) {
                    Util.dismissProgressDialog();
                    UserUpdate UserUpdate = response.body();
                    if (UserUpdate.getStatus() == 200) {
                        Toast.makeText(SettingsActivity.this, UserUpdate.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    /*m*/
                }

                @Override
                public void onFailure(Call<UserUpdate> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(SettingsActivity.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Util.dismissProgressDialog();
            Toast.makeText(SettingsActivity.this, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setView(UserDetailsModel userDetailsModel) {
        ed_name.setText(userDetailsModel.getUser().getName());
        ed_email.setText(userDetailsModel.getUser().getEmail());
        ed_gender.setText(userDetailsModel.getUser().getGender());
        ed_mobile.setText(userDetailsModel.getUser().getMobile());

    }
}