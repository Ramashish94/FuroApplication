package com.app.furoapp.activity.challengeRecieveMap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.MapOverViewActivity;
import com.app.furoapp.activity.PreviewCardActivity;
import com.app.furoapp.model.challengemap.ChallenegeMapResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PreviewCardActivityRecieve extends AppCompatActivity {
    private ImageView backButton;

    String image;
    private Context mContext;
    String distanceInKm;

    String userMapImage;
    private String sharePath = "no", sharePathMap;
    String uniqueUsernewName, uniqueTimer, actChallName, idUser;
    File imageFileNew;
    TextView textViewChallenge, tvDistanceMeter, saveandShare;
    MultipartBody.Part imagee;

    ImageView imageView, iconfuro, dotImage, imageSharePreview, imageBackButton, profileImageView, textViewFb, textViewWhatsApp, imageDownload;
    private TextView userNameNew, uniqueTimerMap, activityChallenge, tvTextSec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_challenge_cardview);


//later


        mContext = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            this.setTurnScreenOn(true);
        } else {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
        tvTextSec = findViewById(R.id.tvTextSecond);
        textViewChallenge = findViewById(R.id.tvChallenges);
        imageView = findViewById(R.id.mapimage);
        saveandShare = findViewById(R.id.tvSaveAndShareCardmap);
        iconfuro = findViewById(R.id.furobrandid);
        dotImage = findViewById(R.id.furoiconbrand);
        tvDistanceMeter = findViewById(R.id.minutesms);
        imageDownload = findViewById(R.id.download);
        profileImageView = findViewById(R.id.mapProfileimage);
        uniqueTimerMap = findViewById(R.id.timerNEW);
        activityChallenge = findViewById(R.id.tvActivityCategory);
        userNameNew = findViewById(R.id.tvUserNameNew);
        textViewFb = findViewById(R.id.previewfacebook);
        textViewWhatsApp = findViewById(R.id.previewwhatsapp);
        imageSharePreview = findViewById(R.id.shareOreviewCard);

        Intent intent = getIntent();
        distanceInKm = String.valueOf(intent.getIntExtra("Distanceinm", 0));
        image = FuroPrefs.getString(getApplication(), "mapScreenshot");
        /* distanceInKm = FuroPrefs.getString(getApplicationContext(), "Distanceinm");*/
        userMapImage = FuroPrefs.getString(getApplication(), "userImage");
        uniqueUsernewName = FuroPrefs.getString(getApplication(), "loginUserNameNew");
        uniqueTimer = FuroPrefs.getString(getApplication(), "time");
        idUser = FuroPrefs.getString(getApplication(), "loginUserId");
        actChallName = FuroPrefs.getString(getApplication(), "mapActivity");

        saveandShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMapData();
            }
        });


        if (TextUtils.isEmpty(userMapImage)) {

        } else {
            Picasso.with(getApplicationContext()).load(userMapImage).error(R.drawable.ic_userimageiconss).into(profileImageView);


        }


        userNameNew.setText(uniqueUsernewName);
        uniqueTimerMap.setText(uniqueTimer);
        activityChallenge.setText(actChallName);
        tvDistanceMeter.setText(distanceInKm);


        textViewChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewCardActivityRecieve.this, WinnerActivityMap.class);
                startActivity(intent);
                finish();


            }
        });


        imageSharePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogout();

            }
        });


        textViewFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, image);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplication(), "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();

                }

            }
        });
        imageView.setImageURI(Uri.fromFile(new File(image)));


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

    private void takeScreenshot() {
        // Date now = new Date();
        // android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + System.currentTimeMillis() + ".jpeg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //setting screenshot in imageview
            String filePath = imageFile.getPath();

            Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            // iv.setImageBitmap(ssbitmap);
            sharePath = filePath;

            if (!sharePath.equals("no")) {
                share(sharePath);
            }

        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void share(String sharePath) {


        try {
            File file = new File(sharePath);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkURI = FileProvider.getUriForFile(
                    this,
                    this.getApplicationContext()
                            .getPackageName() + ".provider", file);
            install.setDataAndType(apkURI, "image/*");
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(install);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void takeScreenshotmap() {
        // Date now = new Date();
        // android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + System.currentTimeMillis() + ".jpeg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //setting screenshot in imageview
            String filePathNew = imageFile.getPath();

            Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            // iv.setImageBitmap(ssbitmap);
            sharePathMap = filePathNew;


        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }


    public void saveMapData() {

        String challengeIdUser = String.valueOf(FuroPrefs.getInt(getApplicationContext(), "challengefuroid", 0));

        RequestBody challUserId = RequestBody.create(MediaType.parse("text/plain"), challengeIdUser);
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), idUser);
        RequestBody acitivityduration = RequestBody.create(MediaType.parse("text/plain"), uniqueTimer);
        RequestBody distance = RequestBody.create(MediaType.parse("text/plain"), distanceInKm);
        RequestBody challengeactivity = RequestBody.create(MediaType.parse("text/plain"), actChallName);


        if (!TextUtils.isEmpty(image)) {

            imageFileNew = new File(image);

            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), imageFileNew);
            // Create MultipartBody.Part using file request-body,file name and part name
            imagee = MultipartBody.Part.createFormData("image", imageFileNew.getName(), fileReqBody);

            Util.showProgressDialog(this);
            RestClient.saveMapChallenge(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), userId, acitivityduration, distance, challengeactivity, challUserId, imagee, new Callback<ChallenegeMapResponse>() {
                @Override
                public void onResponse(Call<ChallenegeMapResponse> call, Response<ChallenegeMapResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.body() != null) {


                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            int submissionId = response.body().getNewchallenge().getId();
                            String userId = response.body().getNewchallenge().getUserId();
                            FuroPrefs.putInt(getApplicationContext(), "SubmissionChallenegeIdmap", submissionId);
                            FuroPrefs.putString(getApplicationContext(), "userIdLoginmap", userId);
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(PreviewCardActivityRecieve.this, WinnerActivityMap.class);
                            startActivity(intent);
                            finish();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                    }


                }


                @Override
                public void onFailure(Call<ChallenegeMapResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }


            });


        }


    }


    private void userlogout() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_alertdialog, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        Button btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
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
                dialog.dismiss();

                dotImage.setVisibility(View.VISIBLE);
                textViewChallenge.setVisibility(View.GONE);
                textViewWhatsApp.setVisibility(View.GONE);
                textViewFb.setVisibility(View.GONE);
                imageSharePreview.setVisibility(View.GONE);
                tvTextSec.setVisibility(View.GONE);
                iconfuro.setVisibility(View.VISIBLE);

                takeScreenshot();
            }
        });

        dialog.show();

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
                Intent intent = new Intent(PreviewCardActivityRecieve.this, MapOverViewActivity.class);
                startActivity(intent);
                finish();


            }
        });

        dialog.show();

    }

}


