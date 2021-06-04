package com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreate.WaterGlassSizeModel;

import java.util.List;

public class WaterInMlAdapter extends RecyclerView.Adapter<WaterInMlAdapter.MyViewHolder> {
    Context context;
    List<WaterGlassSizeModel> waterGlassSizeModelList;
    public WaterSizeInMLClickCallBack waterGlassSizeClickCallBack;
    private int row_index = -1;
    public Boolean clicked = false;

    public WaterInMlAdapter(Context applicationContext, List<WaterGlassSizeModel> waterGlassSizeModelList, WaterSizeInMLClickCallBack waterGlassSizeClickCallBack) {
        this.context = applicationContext;
        this.waterGlassSizeModelList = waterGlassSizeModelList;
        this.waterGlassSizeClickCallBack = waterGlassSizeClickCallBack;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_create_plan_glass_size, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WaterGlassSizeModel waterGlassSizeModel = waterGlassSizeModelList.get(position);
        holder.tvWaterGlassSize.setText("" + waterGlassSizeModel.getGlass_size_in_ml());

        holder.tvWaterGlassSize.setOnClickListener(v -> {
            waterGlassSizeClickCallBack.getWaterInMlSelect(position, Integer.parseInt(waterGlassSizeModel.getGlass_size_in_ml()));
            row_index = position;
            notifyDataSetChanged();
        });

        if (row_index == position) {
//            clicked=true;
            holder.tvWaterGlassSize.setTextColor(Color.parseColor("#FFFFFF"));/*19CFE6*/
        } else {
//           clicked=false;
            holder.tvWaterGlassSize.setTextColor(Color.parseColor("#8C8888"));
        }

    }

    @Override
    public int getItemCount() {
        if (waterGlassSizeModelList != null && waterGlassSizeModelList.size() > 0) {
            return waterGlassSizeModelList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWaterGlassSize, tvWakeTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWaterGlassSize = itemView.findViewById(R.id.tvWaterGlassSize);
        }
    }

    public interface WaterSizeInMLClickCallBack {
        void getWaterInMlSelect(int pos, int waterInMl);
    }
}
