package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.databinding.FragmentVideoCountdownBinding;
import com.app.furoapp.utils.Utils;

import java.io.File;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

public class VideoOverViewStopWatch extends AppCompatActivity {

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";


    public static final String VIDEO_EXTENSION = "mp4";

    private static String imageStoragePath;
    CircleProgressView circleProgressView;
    CountDownTimer countDownTimer;
    int counter = 15;


    FragmentVideoCountdownBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_video_countdown);
        circleProgressView = binding.circleView;
        setOnClickListeners();
        setOnClickListnerTwo();

        if (!Utils.isDeviceSupportCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void setOnClickListeners() {

        circleProgressView.setMaxValue(15f);
        binding.etSignUp.setElevation(8f);
        binding.circleView.setTextMode(TextMode.TEXT);


        countDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long l) {
                --counter;
                circleProgressView.setText(String.valueOf((counter)));
                circleProgressView.setValue(Float.valueOf(counter));

            }


            @Override
            public void onFinish() {
                boolean result = Utils.checkPermission(VideoOverViewStopWatch.this);
                if (result) {
                    captureVideo();

                }
            }


        };
    }

    public void setOnClickListnerTwo() {
        binding.etSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etSignUp.isEnabled();
                binding.etSignUp.setElevation(0f);
                countDownTimer.start();
                binding.etSignUp.setVisibility(View.GONE);
            }
        });


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }


    private void captureVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        File file = Utils.getOutputMediaFile(MEDIA_TYPE_VIDEO, getApplicationContext());
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = Utils.getOutputMediaFileUri(getApplicationContext(), file);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                Utils.refreshGallery(getApplicationContext(), imageStoragePath);

                // video successfully recorded
                // preview the recorded video
                Intent intent = new Intent(VideoOverViewStopWatch.this, PreviewActivity.class);
                intent.putExtra("imagestoragepath", imageStoragePath);
                startActivity(intent);
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


}


