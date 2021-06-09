package com.app.furoapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.databinding.FragmentVideoOverviewBinding;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import java.io.File;
import java.util.List;


public class VideoOverViewActivity extends AppCompatActivity {
    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;
    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "FqByFuro";
    // Image and Video file extensions
    /* public static final String IMAGE_EXTENSION = "jpg";*/
    public static final String VIDEO_EXTENSION = "mp4";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    private static String imageStoragePath;
    FragmentVideoOverviewBinding binding;
    String VideoQualityLow = "Videohigh";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_video_overview);

        VideoQualityLow = FuroPrefs.getString(getApplicationContext(), "true");
        getStoragePermission();

        binding.tvStartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onProfileImageClickNew();


            }


        });

    }

    public void getStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(/*Manifest.permission.READ_CONTACTS,*/
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

    @Override
    protected void onResume() {
        super.onResume();

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
        File file = Utils.getExternalStorage(MEDIA_TYPE_VIDEO);
        if (file != null) {

            imageStoragePath = file.getAbsolutePath();

        }
        // error one plus
        Uri fileUri = Utils.getOutputMediaFileUri(getApplicationContext(), file);
        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        /*intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);*/

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }


    public void captureVideoLOW() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File file = Utils.getExternalStorage(MEDIA_TYPE_VIDEO);
        if (file != null) {

            imageStoragePath = file.getAbsolutePath();

        }
        // error one plus
        Uri fileUri = Utils.getOutputMediaFileUri(getApplicationContext(), file);

        // set video quality
        intent.putExtra(MediaStore.ACTION_VIDEO_CAPTURE, 0);
       /* intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);*/

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
                Intent intent = new Intent(VideoOverViewActivity.this, PreviewActivity.class);
                intent.putExtra("imagestoragepath", imageStoragePath);
                startActivity(intent);
                //finish();

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
        AlertDialog.Builder builder = new AlertDialog.Builder(VideoOverViewActivity.this);
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

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    private void galleryAddPic(String currentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}