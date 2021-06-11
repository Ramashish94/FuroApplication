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
import com.app.furoapp.adapter.ContentFeedFoodAdapter;
import com.app.furoapp.databinding.FragmentContentFoodBinding;
import com.app.furoapp.model.content_feed.ContentFeedModel;
import com.app.furoapp.model.content_feed.Food;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentFoodFragment extends Fragment {
    FragmentContentFoodBinding binding;
    ContentFeedFoodAdapter contentFeedFoodAdapter;
    HomeMainActivity homeMainActivity;
    ProgressBar progressBarr;
    TextView textView;
    RecyclerView recyclerView;
    GifView pGif;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_food, container, false);
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.foodrecycler);
        progressBarr = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.pbText);
        pGif = view.findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.sqats);
        /*progressBarr.setVisibility(View.VISIBLE);*/
        /* textView.setVisibility(View.VISIBLE);*/
        pGif.setVisibility(View.VISIBLE);

        return view;
    }


    public ContentFoodFragment() {
        setOnClickListeners();

    }

    public static ContentFoodFragment newInstance(String name) {
        ContentFoodFragment fragment = new ContentFoodFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setOnClickListeners() {


        RestClient.myContentfeedActivity(new Callback<ContentFeedModel>() {
            @Override

            public void onResponse(Call<ContentFeedModel> call, Response<ContentFeedModel> response) {
                /* progressBarr.setVisibility(View.GONE);*/

                pGif.setVisibility(View.GONE);
                if (response != null) {

                    if (response.body().getData() != null && response.body().getData().getFood().size() > 0) {
                        List<Food> foodList = response.body().getData().getFood();
                        if (foodList != null) {
                            contentFeedFoodAdapter = new ContentFeedFoodAdapter(foodList, getContext());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(contentFeedFoodAdapter);
                        }


                        contentFeedFoodAdapter.setContentFoodList(new ContentFeedFoodAdapter.ContentFeedFoodInterface() {
                            @Override
                            public void contentFeedFoodItem(int id) {
                                //  homeMainActivity.replaceFragmentWithStack(R.id.container_main, ContentFeedDetailFragment.newInstance(null), ContentFeedDetailFragment.class.getSimpleName());
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
                Toast.makeText(homeMainActivity, "Something went wrong !", Toast.LENGTH_SHORT).show();

            }
        });


    }
    /*private List<ContentListModel> getContentListRecord(){

        List<ContentListModel> contentListRecords = new ArrayList<ContentListModel>();
        String  categoryName[] ={ "Food", "Food", "Food"};
        Integer categoryImage[] = { R.drawable.food_ic, R.drawable.food_ic, R.drawable.food_ic};
        Integer contentImage[] = { R.drawable.food_first, R.drawable.food_second, R.drawable.food_third};

        for (int index= 0; index<1; index++)
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
}





