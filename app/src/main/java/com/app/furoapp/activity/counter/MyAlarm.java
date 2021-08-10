package com.app.furoapp.activity.counter;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.furoapp.activity.newFeature.StepsTracker.FqStepsCounterActivity;
import com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel.UserStepsGoalRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel.UserStepsGoalResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//class extending the Broadcast Receiver
public class MyAlarm extends BroadcastReceiver {
    private String getAccessToken;

    //the method will be fired when the alarm is triggerred
    @Override
    public void onReceive(Context context, Intent intent) {

        //you can check the log that it is fired
        //Here we are actually not doing anything
        //but you can do any task here that you want to be done at a specific time everyday
        Log.d("MyAlarmBelal", "Alarm just fired");

        Float calories = FuroPrefs.getFloat(context, "calories");
        Float steps = FuroPrefs.getFloat(context, "steps");
        Float distance = FuroPrefs.getFloat(context, "distance");
        String stepsAchivedVal = FuroPrefs.getString(context, "stepsAchivedVal");
        Float selectNumberAchievedVal = FuroPrefs.getFloat(context, "steps");

        boolean isActuvate = FuroPrefs.getBoolean(context, "isAlreadyActivate");
        if (isActuvate == true) {
            FuroPrefs.putInt(context, "stepsWhenGoogleDisabled", 0);
            callUserStepGoalApi(context, steps, calories, distance, stepsAchivedVal, selectNumberAchievedVal);
        }else{
            FuroPrefs.putInt(context, "stepsWhenGoogleDisabled", 0);
        }


    }

    private void callUserStepGoalApi(Context context, Float step, Float calories, Float distance, String stepsAchivedVal, Float selectNumberAchievedVal) {
        getAccessToken = FuroPrefs.getString(context, Constants.Get_ACCESS_TOKEN);

        UserStepsGoalRequest userStepsGoalRequest = new UserStepsGoalRequest();
        userStepsGoalRequest.setTime("00:00");
        userStepsGoalRequest.setDistance(String.valueOf(distance));
        userStepsGoalRequest.setCalories(String.valueOf(calories));
        userStepsGoalRequest.setCount_steps(String.valueOf(step));
        userStepsGoalRequest.setTotal_steps("12000");

//        if (stepsAchivedVal != null) {
//            userStepsGoalRequest.setTotal_steps(stepsAchivedVal);
//        } else {
//            userStepsGoalRequest.setTotal_steps(String.valueOf(selectNumberAchievedVal));
//        }

        if (Util.isInternetConnected(context)) {

            RestClient.getUserStepsGoal(getAccessToken, userStepsGoalRequest, new Callback<UserStepsGoalResponse>() {
                @Override
                public void onResponse(Call<UserStepsGoalResponse> call, Response<UserStepsGoalResponse> response) {

                    if (response.code() == 200) {
                        Log.d("Api succeed", "success");
                        FuroPrefs.putInt(context, "stepsWhenGoogleDisabled", 0);

                        Toast.makeText(context, "Data saved Successfully !", Toast.LENGTH_SHORT).show();

                    } else {
                        if (response.code() == 500) {
                            Toast.makeText(context, "Internal server error !", Toast.LENGTH_SHORT).show();
                        }
                        if (response.code() == 403) {
                            Toast.makeText(context, response.code() + "Session expire Please login again", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<UserStepsGoalResponse> call, Throwable t) {
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}