package com.app.furoapp.fragment.content_feed;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.adapter.ContentFeedFoodDetailAdapter;
import com.app.furoapp.databinding.FragmentContentFoodDetailsBinding;
import com.app.furoapp.model.contentFeedDetail.Body;
import com.app.furoapp.model.contentFeedDetail.ContentFeedDetailRequest;
import com.app.furoapp.model.contentFeedDetail.ContentFeedDetailResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentFeedDetailFood extends Fragment {
    HomeMainActivity homeMainActivity;
    FragmentContentFoodDetailsBinding binding;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    GifView pGif;
    ContentFeedFoodDetailAdapter contentFeedFoodDetailAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_food_details, container, false);
        View view = binding.getRoot();
        pGif = view.findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.pushup);
        recyclerView = view.findViewById(R.id.contentFeedFoodDetailRecycler);

        contentFeedApi();


        return view;
    }

    public ContentFeedDetailFood() {

    }

    public static ContentFeedDetailFood newInstance(String name) {
        ContentFeedDetailFood fragment = new ContentFeedDetailFood();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    public void contentFeedApi() {

        String activityId = FuroPrefs.getString(getContext(), "foodid");
        ContentFeedDetailRequest contentFeedDetailRequest = new ContentFeedDetailRequest();
        contentFeedDetailRequest.setActivityId(activityId);


        /*pGif.setVisibility(View.VISIBLE);*/
        RestClient.activityDetail(contentFeedDetailRequest, new Callback<ContentFeedDetailResponse>() {
            @Override
            public void onResponse(Call<ContentFeedDetailResponse> call, Response<ContentFeedDetailResponse> response) {
              /*  pGif.setVisibility(View.GONE);
*/
                if (response != null) {
                    if (response.body() != null && response.body().getActivityDetails().size() > 0) {

                        List<Body> bodyList = response.body().getActivityDetails().get(0).getBody();

                        if (bodyList != null) {
                            contentFeedFoodDetailAdapter = new ContentFeedFoodDetailAdapter(bodyList, getContext());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(contentFeedFoodDetailAdapter);
                        }


                    }else {
                        Toast.makeText(homeMainActivity, "Failure", Toast.LENGTH_SHORT).show();

                    }


                }


            }

            @Override
            public void onFailure(Call<ContentFeedDetailResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, "No internet connection", Toast.LENGTH_SHORT).show();

            }
        });


    }


}


