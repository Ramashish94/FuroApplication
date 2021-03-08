package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.app.furoapp.R;
import com.app.furoapp.model.Items;
import com.app.furoapp.widget.francyconverflow.FancyCoverFlow;
import com.app.furoapp.widget.francyconverflow.FancyCoverFlowAdapter;

import java.util.List;


public class MyFancyCoverWhiteFlowAdapter extends FancyCoverFlowAdapter {
    private Context mContext;

    public List<Items> list;

    public MyFancyCoverWhiteFlowAdapter(Context context, List<Items> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public View getCoverFlowItem(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fancycoverflow_white, null);
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            convertView.setLayoutParams(new FancyCoverFlow.LayoutParams(width / 3, FancyCoverFlow.LayoutParams.WRAP_CONTENT));
            holder = new ViewHolder();
            holder.product_name = (TextView) convertView.findViewById(R.id.text);
            holder.product_image = (ImageView) convertView.findViewById(R.id.profile_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        convertView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.white_gradient));
        final Items item = getItem(position);
        holder.product_name.setText(item.categoryName/*+"\n"+item.name*/);
        holder.product_image.setImageResource(item.imageName);
        ;
        return convertView;
    }

    public void setSelected(int position) {
        position = position % list.size();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i == position) {
                    list.get(i).setSelected(true);
                } else {
                    list.get(i).setSelected(false);
                }
            }
        }
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Items getItem(int i) {
        return list.get(i % list.size());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    static class ViewHolder {
        public ImageView product_image;
        TextView product_name;
    }
}
