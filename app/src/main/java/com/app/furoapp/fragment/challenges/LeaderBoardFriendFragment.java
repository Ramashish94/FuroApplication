package com.app.furoapp.fragment.challenges;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.app.furoapp.R;
import com.app.furoapp.adapter.FriendLeaderBoardAdapter;
import com.app.furoapp.databinding.FragmentLeaderBoardFriendsBinding;
import com.app.furoapp.model.FriendModel.AddFriend;
import com.app.furoapp.model.FriendModel.FriendInviteModel;
import com.app.furoapp.model.LeaderFriendModel;
import com.app.furoapp.model.challangeNotification.ChallangeNotificationRequest;
import com.app.furoapp.model.challangeNotification.ChallangeNotificationResponse;
import com.app.furoapp.model.existsContact.ExistContact;
import com.app.furoapp.model.existsContact.ExistContactUpdate;
import com.app.furoapp.model.existsContact.ExistsContactResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


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

public class LeaderBoardFriendFragment extends Fragment implements FriendLeaderBoardAdapter.FriendLeaderBoardInterface {
    private static final String LOG_TAG = LeaderBoardFriendFragment.class.getName();
    List<LeaderFriendModel> leaderFriendModelList = new ArrayList<>();
    FriendLeaderBoardAdapter friendLeaderBoardAdapterr;
    FragmentLeaderBoardFriendsBinding binding;
    String token;

    EditText editTextSearch;
    TextView textView, lodingtxt;
    String challId, userLoginId;
    ArrayList<String> alContacts;
    ArrayList<String> valid_mob_no_list;
    String userId;
    List<ExistContactUpdate> exact_list_update;
    List<ExistContact> existContacts = new ArrayList<>();

    List<ExistContact> existfqfriends = new ArrayList<>();
    List<ExistContact> existcontactsfriends = new ArrayList<>();
    Context context = getActivity();
    ArrayList<String> list_user_id;
    //    ArrayList<String> list_user_name;
    ArrayList<String> list_pos;
    ImageView img_gif;
    LinearLayout linearLayout;
    GifView pGif;
    RecyclerView recyclerViewFqFriends;
    private RecyclerView recyclerViewLeadeBoardFragment;

    //   FriendLeaderBoardAdapter.FriendLeaderBoardInterface leaderBoardInterface;
    public static LeaderBoardFriendFragment newInstance(String name) {
        LeaderBoardFriendFragment fragment = new LeaderBoardFriendFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_leader_board_friends, container, false);
        View view = binding.getRoot();
        textView = view.findViewById(R.id.nofriends);
        editTextSearch = view.findViewById(R.id.friends_search);
        lodingtxt = view.findViewById(R.id.loadingtext);
        pGif = view.findViewById(R.id.progressBarJumpingJacks);
        lodingtxt.setVisibility(View.VISIBLE);
        linearLayout = view.findViewById(R.id.refreshButton);

        userLoginId = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        token = FuroPrefs.getString(getContext(), "token");

        // add on 20-11-2019 by pankaj
        new CallFriends().execute();

        setOnClickListner();

        exact_list_update = new ArrayList<>();
        list_user_id = new ArrayList<>();
//        list_user_name = new ArrayList<>();
        list_pos = new ArrayList<>();
        recyclerViewLeadeBoardFragment = view.findViewById(R.id.friendsRecyclerview);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendContactsToServer();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                // Call back the Adapter with current character to Filter
                if (existContacts.size() > 0 && existContacts != null) {
                    filter(s.toString());
                }

            }
        });

    }


    private void getContactList() {

        ContentResolver cr = getContext().getContentResolver();
        //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor.moveToFirst()) {

//            alContacts = new ArrayList<>();
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
        // }
    }


    public void sendContactsToServer() {

        Gson gson = new Gson();
        String json = gson.toJson(valid_mob_no_list);

        challId = FuroPrefs.getString(getApplicationContext(), "challengeidforshare");

        RequestBody userContacts = RequestBody.create(MediaType.parse("text/plain"), json);
        RequestBody userChallengeId = RequestBody.create(MediaType.parse("text/plain"), challId);
        RequestBody userLogin = RequestBody.create(MediaType.parse("text/plain"), userLoginId);

        Log.i("result_contacts_1", json);


        RestClient.userExistsContact(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), userContacts, userLogin, new Callback<ExistsContactResponse>() {
            @Override
            public void onResponse(Call<ExistsContactResponse> call, Response<ExistsContactResponse> response) {

                pGif.setVisibility(View.GONE);
                lodingtxt.setVisibility(View.GONE);

                if (response.body() != null && response.body().getExistContacts().size() > 0) {
                    existContacts = response.body().getExistContacts();


                    if (existContacts.size() > 0 && existContacts != null) {
                        for (int i = 0; i < existContacts.size(); i++) {
                            if (existContacts.get(i).getFqfrnd().equalsIgnoreCase("0")) {
                                existcontactsfriends.add(existContacts.get(i));
                            } else {
                                existfqfriends.add(existContacts.get(i));
                            }
                        }
                        lodingtxt.setVisibility(View.GONE);

                        for (ExistContact existContact : existContacts) {
                            exact_list_update.add(new ExistContactUpdate(existContact.getMobile(), existContact.getUserId(), existContact.getName(), existContact.getUsername(), existContact.getImage(), existContact.getFqfrnd(), false, false, false));
                        }

                        setAdapter();

                    } else {
                        textView.setVisibility(View.VISIBLE);
                    }

                } else {
                    lodingtxt.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<ExistsContactResponse> call, Throwable t) {
//                Util.dismissProgressDialog();
                pGif.setVisibility(View.GONE);
                lodingtxt.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void setOnClickListner() {


        binding.sendChallange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userIdlist = String.valueOf(list_user_id);
                String shareChallengeid = FuroPrefs.getString(getApplicationContext(), "challengeidforshare");
                Util.isInternetConnected(getContext());

                ChallangeNotificationRequest challangeNotificationRequest = new ChallangeNotificationRequest();
                challangeNotificationRequest.setUserId(Collections.singletonList(userIdlist));
                challangeNotificationRequest.setLoginId(userLoginId);
                challangeNotificationRequest.setChallengeId(shareChallengeid);
                Util.showProgressDialog(getContext());
                RestClient.userChallangeNotification(FuroPrefs.getString(getActivity(), Constants.Get_ACCESS_TOKEN), challangeNotificationRequest, new Callback<ChallangeNotificationResponse>() {
                    @Override
                    public void onResponse(Call<ChallangeNotificationResponse> call, Response<ChallangeNotificationResponse> response) {
                        Util.dismissProgressDialog();
                        if (response != null) {
                            if (response.body() != null) {
                                if (response.body().getStatus().equalsIgnoreCase("200")) {


                                    for (String pos : list_pos) {
                                        exact_list_update.get(Integer.parseInt(pos)).setSend(true);
                                    }
                                    list_user_id.clear();
                                    list_pos.clear();

                                    setAdapter();

                                    Toast.makeText(getContext(), "Challenge sent Successfully", Toast.LENGTH_SHORT).show();


                                }


                            } else {
                                Toast.makeText(getContext(), "Error in Sending challenge", Toast.LENGTH_SHORT).show();
                            }

                        }


                    }

                    @Override
                    public void onFailure(Call<ChallangeNotificationResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Something went wrong   !", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }


    public void setAdapter() {
        friendLeaderBoardAdapterr = null;
        friendLeaderBoardAdapterr = new FriendLeaderBoardAdapter(this, exact_list_update, list_user_id, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewLeadeBoardFragment.setLayoutManager(layoutManager);
        recyclerViewLeadeBoardFragment.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLeadeBoardFragment.setAdapter(friendLeaderBoardAdapterr);

        friendLeaderBoardAdapterr.setAddFriends(new FriendLeaderBoardAdapter.AddNewFriend() {
            @Override
            public void AddFriendClick(int userid, int pos) {
                AddFriendsData(String.valueOf(userid), pos);
            }


        });
    }

    @Override
    public void OnCheckItem(int position, boolean flag) {
        exact_list_update.get(position).setChecked(flag);
        if (exact_list_update.get(position).getChecked()) {
//            Toast.makeText(getActivity(),exact_list_update.get(position).getUserId(),Toast.LENGTH_LONG).show();

            list_user_id.add(String.valueOf(exact_list_update.get(position).getUserId()));
//            list_user_name.add(String.valueOf(exact_list_update.get(position).getName()));
            list_pos.add(String.valueOf(position));
        } else {
            list_user_id.remove(String.valueOf(exact_list_update.get(position).getUserId()));
//            list_user_name.remove(String.valueOf(exact_list_update.get(position).getName()));
            list_pos.remove(String.valueOf(position));
        }

//        Toast.makeText(getActivity(),"",Toast.LENGTH_LONG).show();
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        List<ExistContactUpdate> filterdList = new ArrayList<>();

        //looping through existing elements
        for (ExistContactUpdate p : exact_list_update) {
            //if the existing elements contains the search input
            if (p.getName().toLowerCase().contains(text.toLowerCase()) || p.getUsername().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdList.add(p);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        friendLeaderBoardAdapterr.filterList(filterdList);
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private void AddFriendsData(String friend_id, int pos) {
        if (Util.isInternetConnected(getActivity())) {
            Util.showProgressDialog(getActivity());
            RestClient.AddFriends(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), new AddFriend(FuroPrefs.getString(getActivity(), "loginUserId"), friend_id), new Callback<FriendInviteModel>() {
                @Override
                public void onResponse(Call<FriendInviteModel> call, Response<FriendInviteModel> response) {
                    Util.dismissProgressDialog();
                    FriendInviteModel friends = response.body();
                    if (friends != null) {
                        if (friends.getStatus().equals("200")) {
                            Toast.makeText(getActivity(), friends.getMessage(), Toast.LENGTH_SHORT).show();
                            exact_list_update.get(pos).setFrndReqsend(true);
                        }

                    } else {
                        exact_list_update.get(pos).setFrndReqsend(false);
                        Toast.makeText(getContext(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
                    }
                    friendLeaderBoardAdapterr.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<FriendInviteModel> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(getActivity(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Util.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private class CallFriends extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alContacts = new ArrayList<>();
            valid_mob_no_list = new ArrayList<>();
            pGif.setVisibility(View.VISIBLE);
            pGif.setImageResource(R.drawable.run);
//            Util.showProgressDialog(getContext());
        }

        @Override
        protected String doInBackground(String... strings) {
            getContactList();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            dismissProgressDialog();

            if (alContacts.size() > 0) {
                for (String number : alContacts) {
                    if (isValidMobile(number)) {
                        valid_mob_no_list.add(number);
                    }
                }
            }


            sendContactsToServer();

        }
    }


}
