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
import com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.Comment;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.VewHolder> {
    Context context;
    List<Comment> commentList;

    public CommentsAdapter(Context applicationContext, List<Comment> commentList) {
        this.context = applicationContext;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public VewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_items, parent, false);

        return new VewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VewHolder holder, int position) {
        if (commentList != null && commentList.size() > 0) {
            Comment comment = commentList.get(position);
            if (comment.getUser()!=null && comment.getUser().getUsername()!=null) {
               /* if (comment.getUser().getUsername().equalsIgnoreCase("null")) {

                } else {*/
                    holder.tvName.setText("" + comment.getUser().getUsername());
               // }
            }else {

            }
            if (comment.getComment()!=null) {
                holder.tvComments.setText("" + comment.getComment());
            }else {

            }
        } else {

        }
    }

    @Override
    public int getItemCount() {
        if (commentList != null && commentList.size() > 0) {
            return commentList.size();
        } else {
            return 0;
        }
    }

    public class VewHolder extends RecyclerView.ViewHolder {
        public TextView tvComments, tvName;
        public ImageView ivUserImage;

        public VewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvComments = itemView.findViewById(R.id.tvComments);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
        }
    }
}
