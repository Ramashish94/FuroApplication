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
import com.app.furoapp.model.content_feed.Motivation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentFeedMotivationAdapter extends RecyclerView.Adapter<ContentFeedMotivationAdapter.MyViewHolder> {

    ContentFeedMotivationInterface contentFeedMotivationInterface;
    private List<Motivation> motivationList;
    private Context context;
    @NonNull
    @Override
    public ContentFeedMotivationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_feed_motivation, parent, false);

        return new ContentFeedMotivationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentFeedMotivationAdapter.MyViewHolder holder, int position) {
        Motivation motivation = motivationList.get(position);
        holder.motivationText.setText(motivation.getDescription());
        holder.motivationTitle.setText(motivation.getTitle());
        Picasso.with(context).load(motivation.getImageUrl())
                .into(holder.motivationImg);

        holder.motivationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contentFeedMotivationInterface != null) {
                    contentFeedMotivationInterface.contentFeedMotivationItem(motivation.getId());
                }

            }
        });
    }
    public ContentFeedMotivationAdapter(List<Motivation> motivationList, Context context) {
        this.motivationList = motivationList;
        this.context = context;


    }

    @Override
    public int getItemCount() {
        if (motivationList != null && motivationList.size() > 0) {
            return motivationList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView motivationImg ;
        private TextView motivationTitle, motivationText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            motivationImg = itemView.findViewById(R.id.activityImageMotivation);
            motivationTitle = itemView.findViewById(R.id.ac_titleMotivation);
            motivationText = itemView.findViewById(R.id.acTextMotivation);
        }
    }
    public interface ContentFeedMotivationInterface {
        public void contentFeedMotivationItem(int id);

    }

    public void setContentFeedMotivationList(ContentFeedMotivationInterface contentFeedMotivationInterface) {
        this.contentFeedMotivationInterface = contentFeedMotivationInterface;

    }
}




