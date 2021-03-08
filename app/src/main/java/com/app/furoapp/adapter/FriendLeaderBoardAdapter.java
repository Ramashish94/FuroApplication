package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.existsContact.ExistContactUpdate;
import com.app.furoapp.utils.FuroPrefs;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FriendLeaderBoardAdapter extends RecyclerView.Adapter<FriendLeaderBoardAdapter.MyViewHolder> {

    private static final String TAG = "FriendLeaderBoardAdapte";
    FriendLeaderBoardInterface mlistener;
    int i = 0;
    private AddNewFriend addNewFriend;
    private List<ExistContactUpdate> existContactslist;
    private List<String> list_send;
    private Context context;
    private boolean isFQ = true;

    public FriendLeaderBoardAdapter(FriendLeaderBoardInterface listener, List<ExistContactUpdate> existContactslist, List<String> list_send, Context context) {

        this.mlistener = listener;
        this.existContactslist = existContactslist;
        this.list_send = list_send;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendLeaderBoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_user_listview, viewGroup, false);

        return new FriendLeaderBoardAdapter.MyViewHolder(itemView);
    }

    public void setAddFriends(FriendLeaderBoardAdapter.AddNewFriend addNewFriend) {
        this.addNewFriend = addNewFriend;
        /* notifyDataSetChanged();*/
    }

    @Override
    public void onBindViewHolder(@NonNull FriendLeaderBoardAdapter.MyViewHolder holder, final int position) {
        holder.uniquename.setText(existContactslist.get(position).getUsername());
        holder.name.setText(existContactslist.get(position).getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addNewFriend != null) {
                    addNewFriend.AddFriendClick(existContactslist.get(position).getUserId(), position);


                }
            }
        });

        if (existContactslist.get(position).isFrndReqsend()) {
            holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.background_border_gray_rounded));
        } else {
            holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.background_border_blue_rounded));
        }


        Picasso.with(context).load(existContactslist.get(position).getImage()).error(R.drawable.profile_circle).into(holder.friendsImage);

        if (existContactslist.get(position).getFqfrnd().equalsIgnoreCase("1")) {
            holder.linearLayout.setVisibility(View.GONE);

        } else if (existContactslist.get(position).getFqfrnd().equalsIgnoreCase("0")) {
            holder.linearLayout.setVisibility(View.VISIBLE);
        }


        if (existContactslist.get(position).getFqfrnd().equalsIgnoreCase("1") && position == 0) {
            holder.tvfriendsHeader.setVisibility(View.VISIBLE);
            holder.tvfriendsHeader.setText("FQ Friends");
            holder.linearLayout.setVisibility(View.GONE);

        } else {

            if (existContactslist.get(position).getFqfrnd().equalsIgnoreCase("0") && isFQ) {
                holder.linearLayout.setVisibility(View.VISIBLE);
                FuroPrefs.putInt(context, "isFq_value", position);
                isFQ = false;


            }

            if (existContactslist.get(position).getFqfrnd().equalsIgnoreCase("0") && FuroPrefs.getInt(context, "isFq_value", 0) == position) {
                holder.tvfriendsHeader.setVisibility(View.VISIBLE);
                holder.tvfriendsHeader.setText("Your Contacts");


            } else {
                holder.tvfriendsHeader.setVisibility(View.GONE);
                holder.tvfriendsHeader.setText("");

            }


        }


        if (existContactslist.get(position).getChecked()) {

            holder.selectMultipleFriends.setChecked(true);
        } else {
            holder.selectMultipleFriends.setChecked(false);
        }


        if (existContactslist.get(position).isSend()) {
            holder.selectMultipleFriends.setClickable(false);
            holder.selectMultipleFriends.setEnabled(false);
//            holder.selectMultipleFriends.setVisibility(View.GONE);

        } else {
            holder.selectMultipleFriends.setClickable(true);
            holder.selectMultipleFriends.setEnabled(true);
//            holder.selectMultipleFriends.setVisibility(View.VISIBLE);
        }

        holder.selectMultipleFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(context, existContactslist.get(position).getName() + " clicked!", Toast.LENGTH_SHORT).show();

                if (existContactslist.get(position).getChecked()) {
                    existContactslist.get(position).setChecked(false);
                    mlistener.OnCheckItem(position, false);


                } else {
                    existContactslist.get(position).setChecked(true);
                    mlistener.OnCheckItem(position, true);
//                    Toast.makeText(context, " ischecked " +existContactslist.get(position).getChecked() , Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    @Override
    public int getItemCount() {
        if (existContactslist != null) {
            return existContactslist.size();
        } else {
            return 0;
        }

    }

    public void filterList(List<ExistContactUpdate> filterdList) {
        this.existContactslist = filterdList;
        notifyDataSetChanged();
    }

    public interface FriendLeaderBoardInterface {
        void OnCheckItem(int position, boolean flag);


    }

    public interface AddNewFriend {
        void AddFriendClick(int userid, int pos);
//        void OnAddFrnd(int position, boolean flag);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout, linearLayoutgray;
        private TextView name, uniquename, tvfriendsHeader;
        private ImageView friendsImage;
        private CheckBox selectMultipleFriends;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name);
            uniquename = itemView.findViewById(R.id.uniqueName);
            selectMultipleFriends = itemView.findViewById(R.id.checkBox);
            friendsImage = itemView.findViewById(R.id.friendProfile);
            tvfriendsHeader = itemView.findViewById(R.id.friendsHeader);
            linearLayout = itemView.findViewById(R.id.addlinearlayout);
            linearLayoutgray = itemView.findViewById(R.id.addlinearlayoutgray);


        }
    }


}
