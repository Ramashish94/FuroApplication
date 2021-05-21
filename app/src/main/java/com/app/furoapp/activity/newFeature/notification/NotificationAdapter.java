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

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    Context context;
    List<NotificationModelTests> notificationModelTestsList;

    public NotificationAdapter(Context applicationContext, List<NotificationModelTests> notificationModelTestsList) {
        this.context = applicationContext;
        this.notificationModelTestsList = notificationModelTestsList;
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
        NotificationModelTests notificationModelTests = notificationModelTestsList.get(position);
        holder.tvNtifictionTxt.setText("" + notificationModelTests.getNotification());
        holder.tvTimeReadingTxt.setText("" + notificationModelTests.getNotificationReadTime());

    }

    @Override
    public int getItemCount() {
        if (notificationModelTestsList != null && notificationModelTestsList.size() > 0) {
            return notificationModelTestsList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNtifictionTxt,tvTimeReadingTxt;
        public ImageView ivNotificatonIcnImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNtifictionTxt = itemView.findViewById(R.id.tvNtifictionTxt);
            tvTimeReadingTxt = itemView.findViewById(R.id.tvTimeReadingTxt);
            ivNotificatonIcnImg = itemView.findViewById(R.id.ivNotificatonIcnImg);

        }
    }
}
