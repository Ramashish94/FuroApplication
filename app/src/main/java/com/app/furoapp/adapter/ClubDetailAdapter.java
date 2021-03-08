package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.clubDetails.Club;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClubDetailAdapter extends RecyclerView.Adapter<ClubDetailAdapter.MyViewHolder> {


    private List<com.app.furoapp.model.clubDetails.Club> clubList;
    private Context context;

    @NonNull
    @Override
    public ClubDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clubdetailitem, parent, false);

        return new ClubDetailAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ClubDetailAdapter.MyViewHolder holder, int position) {
       com.app.furoapp.model.clubDetails.Club club = clubList.get(position);

        holder.clubTitle.setText(club.getName());
        holder.textViewrule1.setText(club.getRule1());
        holder.textViewrule2.setText(club.getRule2());
        Picasso.with(context).load(club.getActiveImage())
                .into(holder.imageViewClub);
        holder.textViewChallengeName.setText(club.getChallengeType());
        holder.clubParticipant.setText(club.getMembers());


    }
    @Override
    public int getItemCount() {
        if ( clubList!= null && clubList.size() > 0) {
            return clubList.size();
        } else {
            return 0;
        }
    }

    public ClubDetailAdapter(List<Club> clubList, Context context) {
        this.clubList = clubList;
        this.context = context;


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewClub ;
        private TextView clubTitle, textViewrule1,textViewrule2,textViewChallengeName,clubParticipant;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewClub = itemView.findViewById(R.id.headerImage);
            clubTitle = itemView.findViewById(R.id.titleClub);
            textViewrule1 = itemView.findViewById(R.id.rules1);
            textViewrule2= itemView.findViewById(R.id.Rules2);
            textViewChallengeName = itemView.findViewById(R.id.clubchallengeName);
            clubParticipant = itemView.findViewById(R.id.participants);


        }
    }


}



