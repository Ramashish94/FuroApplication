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
import com.app.furoapp.model.Bannersecond.Banner;
import com.app.furoapp.model.challengeforyouRecieve.ReceiveChallenge;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BannerImageeAdapter extends RecyclerView.Adapter<BannerImageeAdapter.MyViewHolder> {


    private List<Banner> banners;
    private Context context;


    @NonNull
    @Override
    public BannerImageeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bannerrr_xml, parent, false);

        return new BannerImageeAdapter.MyViewHolder(itemView);
    }

    public BannerImageeAdapter(List<com.app.furoapp.model.Bannersecond.Banner> banners, Context context) {
        this.banners = banners;
        this.context = context;


    }



    @Override
    public void onBindViewHolder(@NonNull BannerImageeAdapter.MyViewHolder holder, int position) {

        Banner receiveChallenge = banners.get(position);
        Picasso.with(context).load(receiveChallenge.getImage()).into(holder.bannerImage);

    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView bannerImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerImage= itemView.findViewById(R.id.bannerImage);

        }
    }


}
