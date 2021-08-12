package com.app.furoapp.fragment.FriendsSection;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.adapter.FriendAdapter;
import com.app.furoapp.adapter.FriendPendingAdapter;
import com.app.furoapp.model.FriendModel.AddFriend;
import com.app.furoapp.model.FriendModel.FriendInviteModel;
import com.app.furoapp.model.FriendModel.FriendPendingModel;
import com.app.furoapp.model.FriendModel.Friends;
import com.app.furoapp.model.FriendModel.Particpant;
import com.app.furoapp.model.FriendModel.PendingFriendList;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFriendPending extends Fragment implements FriendPendingAdapter.DataAdapterListener {

    private RecyclerView recyclerView;
    private EditText ed_search;
    GifView pGif;
    private List<PendingFriendList> participant_list;
    FriendPendingAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View leadView = inflater.inflate(R.layout.fragment_pending_friends, container, false);
        recyclerView = leadView.findViewById(R.id.recycler_view);
        ed_search = leadView.findViewById(R.id.ed_search);
        pGif = leadView.findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.run);
        AddFriendsData();
        return leadView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ed_search.addTextChangedListener(new TextWatcher() {

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
                if (participant_list.size() > 0) {
                    filter(s.toString());
                }
//                String search = ed_search.getText().toString();
//                if (search.length()>3) {
//                    if (participant_list.size() > 0) {
//                        for (Particpant p : participant_list) {
//                            if (p.getName().contains(search)) {
//                                search_list.add(p);
//                                setAdapter(search_list);
//                            }
//                        }
//                    }
//                }

            }
        });

    }


    private void AddFriendsData() {
        if (Util.isInternetConnected(getActivity())) {
            pGif.setVisibility(View.VISIBLE);
            RestClient.PendingFriends(new AddFriend(FuroPrefs.getString(getActivity(), "loginUserId")), new Callback<FriendPendingModel>() {
                @Override
                public void onResponse(Call<FriendPendingModel> call, Response<FriendPendingModel> response) {
                    pGif.setVisibility(View.GONE);
                    FriendPendingModel friends = response.body();
                    setAdapter(friends.getPendingFriendList());
                    participant_list = friends.getPendingFriendList();
                }

                @Override
                public void onFailure(Call<FriendPendingModel> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(getActivity(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Util.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void AcceptFriendsData(String friend_id) {
        if (Util.isInternetConnected(getActivity())) {
            Util.showProgressDialog(getActivity());
            RestClient.AcceptFriend(new AddFriend(FuroPrefs.getString(getActivity(), "loginUserId"), friend_id), new Callback<FriendInviteModel>() {
                @Override
                public void onResponse(Call<FriendInviteModel> call, Response<FriendInviteModel> response) {
                    Util.dismissProgressDialog();
                    FriendInviteModel friends = response.body();
                    if (friends.getStatus().equals("200")) {
                        Toast.makeText(getActivity(), friends.getMessage(), Toast.LENGTH_SHORT).show();

                    }
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

    private void RejectFriendsData(String friend_id) {
        if (Util.isInternetConnected(getActivity())) {
            Util.showProgressDialog(getActivity());
            RestClient.RejectFriends(new AddFriend(FuroPrefs.getString(getActivity(), "loginUserId"), friend_id), new Callback<FriendInviteModel>() {
                @Override
                public void onResponse(Call<FriendInviteModel> call, Response<FriendInviteModel> response) {
                    Util.dismissProgressDialog();
                    FriendInviteModel friends = response.body();
                    if (friends.getStatus().equals("200")) {
                        Toast.makeText(getActivity(), friends.getMessage(), Toast.LENGTH_SHORT).show();

                    }
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


    private void setAdapter(List<PendingFriendList> list) {
        if (list != null && list.size() > 0) {
            adapter = null;
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter = new FriendPendingAdapter(list, getActivity(), this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "No records founds", Toast.LENGTH_SHORT).show();
        }
    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        List<PendingFriendList> filterdList = new ArrayList<>();

        //looping through existing elements
        for (PendingFriendList p : participant_list) {
            //if the existing elements contains the search input
            if (p.getName().toLowerCase().contains(text.toLowerCase()) || p.getUsername().toLowerCase().contains(text.toLowerCase()) || p.getEmail().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdList.add(p);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdList);
    }


    @Override
    public void onDataSelected(String type, int id) {

        if (type.equals("accept")) {
            AcceptFriendsData(String.valueOf(id));
            AddFriendsData();
        }

        if (type.equals("reject")) {
            RejectFriendsData(String.valueOf(id));
            AddFriendsData();
        }
    }
}

