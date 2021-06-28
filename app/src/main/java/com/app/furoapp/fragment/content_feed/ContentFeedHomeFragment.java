package com.app.furoapp.fragment.content_feed;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.ContentFeedDetailActivity;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.YoutubePlayerActivity;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.feedHomeFragment_ListingNew.ActivitiesListing;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.feedHomeFragment_ListingNew.Datum;
import com.app.furoapp.activity.newFeature.healthCare.Days21FitnessChallangeActivity;
import com.app.furoapp.activity.newFeature.likeAndSaved.LikedAndSavedActivity;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.like.LikeRequest;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.like.LikeResponse;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.saveBookmark.SavedRequest;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.saveBookmark.SavedResponse;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.userView.ViewsRequest;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.userView.ViewsResponse;
import com.app.furoapp.adapter.ContentFeedHomeAdapter;
import com.app.furoapp.databinding.FragmentContentFeedsListBinding;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.feedHomeFragment_ListingNew.ActivityFilterData;
import com.app.furoapp.model.updateToken.UdateTokenResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.widget.SwitchButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_WRITE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ContentFeedHomeFragment extends Fragment implements ContentFeedHomeAdapter.ContentFeedCallback {


    FragmentContentFeedsListBinding binding;
    ContentFeedHomeAdapter contentFeedHomeAdapter;
    HomeMainActivity homeMainActivity;
    RecyclerView recyclerView;
    SwitchButton switchButtonVideo, switchButtonArticle;
    String type = "", newToken, unique_id, loginuserid;
    GifView pGif;
    TextView textView;
    Datum datum;
    List<Datum> data = new ArrayList<>();
    public static String TAG = ContentFeedHomeFragment.class.getSimpleName();
    public LinearLayout llSavedBookmarked, llViewDetals;
    public Boolean clicked = true;
    private String getAccessToken;
    public Integer id;
    int adapterCurrentPos = 0;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_content_feeds_list, container, false);

        View view = binding.getRoot();

        pGif = view.findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.run);
        textView = view.findViewById(R.id.pbText);
        recyclerView = view.findViewById(R.id.rvContentfeed);
        switchButtonVideo = binding.switchButtonVideo;
        switchButtonArticle = binding.switchbuttonArticle;
        llSavedBookmarked = view.findViewById(R.id.llSavedBookmarked);
        llViewDetals = view.findViewById(R.id.llViewDetals);

        unique_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        loginuserid = FuroPrefs.getString(getContext(), "loginUserId");
        /*added get access token*/
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        Log.d(TAG, "onCreateView()" + getAccessToken);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(homeMainActivity, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                FuroPrefs.putString(getActivity(), "token", newToken);
            }
        });

        //  Toast.makeText(homeMainActivity, "uniqueid" + unique_id + "" + "fcmtoken" + newToken + "" + "userid" + loginuserid, Toast.LENGTH_LONG).show();

        switchButtonVideo.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    type = "Video";
                    switchButtonArticle.setChecked(false);
                    getDataAll(type);
                } else {
                    if ((switchButtonArticle.isChecked()))
                        getDataAll("Article");
                    else {
                        getDataAll("");
                    }
                }
            }
        });


        switchButtonArticle.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    type = "Article";
                    switchButtonVideo.setChecked(false);
                    getDataAll(type);
                } else {
                    if ((switchButtonVideo.isChecked()))
                        getDataAll("Video");
                    else {
                        getDataAll("");
                    }
                }
            }
        });

        getDataAll(type);
        updateTokenDetails();

        clickListener();
        return view;
    }

    private void getDataAll(String type) {
        if (Util.isInternetConnected(getActivity())) {
            Util.showProgressDialog(getActivity());
            RestClient.myContentfeedAllActivity(getAccessToken, new Callback<ActivitiesListing>() {
                @Override
                public void onResponse(Call<ActivitiesListing> call, Response<ActivitiesListing> response) {
                    Util.dismissProgressDialog();

                    if (response.body() != null) {
                        ActivitiesListing activitiesListing = response.body();
                        if (activitiesListing != null && activitiesListing.getData() != null) {
                            List<Datum> datumList = activitiesListing.getData();
                            if (datumList != null && datumList.size() > 0) {
                                Log.d(TAG, "" + datumList.size());
                                data.clear();
                                if (type.equalsIgnoreCase("video")) {
                                    for (int i = 0; i < datumList.size(); i++) {
                                        if (datumList.get(i).getVideo() != null) {
                                            ActivityFilterData filterData = new ActivityFilterData();
                                            filterData.setDatum(datumList.get(i));
                                            datum = filterData.getDatum();
                                            datum.setVideo("" + datumList.get(i).getVideo());
                                            datum.setId(datumList.get(i).getId());
                                            datum.setIcon(datumList.get(i).getIcon());
                                            datum.setType("" + datumList.get(i).getType());
//                                        datum.setType("" + datumList.get(i).getType());
                                            datum.setDescription("" + datumList.get(i).getDescription());

                                            Log.d(TAG, "Add DatumList");
                                            data.addAll(Collections.singleton(datum));
                                        }
                                    }
                                    setContentFeedHomeAdapter(data);
                                }
                                if (type.equalsIgnoreCase("Article")) {
                                    for (int i = 0; i < datumList.size(); i++) {
                                        if (datumList.get(i).getVideo() == null) {
                                            ActivityFilterData filterData = new ActivityFilterData();
                                            filterData.setDatum(datumList.get(i));
                                            datum = filterData.getDatum();
                                            datum.setImage("" + datumList.get(i).getImage());
                                            datum.setId(datumList.get(i).getId());
                                            datum.setIcon(datumList.get(i).getIcon());
                                            datum.setType("" + datumList.get(i).getType());
//                                        datum.setType("" + datumList.get(i).getType());
                                            datum.setDescription("" + datumList.get(i).getDescription());
                                            data.addAll(Collections.singleton(datum));
                                        }

                                    }
                                    setContentFeedHomeAdapter(data);
                                }
                            }

                            if (type.equalsIgnoreCase("")) {
                                if (datumList != null && datumList.size() > 0) {
                                    id = datumList.get(0).getId();
                                    FuroPrefs.putString(getContext(), "activity_id", String.valueOf(id));
                                    FuroPrefs.putString(getApplicationContext(), Constants.ACTIVITY_Id, String.valueOf(id));
                                    setContentFeedHomeAdapter(datumList);
                                    //recyclerView.setAdapter(contentFeedHomeAdapter);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ActivitiesListing> call, Throwable t) {
                    Toast.makeText(homeMainActivity, "Something went wrong !", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void setContentFeedHomeAdapter(List<Datum> list) {
        contentFeedHomeAdapter = new ContentFeedHomeAdapter(list, getContext(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contentFeedHomeAdapter);

        recyclerView.scrollToPosition(adapterCurrentPos);   // scroll to current position in android
    }


    public ContentFeedHomeFragment() {

    }

    public static ContentFeedHomeFragment newInstance(String name) {
        ContentFeedHomeFragment fragment = new ContentFeedHomeFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    public void setSwitchButtonVideo() {
        switchButtonVideo.setChecked(true);
        switchButtonVideo.isChecked();
        switchButtonVideo.toggle();     //switch state
        switchButtonVideo.toggle(false);//switch without animation
        switchButtonVideo.setShadowEffect(true);//disable shadow effect
        switchButtonVideo.setEnabled(false);//disable button
        switchButtonVideo.setEnableEffect(false);//disable the switch animation
        switchButtonVideo.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
    }


    public void updateTokenDetails() {
        String userfcmticket = FuroPrefs.getString(homeMainActivity, "token");
        Log.i("userfcmticketttt", userfcmticket);
        RequestBody Iduser = RequestBody.create(MediaType.parse("text/plain"), loginuserid);
        RequestBody userdeviceid = RequestBody.create(MediaType.parse("text/plain"), unique_id);
        RequestBody userfcmtoken = RequestBody.create(MediaType.parse("text/plain"), userfcmticket);
        RestClient.updateTokenofuser(Iduser, userdeviceid, userfcmtoken, new Callback<UdateTokenResponse>() {
            @Override
            public void onResponse(Call<UdateTokenResponse> call, Response<UdateTokenResponse> response) {
                if (response.body() != null) {
                    // Toast.makeText(homeMainActivity, "success", Toast.LENGTH_SHORT).show();
                } else {
                }
            }

            @Override
            public void onFailure(Call<UdateTokenResponse> call, Throwable t) {
            }
        });
    }

    /*com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.Like event */
    private void callLikeApi(LikeRequest data) {
        RestClient.userPostLike(getAccessToken, data, new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if (response.body() != null) {
                    /*LikeResponse likeResponse = response.body();
                    showToast(likeResponse.getStatus());*/
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private LikeRequest getLikeApiParams(int postId, int flag) {
        LikeRequest request = new LikeRequest();
        request.setLikeFlag(flag);
        request.setPostId(postId);
        return request;
    }

    @Override
    public void onClickLike(int pos, Datum data) {
//        showToast(data.getUserLike());
        if (data.getUserLike().equals("0")) {
            data.setTotalLikes(data.getTotalLikes() + 1);
            data.setUserLike("1");
        } else {
            data.setTotalLikes(data.getTotalLikes() - 1);
            data.setUserLike("0");
        }
        contentFeedHomeAdapter.updateData(pos, data);
        callLikeApi(getLikeApiParams(data.getId(), Integer.parseInt(data.getUserLike())));
    }

    /*View event*/
    private void callViewsApi(ViewsRequest data) {
        RestClient.userPostView(getAccessToken, data, new Callback<ViewsResponse>() {
            @Override
            public void onResponse(Call<ViewsResponse> call, Response<ViewsResponse> response) {
                if (response.body() != null) {
                    /*ViewsResponse viewsResponse = response.body();
                    showToast(viewsResponse.getStatus());*/
                }
            }

            @Override
            public void onFailure(Call<ViewsResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ViewsRequest getViewsApiParams(Integer id, int postId) {
        ViewsRequest request = new ViewsRequest();
        request.setPostId(postId);
        return request;
    }

    @Override
    public void onClickView(int pos, Datum data) {
        if (data.getUserView().equals("0")) {
            data.setTotalViews(data.getTotalViews() + 1);/**/
            data.setUserView("1");
        } else {
            data.setTotalViews(data.getTotalViews() - 1);/* */
            data.setUserView("0");
        }
        contentFeedHomeAdapter.updateData(pos, data);
        callViewsApi(getViewsApiParams(data.getId(), Integer.parseInt(data.getUserView())));
    }

    /*Saved event*/
    private void callSavedApi(SavedRequest data) {
        RestClient.userPostSaved(getAccessToken, data, new Callback<SavedResponse>() {
            @Override
            public void onResponse(Call<SavedResponse> call, Response<SavedResponse> response) {
                if (response.body() != null) {
                   /* SavedResponse likeResponse = response.body();
                    showToast(likeResponse.getStatus());*/
                }
            }

            @Override
            public void onFailure(Call<SavedResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private SavedRequest getSavedApiParams(int postId, int flag) {
        SavedRequest request = new SavedRequest();
        request.setSaveFlag(flag);
        request.setPostId(postId);
        return request;
    }

    @Override
    public void onClickSaveBookmark(int pos, Datum data) {
        if (data.getUserSave().equals("0")) {
            data.setUserSave("1");
        } else {
            data.setUserSave("0");
        }
        contentFeedHomeAdapter.updateData(pos, data);
        callSavedApi(getSavedApiParams(data.getId(), Integer.parseInt(data.getUserSave())));
    }

    @Override
    public void onClickShare(int pos, Datum data) {
        String title = data.getDescription();
        String url;
        boolean isArticle = false;
        String videoId = null;
        try {
            videoId = Util.extractYoutubeId(data.getVideo());
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(videoId)) {
            url = "http://www.youtube.com/watch?v=" + videoId;
            isArticle = false;
        } else {
            url = data.getImage();
            isArticle = true;
        }
        if (isArticle) {
            if (checkPermission()) {
                shareImage(url, title);
            }
        } else {
            Log.d(TAG, "() calleonClickShared with: pos = [" + url + "], data = [" + url + "]");
            String fullMessage = title + "\n" + url;
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Furo FQ");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, fullMessage);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

    }

    @Override
    public void contentFeedItem(int pos, int videoId) {
        adapterCurrentPos = pos;
        Intent intent = new Intent(getContext(), YoutubePlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void contentFeedItem2(int pos, int id) {
        adapterCurrentPos = pos;
        Intent intent = new Intent(getContext(), ContentFeedDetailActivity.class);
        startActivity(intent);
        FuroPrefs.putString(getActivity(), "id", String.valueOf(id));
    }

    @Override
    public void onClickComment(int pos, Datum data) {
        adapterCurrentPos = pos;
        Intent intent = new Intent(getApplicationContext(), ContentFeedDetailActivity.class);
        FuroPrefs.putString(getApplicationContext(), "id", String.valueOf(data.getId()));
        startActivity(intent);
    }

    private void clickListener() {
        llSavedBookmarked.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LikedAndSavedActivity.class);
            FuroPrefs.putString(getApplicationContext(), "id", String.valueOf(id));
            startActivity(intent);
        });

        llViewDetals.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Days21FitnessChallangeActivity.class);
            startActivity(intent);
        });
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataAll(type);
    }

    public void shareImage(String url, String title) {
        Picasso.with(getApplicationContext()).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                String fileUri = "";

                String filename = System.currentTimeMillis() + ".png";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                File ExternalStorageDirectory = Environment.getExternalStorageDirectory();
                File file = new File(ExternalStorageDirectory + File.separator + filename);
                FileOutputStream fileOutputStream = null;
                try {
                    file.createNewFile();
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(bytes.toByteArray());
                    fileUri = file.getAbsolutePath();

//                    File sd = Environment.getExternalStorageDirectory();
//                    File dest = new File(sd, filename);
//
//                    FileOutputStream outputStream = new FileOutputStream(dest);
//                    fileUri = dest.getAbsolutePath();
//                    Log.d(TAG, "onBitmapLoaded() called with: bitmap = [" + fileUri + "], from = [" + fileUri + "]");
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    outputStream.flush();
//                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), BitmapFactory.decodeFile(fileUri), null, null));
                // use intent to share image
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_TEXT, title);

                startActivity(Intent.createChooser(share, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }


    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_MEDIA_LOCATION}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }
}




