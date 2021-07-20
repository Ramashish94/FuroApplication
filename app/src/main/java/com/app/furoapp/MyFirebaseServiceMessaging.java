package com.app.furoapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Log;

import com.app.furoapp.activity.FriendsActivity;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.activity.SplashActivity;
import com.app.furoapp.activity.challengeRecieve.ChallengeRecieveActivity;
import com.app.furoapp.activity.challengeRecieveMap.ChallengeRecieveMapActivity;
import com.app.furoapp.fragment.profileSection.ProfileHomeNewActivity;
import com.app.furoapp.model.Settings.NotificationSound;
import com.app.furoapp.utils.BaseUtil;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MyFirebaseServiceMessaging extends FirebaseMessagingService {


    private static final String TAG = MyFirebaseServiceMessaging.class.getSimpleName();
    public static final String ACTION_1 = "action_1";
    JSONObject obj;
    Intent intent;
    int x, flag, content_id, challengeid, userid, checkflag;
    String friendsAct, type, message = "";
    private NotificationManager notificationManager;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, remoteMessage.getData().toString() + "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d("payload", "Message data payload: " + remoteMessage.getData());

            try {
                //  String type = remoteMessage.getData().get("Type");

                JSONObject obj = new JSONObject(remoteMessage.getData());
                type = obj.getString("type");

                switch (type) {
                    case Constants.FRIENDS:

                        message = obj.getString("message");
                        sendBackgroundForegroundNotification(remoteMessage.getData(), message);

                        break;

                    case Constants.ACCEPTFRIENDS:

                        message = obj.getString("message");
                        sendBackgroundForegroundNotification(remoteMessage.getData(), message);

                        break;

                    case Constants.PENDINGFRIENDS:

                        message = obj.getString("message");
                        sendBackgroundForegroundNotification(remoteMessage.getData(), message);

                        break;

                    case Constants.CHALLENGE:
                        message = obj.getString("message");
                        x = obj.getInt("challenge_id");
                        flag = obj.getInt("map_flag");
                        FuroPrefs.putInt(getApplicationContext(), "challengefuroid", x);
                        sendBackgroundForegroundNotification(remoteMessage.getData(), message);

                        break;

                    case Constants.PROFILE:

                        message = obj.getString("message");
                        sendBackgroundForegroundNotification(remoteMessage.getData(), message);

                        break;

                    case Constants.ACCEPTCHALLENGE:

                        message = obj.getString("message");
                        sendBackgroundForegroundNotification(remoteMessage.getData(), message);
                        break;

                    case Constants.REJECTCHALLENGE:

                        message = obj.getString("message");
                        sendBackgroundForegroundNotification(remoteMessage.getData(), message);
                        break;

                    case Constants.CONTANT:
                        message = obj.getString("message");
                        content_id = obj.getInt("content_id");
                        FuroPrefs.putString(getApplicationContext(), "id", String.valueOf(content_id));
                        sendBackgroundForegroundNotification(remoteMessage.getData(), message);
                        break;

                    case Constants.SUBMITCHALLENGE:
                        challengeid = obj.getInt("challenge_id");
                        userid = obj.getInt("user_id");
                        FuroPrefs.putInt(getApplicationContext(), "SubmissionChallenegeIdmap", challengeid);
                        FuroPrefs.putString(getApplicationContext(), "userIdLoginmap", String.valueOf(userid));
                        FuroPrefs.putInt(getApplicationContext(), "SubmissionChallenegeId", challengeid);
                        FuroPrefs.putString(getApplicationContext(), "userIdLogin", String.valueOf(userid));
                        message = obj.getString("message");
                        checkflag = obj.getInt("check_flag");
                        sendBackgroundForegroundNotification(remoteMessage.getData(), message);
                        break;

                    /*added by me*/
                    case Constants.WATER_INTAKE:
                        message = obj.getString("message");
                        sendBackgroundForegroundNotification(remoteMessage.getData(), message);
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.Body: " + remoteMessage.getNotification().getBody());

        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /**
     * Handle Background and Foreground Notifications
     *
     * @param message A Map with key value pair that hold
     *                information regarding pending Intent that navigate to corresponding  screen
     */
    private void sendBackgroundForegroundNotification(Map<String, String> message, String body) {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder notificationBuilder;

        try {
            if (type.equalsIgnoreCase("Friend")) {
                intent = new Intent(this, FriendsActivity.class);

            } else if (type.equalsIgnoreCase("Challenge")) {
                if (flag == 1) {
                    intent = new Intent(this, ChallengeRecieveMapActivity.class);
                } else {
                    intent = new Intent(this, ChallengeRecieveActivity.class);
                }
                intent.putExtra("challenegid", x);
            } else if (type.equalsIgnoreCase("Content")) {
                if (content_id == 0) {
                    intent = new Intent(this, SplashActivity.class);
                } else {
                    intent = new Intent(this, com.app.furoapp.activity.ContentFeedDetailActivity.class);
                }
            } else if (type.equalsIgnoreCase("Profile")) {
                intent = new Intent(this, ProfileHomeNewActivity.class);
            } else if (type.equalsIgnoreCase("Acceptchallenge")) {
                intent = new Intent(this, SplashActivity.class);
            } else if (type.equalsIgnoreCase("Rejectchallenge")) {
                intent = new Intent(this, SplashActivity.class);
            } else if (type.equalsIgnoreCase("Submitchallenge")) {
                if (checkflag == 0) {
                    intent = new Intent(this, com.app.furoapp.activity.challengeRecieve.WinnerActivity.class);

                } else {
                    intent = new Intent(this, com.app.furoapp.activity.challengeRecieveMap.WinnerActivityMap.class);

                }
            } else if (type.equalsIgnoreCase("Acceptfriend")) {
                intent = new Intent(this, FriendsActivity.class);

            } else if (type.equalsIgnoreCase("Pendingfriend")) {
                intent = new Intent(this, FriendsActivity.class);

            } else if (type.equalsIgnoreCase(Constants.WATER_INTAKE)) {
//                importance = NotificationManager.IMPORTANCE_HIGH;
                intent = new Intent(this, HomeMainActivity.class);
                int selectedId = FuroPrefs.getInt(this, Constants.NOTIFICATION_WATER_INTAKE_SELECTED_SOUND_KEY, 0);
                List<NotificationSound> list = BaseUtil.getNotificationSoundList(this);
                if (selectedId != 0) {
                    for (int selected = 0; selected < list.size(); selected++) {
                        if (selectedId == list.get(selected).getId()) {
                            defaultSoundUri = list.get(selected).getPath();
                        }
                    }
                }
                Log.d(TAG, "sendBackgroundForegroundNotification() called with: selectedId = [" + selectedId + "]");
            }
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            /*check for  oreo check  for notification builder */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.default_notification_channel_id), type, importance);
                notificationChannel.setSound(defaultSoundUri, audioAttributes);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            notificationBuilder = new Notification.Builder(this, getString(R.string.default_notification_channel_id));
            //notificationBuilder=NotificationCompat.Builder(Context context, String channelId)

            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon))
                    .setContentTitle(getString(R.string.app_name))
                    .setStyle(new
                            Notification.BigTextStyle().bigText(body))
                    // .setContentText(body).setColor(getResources().getColor(R.color.white))
                    .setSubText(DateUtils.getRelativeTimeSpanString(this, System.currentTimeMillis()))
                    .setAutoCancel(true)
                    .addAction(new Notification.Action(R.mipmap.app_icon,
                            "Fitness Quotient by Furo Sports", pendingIntent))
                    .setSound(defaultSoundUri)
                    // set Style for large text notification
                    .setContentIntent(pendingIntent);

            /**
             * Add notification small transparent icon
             * ***/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                notificationBuilder.setSmallIcon(R.mipmap.app_icon);
            else
                notificationBuilder.setSmallIcon(R.mipmap.app_icon);

            if (notificationManager != null) {

                notificationManager.notify("My Voice Data", (int) System.currentTimeMillis()
                        /*ID of notification */, notificationBuilder.build());
//                MediaPlayer mediaPlayer = MediaPlayer.create(this, defaultSoundUri);
//                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




/*
package com.app.furoapp;

        import android.app.NotificationChannel;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.graphics.BitmapFactory;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.Build;
        import android.text.format.DateUtils;
        import android.util.Log;
        import android.widget.Toast;

        import androidx.core.app.NotificationCompat;
        import androidx.core.util.DebugUtils;
        import androidx.localbroadcastmanager.content.LocalBroadcastManager;

        import com.app.furoapp.activity.challengeRecieve.ChallengeRecieveActivity;
        import com.app.furoapp.activity.challengeRecieveMap.ChallengeRecieveMapActivity;
        import com.app.furoapp.utils.Constants;
        import com.app.furoapp.utils.FuroPrefs;
        import com.google.firebase.messaging.FirebaseMessagingService;
        import com.google.firebase.messaging.RemoteMessage;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.Map;

        import static androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance;

public class MyFirebaseServiceMessaging extends FirebaseMessagingService {


    private static final String TAG = MyFirebaseServiceMessaging.class.getSimpleName();
    public static final String ACTION_1 = "action_1";

    Intent intent;
    int x,flag;



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, remoteMessage.getData().toString() + "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d("payload", "Message data payload: " + remoteMessage.getData());
            JSONObject obj = new JSONObject(remoteMessage.getData());

            try {
                x = obj.getInt("challenge_id");
                flag =  obj.getInt("map_flag");
                Log.d("data", "data" + x);
                FuroPrefs.putInt(getApplicationContext(), "challengefuroid", x);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            sendBackgroundForegroundNotification(remoteMessage.getData());
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.Body: " + remoteMessage.getNotification().getBody());

        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    */
/**
 * Handle Background and Foreground Notifications
 *
 * @param message A Map with key value pair that hold
 * information regarding pending Intent that navigate to corresponding  screen
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 * <p>
 * Add notification small transparent icon
 ***//*

    private void sendBackgroundForegroundNotification(Map<String, String> message) {


        */
/*Shown notification only when you have data object model*//*



        try {
            if(flag == 1){
                intent = new Intent(this, ChallengeRecieveMapActivity.class);
                intent.putExtra("challenegid", x);

            }else{
                intent = new Intent(this,ChallengeRecieveActivity.class);
                intent.putExtra("challenegid", x);
            }


            //Toast.makeText(this, "" + x, Toast.LENGTH_SHORT).show();


            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder;

            */
/*check for  oreo check  for notification builder *//*

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
                notificationManager.createNotificationChannel(notificationChannel);
                notificationBuilder = new NotificationCompat.Builder(this, notificationChannel.getId());
            } else
                notificationBuilder = new NotificationCompat.Builder(this, null);

            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon))
                    .setContentTitle("New challenge")
                    .setContentText("you get new Challenge")
                    .setSubText(DateUtils.getRelativeTimeSpanString(this, System.currentTimeMillis()))
                    .setAutoCancel(true)
                    .addAction(new NotificationCompat.Action(R.drawable.user_icon,
                            "Fitness Quotient by Furo Sports", pendingIntent))

                    .setSound(defaultSoundUri)
                    // set Style for large text notification
                    .setContentIntent(pendingIntent);

            */
/**
 * Add notification small transparent icon
 * ***//*

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                notificationBuilder.setSmallIcon(R.mipmap.app_icon);
            else
                notificationBuilder.setSmallIcon(R.mipmap.app_icon);

            if (notificationManager != null) {

                notificationManager.notify("My Voice Data", (int) System.currentTimeMillis()
                        */
/*ID of notification *//*
, notificationBuilder.build());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
*/




