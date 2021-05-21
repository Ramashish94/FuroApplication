package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

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
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;

import java.util.List;

public class GlassWaterAdapter extends RecyclerView.Adapter<GlassWaterAdapter.MyViewHolder> {

    Context context;
    List<AgeModelTest> ageModelTestList;
    public GlassCentringInterface glassCentringInterface;

    public GlassWaterAdapter(Context applicationContext, List<AgeModelTest> ageModelTestList) {
        this.context = applicationContext;
        this.ageModelTestList = ageModelTestList;
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
        AgeModelTest ageModelTest = ageModelTestList.get(position);
        holder.tvWaterMlSize.setText("" + ageModelTest.getAge());
       /* Picasso.with(context)
                .load(String.valueOf(ageModelTest.getImage()))
                //.resize(50, 50) // here you resize your image to whatever width and height you like
                .into(holder.ivGlass);*/

        holder.ivPlusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (glassCentringInterface != null) {
                    glassCentringInterface.plusIconClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ageModelTestList != null && ageModelTestList.size() > 0) {
            return ageModelTestList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivGlass, ivPlusIcon, ivCheckIcon;
        public TextView tvWaterMlSize;
        public LinearLayout llGlassMeasuring;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGlass = itemView.findViewById(R.id.ivGlass);
            ivPlusIcon = itemView.findViewById(R.id.ivPlusIcon);
            ivCheckIcon = itemView.findViewById(R.id.ivCheckIcon);
            tvWaterMlSize = itemView.findViewById(R.id.tvWaterMlSize);
            llGlassMeasuring = itemView.findViewById(R.id.llGlassMeasuring);
        }
    }


    public interface GlassCentringInterface {
        void plusIconClick(int adapterPosition);
    }

    public void setContentFeedList(GlassCentringInterface glassCentringInterface) {
        this.glassCentringInterface = glassCentringInterface;
    }
}
