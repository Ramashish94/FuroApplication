package com.app.furoapp.fragment.FriendsSection;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.app.furoapp.adapter.FriendListAdapter;
import com.app.furoapp.adapter.FriendPendingAdapter;
import com.app.furoapp.model.FriendModel.AddFriend;
import com.app.furoapp.model.FriendModel.FriendInviteModel;
import com.app.furoapp.model.FriendModel.FriendListModel;
import com.app.furoapp.model.FriendModel.FriendPendingModel;
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

public class FragmentFriend extends Fragment implements FriendListAdapter.DataAdapterListener {

    private RecyclerView recyclerView;
    private EditText ed_search;
    private List<PendingFriendList> friend_list = new ArrayList<>();
    FriendListAdapter adapter;
    GifView pGif;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View leadView = inflater.inflate(R.layout.fragment_friends, container, false);
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
                if (friend_list.size() > 0) {
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
            Log.i("log_id",FuroPrefs.getString(getActivity(), "loginUserId"));
            RestClient.FriendsList(new AddFriend(FuroPrefs.getString(getActivity(), "loginUserId")),new Callback<FriendListModel>() {
                @Override
                public void onResponse(Call<FriendListModel> call, Response<FriendListModel> response) {
                    pGif.setVisibility(View.GONE);
                    FriendListModel friends = response.body();
                    setAdapter(friends.getFriendList());
                    friend_list = friends.getFriendList();
                }

                @Override
                public void onFailure(Call<FriendListModel> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(getActivity(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Util.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }



    private void RemoveFriendsData(String friend_id) {
        if (Util.isInternetConnected(getActivity())) {
            Util.showProgressDialog(getActivity());
            RestClient.RemoveFriends(new AddFriend(FuroPrefs.getString(getActivity(), "loginUserId"),friend_id),new Callback<FriendInviteModel>() {
                @Override
                public void onResponse(Call<FriendInviteModel> call, Response<FriendInviteModel> response) {
                    Util.dismissProgressDialog();
                    FriendInviteModel friends = response.body();
                    if(friends.getStatus().equals("200"))
                    {
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
        adapter = null;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new FriendListAdapter(list, getActivity(),this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        List<PendingFriendList> filterdList = new ArrayList<>();

        //looping through existing elements
        for (PendingFriendList p : friend_list) {
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
    public void onDataSelected(String type,int id) {

        if(type.equals("remove"))
        {
            RemoveFriendsData(String.valueOf(id));
            AddFriendsData();
        }


    }
}


