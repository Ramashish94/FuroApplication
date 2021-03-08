package com.app.furoapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.BuildConfig;
import com.app.furoapp.R;
import com.app.furoapp.model.versionStore.versionget.VersiongetResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity  {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    VersiongetResponse playstoreUpdateResponse;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        UpdateApiCall();

       /* FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.i("newTokennnn", newToken);
                FuroPrefs.putString(getApplicationContext(), "token", newToken);


            }
        });
*/

    }

    public void splashCall() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (FuroPrefs.getBoolean(SplashActivity.this, Constants.LOGGEDIN)) {
                    Intent intent = new Intent(SplashActivity.this, HomeMainActivity.class);
                    intent.putExtra("contestpage","");
                    startActivity(intent);
                    finish();

                } else if (FuroPrefs.getBoolean(SplashActivity.this, Constants.LOGGEDFBIN)) {
                    Intent intent = new Intent(SplashActivity.this, HomeMainActivity.class);
                    intent.putExtra("contestpage","");
                    startActivity(intent);
                    finish();

                } else if (FuroPrefs.getBoolean(SplashActivity.this, Constants.LOGGEDALERADYIN)) {
                    Intent intent = new Intent(SplashActivity.this, HomeMainActivity.class);
                    intent.putExtra("contestpage","");
                    startActivity(intent);

                }else if (FuroPrefs.getBoolean(SplashActivity.this, Constants.LOGGEDGOOGLEIN)) {
                    Intent intent = new Intent(SplashActivity.this, HomeMainActivity.class);
                    intent.putExtra("contestpage","");
                    startActivity(intent);

                }

                else {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginTutorialScreen.class);
                    startActivity(mainIntent);
                    finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);


    }


    private void UpdateApiCall() {
        if (Util.isInternetConnected(this)) {

            RestClient.appVersionGet(new Callback<VersiongetResponse>() {
                @Override
                public void onResponse(Call<VersiongetResponse> call, Response<VersiongetResponse> response) {

                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            playstoreUpdateResponse = response.body();
                            if (playstoreUpdateResponse != null &&
                                    playstoreUpdateResponse.getDetails().getAppName().equalsIgnoreCase("Furo Quotient")) {
                                if (Integer.parseInt(playstoreUpdateResponse
                                        .getDetails().getVersion()) > BuildConfig.VERSION_CODE && playstoreUpdateResponse
                                        .getDetails().getForceUpgrade().equalsIgnoreCase("true")) {
                                    forceToUpgradeDialog(true);

                                } else {
                                    splashCall();

                                }
                            }
                        }
                    } else {
                        Toast.makeText(SplashActivity.this, "No internet connection !!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<VersiongetResponse> call, Throwable t) {
                    splashCall();
                }
            });
        } else {
            Util.dismissProgressDialog();
            Toast.makeText(this, "Internet connection failed", Toast.LENGTH_SHORT).show();
        }
    }

   /* private void forceToUpgradeDialog(boolean isForceUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("Update Available!");
        builder.setCancelable(false);
        builder.setMessage("In order to continue, you must update the Furo application. This should only take a few moments.\n");

        builder.setPositiveButton("OK", (dialog, which) -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.app.furoapp&ah=6k4dewAj7xLuYXgtSTZmJToGouQ")));
            }

           LoginManager.getInstance().logOut();


            dialog.dismiss();
        });

        if (!isForceUpdate) {
            builder.setNegativeButton("SKIP", (dialog, which) -> {
                FuroPrefs.putBoolean(SplashActivity.this, Constants.SOFT_UPGRADE_SKIP, true);
                dialog.dismiss();
            });
        }

        AlertDialog dialog = builder.show();

        if (isFinishing() && dialog != null) {
            dialog.dismiss();
        }
    }*/


    private void forceToUpgradeDialog(boolean isForceUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        //builder.setTitle("Update Available!");
        builder.setCancelable(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.upgrade_view, null);

        TextView title = view.findViewById(R.id.title);
        TextView message = view.findViewById(R.id.message);
        title.setText("New Version Available       " + BuildConfig.VERSION_NAME);
        message.setText("In order to continue, you must update the Furo application. This should only take a few moments.\n");
        builder.setView(view);


        builder.setPositiveButton("Update", (dialog, which) -> {
            try {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));

            } catch (android.content.ActivityNotFoundException anfe) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.app.furoapp&ah=6k4dewAj7xLuYXgtSTZmJToGouQ")));

            }
            LoginManager.getInstance().logOut();
            dialog.dismiss();
        });

        if (!isForceUpdate) {
            builder.setNegativeButton("Later", (dialog, which) -> {
                FuroPrefs.putBoolean(SplashActivity.this, Constants.SOFT_UPGRADE_SKIP, true);
                dialog.dismiss();
            });
        }

        AlertDialog dialog = builder.show();
        if (isFinishing() && dialog != null) {
            dialog.dismiss();
        }
    }

}