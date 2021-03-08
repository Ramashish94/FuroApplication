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
import com.app.furoapp.activity.GlideApp;
import com.app.furoapp.model.FriendModel.PendingFriendList;
import com.app.furoapp.widget.MyCircleImageView;

import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.MyViewHolder> {

    private List<PendingFriendList> particpants;
    private Context context;
    private FriendListAdapter.DataAdapterListener dataAdapterListener;


    @NonNull
    @Override
    public FriendListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_pending_friend_list, parent, false);

        return new FriendListAdapter.MyViewHolder(itemView);
    }

    public FriendListAdapter(List<PendingFriendList> particpants, Context context, FriendListAdapter.DataAdapterListener dataAdapterListener) {
        this.particpants = particpants;
        this.context = context;
        this.dataAdapterListener = dataAdapterListener;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListAdapter.MyViewHolder holder, int position) {

        PendingFriendList particpant = particpants.get(position);
        holder.tv_name.setText(particpant.getName());
        holder.tv_accept.setVisibility(View.GONE);
        holder.tv_reject.setText("Remove");

        holder.tv_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataAdapterListener.onDataSelected("remove",particpant.getId());
            }
        });

        GlideApp.with(context).load(particpant.getImage()).error(R.drawable.profile_circle)
                .into(holder.circleImageView);


    }

    @Override
    public int getItemCount() {
        return particpants.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_accept,tv_reject;

        ImageView circleImageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name= itemView.findViewById(R.id.tv_name);
            tv_accept = itemView.findViewById(R.id.tv_accept);
            tv_reject = itemView.findViewById(R.id.tv_reject);
            circleImageView = itemView.findViewById(R.id.ivUserProfilefriend);
        }
    }

    public void filterList(List<PendingFriendList> filterdList) {
        this.particpants = filterdList;
        notifyDataSetChanged();
    }

    public interface DataAdapterListener {
        void onDataSelected(String type,int id);
    }
}
