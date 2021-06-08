package com.app.furoapp.activity.newFeature.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.notification.challangeNotification.ChallengeNotification;

import java.util.List;

public class ChallangeNotificationAdapter extends RecyclerView.Adapter<ChallangeNotificationAdapter.MyViewHolder> {
    Context context;
    List<ChallengeNotification> challengeNotificationList;

    public ChallangeNotificationAdapter(Context applicationContext, List<ChallengeNotification> challengeNotificationList) {
        this.context = applicationContext;
        this.challengeNotificationList = challengeNotificationList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChallengeNotification challengeNotification = challengeNotificationList.get(position);
        holder.tvNtifictionTxt.setText("" + challengeNotification.getTitle());
        holder.tvTimeReadingTxt.setText("" + challengeNotification.getBody());
    }

    @Override
    public int getItemCount() {
        if (challengeNotificationList != null && challengeNotificationList.size() > 0) {
            return challengeNotificationList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNtifictionTxt, tvTimeReadingTxt;
        public ImageView ivNotificatonIcnImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNtifictionTxt = itemView.findViewById(R.id.tvNtifictionTxt);
            tvTimeReadingTxt = itemView.findViewById(R.id.tvTimeReadingTxt);
            ivNotificatonIcnImg = itemView.findViewById(R.id.ivNotificatonIcnImg);

        }
    }
}
