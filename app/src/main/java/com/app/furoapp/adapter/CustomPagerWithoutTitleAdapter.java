package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.app.furoapp.R;

import java.util.ArrayList;

public class CustomPagerWithoutTitleAdapter extends PagerAdapter {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private ArrayList<Integer> images;


    public CustomPagerWithoutTitleAdapter(Context context, ArrayList<Integer> images) {

        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        View itemView = mLayoutInflater.inflate(R.layout.login_back1, container, false);
        ImageView itemImage = itemView.findViewById(R.id.ivImageVIew);
        itemImage.setImageResource(images.get(position));
        container.addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
