package com.app.furoapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.activity.tutorialScreens.LoginWithEmailActivity;
import com.app.furoapp.activity.tutorialScreens.WelcomeUserYouAreInActivity;
import com.app.furoapp.model.loginWithFb.LoginWithFbRequest;
import com.app.furoapp.model.loginWithFb.LoginWithFbResponse;
import com.app.furoapp.model.loginwithgmail.LoginwithGmailRequest;
import com.app.furoapp.model.loginwithgmail.LoginwithGmailResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.app.furoapp.R;
import com.app.furoapp.adapter.CustomPagerWithoutTitleAdapter;
import com.app.furoapp.databinding.ActivityOnLoginTutorialScreenBinding;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTutorialScreen extends AppCompatActivity {
    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;
    final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    //creating a GoogleSignInClient object
    public GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    int currentPage = 0;
    Timer timer;
    ActivityOnLoginTutorialScreenBinding binding;
    ImageView loginWithFb;

    // add on 25-11-2019 by pankaj
    ImageView signInButton;
    FirebaseAuth mAuth;
    private CustomPagerWithoutTitleAdapter adapter;
    private ImageView getLoginWithGmail;
    private boolean isGoogleSignIn = false;
    private FrameLayout loginFrmActivity;
    private Object WhiteRectangleDetector;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_login_tutorial_screen);
        onProfileImageClickNew();
        loginWithFb = binding.ivSignWithFB;
//        getLoginWithGmail = binding.ivSignWithGoogle;
        callbackManager = CallbackManager.Factory.create();
        initView();

        // add on 25-11-2019 by pankaj
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        loginFrmActivity = findViewById(R.id.LoginFrmActivity);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFrmActivity.setBackgroundColor(Color.argb(255, 255, 255, 255)); ////////////
                signIn();
            }
        });

        binding.ivSignWithFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginTutorialScreen.this, Arrays.asList("public_profile", "email"));
                loginwithFb();
            }
        });
    }


    private void initView() {
        setPagerAdapter();
        setPagerIndicator();
        setOnClickListener();
    }


    private void setOnClickListener() {

        binding.llsignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FuroPrefs.putString(getApplicationContext(), "googl_id", "");
                FuroPrefs.putString(getApplicationContext(), "fb_id", "");
                Intent mainIntent = new Intent(LoginTutorialScreen.this, SignUpActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainIntent);

            }
        });


        binding.llLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(LoginTutorialScreen.this, LoginWithEmailActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainIntent);

            }
        });


    }

    private void setPagerIndicator() {
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewPager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;

        binding.indicator.setViewPager(binding.vpOnboard);
    }

    private void setPagerAdapter() {
        adapter = new CustomPagerWithoutTitleAdapter(this, getImages());
        binding.vpOnboard.setAdapter(adapter);
        binding.vpOnboard.setCurrentItem(0);
        binding.vpOnboard.setOffscreenPageLimit(3); ///
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                int NUM_PAGES = 3;
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                binding.vpOnboard.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }


    private ArrayList<Integer> getImages() {

        ArrayList imagesList = new ArrayList<Integer>();
        imagesList.add(R.drawable.loginfirst);
        imagesList.add(R.drawable.loginsecond);
        imagesList.add(R.drawable.loginthird);
        return imagesList;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // add on 25-11-2019 by pankaj
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                 account = task.getResult(ApiException.class);
                // handleSignInResult(task);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }

        }
    }


    // add on 25-11-2019 by pankaj
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "sign in google", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("failed code=", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "sign in failed ", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String gEmail, gName, googleid, getGoogleIdToken, userImage;
                            gEmail = acct.getEmail();
                            gName = acct.getDisplayName();
                            googleid = acct.getId();
                            getGoogleIdToken = acct.getIdToken();

                            Log.i("googl_id", googleid);
                            FuroPrefs.putString(getApplicationContext(), "googl_id", googleid);

                            LoginwithGmailRequest loginwithGmailRequest = new LoginwithGmailRequest();
                            loginwithGmailRequest.setGoogleId(googleid);
                            RestClient.userloginwithgmail(loginwithGmailRequest, new Callback<LoginwithGmailResponse>() {
                                @Override
                                public void onResponse(Call<LoginwithGmailResponse> call, Response<LoginwithGmailResponse> response) {
                                    if (response != null) {
                                        if (response.body() != null) {
                                            if (response.body().getStatus() == 0) {
                                                FuroPrefs.putString(getApplicationContext(), "email", gEmail);
                                                FuroPrefs.putString(getApplicationContext(), "name", gName);
                                                Toast.makeText(LoginTutorialScreen.this, "GoogleIdToken " + getGoogleIdToken, Toast.LENGTH_SHORT).show();
                                                Toast.makeText(LoginTutorialScreen.this, "googleid " + googleid, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginTutorialScreen.this, SignUpActivity.class);
                                                startActivity(intent);
                                                finish();


                                            } else if (response.body().getStatus() == 1) {
                                                if (response.body().getReasons().equalsIgnoreCase("0")) {
                                                    FuroPrefs.putBoolean(getApplicationContext(), Constants.LOGGEDALERADYIN, true);
                                                    String loginUserId = String.valueOf(response.body().getUser().getId());

                                                    String userNameee = response.body().getUser().getName();
                                                    String Image = response.body().getUser().getImage();
                                                    String accessToken = response.body().getUser().getAccessToken();
                                                    FuroPrefs.putString(getApplicationContext(), "accessToken", accessToken);
                                                    Toast.makeText(LoginTutorialScreen.this, "" + loginUserId, Toast.LENGTH_SHORT).show();
                                                    FuroPrefs.putString(getApplicationContext(), "loginUserId", loginUserId);
                                                    FuroPrefs.putString(getApplicationContext(), "userimage", Image);
                                                    FuroPrefs.putString(getApplicationContext(), "loginUserNameNew", userNameee);

                                                    Intent intent = new Intent(getApplicationContext(), WelcomeUserYouAreInActivity.class);
                                                    Toast.makeText(LoginTutorialScreen.this, "GoogleIdToken " + getGoogleIdToken, Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(LoginTutorialScreen.this, "googleid " + googleid, Toast.LENGTH_SHORT).show();

                                                    startActivity(intent);
                                                    finish();
                                                    //tutorialScreen.setDisplayFragment(EnumConstants.HOME_TUTORIAL_PAGE, null);
                                                   // Toast.makeText(LoginTutorialScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                                } else if (response.body().getReasons().equalsIgnoreCase("1")) {
                                                    FuroPrefs.putBoolean(getApplicationContext(), Constants.LOGGEDIN, true);
                                                    String loginUserId = String.valueOf(response.body().getUser().getId());
                                                    String userNameNew = response.body().getUser().getName();
                                                    String Image = response.body().getUser().getImage();
                                                    FuroPrefs.putString(getApplicationContext(), "loginUserId", loginUserId);
                                                    FuroPrefs.putString(getApplicationContext(), "userimage", Image);
                                                    FuroPrefs.putString(getApplicationContext(), "loginUserNameNew", userNameNew);
                                                    Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                                                    Toast.makeText(LoginTutorialScreen.this, "GoogleIdToken " + getGoogleIdToken, Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(LoginTutorialScreen.this, "googleid " + googleid, Toast.LENGTH_SHORT).show();
                                                    intent.putExtra("contestpage", "");
                                                    startActivity(intent);
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginwithGmailResponse> call, Throwable t) {
                                    Toast.makeText(LoginTutorialScreen.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginTutorialScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }


    private void loginwithFb() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        JSONObject data = response.getJSONObject();
                        try {
                            String name = data.getString("name");
                            String email = data.optString("email");
                            String facebook_id = data.getString("id");
                            String pictureurl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                            RequestBody facebookRequestBody = RequestBody.create(MediaType.parse("text/plain"), facebook_id);
                            LoginWithFbRequest loginWithFbRequest = new LoginWithFbRequest();

                            loginWithFbRequest.setFbId(facebook_id);
                            RestClient.loginFb(loginWithFbRequest, new Callback<LoginWithFbResponse>() {
                                @Override
                                public void onResponse(Call<LoginWithFbResponse> call, Response<LoginWithFbResponse> response) {
                                    if (response != null) {
                                        if (response.body() != null) {
                                            if (response.body().getStatus() == 0) {
                                                Intent intent = new Intent(LoginTutorialScreen.this, SignUpActivity.class);

                                                FuroPrefs.putString(getApplicationContext(), "fb_id", facebook_id);
                                                FuroPrefs.putString(getApplicationContext(), "loginUserNameNew", name);
                                                FuroPrefs.putString(getApplicationContext(), "email", email);
                                                FuroPrefs.putString(getApplicationContext(), "picture", pictureurl);
                                                FuroPrefs.putBoolean(getApplicationContext(), "FacebookUrl", true);
                                                startActivity(intent);
                                                finish();


                                            } else if (response.body().getStatus() == 1) {
                                                if (response.body().getReasons().equalsIgnoreCase("0")) {
                                                    FuroPrefs.putBoolean(getApplicationContext(), Constants.LOGGEDALERADYIN, true);
                                                    String loginUserId = String.valueOf(response.body().getUser().getId());

                                                    String userNameee = response.body().getUser().getName();
                                                    String Image = response.body().getUser().getImage();
                                                    String accessToken = response.body().getUser().getAccessToken();
                                                    FuroPrefs.putString(getApplicationContext(), "accessToken", accessToken);
                                                    FuroPrefs.putString(getApplicationContext(), "loginUserId", loginUserId);
                                                    FuroPrefs.putString(getApplicationContext(), "userimage", Image);
                                                    FuroPrefs.putString(getApplicationContext(), "loginUserNameNew", userNameee);

                                                    Intent intent = new Intent(getApplicationContext(), WelcomeUserYouAreInActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //tutorialScreen.setDisplayFragment(EnumConstants.HOME_TUTORIAL_PAGE, null);
                                                    Toast.makeText(LoginTutorialScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                                } else if (response.body().getReasons().equalsIgnoreCase("1")) {
                                                    FuroPrefs.putBoolean(getApplicationContext(), Constants.LOGGEDIN, true);
                                                    String loginUserId = String.valueOf(response.body().getUser().getId());
                                                    String userNameNew = response.body().getUser().getName();
                                                    String Image = response.body().getUser().getImage();
                                                    FuroPrefs.putString(getApplicationContext(), "loginUserId", loginUserId);
                                                    FuroPrefs.putString(getApplicationContext(), "userimage", Image);
                                                    FuroPrefs.putString(getApplicationContext(), "loginUserNameNew", userNameNew);
                                                    Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                                                    intent.putExtra("contestpage", "");
                                                    startActivity(intent);

                                                }

                                            }


                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<LoginWithFbResponse> call, Throwable t) {
                                    Toast.makeText(LoginTutorialScreen.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "id,name,email,picture,birthday,gender,age_range");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();


            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginTutorialScreen.this, "Login cancelled ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginTutorialScreen.this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void signIn() {
        isGoogleSignIn = true;
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void onProfileImageClickNew() {
        Dexter.withActivity(this)
                .withPermissions(/*Manifest.permission.READ_CONTACTS,*/
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
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
    public void onBackPressed() {

        if (!isGoogleSignIn) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.app_close_alertdialognew, null);


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
                    finishAffinity();
                    System.exit(0);


                }
            });

            dialog.show();
        }

    }


}






