package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.FriendModel.Particpant;
import com.app.furoapp.model.FriendModel.PendingFriendList;

import java.util.List;

public class FriendPendingAdapter extends RecyclerView.Adapter<FriendPendingAdapter.MyViewHolder> {

    private List<PendingFriendList> particpants;
    private Context context;
    private FriendPendingAdapter.DataAdapterListener dataAdapterListener;

    ContentFeedDetailAdapter.ContentFeedDetailInterface contentFeedDetailInterface;

    @NonNull
    @Override
    public FriendPendingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_pending_friend_list, parent, false);

        return new FriendPendingAdapter.MyViewHolder(itemView);
    }

    public FriendPendingAdapter(List<PendingFriendList> particpants, Context context, FriendPendingAdapter.DataAdapterListener dataAdapterListener) {
        this.particpants = particpants;
        this.context = context;
        this.dataAdapterListener = dataAdapterListener;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendPendingAdapter.MyViewHolder holder, int position) {

        PendingFriendList particpant = particpants.get(position);
        holder.tv_name.setText(particpant.getName());
        holder.tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataAdapterListener.onDataSelected("accept", particpant.getId());
            }
        });

        holder.tv_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataAdapterListener.onDataSelected("reject", particpant.getId());

            }
        });


    }

    @Override
    public int getItemCount() {
        return particpants.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_accept, tv_reject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_accept = itemView.findViewById(R.id.tv_accept);
            tv_reject = itemView.findViewById(R.id.tv_reject);
        }
    }

    public void filterList(List<PendingFriendList> filterdList) {
        this.particpants = filterdList;
        notifyDataSetChanged();
    }

    public interface DataAdapterListener {
        void onDataSelected(String type, int id);
    }
}
