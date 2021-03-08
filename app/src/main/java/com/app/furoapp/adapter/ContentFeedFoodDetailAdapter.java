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
import com.app.furoapp.model.contentFeedDetail.Body;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentFeedFoodDetailAdapter extends RecyclerView.Adapter<ContentFeedFoodDetailAdapter.MyViewHolder> {
    private List<Body> bodyList;
    private Context context;

    @NonNull
    @Override
    public ContentFeedFoodDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_feed_detail_recycler_item, parent, false);

        return new ContentFeedFoodDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentFeedFoodDetailAdapter. MyViewHolder holder, int position) {
        Body body = bodyList.get(position);
        //nikli to hai
        if (body.getType().equalsIgnoreCase("title1")) {

            holder.mainParagraph.setVisibility(View.GONE);
            holder.contentdetailimagecf.setVisibility(View.GONE);
            holder.mainTitle2.setVisibility(View.GONE);
            holder.mainTitle1.setText(body.getValue());


        } else if (body.getType().equalsIgnoreCase("paragraph")) {
            holder.mainParagraph.setText(body.getValue());
            holder.contentdetailimagecf.setVisibility(View.GONE);
            holder.mainTitle2.setVisibility(View.GONE);
            holder.mainTitle1.setVisibility(View.GONE);


        } else if (body.getType().equalsIgnoreCase("image")) {
            Picasso.with(context).load(body.getValue())
                    .into(holder.contentdetailimagecf);
            holder.mainParagraph.setVisibility(View.GONE);

            holder.mainTitle2.setVisibility(View.GONE);
            holder.mainTitle1.setVisibility(View.GONE);

        } else if (body.getType().equalsIgnoreCase("title2")) {
            holder.mainTitle2.setText(body.getValue());
            holder.mainParagraph.setVisibility(View.GONE);
            holder.contentdetailimagecf.setVisibility(View.GONE);

            holder.mainTitle1.setVisibility(View.GONE);


        }
    }


    public ContentFeedFoodDetailAdapter(List<Body> bodyList, Context context) {
        this.bodyList = bodyList;
        this.context = context;


    }



    @Override
    public int getItemCount() {
        if (bodyList != null && bodyList.size() > 0) {
            return bodyList.size();
        } else {
            return 0;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  mainTitle1, mainTitle2, mainParagraph;
        private ImageView contentdetailimagecf;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainTitle1 = itemView.findViewById(R.id.title1);
            mainTitle2 = itemView.findViewById(R.id.title2);
            mainParagraph = itemView.findViewById(R.id.paragraph);
            contentdetailimagecf = itemView.findViewById(R.id.contentdetailimage);
        }
    }
}
