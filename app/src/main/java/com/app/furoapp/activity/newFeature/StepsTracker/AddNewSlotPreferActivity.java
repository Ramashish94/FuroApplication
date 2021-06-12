package com.app.furoapp.activity.newFeature.StepsTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.newFeature.StepsTracker.adapter.FetchAllPlanAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.addNewSlot.AddNewSlotResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.addNewSlot.AddNewSlotTimeRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.fetchAllSlot.Datum;
import com.app.furoapp.activity.newFeature.StepsTracker.fetchAllSlot.FetchAllSlotResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewSlotPreferActivity extends Activity implements FetchAllPlanAdapter.TimeSlotClickCallBack {
    public ImageView ivContinue, ivSkip, ivAddNewSlot;
    Intent intent;
    FetchAllPlanAdapter fetchAllPlanAdapter;
    List<Datum> datumList = new ArrayList<>();
    public RecyclerView rvSlotTime;
    public String getAccessToken;
    public TimePicker tpSlotTime;
    public TextView tvCreateSlot;
    private String getTimeHours;
    private boolean isTimeSlotSelected;
    private View includePopMenuOfAddTimeSlot;
    private LinearLayout llAddNewPreferSlot, llClosedIcon;
    public GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_slot_prefer);

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        findViews();
        clicklistner();
        setFetchAllPlanAdapter();
        callFetchAllPlanApi();
        timePickerEvent();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

    }

    private void findViews() {
        ivContinue = findViewById(R.id.ivContinue);
        ivSkip = findViewById(R.id.ivSkip);
        ivAddNewSlot = findViewById(R.id.ivAddNewSlot);
        rvSlotTime = findViewById(R.id.rvSlotTime);
        tpSlotTime = findViewById(R.id.tpSlotTime);
        tvCreateSlot = findViewById(R.id.tvCreateSlot);
        includePopMenuOfAddTimeSlot = findViewById(R.id.includePopMenuOfAddTimeSlot);
        llAddNewPreferSlot = findViewById(R.id.llAddNewPreferSlot);
        llClosedIcon = findViewById(R.id.llClosedIcon);
    }

    private void clicklistner() {
        ivAddNewSlot.setOnClickListener(v -> {
            includePopMenuOfAddTimeSlot.setVisibility(View.VISIBLE);
            llAddNewPreferSlot.setClickable(false);
        });
        llClosedIcon.setOnClickListener(v -> {
            includePopMenuOfAddTimeSlot.setVisibility(View.GONE);
            llAddNewPreferSlot.setVisibility(View.VISIBLE);
        });

        ivContinue.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), WantToAcivedActivity.class);
            startActivity(intent);
            finish();
        });
        ivSkip.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), CreateYourStepGoalActivity.class);
            startActivity(intent);
            finish();
        });

        tvCreateSlot.setOnClickListener(v -> {
            if (isTimeSlotSelected) {
                includePopMenuOfAddTimeSlot.setVisibility(View.GONE);
                llAddNewPreferSlot.setVisibility(View.VISIBLE);
                callAddNewSlotApi();
            } else {
                Toast.makeText(getApplicationContext(), "Please select wake up time and bed time!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void timePickerEvent() {
        tpSlotTime.setIs24HourView(true); // used to display AM/PM mode
        // perform set on time changed listener event
        tpSlotTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                // Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                //  time.setText("Time is :: " + hourOfDay + " : " + minute); // set the current time in text view
                getTimeHours = hourOfDay + ":" + minute;
                Log.d("Morning Time", getTimeHours);
                isTimeSlotSelected = true;
            }
        });
    }

    private void callAddNewSlotApi() {
        AddNewSlotTimeRequest addNewSlotTimeRequest = new AddNewSlotTimeRequest();
        addNewSlotTimeRequest.setTimeslot(getTimeHours);
        RestClient.getAddNewSlotTime(getAccessToken, addNewSlotTimeRequest, new Callback<AddNewSlotResponse>() {
            @Override
            public void onResponse(Call<AddNewSlotResponse> call, Response<AddNewSlotResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        Toast.makeText(AddNewSlotPreferActivity.this, "New TimeSlot Created Successfully!", Toast.LENGTH_SHORT).show();
                        callFetchAllPlanApi();
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(AddNewSlotPreferActivity.this, "Internal server error!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(AddNewSlotPreferActivity.this, "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddNewSlotResponse> call, Throwable t) {
                Toast.makeText(AddNewSlotPreferActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callFetchAllPlanApi() {
        Util.showProgressDialog(getApplicationContext());
        Util.showProgressDialog(getApplicationContext());
        RestClient.getFetchAllSlot(getAccessToken, new Callback<FetchAllSlotResponse>() {
            @Override
            public void onResponse(Call<FetchAllSlotResponse> call, Response<FetchAllSlotResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        getAlertTokenDialog();//////////////

                        // notifyFetchAllSlotTime(response.body().getData());
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(AddNewSlotPreferActivity.this, "Internal server error!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(AddNewSlotPreferActivity.this, "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    // getAlertTokenDialog();
                }
            }

            @Override
            public void onFailure(Call<FetchAllSlotResponse> call, Throwable t) {
                Toast.makeText(AddNewSlotPreferActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFetchAllPlanAdapter() {
        fetchAllPlanAdapter = new FetchAllPlanAdapter(getApplicationContext(), datumList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSlotTime.setLayoutManager(layoutManager);
        rvSlotTime.setItemAnimator(new DefaultItemAnimator());
        rvSlotTime.setAdapter(fetchAllPlanAdapter);
    }

    private void notifyFetchAllSlotTime(List<Datum> data) {
        datumList.clear();
        datumList.addAll(data);
        if (datumList != null && datumList.size() > 0) {
            fetchAllPlanAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No record found !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void timeSlotClick(Integer id, String timeslot) {
        String getId = String.valueOf(id);
        Log.d("id", getId);
        String getTimeSlot = timeslot;
        Log.d("TimeSlot", timeslot);
    }


    private void getAlertTokenDialog() {
        if (getAccessToken != null) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.profile_alertdialog_logoutt_new, null);
            dialogBuilder.setView(dialogView);
            final AlertDialog dialog = dialogBuilder.create();
            ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
            TextView text_logout = dialogView.findViewById(R.id.text_logout);
            TextView noiwanttocontinue = dialogView.findViewById(R.id.noiwanttocontinuee);
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


}