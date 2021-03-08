package com.app.furoapp.fragment.challenges;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.adapter.ClubFriendLeaderBoardAdapter;
import com.app.furoapp.databinding.ClubChallengeFragmentLeaderBoardFriendsBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.model.LeaderFriendModel;
import com.app.furoapp.utils.FuroPrefs;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ClubChallengeFriendLeaderBoardFragment extends Fragment {
    private RecyclerView recyclerViewLeadeBoardFragment;

    List<LeaderFriendModel> leaderFriendModelList = new ArrayList<>();
    ClubFriendLeaderBoardAdapter clubFriendLeaderBoardAdapter;
    ClubChallengeFragmentLeaderBoardFriendsBinding binding;
    String token;
    HomeMainActivity homeMainActivity;
    TextView backButton,noFriends;

    ArrayList<String> alContacts = new ArrayList<>();
    String userId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.club_challenge_fragment_leader_board_friends, container, false);
        View view = binding.getRoot();

        backButton=view.findViewById(R.id.backtvTitle);
        noFriends=view.findViewById(R.id.friends);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeMainActivity.setDisplayFragment(EnumConstants.HOME_CHALLENGE_DETAILS_FRAGMENT,null);


            }
        });


        token = FuroPrefs.getString(getContext(), "token");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                onProfileImageClick();
            }
        });



        recyclerViewLeadeBoardFragment = view.findViewById(R.id.clubfriendsRecyclerview);


        return view;
    }


    public static ClubChallengeFriendLeaderBoardFragment newInstance(String name) {
        ClubChallengeFriendLeaderBoardFragment fragment = new ClubChallengeFriendLeaderBoardFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    public void onProfileImageClick() {
        Dexter.withActivity((Activity) getContext())
                .withPermissions(Manifest.permission.READ_CONTACTS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            getContactList();
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

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private void getContactList() {

        ContentResolver cr = getContext().getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {

            alContacts = new ArrayList<>();
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        alContacts.add(contactNumber);

                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext());
        }

      /*  sendContactsToServer();*/

    }


   /* public void sendContactsToServer() {

        Gson gson = new Gson();
        String json = gson.toJson(alContacts);

        ExistsContactRequest existsContactRequest = new ExistsContactRequest();
        existsContactRequest.setContacts(Collections.singletonList(json));
        Util.isInternetConnected(getContext());
        Util.showProgressDialog(getContext());
        RestClient.userExistsContact(existsContactRequest, new Callback<ExistsContactResponse>() {

            @Override
            public void onResponse(Call<ExistsContactResponse> call, Response<ExistsContactResponse> response) {
                Util.dismissProgressDialog();

                if (response.body() != null) {
                    List<ExistContact> existContacts = response.body().getExistContacts();
                    clubFriendLeaderBoardAdapter = new ClubFriendLeaderBoardAdapter(existContacts, getContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerViewLeadeBoardFragment.setLayoutManager(layoutManager);
                    recyclerViewLeadeBoardFragment.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewLeadeBoardFragment.setAdapter(clubFriendLeaderBoardAdapter);


                }else {
                    noFriends.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ExistsContactResponse> call, Throwable t) {

                Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
            }
        });


    }*/





}


