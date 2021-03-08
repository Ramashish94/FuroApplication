package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.challengeByYouDetail.Receiver;

import java.util.List;

public class ChallengeByYouDetailAdapterBy extends RecyclerView.Adapter<ChallengeByYouDetailAdapterBy.MyViewHolder> {

    private List<Receiver> receivers;
    private Context context;
    ChallengeByYouDetailInterface challengeByYouDetailInterface;
    @NonNull
    @Override
    public ChallengeByYouDetailAdapterBy.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.challengeitemdetailpending, parent, false);

        return new ChallengeByYouDetailAdapterBy.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeByYouDetailAdapterBy.MyViewHolder holder, int position) {
        Receiver receiver = receivers.get(position);
        holder.textstatus.setText(receiver.getStatus());
        holder.textname.setText(receiver.getName());

        if(receiver.getStatus().equalsIgnoreCase("Winner") || (receiver.getStatus().equalsIgnoreCase("Loser"))){
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (challengeByYouDetailInterface != null) {

                        challengeByYouDetailInterface.contentByYouDetailItem(receiver.getReceiverChallenge().getId());
                    }

                }
            });

        }else{

        }




    }
    public ChallengeByYouDetailAdapterBy(List<Receiver> receivers, Context context) {
        this.receivers = receivers;
        this.context = context;


    }

    @Override
    public int getItemCount() {
        return receivers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textname,textstatus;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearlayoutdetail);
            textname = itemView.findViewById(R.id.name);
            textstatus = itemView.findViewById(R.id.status);
        }
    }

    public interface ChallengeByYouDetailInterface {
        public void contentByYouDetailItem(int id);

    }

    public void setContentFeedActivityList(ChallengeByYouDetailInterface challengeByYouDetailInterface) {
        this.challengeByYouDetailInterface = challengeByYouDetailInterface;

    }
}

