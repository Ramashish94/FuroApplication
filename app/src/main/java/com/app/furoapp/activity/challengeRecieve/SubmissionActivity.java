package com.app.furoapp.activity.challengeRecieve;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.furoapp.R;
import com.app.furoapp.activity.PreviewActivity;
import com.app.furoapp.activity.VideoRecoringViewAct;
import com.app.furoapp.model.createVideoChallenge.CreateVideoChallangeResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.ProgressRequestBody;
import com.app.furoapp.utils.Util;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmissionActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacksVieo {
    private TextView submitChallnage, discard;
    private VideoView videoView;
    String VideoQualityLow;
    private ImageView playIvButton, pauseButton, backButton;
    private TextView recordingDurationTime, challengeYourFriend, actDuration;
    String imagestoragepath;
    ProgressDialog progressDialog;
    String priviewActivitySubmission;
    EditText activityCount;
    LinearLayout linearLayout;
    String formatted;
    String user_Challenge_New_id, activityNameNew;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        submitChallnage = findViewById(R.id.submitChallenge);
        activityCount = findViewById(R.id.countactivity);

        videoView = findViewById(R.id.videoPreview);
        discard = findViewById(R.id.tvDiscardThisSubmission);


        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogout();
            }
        });


        playIvButton = findViewById(R.id.ivPlayButton);
        pauseButton = findViewById(R.id.ivPlauseButtonnnn);
        linearLayout = findViewById(R.id.lineaarlayout);
        backButton = findViewById(R.id.ivBackBtn);
        actDuration = findViewById(R.id.activityDuration);
        recordingDurationTime = findViewById(R.id.recordingDuration);
        submitChallnage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadVideo();

            }
        });
        Intent intent = getIntent();
        imagestoragepath = intent.getStringExtra("imagestoragepath");
        priviewActivitySubmission = FuroPrefs.getString(getApplicationContext(), "subCategory");
        VideoQualityLow = FuroPrefs.getString(getApplicationContext(), "true");
        if (imagestoragepath != null) {
            videoView.setVideoPath(imagestoragepath);

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


                }
            });

        }


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
    }

    public void uploadVideo() {


        String actCount = activityCount.getText().toString().trim();
        if (TextUtils.isEmpty(actCount)) {
            Toast.makeText(this, "Enter Activity Count", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String useid = FuroPrefs.getString(this, "loginUserId");
            user_Challenge_New_id = String.valueOf(FuroPrefs.getInt(getApplicationContext(), "challengefuroid", 0));
            activityNameNew = FuroPrefs.getString(this, "actVityyyy");

            File videoFile = new File(imagestoragepath);

            ProgressRequestBody fileBody = new ProgressRequestBody(videoFile, SubmissionActivity.this);
            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), useid);
            RequestBody Challenge_new_id = RequestBody.create(MediaType.parse("text/plain"), user_Challenge_New_id);
            RequestBody acitivityduration = RequestBody.create(MediaType.parse("text/plain"), formatted);
            RequestBody acitivityName = RequestBody.create(MediaType.parse("text/plain"), activityNameNew);
            RequestBody challenge_name = RequestBody.create(MediaType.parse("text/plain"), priviewActivitySubmission);
            RequestBody actCountNumber = RequestBody.create(MediaType.parse("text/plain"), actCount);
            MultipartBody.Part videofile = MultipartBody.Part.createFormData("video_file", videoFile.getName(), fileBody);

            if (Util.isInternetConnected(this)) ;
            RestClient.videoChallange(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), userid, acitivityduration, actCountNumber, challenge_name, acitivityName, Challenge_new_id, videofile, new Callback<CreateVideoChallangeResponse>() {

                @Override
                public void onResponse(Call<CreateVideoChallangeResponse> call, Response<CreateVideoChallangeResponse> response) {
                    progressDialog.dismiss();
                    if (response != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            Toast.makeText(SubmissionActivity.this, " Video uploaded successfully ", Toast.LENGTH_SHORT).show();
                            submitChallnage.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
                            int submissionId = response.body().getNewchallenge().getId();
                            String userId = response.body().getNewchallenge().getUserId();
                            FuroPrefs.putInt(getApplicationContext(), "SubmissionChallenegeId", submissionId);
                            FuroPrefs.putString(getApplicationContext(), "userIdLogin", userId);
                            Intent intent = new Intent(SubmissionActivity.this, WinnerActivity.class);
                            startActivity(intent);
                            finish();
                            submitChallnage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(SubmissionActivity.this, "You can Save Video only once ", Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else {
                            Toast.makeText(SubmissionActivity.this, "You can Save Video only once ", Toast.LENGTH_SHORT).show();

                        }

                    }


                }

                @Override
                public void onFailure(Call<CreateVideoChallangeResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(SubmissionActivity.this, "Something went Wrong !", Toast.LENGTH_SHORT).show();
                }
            });


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

    private void initialise() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading File");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
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


    private void userlogout() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_alert_discard_dialognew, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        TextView noiwanttocontinue = dialogView.findViewById(R.id.noiwanttocontinuee);
        ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
        TextView text_logout = dialogView.findViewById(R.id.text_logout);
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
                Intent intent = new Intent(SubmissionActivity.this, VideoRecoringViewAct.class);
                startActivity(intent);
                finish();


            }
        });

        dialog.show();


    }


}

