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

import java.util.List;

public class ChallangeNotificationAdapter extends RecyclerView.Adapter<ChallangeNotificationAdapter.MyViewHolder> {
    Context context;
    List<Datum__1> challengeNotificationList;

    public ChallangeNotificationAdapter(Context applicationContext, List<Datum__1> challengeNotificationList) {
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
        Datum__1 challengeNotification = challengeNotificationList.get(position);
        holder.tvNtifictionTxt.setText("" + challengeNotification.getTitle());
       // holder.tvTimeReadingTxt.setText("" + challengeNotification.getBody());
        String upperString = challengeNotification.getBody().substring(0, 1).toUpperCase() + challengeNotification.getBody().substring(1).toLowerCase();
        holder.tvTimeReadingTxt.setText("" + upperString);
        /*if (challengeNotification.getUsers().get.getImage() != null) {
            Picasso.with(context).load(dailyDataCount.getUser().getImage()).error(R.drawable.ic_userimageiconss).into(holder.ivUserImage);
        }
*/

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
