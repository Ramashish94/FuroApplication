package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.app.furoapp.databinding.PagerItemBinding;
import com.app.furoapp.R;
import com.squareup.picasso.Picasso;


public class ChallengesPagerAdapter extends PagerAdapter {

    Context mContext;
    private Integer[] mResources;
    private String[] namesImages;
    private LayoutInflater mLayoutInflater;
    PagerItemBinding pagerItemBinding;


    public ChallengesPagerAdapter(Context context, Integer[] stringURls) {
        mContext = context;
        this.mResources = stringURls;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView itemImage = itemView.findViewById(R.id.imageView);
        ImageView tvHomeBanner = itemView.findViewById(R.id.tvHomeBanner);

        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pagerItemBinding.tvHomeBanner.setColorFilter(ContextCompat.getColor(mContext, R.color.light_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        });


        Picasso.with(mContext).load(mResources[position]).into(itemImage);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
