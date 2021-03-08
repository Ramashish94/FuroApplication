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
import com.app.furoapp.adapter.ContentFeedHealthAdapter;
import com.app.furoapp.databinding.FragmentContentHealthBinding;
import com.app.furoapp.model.content_feed.ContentFeedModel;
import com.app.furoapp.model.content_feed.Health;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentHealthFragment extends Fragment {
    FragmentContentHealthBinding binding;
    ContentFeedHealthAdapter contentFeedHealthAdapter;
    ProgressBar progressBarr;
    TextView textView;
    RecyclerView recyclerView;
    HomeMainActivity homeMainActivity;
    GifView pGif;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeMainActivity = (HomeMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_health, container, false);
        View view = binding.getRoot();
        setOnClickListeners();
        progressBarr = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.pbText);
        recyclerView = view.findViewById(R.id.healthrecycler);

        pGif = view.findViewById(R.id.progressBarJumpingJacks);
        pGif.setImageResource(R.drawable.jumpingjacks);

        pGif.setVisibility(View.VISIBLE);

        RestClient.myContentfeedActivity(new Callback<ContentFeedModel>() {
            @Override

            public void onResponse(Call<ContentFeedModel> call, Response<ContentFeedModel> response) {
                pGif.setVisibility(View.GONE);
                if (response != null) {

                    if (response.body().getData() != null && response.body().getData().getHealth().size()>0) {
                        List<Health> healthList = response.body().getData().getHealth();

                        contentFeedHealthAdapter = new ContentFeedHealthAdapter(healthList, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(contentFeedHealthAdapter);

                        contentFeedHealthAdapter.setContentFeedHealthList(new ContentFeedHealthAdapter.ContentFeedHealthInterface() {
                            @Override
                            public void contentFeedHealthItem(int id) {

                                Intent intent = new Intent(getContext(), ContentFeedDetailActivity.class);
                                startActivity(intent);
                                FuroPrefs.putString(getActivity(), "id", String.valueOf(id));
                            }
                        });

                    }else{

                        Toast.makeText(homeMainActivity, "Failure", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ContentFeedModel> call, Throwable t) {
                Toast.makeText(homeMainActivity, "No internet connection", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    public ContentHealthFragment() {

    }

    public static ContentHealthFragment newInstance(String name) {
        ContentHealthFragment fragment = new ContentHealthFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOnClickListeners() {

    }
   /* private List<ContentListModel> getContentListRecord(){

        List<ContentListModel> contentListRecords = new ArrayList<ContentListModel>();
        String  categoryName[] ={ "Health", "Health"};
        Integer categoryImage[] = {  R.drawable.heart_ic, R.drawable.heart_ic};
        Integer contentImage[] = {  R.drawable.health_first, R.drawable.health_second};

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







