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

public class ChallengeByYouDetailAdapter extends RecyclerView.Adapter<ChallengeByYouDetailAdapter.MyViewHolder> {

    private List<Receiver> receivers;
    private Context context;
    ChallengeByYouDetailInterface challengeByYouDetailInterface;
    @NonNull
    @Override
    public ChallengeByYouDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.challengeitemdetailpending, parent, false);

        return new ChallengeByYouDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeByYouDetailAdapter.MyViewHolder holder, int position) {
        Receiver receiver = receivers.get(position);
        holder.textstatus.setText(receiver.getStatus());
        holder.textname.setText(receiver.getName());



            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (receiver.getStatus().equalsIgnoreCase("Won") || receiver.getStatus().equalsIgnoreCase("Lost")) {
                        if (challengeByYouDetailInterface != null) {
                            challengeByYouDetailInterface.contentByYouDetailItem(receiver.getReceiverChallenge().getId(), receiver.getReceiverChallenge().getUserId(), position);
                        }

                    }else{

                    }
                }
            });

        }







    public ChallengeByYouDetailAdapter(List<Receiver> receivers, Context context) {
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
        public void contentByYouDetailItem(int id,String user_id, int position);

    }

    public void setContentFeedActivityList(ChallengeByYouDetailInterface challengeByYouDetailInterface) {
        this.challengeByYouDetailInterface = challengeByYouDetailInterface;

    }
}

