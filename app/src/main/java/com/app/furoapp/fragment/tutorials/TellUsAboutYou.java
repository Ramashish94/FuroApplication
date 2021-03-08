package com.app.furoapp.fragment.tutorials;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.furoapp.R;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.TutorialScreen;
import com.app.furoapp.databinding.FragmenttellusaboutyouBinding;
import com.app.furoapp.model.whatBringsYoutoFuro.WhatBringsYouToFuroRequest;
import com.app.furoapp.model.whatBringsYoutoFuro.WhatBringsYouToFuroResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.furoapp.enums.EnumConstants.WHAT_BRINGS_YOU_TO_FURO;

public class TellUsAboutYou extends Fragment {
    FragmenttellusaboutyouBinding binding;
    TutorialScreen tutorialScreen;
    Context context;
    TextView weightPickerValueTv, heightPickerValueTv,getStarted;
    private String whatBringsToYouFuro, userId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tutorialScreen = (TutorialScreen) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragmenttellusaboutyou, container, false);
        View view = binding.getRoot();



        whatBringsToYouFuro = (FuroPrefs.getString(getActivity(), "whatbringstoyouarraylist"));
        userId = FuroPrefs.getString(getActivity(), "loginUserId");


        setOnClickListnerBack();
        setOntvGetStartedClickListner();


        heightPickerValueTv = view.findViewById(R.id.height_value_tv);
        final RulerValuePicker heightPicker = view.findViewById(R.id.height_ruler_picker);
        heightPicker.selectValue(156);
        heightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                heightPickerValueTv.setText(selectedValue + " cms");
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                heightPickerValueTv.setText(selectedValue + " cms");
            }
        });

        weightPickerValueTv = view.findViewById(R.id.weight_value_tv);
        final RulerValuePicker weightPicker = view.findViewById(R.id.weight_ruler_picker);
        weightPicker.selectValue(55);
        weightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                weightPickerValueTv.setText(selectedValue + " kgs");

            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                weightPickerValueTv.setText(selectedValue + " kgs");

            }
        });

        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    public TellUsAboutYou() {

    }

    public static TellUsAboutYou newInstance(String name) {
        TellUsAboutYou fragment = new TellUsAboutYou();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    public void setOnClickListnerBack() {
        binding.ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                tutorialScreen.setDisplayFragment(WHAT_BRINGS_YOU_TO_FURO, bundle);
            }
        });


    }

    public void setOntvGetStartedClickListner() {

        binding.tvGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
              whatBringYouToFuroValidation();
            }
        });

    }

    public void whatBringYouToFuroValidation() {
        boolean check = true;


        String height = weightPickerValueTv.getText().toString().trim();
        String weight = heightPickerValueTv.getText().toString().trim();
        if (TextUtils.isEmpty(height.trim()) || weightPickerValueTv.length() == 0) {
            Util.displayToast(getContext(), "Please select height");
            check = false;
        }

        if (TextUtils.isEmpty(weight.trim()) || heightPickerValueTv.length() == 0) {
            Util.displayToast(getContext(), "Please select Weight");
            check = false;
        }
        if(TextUtils.isEmpty(whatBringsToYouFuro.trim())){
            Util.displayToast(getContext(), "select reason");
            check= false;

        }
        if (check) {

            WhatBringsYouToFuroRequest whatBringsYouToFuroRequest = new WhatBringsYouToFuroRequest();
            whatBringsYouToFuroRequest.setHeight(height);
            whatBringsYouToFuroRequest.setWeight(weight);
            whatBringsYouToFuroRequest.setReasons(whatBringsToYouFuro);
            whatBringsYouToFuroRequest.setUserId(userId);

             Util.isInternetConnected(getContext());
             Util.showProgressDialog(getContext());
            RestClient.userChooseOneReason(whatBringsYouToFuroRequest, new Callback<WhatBringsYouToFuroResponse>() {
                @Override
                public void onResponse(Call<WhatBringsYouToFuroResponse> call, Response<WhatBringsYouToFuroResponse> response) {
                    Util.dismissProgressDialog();
                    if(response != null){
                        if(response.body() != null){
                            if(response.body().getStatus().equalsIgnoreCase("200")){
                                Toast.makeText(tutorialScreen, "success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),HomeMainActivity.class);
                                intent.putExtra("contestpage","");
                                startActivity(intent);

                            }else {
                                Toast.makeText(tutorialScreen, "failure", Toast.LENGTH_SHORT).show();

                            }




                        }


                    }

                }

                @Override
                public void onFailure(Call<WhatBringsYouToFuroResponse> call, Throwable t) {
                    Toast.makeText(tutorialScreen, "check your Network", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }


}




