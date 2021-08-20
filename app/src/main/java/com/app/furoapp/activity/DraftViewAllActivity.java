package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.challengeRecieve.ChallengeForYouDetailActivity;
import com.app.furoapp.adapter.ChallangesForYouAdapter;
import com.app.furoapp.adapter.DraftAdapter;
import com.app.furoapp.adapter.OpenAndChallengeCloseAdapter;
import com.app.furoapp.databinding.RowOpenCloseLoaderBinding;
import com.app.furoapp.model.draft.DraftChallenge;
import com.app.furoapp.model.draft.DraftRequest;
import com.app.furoapp.model.draft.DraftResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DraftViewAllActivity extends AppCompatActivity {
    String useridopenandclose;
    HomeMainActivity homeMainActivity;
    TextView textViewloadmoreby, textViewloadmorefor, draftloadmore;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    DraftAdapter draftAdapter;
    RowOpenCloseLoaderBinding binding;
    RecyclerView recyclerView, recyclerView1, recyclerViewdraft;
    ChallangesForYouAdapter challangesForYouAdapter;
    OpenAndChallengeCloseAdapter openAndChallengeCloseAdapter;
    TextView youHaveNotChallange, Youhavechallange, draft;
    int userIdChallenge;
    GifView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_view_all);
        recyclerViewdraft = findViewById(R.id.challengeDraftrecycler);
        gifView = findViewById(R.id.progressBarJumpingJacks);
        gifView.setImageResource(R.drawable.sqats);
        draftloadmore = findViewById(R.id.loadmorefordraft);
        draft = findViewById(R.id.youhavechallangedraft);
        useridopenandclose = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        getDChallengeDraft();
    }

    public void getDChallengeDraft() {


        DraftRequest draftRequest = new DraftRequest();
        draftRequest.setUserId(useridopenandclose);


        Util.isInternetConnected(getApplicationContext());

        gifView.setVisibility(View.VISIBLE);

        RestClient.userdraft(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), draftRequest, new Callback<DraftResponse>() {
            @Override
            public void onResponse(Call<DraftResponse> call, Response<DraftResponse> response) {
                gifView.setVisibility(View.GONE);
                if (response != null) {

                    if (response.body() != null && response.body().getDraftChallenges().size() > 0) {
                        List<DraftChallenge> draftChallenges = response.body().getDraftChallenges();

                        draftAdapter = new DraftAdapter(draftChallenges, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewdraft.setLayoutManager(layoutManager);
                        recyclerViewdraft.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewdraft.setAdapter(draftAdapter);

                        draftAdapter.setDraftItemList(new DraftAdapter.DraftInterface() {
                            @Override
                            public void draftItem(String IdChallengedraft, int position) {
                                String challTypedraft = response.body().getDraftChallenges().get(position).getChallengeType();
                                if (challTypedraft.equalsIgnoreCase("map")) {
                                    Intent intent = new Intent(getApplicationContext(), MapChallengeActivityDraft.class);
                                    String Challengeiddraft = IdChallengedraft;
                                    intent.putExtra("userIdChallenge", Challengeiddraft);
                                    startActivity(intent);
                                } else if (challTypedraft.equalsIgnoreCase("video")) {
                                    Intent intent = new Intent(getApplicationContext(), ChallengeActivityVideoDraft.class);
                                    String Challengeiddraft = IdChallengedraft;
                                    intent.putExtra("userIdChallenge", Challengeiddraft);
                                    startActivity(intent);


                                }

                            }
                        });


                    } else {
                        draft.setVisibility(View.VISIBLE);
                    }


                }
            }

            @Override
            public void onFailure(Call<DraftResponse> call, Throwable t) {
                Toast.makeText(homeMainActivity, "No Internet connection !!", Toast.LENGTH_SHORT).show();

            }
        });


    }

}
