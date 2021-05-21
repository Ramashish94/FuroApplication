package com.app.furoapp.activity.newFeature.likeAndSaved.SavedList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SavedFragment extends Fragment {
    public RecyclerView rvSavedRecycler;
    SavedAdapter savedAdapter;
    List<SavedTestModel> savedTestModelList = new ArrayList<>();
    public String getAccessToken;
    public List<Object> savedListResponses = new ArrayList<>();


    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance() {
        SavedFragment fragment = new SavedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        rvSavedRecycler = view.findViewById(R.id.rvSavedRecycler);
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        setSavedAdapter();
        // setSavedListAdapter();
        getApiCalling();
        return view;
    }


    private void getApiCalling() {
        Util.isInternetConnected(getContext());
        Util.showProgressDialog(getActivity());
        RestClient.getAllSavedList(getAccessToken, new Callback<SavedListResponse>() {
            @Override
            public void onResponse(Call<SavedListResponse> call, Response<SavedListResponse> response) {
                if (response != null && response.code() == 200 && response.body() != null) {
                    if (response.body().getSaved() != null) {
                        //saved post list
                        //notifySavedListAdapter(response.body().getSaved());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failure" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SavedListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setSavedAdapter() {
        savedAdapter = new SavedAdapter(getApplicationContext(), savedTestModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSavedRecycler.setLayoutManager(layoutManager);
        rvSavedRecycler.setItemAnimator(new DefaultItemAnimator());
        rvSavedRecycler.setAdapter(savedAdapter);
        List<SavedTestModel> savedTestModels = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            SavedTestModel savedTestModel = new SavedTestModel();
            savedTestModel.setTittle("ACTIVITY" + i);
            savedTestModel.setDefineText("BUSTIG MYTHUS ABOUT WORKOUT INJURIES");
            savedTestModels.add(savedTestModel);
        }
        SavedAdapter savedAdapter = new SavedAdapter(getApplicationContext(), savedTestModels);
        rvSavedRecycler.setAdapter(savedAdapter);
    }

    private void notifySavedListAdapter(List<Object> saved) {
        savedTestModelList.clear();
        // savedTestModelList.addAll(saved);
        if (savedListResponses != null && savedListResponses.size() > 0) {
            savedAdapter.notifyDataSetChanged();
        }
    }
}