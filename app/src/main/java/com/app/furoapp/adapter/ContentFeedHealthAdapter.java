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
import com.app.furoapp.model.content_feed.Health;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentFeedHealthAdapter extends RecyclerView.Adapter<ContentFeedHealthAdapter.MyViewHolder> {
    private List<Health> healthList;


    private Context context;
    private ContentFeedHealthInterface contentFeedHealthInterface;

    @NonNull
    @Override
    public ContentFeedHealthAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_feed_health, parent, false);

        return new ContentFeedHealthAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentFeedHealthAdapter.MyViewHolder holder, int position) {
        Health health = healthList.get(position);
        holder.healthTitle.setText(health.getType());
        holder.healthText.setText(health.getDescription());
        Picasso.with(context).load(health.getImageUrl())
                .into(holder.healthImg);

        holder.healthImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contentFeedHealthInterface != null) {

                    contentFeedHealthInterface.contentFeedHealthItem(health.getId());
                }

            }
        });
    }
    public ContentFeedHealthAdapter(List<Health> healthList, Context context) {
        this.healthList = healthList;
        this.context = context;


    }

    @Override
    public int getItemCount() {
        if (healthList != null && healthList.size() > 0) {
            return healthList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView healthImg ;
        private TextView healthTitle,healthText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            healthImg = itemView.findViewById(R.id.activityImageHealth);
            healthTitle = itemView.findViewById(R.id.ac_title_health);
            healthText = itemView.findViewById(R.id.acTexthealth);
        }
    }

    public interface ContentFeedHealthInterface {
        public void contentFeedHealthItem(int id);

    }

    public void setContentFeedHealthList(ContentFeedHealthInterface contentFeedHealthInterface) {
        this.contentFeedHealthInterface = contentFeedHealthInterface;

    }
}

