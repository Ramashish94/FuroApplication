package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.content_feed.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentFeedFoodAdapter extends RecyclerView.Adapter<ContentFeedFoodAdapter.MyViewHolder> {


    private List<Food> foodList;
    private Context context;
    private  ContentFeedFoodInterface contentFeedFoodInterface;
    @NonNull
    @Override
    public ContentFeedFoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_feed_food, parent, false);

        return new ContentFeedFoodAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ContentFeedFoodAdapter.MyViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.foodTitle.setText(food.getType());
        holder.foodText.setText(food.getDescription());
        Picasso.with(context).load(food.getImageUrl())
                .into(holder.foodImg);

        holder.foodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contentFeedFoodInterface != null) {

                    contentFeedFoodInterface.contentFeedFoodItem(food.getId());
                }

            }
        });
    }
    @Override
    public int getItemCount() {
        if (foodList != null && foodList.size() > 0) {
            return foodList.size();
        } else {
            return 0;
        }
    }

    public  ContentFeedFoodAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodImg ;
        private TextView foodTitle, foodText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImg = itemView.findViewById(R.id.activityImageFood);
            foodTitle = itemView.findViewById(R.id.ac_title_food);
            foodText = itemView.findViewById(R.id.acTextfood);
        }
    }

    public interface ContentFeedFoodInterface {
       void contentFeedFoodItem(int id);

    }

    public void setContentFoodList(ContentFeedFoodInterface contentFeedFoodInterface) {
        this.contentFeedFoodInterface = contentFeedFoodInterface;

    }
}



