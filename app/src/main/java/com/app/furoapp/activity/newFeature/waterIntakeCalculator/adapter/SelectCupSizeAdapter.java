package com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass.UserGlassSize;

import java.util.List;

public class SelectCupSizeAdapter extends RecyclerView.Adapter<SelectCupSizeAdapter.MyViewHolder> {

    Context context;
    List<UserGlassSize> userGlassSizeList;
    public GlassClickCallBack glassClickCallBack;
    private int row_index=-1;

    public SelectCupSizeAdapter(Context applicationContext, List<UserGlassSize> userGlassSizeList1, GlassClickCallBack glassClickCallBack) {
        this.context = applicationContext;
        this.userGlassSizeList = userGlassSizeList1;
        this.glassClickCallBack =glassClickCallBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_glass_water, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserGlassSize userGlassSize = userGlassSizeList.get(position);
        holder.tvWaterMlSize.setText("" + userGlassSize.getGlassSizeInMl()+" ml");
//        Picasso.with(context).load(userGlassSize.getGlassSize()).error(R.drawable.glassicon).into(holder.ivGlass);
//        Picasso.with(context).load(userGlassSize.getGlassSize()).error(R.drawable.plus_icon).into(holder.ivPlusIcon);

        holder.ivPlusIcon.setOnClickListener(v -> {
            glassClickCallBack.glassSelectItem(Integer.parseInt(userGlassSize.getGlassSizeInMl()));
            row_index = position;
            notifyDataSetChanged();
        });

        if(row_index==position){
//            clicked=true;
            holder.ivPlusIcon.setVisibility(View.GONE);
            holder.ivCheckIcon.setVisibility(View.VISIBLE);
            holder.tvWaterMlSize.setTextColor(Color.parseColor("#19CFE6"));
        }
        else {
//           clicked=false;
            holder.ivPlusIcon.setVisibility(View.VISIBLE);
            holder.ivCheckIcon.setVisibility(View.GONE);
            holder.tvWaterMlSize.setTextColor(Color.parseColor("#797979"));
        }
    }

    @Override
    public int getItemCount() {
        if (userGlassSizeList != null && userGlassSizeList.size() > 0) {
            return userGlassSizeList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWaterMlSize;
        public ImageView ivGlass,ivPlusIcon,ivCheckIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWaterMlSize = itemView.findViewById(R.id.tvWaterMlSize);
            ivGlass = itemView.findViewById(R.id.ivGlass);
            ivPlusIcon = itemView.findViewById(R.id.ivPlusIcon);
            ivCheckIcon=itemView.findViewById(R.id.ivCheckIcon);
        }
    }

    public interface GlassClickCallBack {
        void glassSelectItem(int glassSizeInMl);
    }
}
