package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.ClubChallengeModel;
import com.app.furoapp.model.clubChallenge.Club;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClubChallengesAdapter extends RecyclerView.Adapter<ClubChallengesAdapter.MyViewHolder> {
    private List<Club>clubChallengeModels;
    private Context context;
    private  ClubChallengeInterface clubChallengeInterface;
    @NonNull
    @Override
    public ClubChallengesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cluc_challnge_recycler_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClubChallengesAdapter.MyViewHolder holder, int position) {

        Club clubChallengeModel = clubChallengeModels.get(position);
        holder.heading.setText(clubChallengeModel.getName());
        holder.title.setText(clubChallengeModel.getDescription());
        Picasso.with(context).load(clubChallengeModel.getInactiveImage()).into(holder.imageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clubChallengeInterface != null){
                    clubChallengeInterface.clubChallengeItem(clubChallengeModel.getId());
                }
            }
        });

    }
    public ClubChallengesAdapter(List<Club>clubChallengeModels,Context context){

        this.clubChallengeModels = clubChallengeModels;
        this.context= context;

    }
    public  void  setClubChallengeInterface(ClubChallengeInterface clubChallengeInterface){
        this.clubChallengeInterface = clubChallengeInterface;

    }



    @Override
    public int getItemCount() {
        return clubChallengeModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView heading,title;
        LinearLayout linearLayout;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearlayout);
            heading = itemView.findViewById(R.id.kmchallenge);
            title = itemView.findViewById(R.id.runningclub);
            imageView= itemView.findViewById(R.id.imageviewClub);

        }
    }
    public  interface ClubChallengeInterface{
        public void clubChallengeItem(int id);

    }
}
