package com.app.furoapp.activity.newFeature.bmiCalculator;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.likeAndSaved.likedList.LikeListAdapter;

import java.util.List;

public class AgeAdapter extends RecyclerView.Adapter<AgeAdapter.MyViewHolder> {
    Context context;
    List<AgeModelTest> ageModelTestList;
    public AgeClickCallBack ageClickCallBack;
    private int row_index=-1;
    public Boolean clicked = false;



    public AgeAdapter(Context applicationContext, List<AgeModelTest> ageModelTestList, AgeClickCallBack ageClickCallBack) {
        this.context = applicationContext;
        this.ageModelTestList = ageModelTestList;
        this.ageClickCallBack= ageClickCallBack;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_age_recycler, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AgeModelTest ageModelTest = ageModelTestList.get(position);
        holder.tvAge.setText("" + ageModelTest.getAge());
        // holder.tvWakeTime.setText("" + ageModelTest.getAge());

        holder.tvAge.setOnClickListener(v -> {
            ageClickCallBack.ageSelectItem(Integer.parseInt(ageModelTest.getAge()));
            row_index = position;
            notifyDataSetChanged();
        });

        if(row_index==position){
//            clicked=true;
            holder.tvAge.setTextColor(Color.parseColor("#19CFE6"));
        }
        else {
//           clicked=false;
            holder.tvAge.setTextColor(Color.parseColor("#FFFFFF"));
        }

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
        public TextView tvAge, tvWakeTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvWakeTime = itemView.findViewById(R.id.tvWakeTime);
        }
    }

    public interface AgeClickCallBack {
        void ageSelectItem(int age);
    }
}
