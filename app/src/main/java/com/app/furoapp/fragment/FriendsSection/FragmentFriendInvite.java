package com.app.furoapp.fragment.FriendsSection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.UserProfileActivity;
import com.app.furoapp.adapter.FriendAdapter;
import com.app.furoapp.model.FriendModel.AddFriend;
import com.app.furoapp.model.FriendModel.FriendInviteModel;
import com.app.furoapp.model.FriendModel.FriendRequest;
import com.app.furoapp.model.FriendModel.Friends;
import com.app.furoapp.model.FriendModel.Particpant;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.facebook.FacebookSdk.getApplicationContext;

public class

FragmentFriendInvite extends Fragment implements FriendAdapter.DataAdapterListener {
    boolean isLoading = false;
    private RecyclerView recyclerView;
    private EditText ed_search;
    GifView pGif;
    FriendAdapter adapter;
    private List<Particpant> friendslist ;
    private List<Particpant> rowsArrayList ;
    private List<Particpant> participant_list = new ArrayList<>();
    String user_id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View leadView = inflater.inflate(R.layout.fragment_invite_friends, container, false);
        recyclerView = leadView.findViewById(R.id.recycler_view_friendd);
        ed_search = leadView.findViewById(R.id.ed_search);

        user_id = FuroPrefs.getString(getContext(), "loginUserId");
        friendslist = new ArrayList<>();
        rowsArrayList = new ArrayList<>();
        pGif = leadView.findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.run);

        getFriendsData();
        initScrollListener();

        return leadView;


    }

    private void populateData() {

        if (friendslist.size() > 0) {
            int i = 0;
            while (i < 10) {
                rowsArrayList.add(friendslist.get(i));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                adapter = new FriendAdapter(rowsArrayList, getActivity(), this);
                recyclerView.setAdapter(adapter);

                i++;
            }

        }

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

            }
        });

    }

    private void getFriendsData() {
        if (Util.isInternetConnected(getActivity())) {
            pGif.setVisibility(View.VISIBLE);
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setUserId(user_id);
            RestClient.userFriendsData(friendRequest, new Callback<Friends>() {
                @Override
                public void onResponse(Call<Friends> call, Response<Friends> response) {
                    pGif.setVisibility(View.GONE);
                    friendslist = response.body().getParticpants();
                    participant_list = friendslist;
                    populateData();

                    adapter.setSellingListInterface(new FriendAdapter.SellingListInterface() {
                        @Override
                        public void sellinglistitem(int position) {
                            Intent i = new Intent(getApplicationContext(), UserProfileActivity.class);
                            i.addFlags(FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("image", friendslist.get(position).image);
                            i.putExtra("name", friendslist.get(position).getName());
                            startActivity(i);
                        }
                    });

                }

                @Override
                public void onFailure(Call<Friends> call, Throwable t) {
                    Util.dismissProgressDialog();
                    Toast.makeText(getActivity(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Util.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void AddFriendsData(String friend_id) {
        if (Util.isInternetConnected(getActivity())) {
            Util.showProgressDialog(getActivity());
            RestClient.AddFriends(new AddFriend(FuroPrefs.getString(getActivity(), "loginUserId"), friend_id), new Callback<FriendInviteModel>() {
                @Override
                public void onResponse(Call<FriendInviteModel> call, Response<FriendInviteModel> response) {
                    Util.dismissProgressDialog();
                    FriendInviteModel friends = response.body();
                    if (friends != null) {
                        if (friends.getStatus().equals("200")) {
                            Toast.makeText(getActivity(), friends.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(getContext(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
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


    private void filter(String text) {
        //new array list that will hold the filtered data
        List<Particpant> filterdList = new ArrayList<>();

        //looping through existing elements
        for (Particpant p : participant_list) {
            //if the existing elements contains the search input
            if (p.getName().contains(text)) {
                //adding the element to filtered list
                filterdList.add(p);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdList);
    }


    private void loadMore() {
        rowsArrayList.add(null);
        adapter.notifyItemInserted(rowsArrayList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rowsArrayList.remove(rowsArrayList.size() - 1);
                int scrollPosition = rowsArrayList.size();
                adapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 < nextLimit && currentSize < friendslist.size()) {
                    rowsArrayList.add(friendslist.get(currentSize));
                    currentSize++;
                }


                adapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);


    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList.size() - 1) {
                        //bottom of list!

                        loadMore();
                        isLoading = true;

                    }
                }
            }
        });


    }


    @Override
    public void onDataSelected(int id) {
        AddFriendsData(String.valueOf(id));
    }


}
