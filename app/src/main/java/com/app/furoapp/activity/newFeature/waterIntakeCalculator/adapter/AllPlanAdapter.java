package com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter;

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
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan.AllPlan;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.List;

public class AllPlanAdapter extends RecyclerView.Adapter<AllPlanAdapter.MyViewHolder> {

    Context context;
    List<AllPlan> allPlanList;
    private int row_index = -1;
    public AllPlanClickCallBack allPlanClickCallBack;
   // AllPlan allPlan;
    private List<AllPlan> data;

    public AllPlanAdapter(Context applicationContext, List<AllPlan> allPlanList, AllPlanClickCallBack allPlanClickCallBack) {
        this.context = applicationContext;
        this.allPlanList = allPlanList;
        this.allPlanClickCallBack = allPlanClickCallBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_plan, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AllPlan allPlan = allPlanList.get(position);
        holder.tvWaterTake.setText(allPlan.getWaterTakeInMl() + " ml");
        holder.tvDurationTime.setText(allPlan.getRecommendedDurationInMins());

//        for (AllPlan allPlan : allPlanList) {
//            if (allPlan.getIsSelected() == 1) {
//                holder.ivCircle.setBackgroundResource(R.drawable.bluecircle);
//            }
//        }

//        if (allPlan.getIsSelected()==1){
//            row_index=position;
//        }

        holder.llAllPlan.setOnClickListener(v -> {
            allPlanClickCallBack.getPlanClickCallBack(allPlan.getId(),
                    String.valueOf(allPlan.getWaterTakeInMl()), allPlan.getRecommendedDurationInMins());
            row_index = position;
//            notifyDataSetChanged();
        });

//        if (row_index == position) {
////            clicked=true;
//            holder.ivCircle.setBackgroundResource(R.drawable.bluecircle);
//        } else {
////           clicked=false;
//            holder.ivCircle.setBackgroundResource(R.drawable.ellipse_20);
//        }

        if (allPlan.getIsSelected() == 1) {
            holder.ivCircle.setBackgroundResource(R.drawable.bluecircle);
        } else {
            holder.ivCircle.setBackgroundResource(R.drawable.ellipse_20);
        }

    }

    @Override
    public int getItemCount() {
        if (allPlanList != null && allPlanList.size() > 0) {
            return allPlanList.size();
        } else {
            return 0;
        }
    }

    public void setNotifyData(AllPlanAdapter allPlanAdapter) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDurationTime, tvWaterTake;
        public LinearLayout llAllPlan;
        public ImageView ivCircle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDurationTime = itemView.findViewById(R.id.tvDurationTime);
            tvWaterTake = itemView.findViewById(R.id.tvWaterTake);
            llAllPlan = itemView.findViewById(R.id.llAllPlan);
            ivCircle = itemView.findViewById(R.id.ivCircle);
        }
    }

    public interface AllPlanClickCallBack {
        void getPlanClickCallBack(Integer id, String waterTakeInMl, String recommendedDurationInMins);
    }

    public void updateItem(int id) {
        for (int change = 0; change < allPlanList.size(); change++) {
         int value;
         if (allPlanList.get(change).getId()==id){
             value= 1;
            }else {
             value=  0;
            }
            allPlanList.get(change).setIsSelected(value);
        }
        notifyDataSetChanged();
    }

}
