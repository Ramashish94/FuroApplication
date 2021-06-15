package com.app.furoapp.activity.newFeature.notification.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.Datum__1;
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.Datum__2;

import java.util.List;

public class CronNotificationAdapter extends RecyclerView.Adapter<CronNotificationAdapter.MyViewHolder> {
    Context context;
    List<Datum__2> cronNotificationList;

    public CronNotificationAdapter(Context applicationContext, List<Datum__2> cronNotificationList) {
        this.context = applicationContext;
        this.cronNotificationList = cronNotificationList;
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
        Datum__2 challengeNotification = cronNotificationList.get(position);
        holder.tvNtifictionTxt.setText("" + challengeNotification.getTitle());
        holder.tvTimeReadingTxt.setText("" + challengeNotification.getBody());
    }

    @Override
    public int getItemCount() {
        if (cronNotificationList != null && cronNotificationList.size() > 0) {
            return cronNotificationList.size();
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
