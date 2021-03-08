package com.app.furoapp.fragment.tutorials;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.FragmentNotificationBinding;

import static com.app.furoapp.enums.EnumConstants.HOME_TUTORIAL_PAGE;
import static com.app.furoapp.enums.EnumConstants.WHAT_BRINGS_YOU_TO_FURO;

public class Notification extends Fragment {

    FragmentNotificationBinding binding;
    TutorialScreen tutorialScreen;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tutorialScreen = (TutorialScreen) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_notification, container, false);
        View view = binding.getRoot();

        setOnClickListeners();
        skipTv();
        setOnClickListenersBack();
        return view;
    }

    public Notification() {

    }

    public static Notification newInstance(String name) {
        Notification fragment = new Notification();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOnClickListeners() {
        binding.etNotifyMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                tutorialScreen.setDisplayFragment(WHAT_BRINGS_YOU_TO_FURO, bundle);
            }
        });


    }

    private void setOnClickListenersBack() {
        binding.ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                tutorialScreen.setDisplayFragment(HOME_TUTORIAL_PAGE, bundle);
            }
        });


    }
    private void skipTv(){

        binding.tvSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });



    }

    public void start()
    {
        new CountDownTimer(10*1000, 1000)
        {
            TextView timer = getView(). findViewById(R.id.tvSkipBtn);
            public void onTick(long millisUntilFinished)
            {
                int TimeSeconds = (int) millisUntilFinished/1000;

                final boolean[] resetStatus = {false};

                if (resetStatus[0])
                {
                    cancel();

                    TimeSeconds = TimeSeconds - 5;
                    final int finalTimeSeconds = TimeSeconds;

                    new CountDownTimer(finalTimeSeconds*1000, 1000)
                    {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (finalTimeSeconds >= 10)
                            {
                                //displays time
                                timer.setText("0:" + finalTimeSeconds);
                            }

                            else
                            {
                                //displays time
                                timer.setText("0:0" + finalTimeSeconds);
                            }
                            resetStatus[0] = false;
                        }

                        @Override
                        public void onFinish() {
                            Bundle bundle = new Bundle();
                            tutorialScreen.setDisplayFragment(WHAT_BRINGS_YOU_TO_FURO, bundle);


                        }
                    }.start();
                }

                else
                {
                    if (TimeSeconds >= 10)
                    {
                        //displays time
                        timer.setText("0:" + TimeSeconds);
                    }

                    else
                    {
                        //displays time
                        timer.setText("0:0" + TimeSeconds);
                    }

                }
            }
            public void onFinish()
            {
                Bundle bundle = new Bundle();
                tutorialScreen.setDisplayFragment(WHAT_BRINGS_YOU_TO_FURO, bundle);

            }
        }.start();
    }

}





