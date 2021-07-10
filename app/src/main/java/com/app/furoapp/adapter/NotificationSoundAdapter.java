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
import com.app.furoapp.model.Settings.NotificationSound;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.List;

public class NotificationSoundAdapter extends RecyclerView.Adapter<NotificationSoundAdapter.MyViewHolder> {

    private List<NotificationSound> notificationSoundList;
    private Context context;
    private Callback callback;

    @NonNull
    @Override
    public NotificationSoundAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification_sound, parent, false);

        return new NotificationSoundAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationSoundAdapter.MyViewHolder holder, int position) {
        NotificationSound sound = notificationSoundList.get(position);

        holder.tvSoundName.setText(sound.getName());
        if (sound.isSelected()) {
            holder.ivIsSelected.setVisibility(View.VISIBLE);
        } else {
            holder.ivIsSelected.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> callback.onClickItem(notificationSoundList.get(position), position));
    }

    @Override
    public int getItemCount() {
        if (notificationSoundList != null && notificationSoundList.size() > 0) {
            return notificationSoundList.size();
        } else {
            return 0;
        }
    }

    public NotificationSoundAdapter(Context context, List<NotificationSound> notificationSoundList, Callback callback) {
        this.notificationSoundList = notificationSoundList;
        this.context = context;
        this.callback = callback;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIsSelected, ivPlay;
        private TextView tvSoundName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIsSelected = itemView.findViewById(R.id.iv_is_selected);
            tvSoundName = itemView.findViewById(R.id.tv_sound_name);
            ivPlay = itemView.findViewById(R.id.iv_play);
        }
    }

    public void updateItem(int pos) {
        for (int change = 0; change < notificationSoundList.size(); change++) {
            notificationSoundList.get(change).setSelected(change == pos);
        }
        FuroPrefs.putInt(context, Constants.NOTIFICATION_SOUND_LIST_KEY, notificationSoundList.get(pos).getId());
        notifyDataSetChanged();
    }

    public interface Callback {
        void onClickItem(NotificationSound notificationSound, int pos);
    }

}



