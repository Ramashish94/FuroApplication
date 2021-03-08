package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.draft.DraftChallenge;

import java.util.List;

public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.MyViewHolder> {

    private List<DraftChallenge> draftChallengeList;
    private Context context;
    private DraftInterface draftInterface;
    @NonNull
    @Override
    public DraftAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_draft, parent, false);

        return new DraftAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DraftAdapter.MyViewHolder holder, int position) {
        DraftChallenge draftChallenge = draftChallengeList.get(position);
        holder.textViewactivityname.setText(draftChallenge.getChallengeActivity());
        holder.textViewdate.setText(draftChallenge.getDate());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( draftInterface!= null) {
                     draftInterface.draftItem(draftChallenge.getChallengeId(),holder.getAdapterPosition());
                }

            }
        });


    }
    public DraftAdapter(List<DraftChallenge> draftChallengeList,Context context) {
        this.draftChallengeList = draftChallengeList;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return draftChallengeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewactivityname,textViewdate;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewactivityname = itemView.findViewById(R.id.activityopendraft);
            textViewdate = itemView.findViewById(R.id.dateopendraft);
            linearLayout = itemView.findViewById(R.id.linearLayoutForYoudraft);
        }
    }

    public interface DraftInterface {
        void draftItem(String IdChallengedraft,int position);

    }

    public void setDraftItemList(DraftInterface draftInterface) {
        this.draftInterface = draftInterface;

    }


}
