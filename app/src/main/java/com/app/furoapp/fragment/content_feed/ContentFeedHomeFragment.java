package com.app.furoapp.fragment.content_feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.ContentFeedDetailActivity;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.newFeature.healthCare.Days21FitnessChallangeActivity;
import com.app.furoapp.activity.newFeature.likeAndSaved.LikedAndSavedActivity;
import com.app.furoapp.activity.YoutubePlayerActivity;
import com.app.furoapp.adapter.ContentFeedHomeAdapter;
import com.app.furoapp.databinding.FragmentContentFeedsListBinding;
import com.app.furoapp.model.content_feed.activityListing.ActivitiesListing;
import com.app.furoapp.model.content_feed.activityListing.Datum;
import com.app.furoapp.model.updateToken.UdateTokenResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.widget.SwitchButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ContentFeedHomeFragment extends Fragment {


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
                    getDataAll("Article");
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
                    getDataAll("Video");
                }
            }
        });
        getDataAll(type);
        updateTokenDetails();

        clickListener();
        clickOnLikeAndDislike();
        return view;
    }

    private void getDataAll(String type) {
        Util.isInternetConnected(getContext());
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
                                        datum = new Datum();
                                        datum.setVideo("" + datumList.get(i).getVideo());
                                        datum.setId(datumList.get(i).getId());
                                        datum.setIcon(datumList.get(i).getIcon());
                                        datum.setType("" + datumList.get(i).getType());
                                        datum.setType("" + datumList.get(i).getType());
                                        datum.setDescription("" + datumList.get(i).getDescription());

                                        Log.d(TAG, "Add DatumList");
                                        data.addAll(Collections.singleton(datum));
                                    }

                                }

                                contentFeedHomeAdapter = new ContentFeedHomeAdapter(data, getContext());
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                // recyclerView.OnLikeCommentsShareViewsBookmark(this);
                                recyclerView.setAdapter(contentFeedHomeAdapter);


                            }
                            if (type.equalsIgnoreCase("Article")) {
                                for (int i = 0; i < datumList.size(); i++) {

                                    if (datumList.get(i).getVideo() == null) {
                                        datum = new Datum();
                                        datum.setImage("" + datumList.get(i).getImage());
                                        datum.setId(datumList.get(i).getId());
                                        datum.setIcon(datumList.get(i).getIcon());
                                        datum.setType("" + datumList.get(i).getType());
                                        datum.setType("" + datumList.get(i).getType());
                                        datum.setDescription("" + datumList.get(i).getDescription());
                                        data.addAll(Collections.singleton(datum));
                                    }

                                }

                                contentFeedHomeAdapter = new ContentFeedHomeAdapter(data, getContext());
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(contentFeedHomeAdapter);


                            }
                        }

                        if (type.equalsIgnoreCase("")) {

                            for (int i = 0; i < datumList.size(); i++) {
                                if (datumList != null && datumList.size() > 0) {
                                    ActivitiesListing activitiesListingNew = response.body();

                                    int id = activitiesListingNew.getData().get(i).getId();
                                    FuroPrefs.putString(getContext(), "activity_id", String.valueOf(id));
                                    contentFeedHomeAdapter = new ContentFeedHomeAdapter(datumList, getContext());
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(contentFeedHomeAdapter);
                                }
                            }
                        }

                        contentFeedHomeAdapter.setContentFeedList(new ContentFeedHomeAdapter.ContentFeedInterface() {
                            @Override
                            public void contentFeedItem(int videoid) {
                                Intent intent = new Intent(getContext(), YoutubePlayerActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void contentFeedItem2(int id) {
                                Intent intent = new Intent(getContext(), ContentFeedDetailActivity.class);
                                startActivity(intent);
                                FuroPrefs.putString(getActivity(), "id", String.valueOf(id));
                            }
                            /*@Override
                            public void shareContentVideoPOst(int videoid) {
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody ="\"http://img.youtube.com/vi/\" + videoid + \"/0.jpg\"";
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                            }

                            @Override
                            public void shareContentPost(int id) {
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "\"http://img.youtube.com/vi/\" + videoid + \"/0.jpg\"";
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                            }*/
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ActivitiesListing> call, Throwable t) {
                Toast.makeText(homeMainActivity, "No internet connection!", Toast.LENGTH_SHORT).show();

            }
        });

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


    /*    private List<ContentListModel> getContentListRecord(){

            List<ContentListModel> contentListRecords = new ArrayList<ContentListModel>();
            String  categoryName[] ={"Activities", "Food", "Food", "Food", "Health", "Health", "Motivation", "Motivation"};
            Integer categoryImage[] = {R.drawable.activity_ic, R.drawable.food_ic, R.drawable.food_ic, R.drawable.food_ic, R.drawable.heart_ic, R.drawable.heart_ic, R.drawable.motivatioin_ic, R.drawable.motivatioin_ic};
            Integer contentImage[] = {R.drawable.activities_first, R.drawable.food_first, R.drawable.food_second, R.drawable.food_third, R.drawable.health_first, R.drawable.health_second, R.drawable.motivation_first, R.drawable.motivation_second};

            for (int index= 0; index<10; index++)
                for (int i= 0; i<  categoryImage.length;i++) {
                    ContentListModel  model =  new ContentListModel();
                    model.setFeedTypeCategoryImage(categoryImage[i]);
                    model.setFeedTypeCategoryName( categoryName[i]);
                    model.setFeedType( categoryName[i]);
                    model.setVideo(i == 0);
                    model.setContentImage( contentImage[i]);
                    model.setFeedTitle(  "Sample Title");
                    contentListRecords.add(model);
                }



            return contentListRecords;
        }*/
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
   /* public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

        public void checkNetworkConnection(){
            AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
            builder.setTitle("No internet Connection");
            builder.setMessage("Please turn on internet connection to continue");
            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }*/

    private void clickListener() {
        llSavedBookmarked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LikedAndSavedActivity.class);
                startActivity(intent);
            }
        });

        llViewDetals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Days21FitnessChallangeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clickOnLikeAndDislike() {


    }
}




