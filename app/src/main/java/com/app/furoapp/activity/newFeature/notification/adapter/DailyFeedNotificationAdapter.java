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
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.Datum;

import java.util.List;

public class DailyFeedNotificationAdapter extends RecyclerView.Adapter<DailyFeedNotificationAdapter.MyViewHolder> {
    Context context;
    List<Datum> dailyFeedNotificationList;

    public DailyFeedNotificationAdapter(Context applicationContext, List<Datum> dailyFeedNotificationList) {
        this.context = applicationContext;
        this.dailyFeedNotificationList = dailyFeedNotificationList;
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
        Datum dailyFeedNotification = dailyFeedNotificationList.get(position);
        holder.tvNtifictionTxt.setText("" + dailyFeedNotification.getNotificationType());
         //holder.tvTimeReadingTxt.setText("" + dailyFeedNotification.getContentTitle());
        String upperString = dailyFeedNotification.getContentTitle().substring(0, 1).toUpperCase() + dailyFeedNotification.getContentTitle().substring(1).toLowerCase();
        holder.tvTimeReadingTxt.setText("" + upperString);
       /* if (dailyFeedNotification.getActivityDetail().get() != null) {
            Picasso.with(context).load(dailyDataCount.getUser().getImage()).error(R.drawable.ic_userimageiconss).into(holder.ivUserImage);
        }*/
    }

    @Override
    public int getItemCount() {
        if (dailyFeedNotificationList != null && dailyFeedNotificationList.size() > 0) {
            return dailyFeedNotificationList.size();
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
