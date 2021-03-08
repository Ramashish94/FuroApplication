package com.app.furoapp.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OpenCloseAdapter extends RecyclerView.Adapter<OpenCloseAdapter.MyViewHolder> {
    @NonNull
    @Override
    public OpenCloseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OpenCloseAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
