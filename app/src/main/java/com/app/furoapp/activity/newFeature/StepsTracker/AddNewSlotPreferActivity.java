package com.app.furoapp.activity.newFeature.StepsTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.app.furoapp.activity.newFeature.StepsTracker.adapter.FetchAllSlotAdapter;
import com.app.furoapp.activity.newFeature.StepsTracker.addNewSlot.AddNewSlotResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.addNewSlot.AddNewSlotTimeRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.addNewSlot.deleteSlot.DeleteSlotReq;
import com.app.furoapp.activity.newFeature.StepsTracker.addNewSlot.deleteSlot.DeleteSlotResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.fetchAllSlot.Datum;
import com.app.furoapp.activity.newFeature.StepsTracker.fetchAllSlot.FetchAllSlotResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.fqsteps.DataItem;
import com.app.furoapp.activity.newFeature.StepsTracker.fqsteps.TipsResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter.WeeklyHistoryAdapter;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewSlotPreferActivity extends Activity implements FetchAllSlotAdapter.DeleteSlotClickCallBack {
    private static final String TAG = "AddNewSlotPreferActivity";
    public ImageView ivContinue, ivSkip, ivAddNewSlot;
    Intent intent;
    FetchAllSlotAdapter fetchAllSlotAdapter;
    List<Datum> datumList = new ArrayList<>();
    public RecyclerView rvSlotTime;
    public String getAccessToken;
    public TimePicker tpSlotTime;
    public TextView tvCreateSlot, tvAddNewSlot, tvPrizmTips;
    private String getTimeHours;
    private boolean isTimeSlotSelected;
    private View includePopMenuOfAddTimeSlot;
    public LinearLayout llAddNewPreferSlot, llClosedIcon;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Handler tipsHandler = new Handler();
    private List<DataItem> tipsList;
    private int tipsListSize = 0;
    private int tipsStart = 0;
    long timeInMilliseconds = 0L;
    long updatedTime = 0L;
    private long startTime = 0L;
    long timeSwapBuff = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_slot_prefer);

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        findViews();
        clicklistner();
        callFetchAllPlanApi();
        ///setFetchAllPlanAdapter();

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
        tvAddNewSlot = findViewById(R.id.tvAddNewSlot);
        rvSlotTime = findViewById(R.id.rvSlotTime);
        tpSlotTime = findViewById(R.id.tpSlotTime);
        tvCreateSlot = findViewById(R.id.tvCreateSlot);
        includePopMenuOfAddTimeSlot = findViewById(R.id.includePopMenuOfAddTimeSlot);
        llAddNewPreferSlot = findViewById(R.id.llAddNewPreferSlot);
        llClosedIcon = findViewById(R.id.llClosedIcon);
        tvPrizmTips = findViewById(R.id.tvPrizmTips);
    }

    private void clicklistner() {
        tvAddNewSlot.setOnClickListener(v -> {
            includePopMenuOfAddTimeSlot.setVisibility(View.VISIBLE);
            llAddNewPreferSlot.setClickable(false);
            callTipsApi();
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

//        llAddNewPreferSlot.setOnClickListener(v -> {
//            if (dialog.isShowing()){
//                dialog.setCancelable(false);
//            }else {
//
//            }
//        });
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
                String min = "";
                if (minute < 10) {
                    min = "0" + minute;
                } else {
                    min = String.valueOf(minute);
                }

                String hrs = "";
                if (hourOfDay < 10) {
                    hrs = "0" + hourOfDay;
                } else {
                    hrs = String.valueOf(hourOfDay);
                }

                getTimeHours = hrs + ":" + min;
                Log.d("Morning Time", getTimeHours);
                isTimeSlotSelected = true;
            }
        });
    }

    private void callAddNewSlotApi() {
        AddNewSlotTimeRequest addNewSlotTimeRequest = new AddNewSlotTimeRequest();
        addNewSlotTimeRequest.setTimeslot(getTimeHours);
        if (Util.isInternetConnected(getApplicationContext())) {
            RestClient.getAddNewSlotTime(getAccessToken, addNewSlotTimeRequest, new Callback<AddNewSlotResponse>() {
                @Override
                public void onResponse(Call<AddNewSlotResponse> call, Response<AddNewSlotResponse> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            // Toast.makeText(AddNewSlotPreferActivity.this, "New TimeSlot Created Successfully!", Toast.LENGTH_SHORT).show();
                            callFetchAllPlanApi();
                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(AddNewSlotPreferActivity.this, "Internal server error!", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(AddNewSlotPreferActivity.this, "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                        getAlertTokenDialog();
                    }
                }

                @Override
                public void onFailure(Call<AddNewSlotResponse> call, Throwable t) {
                    Toast.makeText(AddNewSlotPreferActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please check internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void callFetchAllPlanApi() {
        Util.showProgressDialog(getApplicationContext());
        Util.showProgressDialog(getApplicationContext());
        if (Util.isInternetConnected(getApplicationContext())) {
            RestClient.getFetchAllSlot(getAccessToken, new Callback<FetchAllSlotResponse>() {
                @Override
                public void onResponse(Call<FetchAllSlotResponse> call, Response<FetchAllSlotResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            notifyFetchAllSlotTime(response.body().getData());
                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(AddNewSlotPreferActivity.this, "Internal server error!", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(AddNewSlotPreferActivity.this, "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                        getAlertTokenDialog();
                    }
                }

                @Override
                public void onFailure(Call<FetchAllSlotResponse> call, Throwable t) {
                    Toast.makeText(AddNewSlotPreferActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please check internet connection ! ", Toast.LENGTH_SHORT).show();
        }
    }

    private void notifyFetchAllSlotTime(List<Datum> data) {
        if (data != null && data.size() > 0) {
            rvSlotTime.setLayoutManager(new LinearLayoutManager(this));
            fetchAllSlotAdapter = new FetchAllSlotAdapter(getApplicationContext(), data, this);
            rvSlotTime.setAdapter(fetchAllSlotAdapter);
            fetchAllSlotAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No records founds !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteSlotClick(Integer id, String timeslot) {
        Integer getId = id;
        Log.d("id", String.valueOf(getId));
        String getTimeSlot = timeslot;
        Log.d("TimeSlot", timeslot);
        callDeleteApi(getId);
    }

    private void callDeleteApi(Integer getId) {
        if (Util.isInternetConnected(getApplicationContext())) {
            RestClient.deleteSlot(getAccessToken, getId, new Callback<DeleteSlotResponse>() {
                @Override
                public void onResponse(Call<DeleteSlotResponse> call, Response<DeleteSlotResponse> response) {
                    if (response.code() == 200) {
                        if (response.body().getData() != null) {
                            // Toast.makeText(AddNewSlotPreferActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            callFetchAllPlanApi();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DeleteSlotResponse> call, Throwable t) {
                    Toast.makeText(AddNewSlotPreferActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
        }
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
                        if (response.body().getData() != null && response.body().getData().getData() != null && response.body().getData().getData().size() > 0) {
                            tipsList = response.body().getData().getData();
                            tipsListSize = tipsList.size();
                            tipsHandler.postDelayed(tipsRunnable, 0);
                        } else {
                            Toast.makeText(getApplicationContext(), "No tips data found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (response.code() == 500) {
                            Toast.makeText(getApplicationContext(), "Internal server error !", Toast.LENGTH_SHORT).show();
                        }
                        if (response.code() == 403) {
                            // Toast.makeText(this, response.code() + "Session expire Please login again", Toast.LENGTH_SHORT).show();
                            getAlertTokenDialog();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TipsResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please check internet connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private Runnable tipsRunnable = new Runnable() {
        public void run() {
            if (tipsList != null && tipsList.size() > 0) {
                if (tipsStart == (tipsListSize - 1)) {
                    tvPrizmTips.setVisibility(View.VISIBLE);
                    tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph());
                    tipsStart = 0;
                } else {
                    if (tipsList != null && tipsList.size() > 0) {
                        tvPrizmTips.setVisibility(View.VISIBLE);
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
    protected void onRestart() {
        super.onRestart();
        callFetchAllPlanApi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callFetchAllPlanApi();
    }
}