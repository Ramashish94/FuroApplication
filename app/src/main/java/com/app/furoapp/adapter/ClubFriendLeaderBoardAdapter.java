package com.app.furoapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.existsContact.ExistContact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ClubFriendLeaderBoardAdapter extends RecyclerView.Adapter<ClubFriendLeaderBoardAdapter.MyViewHolder> {

    private List<ExistContact> existContactslist;
    private FriendLeaderBoardInterface friendLeaderBoardInterface;
    private Context context;
    ExistContact existContact;
    List<String> hash_set = new ArrayList<String>();
    List<String> arrayList = new ArrayList<String>();

    private HashMap<Integer, String> hashMap = new HashMap<>();

    @NonNull
    @Override
    public ClubFriendLeaderBoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.club_challenge_row_user_listview, viewGroup, false);

        return new ClubFriendLeaderBoardAdapter.MyViewHolder(itemView);
    }

    public ClubFriendLeaderBoardAdapter(List<ExistContact> existContactslist, Context context) {

        this.existContactslist = existContactslist;

        this.context = context;

    }

    @Override
    public void onBindViewHolder(@NonNull ClubFriendLeaderBoardAdapter.MyViewHolder holder, int position) {
        holder.uniquename.setText(existContactslist.get(position).getUsername());
        holder.name.setText(existContactslist.get(position).getName());
        holder.selectMultipleFriends.setChecked(hashMap.containsKey(existContactslist.get(position).getUserId()));
        holder.selectMultipleFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friendLeaderBoardInterface != null) {

                    friendLeaderBoardInterface.friendsItem(arrayList);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, uniquename;
        private CheckBox selectMultipleFriends;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name);
            uniquename = itemView.findViewById(R.id.uniqueName);
            selectMultipleFriends = itemView.findViewById(R.id.checkBox);

            selectMultipleFriends.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {




                    arrayList.clear();
                    if (isChecked) {
                        hashMap.put(getAdapterPosition(), " " + existContactslist.get(getAdapterPosition()).getUserId());
                        Log.i("TAG", String.valueOf(hashMap));

                        arrayList.add(String.valueOf(hashMap.values()));
                        Log.i("TAG2", String.valueOf(arrayList));
                    }
                    if (!isChecked) {

                        hashMap.remove(getAdapterPosition());
                        arrayList.add(String.valueOf(hashMap.values()));
                        Log.i("TAG", String.valueOf(hashMap));
                        Log.i("TAG2", String.valueOf(arrayList));


                    }


                }
            });
        }
    }


    public interface FriendLeaderBoardInterface {
        public void friendsItem( List<String> arrayList);

    }

    public void setFriendList(FriendLeaderBoardInterface friendLeaderBoardInterface) {
        this.friendLeaderBoardInterface = friendLeaderBoardInterface;

    }

    public boolean isSelected(String text) {
        for (String string : hash_set) {
            if (string.equalsIgnoreCase(text)) {
                hash_set.remove(text);
                return false;
            }
        }
        hash_set.add(text);
        return true;

    }


}
