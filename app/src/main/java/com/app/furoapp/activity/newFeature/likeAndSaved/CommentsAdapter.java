package com.app.furoapp.activity.newFeature.likeAndSaved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.VewHolder> {
    Context context;
    List<ComentsModelTest> comentsModelTests;

    public CommentsAdapter(Context applicationContext, List<ComentsModelTest> comentsModelTests) {
        this.context = applicationContext;
        this.comentsModelTests = comentsModelTests;
    }

    @NonNull
    @Override
    public CommentsAdapter.VewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_items, parent, false);

        return new CommentsAdapter.VewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.VewHolder holder, int position) {
        ComentsModelTest comentsModelTest = comentsModelTests.get(position);
        holder.tvName.setText("" + comentsModelTest.getName());
        holder.tvComments.setText("" + comentsModelTest.getComments());


    }

    @Override
    public int getItemCount() {
        if (comentsModelTests != null && comentsModelTests.size() > 0) {
            return comentsModelTests.size();
        } else {
            return 0;
        }
    }

    public class VewHolder extends RecyclerView.ViewHolder {
        public TextView tvComments, tvName;

        public VewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvComments = itemView.findViewById(R.id.tvComments);

        }
    }
}
