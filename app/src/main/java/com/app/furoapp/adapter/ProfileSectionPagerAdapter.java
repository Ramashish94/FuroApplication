package com.app.furoapp.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.furoapp.fragment.profileSection.ProfileEnduranceFragment;

import java.util.List;


public class ProfileSectionPagerAdapter extends FragmentPagerAdapter {
    Context context;
    private List<String> tabList;


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }

    public ProfileSectionPagerAdapter(FragmentManager fm, List<String> tabList) {
        super(fm);
        this.tabList = tabList;
    }


    @Override
    public Fragment getItem(int position) {
        return ProfileEnduranceFragment.newInstance(null);
    }

    @Override
    public int getCount() {
        return tabList.size();
    }


}
