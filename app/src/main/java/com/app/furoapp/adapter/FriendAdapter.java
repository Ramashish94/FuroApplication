package com.app.furoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.UserProfileActivity;
import com.app.furoapp.model.FriendModel.Particpant;
import com.app.furoapp.model.clubChallenge.Club;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Particpant> particpants;
    private Context context;
    private SellingListInterface sellingListInterface;
    private DataAdapterListener dataAdapterListener;

    ContentFeedDetailAdapter.ContentFeedDetailInterface contentFeedDetailInterface;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_friend_list, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof MyViewHolder) {
            populateItemRows((MyViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }



    }

    public FriendAdapter(List<Particpant> particpants, Context context,DataAdapterListener dataAdapterListener) {
        this.particpants = particpants;
        this.context = context;
        this.dataAdapterListener = dataAdapterListener;
    }


    @Override
    public int getItemCount() {
        return particpants == null ? 0 : particpants.size();
    }

    @Override
    public int getItemViewType(int position) {
        return particpants.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_add_friend;
        CircleImageView imageViewProfile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name= itemView.findViewById(R.id.tv_name);
            imageViewProfile = itemView.findViewById(R.id.ivUserProfileinvitefriends);
            tv_add_friend = itemView.findViewById(R.id.tv_add_friend);
        }
    }


    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    public void filterList(List<Particpant> filterdList) {
        this.particpants = filterdList;
        notifyDataSetChanged();
    }

    public interface DataAdapterListener {
        void onDataSelected(int id);
    }


    private void populateItemRows(MyViewHolder viewHolder, int position) {
        Particpant particpant = particpants.get(position);
        viewHolder.tv_name.setText(particpant.getName());

        Picasso.with(context).load(particpant.getImage()).error(R.drawable.profile_circle).into(viewHolder.imageViewProfile);
        viewHolder.tv_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataAdapterListener.onDataSelected(particpant.getId());
            }
        });

        viewHolder.imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sellingListInterface != null) {                           ///
                    sellingListInterface.sellinglistitem(viewHolder.getAdapterPosition());   ///
                }
            }
        });

    }

    public interface SellingListInterface {       ///
        public void sellinglistitem(int position);



    }
    public void setSellingListInterface(SellingListInterface sellingListInterface) {    ///
        this.sellingListInterface = sellingListInterface;                                 ///
    }

}