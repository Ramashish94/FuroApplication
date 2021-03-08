package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.furoapp.R;
import com.app.furoapp.databinding.ActivityMapOverViewBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.fragment.challenges.CreateChallangeEnduranceFragment;
import com.app.furoapp.fragment.enduranceMapFragments.EnduranceMapFragmentStart;

public class MapOverViewActivity extends AppCompatActivity {
    ActivityMapOverViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map_over_view);
        binding.tvStartMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MapOverViewActivity.this,MapsEnduranceActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    public void replaceFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerId, fragment, name);

            if (!fragment.isAdded()) {
                fragmentTransaction.addToBackStack(name);
                fragmentTransaction.commitAllowingStateLoss();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
