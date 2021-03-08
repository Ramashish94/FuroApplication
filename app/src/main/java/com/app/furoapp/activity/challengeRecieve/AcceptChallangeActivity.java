package com.app.furoapp.activity.challengeRecieve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.adapter.ActivityAcceptChallengeAdapter;
import com.app.furoapp.model.challengedetail.ChallangeDetailRequest;
import com.app.furoapp.model.challengedetail.ChallangeDetailResponse;
import com.app.furoapp.model.challengedetail.ChallengeDetails;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.utils.Utils;

import java.io.File;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptChallangeActivity extends AppCompatActivity {
    ActivityAcceptChallengeAdapter activityAcceptChallengeAdapter;
    RecyclerView recyclerViewAccept;
    String VideoQualityLow= "Videohigh";

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

    // Image and Video file extensions
    /* public static final String IMAGE_EXTENSION = "jpg";*/
    public static final String VIDEO_EXTENSION = "mp4";

    private static String imageStoragePath;
    int userchallangeidAccept;
    private TextView startcamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_challange);
        startcamera = findViewById(R.id.tvStart);
        recyclerViewAccept = findViewById(R.id.acceptchallengeRecycle);

        VideoQualityLow = FuroPrefs.getString(getApplicationContext(), "true");

        userchallangeidAccept =  FuroPrefs.getInt(getApplicationContext(),"challengefuroid",0);

        acceptChallange();
        startcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VideoQualityLow.equalsIgnoreCase("")) {
                    captureVideo();

                }

                if (VideoQualityLow.equalsIgnoreCase("VideoLow")) {
                    captureVideoLOW();

                }
                if (VideoQualityLow.equalsIgnoreCase("Videohigh")) {
                    captureVideo();

                }

            }
        });

        if (!Utils.isDeviceSupportCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }
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

        File file = Utils.getOutputMediaFile(MEDIA_TYPE_VIDEO,getApplicationContext());
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

    public void captureVideoLOW() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        File file = Utils.getOutputMediaFile(MEDIA_TYPE_VIDEO, getApplicationContext());
        if (file != null) {

            imageStoragePath = file.getAbsolutePath();
        }
// error one plus
        Uri fileUri = Utils.getOutputMediaFileUri(getApplicationContext(), file);

        // set video quality
        intent.putExtra(MediaStore.ACTION_VIDEO_CAPTURE, 0);
        /*intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);*/

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
                Intent intent = new Intent(AcceptChallangeActivity.this, SubmissionActivity.class);

                intent.putExtra("imagestoragepath", imageStoragePath);
                startActivity(intent);
                finish();
                //  previewVideo();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void acceptChallange() {
        ChallangeDetailRequest challangeDetailRequest = new ChallangeDetailRequest();
        challangeDetailRequest.setChallengeId(String.valueOf(userchallangeidAccept));
        Util.isInternetConnected(this);
        Util.showProgressDialog(this);
        RestClient.userChallangeDetail(challangeDetailRequest, new Callback<ChallangeDetailResponse>() {
            @Override
            public void onResponse(Call<ChallangeDetailResponse> call, Response<ChallangeDetailResponse> response) {
                Util.dismissProgressDialog();
                if (response != null) {

                    if (response.body().getChallengeDetails() != null) {
                        List<ChallengeDetails> challengeDetails = Collections.singletonList(response.body().getChallengeDetails());

                        activityAcceptChallengeAdapter = new ActivityAcceptChallengeAdapter(challengeDetails, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                        String activityy = response.body().getChallengeDetails().getChallengeActivity();
                        FuroPrefs.putString(getApplicationContext(),"actVityyyy",activityy);
                        recyclerViewAccept.setLayoutManager(layoutManager);
                        recyclerViewAccept.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewAccept.setAdapter(activityAcceptChallengeAdapter);

                    }else{
                        Toast.makeText(AcceptChallangeActivity.this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<ChallangeDetailResponse> call, Throwable t) {

                Toast.makeText(AcceptChallangeActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


    }


}




