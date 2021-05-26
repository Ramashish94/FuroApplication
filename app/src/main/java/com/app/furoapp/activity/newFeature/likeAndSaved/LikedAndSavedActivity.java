package com.app.furoapp.activity.newFeature.likeAndSaved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class LikedAndSavedActivity extends AppCompatActivity {
    public TabLayout tbSavedBookmarked;
    public TabItem tab1Saved, tab2Liked;
    public ViewPager viewPager;
    public PagerAdapter pagerAdapter;
    public ImageView ivLikedImg, ivSavedImg, ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_book_marked);

       /* if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.Like And Saved Videos");

        }*/

        initViews();
        tabAndPagerListiner();
    }

    private void initViews() {
        tbSavedBookmarked = findViewById(R.id.tbSavedBookmarked);
        tab1Saved = findViewById(R.id.tab1Saved);
        tab2Liked = findViewById(R.id.tab2Liked);
        viewPager = findViewById(R.id.viewPager);
        ivLikedImg = findViewById(R.id.ivLikedImg);
        ivSavedImg = findViewById(R.id.ivSavdImg);
        ivBackArrow = findViewById(R.id.ivBackArrow);
    }

    private void tabAndPagerListiner() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tbSavedBookmarked.getTabCount());
        viewPager.setAdapter(pagerAdapter);

      /*  viewPager.setOffscreenPageLimit(2);
        tbSavedBookmarked.setupWithViewPager(viewPager);*/

        tbSavedBookmarked.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1)
                    pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tbSavedBookmarked));
        // it is provided listen of scroll or page change
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


}