package com.app.furoapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.furoapp.R;
import com.app.furoapp.databinding.FragmentVideoOverviewBinding;
import com.app.furoapp.utils.CameraUtils;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

public class VideoRecoringViewAct extends AppCompatActivity {


    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;




    public static final int MEDIA_TYPE_VIDEO = 2;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Furo Fq";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";

    private static String imageStoragePath;


    FragmentVideoOverviewBinding binding;
    String VideoQualityLow = "Videohigh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_video_overview);

        VideoQualityLow = FuroPrefs.getString(getApplicationContext(), "true");

        binding.tvStartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onProfileImageClickNew();


            }
        });
    }



    /**
     * Launching camera app to record video
     */
    private void captureVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        /*intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);*/

      /* File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_VIDEO);
      if (file != null) {
           imageStoragePath = file.getAbsolutePath();
       }

       Uri fileUri = Utils.getOutputMediaFileUri(getApplicationContext(), file);

        // set video quality


       intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
*/
        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    private void captureVideoLOW() {


        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
      /* intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
*/
     /*  File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_VIDEO);
      if (file != null) {
          imageStoragePath = file.getAbsolutePath();
       }

        Uri fileUri = Utils.getOutputMediaFileUri(getApplicationContext(), file);
*/
        // set video quality
     /*  intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);*/

      // intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file*/

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    /**
     * Activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery

                Utils.refreshGallery(getApplicationContext(), imageStoragePath);
                Uri uri = data.getData();
                imageStoragePath = getRealPathFromURIPath(uri, VideoRecoringViewAct.this);

                Intent intent = new Intent(VideoRecoringViewAct.this, PreviewActivity.class);
                intent.putExtra("imagestoragepath", imageStoragePath);
                startActivity(intent);
                finish();
                // video successfully recorded
                // preview the recorded video
                /* previewVideo();*/
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



    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public void onProfileImageClickNew() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
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

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(VideoRecoringViewAct.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}