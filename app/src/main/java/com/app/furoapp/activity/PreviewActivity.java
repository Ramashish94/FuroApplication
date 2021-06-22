package com.app.furoapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.furoapp.R;
import com.app.furoapp.model.content_feed.Data;
import com.app.furoapp.model.createVideoChallenge.CreateVideoChallangeResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.ProgressRequestBody;
import com.app.furoapp.utils.Util;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PreviewActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacksVieo {
    ProgressBar mProgressBar;
    String imagestoragepath, formatted, useid, VideoQualityLow, priviewActivity, activityName;
    Bitmap waterMark;
    LinearLayout linearLayout;
    EditText countActivity;
    ProgressDialog progressDialog;
    SwitchCompat mySwitch;
    String on, of;
    private VideoView videoView;
    RelativeLayout relativeLayoutpopup;
    private ImageView playIvButton, pauseButton, backButton, ivArrow;
    private TextView textView, recordingDurationTime, challengeYourFriend, actDuration, discardThisSubmission, previewactivityTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_challenge_preview);
        videoView = findViewById(R.id.videoPreview);
        challengeYourFriend = findViewById(R.id.tvChallenges);
        textView = findViewById(R.id.tvSaveAndShareCard);
        playIvButton = findViewById(R.id.ivPlayButton);
        ivArrow = findViewById(R.id.rightArrow);
        relativeLayoutpopup = findViewById(R.id.popup);
        mySwitch = findViewById(R.id.switch2);
        pauseButton = findViewById(R.id.ivPlauseButtonnnn);
        linearLayout = findViewById(R.id.lineaarlayout);
        backButton = findViewById(R.id.ivBackBtn);
        previewactivityTextview = findViewById(R.id.previewActivityTextview);
        actDuration = findViewById(R.id.activityDuration);
        recordingDurationTime = findViewById(R.id.recordingDuration);
        discardThisSubmission = findViewById(R.id.tvDiscardThisSubmission);
        countActivity = findViewById(R.id.activitycount);
        priviewActivity = FuroPrefs.getString(getApplicationContext(), "subCategory");
        previewactivityTextview.setText(priviewActivity);
        VideoQualityLow = FuroPrefs.getString(getApplicationContext(), "true");

        relativeLayoutpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                countPopup();
            }

        });


        mySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mySwitch.isChecked()) {
                    on = "VideoLow";
                    FuroPrefs.putString(getApplicationContext(), "true", on);
                    mySwitch.setChecked(true);
                    onResume();

                } else {
                    on = "Videohigh";
                    FuroPrefs.putString(getApplicationContext(), "true", on);
                    mySwitch.setChecked(false);
                    onPause();
                }

            }
        });

        of = FuroPrefs.getString(getApplicationContext(), "true");
        if (of.equalsIgnoreCase("VideoLow")) {
            mySwitch.setChecked(true);
        } else if (of.equalsIgnoreCase("Videohigh")) {
            mySwitch.setChecked(false);
        }


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewActivity.this, VideoRecoringViewAct.class);
                startActivity(intent);

            }
        });

        challengeYourFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PreviewActivity.this, VideoChallangeFriendsActivity.class);

                startActivity(intent);
                finish();
            }
        });
        discardThisSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogout();
            }
        });


        Intent intent = getIntent();
        imagestoragepath = intent.getStringExtra("imagestoragepath");
        if (imagestoragepath != null && !TextUtils.isEmpty(imagestoragepath)) {
            videoView.setVideoPath(imagestoragepath);
            videoView.setVideoURI(Uri.parse(imagestoragepath));

            playIvButton.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.VISIBLE);


            // start playing
            videoView.start();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    int duration = mp.getDuration() / 1000;
                    int hours = duration / 3600;
                    int minutes = (duration / 60) - (hours * 60);
                    int seconds = duration - (hours * 3600) - (minutes * 60);
                    formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
                    recordingDurationTime.setText(formatted);
                    actDuration.setText(formatted);
                }
            });

        }


        setOnClickListner();
        playIvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playIvButton.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.VISIBLE);


                // start playing
                videoView.start();


            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseButton.setVisibility(View.GONE);
                playIvButton.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoPath(imagestoragepath);
                // start playing
                videoView.pause();


            }
        });

        initialise();
        //getPermission();
    }

    private void getPermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS

                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {


                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    public void onProfileImageClickNew() {
        Dexter.withActivity(this)
                .withPermissions(/*Manifest.permission.READ_CONTACTS,*/
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {


                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void initialise() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading File");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
    }

    public void preview() {
        if (playIvButton.callOnClick()) {
            playIvButton.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(imagestoragepath);
            // start playing
            videoView.start();

        } else if (linearLayout.callOnClick()) {
            playIvButton.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(imagestoragepath);
            // start playing
            videoView.pause();

        }


    }


    public void setOnClickListner() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadVideo();
            }
        });

    }


    public void uploadVideo() {

        String actCount = countActivity.getText().toString().trim();
        if (TextUtils.isEmpty(actCount)) {
            Toast.makeText(this, "Enter Activity Count", Toast.LENGTH_SHORT).show();
            return;
        } else {
            useid = FuroPrefs.getString(this, "loginUserId");
            activityName = FuroPrefs.getString(this, "challange");

            File videoFile = new File(imagestoragepath);
            String id_Challenge = "";
            ProgressRequestBody fileBody = new ProgressRequestBody(videoFile, PreviewActivity.this);

            RequestBody challenge_id = RequestBody.create(MediaType.parse("text/plain"), id_Challenge);
            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), useid);
            RequestBody acitivityduration = RequestBody.create(MediaType.parse("text/plain"), formatted);
            RequestBody challenge_name = RequestBody.create(MediaType.parse("text/plain"), priviewActivity);
            RequestBody challengeActivityname = RequestBody.create(MediaType.parse("text/plain"), activityName);
            RequestBody actCountNumber = RequestBody.create(MediaType.parse("text/plain"), actCount);
            MultipartBody.Part videofile = MultipartBody.Part.createFormData("video_file", videoFile.getName(), fileBody);

            if (Util.isInternetConnected(this)) ;
            RestClient.videoChallange(userid, acitivityduration, actCountNumber, challenge_name, challengeActivityname, challenge_id, videofile, new Callback<CreateVideoChallangeResponse>() {

                @Override
                public void onResponse(Call<CreateVideoChallangeResponse> call, Response<CreateVideoChallangeResponse> response) {
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("200")) {
                                String duration = response.body().getNewchallenge().getAcitivityDuration();
                                String count = response.body().getNewchallenge().getActivityCount();
                                String actName = response.body().getNewchallenge().getChallengeActivity();

                                FuroPrefs.putString(getApplicationContext(), "getAcitivityDuration", duration);
                                FuroPrefs.putString(getApplicationContext(), "getActivityCount", count);
                                FuroPrefs.putString(getApplicationContext(), "getChallengeActivity", actName);

                                Toast.makeText(PreviewActivity.this, " Video uploaded successfully.  ", Toast.LENGTH_SHORT).show();
                                getPermission();
                                String actOpen = "video";
                                FuroPrefs.putString(getApplicationContext(), "activity_open", actOpen);
                                String challengeid = String.valueOf(response.body().getNewchallenge().getId());
                                FuroPrefs.putString(getApplicationContext(), "challengeidforshare", challengeid);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Toast.makeText(PreviewActivity.this, "You can Save Video only once ", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            } else {
                                Toast.makeText(PreviewActivity.this, "message " + response.code(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    challengeYourFriend.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<CreateVideoChallangeResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(PreviewActivity.this, "Your Internet is slow/not working kindly connect wifi or better network"
                            , Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public void replaceFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerId, fragment, name);

            if (!fragment.isAdded()) {
                fragmentTransaction.addToBackStack(name);
                fragmentTransaction.commitAllowingStateLoss();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onProgressUpdate(int percentage, long mtotal) {
        progressDialog.setProgress(percentage);
        DecimalFormat df2 = new DecimalFormat("#.##");

        double fsize = (double) mtotal / (1024 * 1024);
        if (fsize > 1) {
            df2.setRoundingMode(RoundingMode.UP);
        }
        if (VideoQualityLow.equalsIgnoreCase("VideoLow")) {
            progressDialog.setMessage("Uploading File (Size: " + df2.format(fsize) + " MB)" + "\n \n Video compression is on !!");


        } else {
            progressDialog.setMessage("Uploading File (Size: " + df2.format(fsize) + " MB)");

        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void uploadStart() {
        progressDialog.show();
        progressDialog.setProgress(0);
    }

    private class MyAsync extends AsyncTask<Void, Integer, Void> {
        int duration = 0;
        int current = 0;

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            System.out.println(values[0]);
            mProgressBar.setProgress(values[0]);
        }
    }

    public Bitmap waterMark(Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);
        waterMark = BitmapFactory.decodeResource(getResources(), R.drawable.whatsappicon);
        int startX = (canvas.getWidth() - waterMark.getWidth()) / 2;
        int startY = (canvas.getHeight() - waterMark.getHeight()) / 2;
        canvas.drawBitmap(waterMark, startX, startY, null);


        return result;
    }

    private void userlogout() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_alert_discard_dialognew, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
        TextView text_logout = dialogView.findViewById(R.id.text_logout);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviewActivity.this, VideoOverViewActivity.class);
                startActivity(intent);
                finish();


            }
        });

        dialog.show();


    }

    private Bitmap addWaterMark(Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Bitmap waterMark = BitmapFactory.decodeResource(getResources(), R.drawable.whatsappicon);
        //  canvas.drawBitmap(waterMark, 0, 0, null);
        int startX = (canvas.getWidth() - waterMark.getWidth()) / 2;//for horisontal position
        int startY = (canvas.getHeight() - waterMark.getHeight()) / 2;//for vertical position
        canvas.drawBitmap(waterMark, startX, startY, null);

        return result;
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_alertdialognew, null);


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
                Intent intent = new Intent(PreviewActivity.this, CreateChallengeActivity.class);
                startActivity(intent);
                finish();


            }
        });

        dialog.show();

    }

    public void countPopup() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.count_pop_up, null);


        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();


        ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);

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
                relativeLayoutpopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }

                });

            }
        });


        dialog.show();


    }


}