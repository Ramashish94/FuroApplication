package com.app.furoapp.activity.counter

import android.annotation.SuppressLint
import android.app.*
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import com.app.furoapp.R
import com.app.furoapp.activity.LoginTutorialScreen
import com.app.furoapp.activity.newFeature.StepsTracker.*
import com.app.furoapp.activity.newFeature.StepsTracker.fqsteps.DataItem
import com.app.furoapp.activity.newFeature.StepsTracker.fqsteps.TipsResponse
import com.app.furoapp.retrofit.RestClient
import com.app.furoapp.utils.Constants
import com.app.furoapp.utils.FuroPrefs
import com.app.furoapp.utils.Util
import com.app.furoapp.utils.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_fq_steps_counter.*
import kotlinx.android.synthetic.main.alertt_dialog_modified_data_.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

enum class FitActionRequestCode {
    SUBSCRIBE,
    READ_DATA
}

/**
 * This sample demonstrates combining the Recording API and History API of the Google Fit platform
 * to record steps, and display the daily current step count. It also demonstrates how to
 * authenticate a user with Google Play Services.
 */
class Padometer : AppCompatActivity() {
    var notifyPendingIntent: PendingIntent? = null
    var dialogBuilder: AlertDialog.Builder? = null
    var notificationManager: NotificationManager? = null
    private var isSwitchedChecked = false
    var mGoogleSignInClient: GoogleSignInClient? = null
    private var getAccessToken: String? = null
    private val tipsList: List<DataItem>? = null
    private var dialog: AlertDialog? = null
    private val tipsHandler = Handler()
    private var tipsListSize = 0
    private var tipsStart = 0
    private var getDistanceMiAndKm = 0f
    var getDetectedSteps: Int? = null
    var stepsAchivedVal: String? = null
    var selectNumberAchievedVal: String? = null

    var isLogin: Boolean = false;

    private var getCalculateCalories = 0f
    private val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .build()

    private val runningQOrLater =
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fq_steps_counter)
        getAccessToken = FuroPrefs.getString(applicationContext, Constants.Get_ACCESS_TOKEN)
        var isActuvate = FuroPrefs.getBoolean(applicationContext, "isAlreadyActivate")
        if (isActuvate == true) {
            tvActivateStepsCounter.isVisible = false
            deactivate.isVisible = true

        }
        //  clickEvent();
        stepsAchivedVal = intent.getStringExtra("getAchievedVal")
        selectNumberAchievedVal = intent.getStringExtra("selectNumber")

        /*added*/
        when {
            stepsAchivedVal != null -> {
                tvTotNumberOfSteps.text = " of $stepsAchivedVal Steps";
            }
            selectNumberAchievedVal != null -> {
                tvTotNumberOfSteps.text = " of $selectNumberAchievedVal Steps ";
            }
            else -> {

            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(applicationContext, gso)

        // This method sets up our custom logger, which will print all log messages to the device
        // screen, as well as to adb logcat.
        var isLogin = FuroPrefs.getBoolean(applicationContext, "isAlreadyLoginForFitness")

        if (isLogin == true) {
            checkPermissionsAndRun(FitActionRequestCode.SUBSCRIBE)

        }
        callTipsApi();
        clickListners()


    }

    fun googleSignOut() {
        mGoogleSignInClient?.signOut()
                ?.addOnCompleteListener(
                        this,
                        OnCompleteListener<Void?> { // Toast.makeText(ActivityMain.this, "Google Sign Out done.", Toast.LENGTH_SHORT).show();
                            revokeAccess()
                        })
    }

    private fun revokeAccess() {
        mGoogleSignInClient?.revokeAccess()
                ?.addOnCompleteListener(this, OnCompleteListener<Void?> {
                    // Toast.makeText(ActivityMain.this, "Google access revoked.", Toast.LENGTH_SHORT).show();
                })
    }


    private fun getAlertTokenExpire() {
        if (getAccessToken != null) {
            dialogBuilder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.session_expired_layout, null)
            dialogBuilder!!.setView(dialogView)
            dialog = dialogBuilder!!.create()
            val btn_Cancel = dialogView.findViewById<ImageView>(R.id.btn_cancel)
            val text_logout = dialogView.findViewById<TextView>(R.id.text_logout)
            val noiwanttocontinue = dialogView.findViewById<TextView>(R.id.noiwanttocontinuee)
            val llLogOut = dialogView.findViewById<LinearLayout>(R.id.llLogOut)
            btn_Cancel.setOnClickListener { dialog!!.dismiss() }
            noiwanttocontinue.setOnClickListener { dialog!!.dismiss() }
            text_logout.setOnClickListener {
                FuroPrefs.clear(applicationContext)
                googleSignOut()
                val intent = Intent(
                        applicationContext,
                        LoginTutorialScreen::class.java
                )
                startActivity(intent)
                finishAffinity()
            }
            dialog!!.show()
        } else {
        }
    }


    private fun checkPermissionsAndRun(fitActionRequestCode: FitActionRequestCode) {
        if (permissionApproved()) {
            fitSignIn(fitActionRequestCode)
        } else {
            requestRuntimePermissions(fitActionRequestCode)
        }
    }

    /**
     * Checks that the user is signed in, and if so, executes the specified function. If the user is
     * not signed in, initiates the sign in flow, specifying the post-sign in function to execute.
     *
     * @param requestCode The request code corresponding to the action to perform after sign in.
     */
    private fun fitSignIn(requestCode: FitActionRequestCode) {
        if (oAuthPermissionsApproved()) {
            performActionForRequestCode(requestCode)
        } else {
            requestCode.let {
                GoogleSignIn.requestPermissions(
                        this,
                        requestCode.ordinal,
                        getGoogleAccount(), fitnessOptions
                )
            }
        }
    }


    /**
     * Runs the desired method, based on the specified request code. The request code is typically
     * passed to the Fit sign-in flow, and returned with the success callback. This allows the
     * caller to specify which method, post-sign-in, should be called.
     *
     * @param requestCode The code corresponding to the action to perform.
     */
    private fun performActionForRequestCode(requestCode: FitActionRequestCode) =
            when (requestCode) {
                FitActionRequestCode.READ_DATA -> readData()
                FitActionRequestCode.SUBSCRIBE -> subscribe()
            }


    private fun oAuthPermissionsApproved() = GoogleSignIn.hasPermissions(
            getGoogleAccount(),
            fitnessOptions
    )

    /**
     * Gets a Google account for use in creating the Fitness client. This is achieved by either
     * using the last signed-in account, or if necessary, prompting the user to sign in.
     * `getAccountForExtension` is recommended over `getLastSignedInAccount` as the latter can
     * return `null` if there has been no sign in before.
     */
    private fun getGoogleAccount() = GoogleSignIn.getAccountForExtension(this, fitnessOptions)


    /**
     * Handles the callback from the OAuth sign in flow, executing the post sign in function
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            RESULT_OK -> {
                val postSignInAction = FitActionRequestCode.values()[requestCode]
                postSignInAction.let {
                    performActionForRequestCode(postSignInAction)
                }
            }
            else -> oAuthErrorMsg(requestCode, resultCode)
        }
    }

    private fun oAuthErrorMsg(requestCode: Int, resultCode: Int) {
        val message = """
            There was an error signing into Fit. Check the troubleshooting section of the README
            for potential issues.
            Request code was: $requestCode
            Result code was: $resultCode
        """.trimIndent()
        Log.e(TAG, message)
    }


    /** Records step data by requesting a subscription to background step data.  */
    private fun subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, getGoogleAccount())
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i(TAG, "Successfully subscribed!")
                        readData()
                    } else {
                        Log.w(TAG, "There was a problem subscribing.", task.exception)
                    }
                }
    }

    /**
     * Reads the current daily step total, computed from midnight of the current day on the device's
     * current timezone.
     */
    private fun readData() {
        Fitness.getHistoryClient(this, getGoogleAccount())
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener { dataSet ->
                    val total = when {
                        dataSet.isEmpty -> 0
                        else -> dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()
                    }
                    Log.i(TAG, "Total steps: $total")




                    getDetectedSteps = total
                    tvCountsSteps.text = getDetectedSteps.toString()


                    /*added*/getCalculateCalories = (getDetectedSteps!! * 0.045).toFloat()
                    tvCalories.text = "$getCalculateCalories Cal"

                    var steps: Float = total.toFloat()

                    isLogin = true
                    FuroPrefs.putBoolean(applicationContext, "isAlreadyLoginForFitness", isLogin)

                    clickListner(steps)

                    showNotification(total)


                    val calendar = Calendar.getInstance()
                    if (Build.VERSION.SDK_INT >= 23) {
                        calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH], 23, 55] =
                                0
                    } else {
                        calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH], 23, 55] =
                                0
                    }



                    setAlarm(calendar.timeInMillis, getCalculateCalories, steps, getDistanceMiAndKm)

                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "There was a problem getting the step count.", e)
                }
    }


    /** Initializes a custom log class that outputs both to in-app targets and logcat.  */

    private fun permissionApproved(): Boolean {
        val approved = if (runningQOrLater) {
            PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACTIVITY_RECOGNITION
            )
        } else {
            true
        }
        return approved
    }


    fun clickListners() {
        ivBackIcon.setOnClickListener { v: View? -> finish() }

        ivSetting.setOnClickListener { v: View? ->
            val intent = Intent(
                    applicationContext,
                    StepCounterSettingsActivity::class.java
            )
            startActivity(intent)
            finish()
        }
        ivLeadBord.setOnClickListener { v: View? ->
            val intent = Intent(
                    applicationContext,
                    LeaderBoardActivity::class.java
            )
            startActivity(intent)
        }
        ivHistory.setOnClickListener { v: View? ->
            //   Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
            val intent = Intent(
                    applicationContext,
                    StepCounterHistoryActivity::class.java
            )
            startActivity(intent)
        }

        tvActivateStepsCounter.setOnClickListener { v: View? ->
            //   Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);

            checkPermissionsAndRun(FitActionRequestCode.SUBSCRIBE)
            Toast.makeText(this, "Step Counter Activate!", Toast.LENGTH_SHORT)
                    .show()
            tvActivateStepsCounter.isVisible = false

            var isAlreadyActivate = true
            FuroPrefs.putBoolean(applicationContext, "isAlreadyActivate", isAlreadyActivate)
            deactivate.isVisible = true
            ivModified.isVisible = false
        }

        deactivate.setOnClickListener {
            googlefitDisabled()
            tvActivateStepsCounter.isVisible = true
            ivModified.isVisible = true
            deactivate.isVisible = false
            var isAlreadyActivate = false
            FuroPrefs.putBoolean(applicationContext, "isAlreadyActivate", isAlreadyActivate)
            //            notificationManager!!.cancel(0)
            when {
                notificationManager != null -> {
                    notificationManager!!.cancel(0)
                }
                else -> {

                }
            }

        }

        ivModified.setOnClickListener {
            openModifiedAlertDialog();
        }

    }

    private fun requestRuntimePermissions(requestCode: FitActionRequestCode) {
        val shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.ACTIVITY_RECOGNITION
                )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        requestCode.let {
            if (shouldProvideRationale) {
                Log.i(TAG, "Displaying permission rationale to provide additional context.")
                Snackbar.make(
                        findViewById(R.id.llFqStepCounter),
                        "",
                        Snackbar.LENGTH_INDEFINITE
                )
                        .setAction("") {
                            // Request permission
                            ActivityCompat.requestPermissions(
                                    this,
                                    arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                                    requestCode.ordinal
                            )
                        }
                        .show()
            } else {
                Log.i(TAG, "Requesting permission")
                // Request permission. It's possible this can be auto answered if device policy
                // sets the permission in a given state or the user denied the permission
                // previously and checked "Never ask again".
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                        requestCode.ordinal
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when {
            grantResults.isEmpty() -> {
                // If user interaction was interrupted, the permission request
                // is cancelled and you receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            }
            grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                // Permission was granted.
                val fitActionRequestCode = FitActionRequestCode.values()[requestCode]
                fitActionRequestCode.let {
                    fitSignIn(fitActionRequestCode)
                }
            }
            else -> {
                // Permission denied.

                // In this Activity we've chosen to notify the user that they
                // have rejected a core permission for the app since it makes the Activity useless.
                // We're communicating this message in a Snackbar since this is a sample app, but
                // core permissions would typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.

            }
        }
    }

    // _________________________ \\
    @SuppressLint("RemoteViewLayout")
    private fun showNotification(counter: Int) {
        if (counter == 0) {
        } else {
            val notifyIntent = Intent(this, Padometer::class.java)
            // Set the Activity to start in a new, empty task
            notifyIntent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            // Create the PendingIntent

            notifyPendingIntent = PendingIntent.getActivity(
                    this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        val contentView = RemoteViews(this.packageName, R.layout.collapsenotification)
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(this, "")
        val count = counter.toString()
        contentView.setTextViewText(R.id.content_title, count)

        notificationBuilder.color = Color.parseColor("#6600cc")
        val colorLED = Color.argb(255, 0, 255, 0)
        notificationBuilder.setLights(colorLED, 500, 500)
        // To  make sure that the Notification LED is triggered.
        notificationBuilder.priority = Notification.PRIORITY_LOW
        notificationBuilder.setOngoing(true)
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
        notificationBuilder.setSilent(true)
        notificationBuilder.setContent(contentView)
        notificationBuilder.setContentIntent(notifyPendingIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel("", "FURO FQ", importance)
            notificationBuilder.setChannelId("")
            assert(mNotificationManager != null)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager!!.notify(0, notificationBuilder.build())
    }

    private fun callTipsApi() {
        if (Util.isInternetConnected(applicationContext)) {
            Utils.showProgressDialogBar(applicationContext)
            RestClient.getAllTipsData(getAccessToken, object : Callback<TipsResponse> {
                override fun onResponse(
                        call: Call<TipsResponse>,
                        response: Response<TipsResponse>
                ) {
                    Util.dismissProgressDialog()
                    if (response.code() == 200) {
                        Log.d(
                                FqStepsCounterActivity.TAG,
                                "onResponse() called with: , response = [" + response.body() + "]"
                        )
                        if (response.body()!!.data != null && response.body()!!.data.data != null && response.body()!!
                                        .data.data.size > 0
                        ) {
                            val tipsList = response.body()!!.data.data
                            tipsListSize = tipsList.size
                            tipsHandler.postDelayed(tipsRunnable, 0)
                        } else {
                            Toast.makeText(
                                    this@Padometer,
                                    "No tips data found",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        if (response.code() == 500) {
                            Toast.makeText(
                                    this@Padometer,
                                    "Internal server error !",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (response.code() == 403) {
                            Toast.makeText(
                                    this@Padometer,
                                    response.code().toString() + "Session expire Please login again",
                                    Toast.LENGTH_SHORT
                            ).show()
                            getAlertTokenExpire()
                        }
                    }
                }

                override fun onFailure(call: Call<TipsResponse>, t: Throwable) {
                    Toast.makeText(this@Padometer, "Failure", Toast.LENGTH_SHORT)
                            .show()
                }
            })
        }
    }


    // ___________________________________________________________________________ \\
    private val tipsRunnable: Runnable = object : Runnable {
        override fun run() {
            if (tipsList != null && tipsList.size > 0) {
                if (tipsStart == tipsListSize - 1) {
                    tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph())
                    tipsStart = 0
                } else {
                    if (tipsList != null && tipsList.size > 0) {
                        tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph())
                        tipsStart++
                    }
                }
            }
            tipsHandler.postDelayed(this, 5000)
        }
    }

    private fun openModifiedAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alertt_dialog_modified_data_, null)
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()

        btn_cancel.setOnClickListener{ dialog.dismiss() }
        tvContinue.setOnClickListener { dialog.dismiss() }
        tvModified.setOnClickListener{
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }

    fun getDistanceRun(steps: Long): Float {
        return (steps * 78).toFloat() / 100.toFloat() //distance in km 100000
    }

    fun calculateCalories(steps: Long): Float {
        return (steps * 0.045).toFloat()
    }

    fun clickListner(stepCount: Float) {
        swBtnInKm.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isSwitchedChecked = true
                getDistanceMiAndKm = (stepCount?.times(78)) as Float / 100000.toFloat()
                tvDistance.text = "$getDistanceMiAndKm km"
            } else {
                isSwitchedChecked = false
                getDistanceMiAndKm = (stepCount?.times(78)) as Float / 100.toFloat()
                tvDistance.text = "$getDistanceMiAndKm m"
            }
            //tvDistance.setText("" + getDistanceMiAndKm + " meter");
        }


        if (isSwitchedChecked) {
            getDistanceMiAndKm = (stepCount?.times(78)) as Float / 100000.toFloat()
            tvDistance.text = "$getDistanceMiAndKm km"
        } else if (isSwitchedChecked == false) {
            getDistanceMiAndKm = (stepCount?.times(78)) as Float / 100.toFloat()
            tvDistance.text = "$getDistanceMiAndKm m"
        }


    }

    private fun setAlarm(time: Long, calo: Float, step: Float, distance: Float) {
        //getting the alarm manager
        val am = getSystemService(ALARM_SERVICE) as AlarmManager

        //creating a new intent specifying the broadcast receiver
        val i = Intent(this, MyAlarm::class.java)
        FuroPrefs.putFloat(applicationContext, "calories", calo)
        FuroPrefs.putFloat(applicationContext, "steps", step)
        FuroPrefs.putFloat(applicationContext, "distance", distance)
        FuroPrefs.putString(applicationContext, "stepsAchivedVal", stepsAchivedVal)
        FuroPrefs.putString(applicationContext, "selectNumberAchievedVal", selectNumberAchievedVal)


        //creating a pending intent using the intent
        val pi = PendingIntent.getBroadcast(this, 0, i, 0)

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi)
    }

    fun googlefitDisabled() {

        Fitness.getConfigClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .disableFit()
                .addOnSuccessListener {
                    Log.i(TAG, "Disabled Google Fit")
                    Toast.makeText(
                            this,
                            "Step Counter Deactivate!",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "There was an error disabling Google Fit", e)
                }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(
                applicationContext,
                WantToAcivedActivity::class.java
        )
        startActivity(intent)
    }
}