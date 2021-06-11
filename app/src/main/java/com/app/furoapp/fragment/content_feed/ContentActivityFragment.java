package com.app.furoapp.fragment.content_feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.app.furoapp.adapter.ContentFeedActivityAdapter;
import com.app.furoapp.databinding.FragmentContentActivityBinding;
import com.app.furoapp.model.content_feed.Activity;
import com.app.furoapp.model.content_feed.ContentFeedModel;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentActivityFragment extends Fragment {
    FragmentContentActivityBinding binding;
    ContentFeedActivityAdapter contentFeedActivityAdapter;
    HomeMainActivity homeMainActivity;
    ProgressBar progressBarr;
    TextView textView;
    GifView pGif;
    RecyclerView recyclerView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_activity, container, false);
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.activityrecycler);
        progressBarr = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.pbText);
        pGif = view.findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.run);


        return view;
    }

    public ContentActivityFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnClickListeners();

    }

    public static ContentActivityFragment newInstance(String name) {
        ContentActivityFragment fragment = new ContentActivityFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOnClickListeners() {
        pGif.setVisibility(View.VISIBLE);
        /*textView.setVisibility(View.VISIBLE);*/
        RestClient.myContentfeedActivity(new Callback<ContentFeedModel>() {
            @Override
            public void onResponse(Call<ContentFeedModel> call, Response<ContentFeedModel> response) {
                pGif.setVisibility(View.GONE);
                /* textView.setVisibility(View.GONE);*/
                if (response != null) {

                    if (response.body().getData() != null && response.body().getData().getActivities().size() > 0) {
                        List<Activity> activityList = response.body().getData().getActivities();

                        contentFeedActivityAdapter = new ContentFeedActivityAdapter(activityList, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(contentFeedActivityAdapter);

                        contentFeedActivityAdapter.setContentFeedActivityList(new ContentFeedActivityAdapter.ContentFeedActivityInterface() {
                            @Override
                            public void contentFeedActivityItem(int id) {
                                //   homeMainActivity.replaceFragmentWithStack(R.id.container_main, ContentFeedDetailFragment.newInstance(null), ContentFeedDetailFragment.class.getSimpleName());
                                Intent intent = new Intent(getContext(), ContentFeedDetailActivity.class);
                                startActivity(intent);
                                FuroPrefs.putString(getActivity(), "id", String.valueOf(id));
                            }
                        });


                    } else {
                        Toast.makeText(homeMainActivity, "Failure", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ContentFeedModel> call, Throwable t) {
                Toast.makeText(homeMainActivity, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
    }

}




