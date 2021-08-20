package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.adapter.CommunityMembersAdapter;
import com.app.furoapp.model.communitymembers.CommunityMembersRequest;
import com.app.furoapp.model.communitymembers.CommunityMembersResponse;
import com.app.furoapp.model.communitymembers.Member;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityMembers extends AppCompatActivity {
    CommunityMembersAdapter communityMembersAdapter;
    RecyclerView recyclerView;
    String usercommunityid, userid;
    TextView communitytitle;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_members);
        recyclerView = findViewById(R.id.communitymembers);
        progressBar = findViewById(R.id.progressBar);
        communitytitle = findViewById(R.id.tvToolbarTitle);
        String community = FuroPrefs.getString(getApplicationContext(), "communityName");
        usercommunityid = FuroPrefs.getString(getApplicationContext(), "community_id");
        userid = FuroPrefs.getString(getApplicationContext(), "loginUserId");
        communitytitle.setText(community);
        members();
    }

    public void members() {
        CommunityMembersRequest communityMembersRequest = new CommunityMembersRequest();
        communityMembersRequest.setCommunityId(usercommunityid);
        communityMembersRequest.setUserId(userid);

        progressBar.setVisibility(View.VISIBLE);
        RestClient.userCommunityMembers(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), communityMembersRequest, new Callback<CommunityMembersResponse>() {
            @Override
            public void onResponse(Call<CommunityMembersResponse> call, Response<CommunityMembersResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    if (response.body() != null && response.body().getMembers().size() > 0) {

                        List<Member> memberList = response.body().getMembers();
                        communityMembersAdapter = new CommunityMembersAdapter(memberList, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(communityMembersAdapter);

                    } else {
                        Toast.makeText(CommunityMembers.this, "Failure!!", Toast.LENGTH_SHORT).show();

                    }


                }
            }

            @Override
            public void onFailure(Call<CommunityMembersResponse> call, Throwable t) {
                Toast.makeText(CommunityMembers.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
