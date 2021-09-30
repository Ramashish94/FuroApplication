package com.app.furoapp.activity.counter

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
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
import com.app.furoapp.activity.newFeature.StepsTracker.FqStepsCounterActivity
import com.app.furoapp.activity.newFeature.StepsTracker.LeaderBoardActivity
import com.app.furoapp.activity.newFeature.StepsTracker.StepCounterHistoryActivity
import com.app.furoapp.activity.newFeature.StepsTracker.StepCounterSettingsActivity
import com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel.UserStepsGoalRequest
import com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel.UserStepsGoalResponse
import com.app.furoapp.activity.newFeature.fqTips.TipsResponse
import com.app.furoapp.retrofit.RestClient
import com.app.furoapp.utils.Constants
import com.app.furoapp.utils.FuroPrefs
import com.app.furoapp.utils.Util
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.*
import com.google.android.gms.fitness.request.SessionReadRequest
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_fq_steps_counter.*
import kotlinx.android.synthetic.main.alertt_dialog_modified_data_.*
import kotlinx.android.synthetic.main.google_fit_pop_up.*
import kotlinx.android.synthetic.main.item_fancycoverflow.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
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

    private var ivSignInWithGoogle: View? = null
    private var tvCancel: View? = null
    private val TAG = "FIT_TAG"

    private val REQUEST_OAUTH_REQUEST_CODE = 1
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    private var sessionsRequest: SessionReadRequest? = null

    var goalDist: TextView? = null
    var goalDistance = 0.0
    var data = ""
    var distance = 0.0
    var fitData: TextView? = null
    var steps = 0.0
    var calories = 0.0
    var decimalFormat = DecimalFormat("0.00")
    var goals = 0


    var notifyPendingIntent: PendingIntent? = null
    var dialogBuilder: AlertDialog.Builder? = null
    var notificationManager: NotificationManager? = null
    private var isSwitchedChecked = false
    var mGoogleSignInClient: GoogleSignInClient? = null
    private var getAccessToken: String? = null

    //    private val tipsList: List<DataItem>? = null
    private var dialog: AlertDialog? = null
    private val tipsHandler = Handler()
    private var tipsListSize = 0
    private var tipsStart = 0
    private var getDistanceMiAndKm = 0f
    var getDetectedSteps: Int? = null
    var stepsAchivedVal: String? = null
    var selectNumberAchievedVal: String? = null

    var isLogin: Boolean = false;
    var includeCongratsStepsTrack: View? = null

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
        fitData = findViewById(R.id.fit_Data)
        includeCongratsStepsTrack = findViewById(R.id.incudeCongratsStepsTrack)
        ivSignInWithGoogle = findViewById(R.id.ivSignInWithGoogle)
        tvCancel = findViewById(R.id.tvCancel)

        getAccessToken = FuroPrefs.getString(applicationContext, Constants.Get_ACCESS_TOKEN)
        var isActuvate = FuroPrefs.getBoolean(applicationContext, "isAlreadyActivate")
        if (isActuvate == true) {
            tvActivateStepsCounter.isVisible = false
            deactivate.isVisible = true

        } else {
            tvActivateStepsCounter.isVisible = true
            deactivate.isVisible = false
        }


        goals = FuroPrefs.getInt(applicationContext, Constants.ACHIVED_VAL, 0)
        tvTotNumberOfSteps.text = goals.toString()
        //  clickEvent();
        stepsAchivedVal = intent.getStringExtra("getAchievedVal")
        selectNumberAchievedVal = intent.getStringExtra("selectNumber")

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

        clickListners()


    }

    override fun onResume() {
        super.onResume()
        callTipsApi()
    }

    private fun hasOAuthPermission(): Boolean {
        val fitnessOptions: FitnessOptions = getFitnessSignInOptions()!!
        return GoogleSignIn.hasPermissions(
                GoogleSignIn.getLastSignedInAccount(this),
                fitnessOptions
        )
    }

    /**
     * Launches the Google SignIn activity to request OAuth permission for the user.
     * Keep
     */
    private fun requestOAuthPermission() {
        val fitnessOptions: FitnessOptions = getFitnessSignInOptions()!!
        GoogleSignIn.requestPermissions(
                this,
                REQUEST_OAUTH_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(this),
                fitnessOptions
        )
    }

    private fun getFitnessSignInOptions(): FitnessOptions? {
        return FitnessOptions.builder()
                .addDataType(DataType.TYPE_ACTIVITY_SEGMENT, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                .build()
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


    fun handler() {
        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {
                Fitness.getHistoryClient(applicationContext, getGoogleAccount())
                        .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)

                        .addOnSuccessListener { dataSet ->
                            val total = when {
                                dataSet.isEmpty -> 0
                                else -> dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()

                            }
                            Log.i(TAG, "Total steps: $total")
                            var isDeactivateClickTrue = FuroPrefs.getBoolean(applicationContext, "isDeactivateClicked")
                            if (isDeactivateClickTrue == true) {

                            } else {
                                FuroPrefs.putInt(applicationContext, "stepsWhenGoogleDisabled", total!!)
                            }


                            getDetectedSteps = total
                            var fullsteps: Float = getDetectedSteps!!.toFloat()


                            var stepsGoodleDisabled =
                                    FuroPrefs.getInt(applicationContext, "stepsWhenGoogleDisabled", 0)

                            var userStep = (getDetectedSteps!! - stepsGoodleDisabled)
                            if (userStep!! >= goals) {
                                includeCongratsStepsTrack!!.setVisibility(View.VISIBLE);

                            } else {
                                includeCongratsStepsTrack!!.setVisibility(View.GONE);
                            }

                            val duration = userStep / 64

                            splashCall(duration, userStep)


                            getCalculateCalories = (userStep!! * 0.045).toFloat()


                            var steps: Float = userStep.toFloat()

                            isLogin = true
                            FuroPrefs.putBoolean(applicationContext, "isAlreadyLoginForFitness", isLogin)
                            splashCallDistance(steps)


                            val isActuvate = FuroPrefs.getBoolean(this@Padometer, "isAlreadyActivate") // add by pankaj on 4-aug-2021
                            if (isActuvate == true) {
                                showNotification(total)
                            }

                            // Read the data that's been collected throughout the past week.


                            val calendar = Calendar.getInstance()
                            if (Build.VERSION.SDK_INT >= 23) {
                                calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH], 23, 55] =
                                        0
                            } else {
                                calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH], 23, 55] =
                                        0
                            }



                            setAlarm(calendar.timeInMillis, getCalculateCalories, fullsteps, getDistanceMiAndKm)

                        }
                ha.postDelayed(this, 5000)
            }
        }, 5000)
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

        handler()


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
            /*added by ramashish*/
            includeGoogleFitPopUp.visibility = View.VISIBLE
            ivSetting.isClickable = false
            ivHistory.isClickable = false
            ivBackIcon.isClickable = false
            ivLeadBord.isClickable = false
            /* //   Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
             FuroPrefs.putBoolean(applicationContext, "isGoogleFitDisabled", false)
             checkPermissionsAndRun(FitActionRequestCode.SUBSCRIBE)
             Toast.makeText(this, "Step Counter Activate!", Toast.LENGTH_SHORT)
                     .show()
             var isAlreadyActivate = true
             FuroPrefs.putBoolean(applicationContext, "isAlreadyActivate", isAlreadyActivate)
             deactivate.isVisible = true
             tvActivateStepsCounter.isVisible = false*/
        }
        /*added by ramashish*/
        ivSignInWithGoogle?.setOnClickListener {
            includeGoogleFitPopUp.visibility = View.GONE
            ivSetting.isClickable = true
            ivHistory.isClickable = true
            ivBackIcon.isClickable = true
            ivLeadBord.isClickable = true
            //   Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
            FuroPrefs.putBoolean(applicationContext, "isGoogleFitDisabled", false)
            checkPermissionsAndRun(FitActionRequestCode.SUBSCRIBE)
            Toast.makeText(this, "Step Counter Activate!", Toast.LENGTH_SHORT)
                    .show()
            var isAlreadyActivate = true
            FuroPrefs.putBoolean(applicationContext, "isAlreadyActivate", isAlreadyActivate)
            deactivate.isVisible = true
            tvActivateStepsCounter.isVisible = false
        }

        tvCancel?.setOnClickListener {
            includeGoogleFitPopUp.visibility = View.GONE
            ivSetting.isClickable = true
            ivHistory.isClickable = true
            ivBackIcon.isClickable = true
            ivLeadBord.isClickable = true
        }
        deactivate.setOnClickListener {

            FuroPrefs.putBoolean(applicationContext, "isDeactivateClicked", true)

            googlefitDisabled()
            if (getDetectedSteps == null) {

            } else {
                FuroPrefs.putInt(applicationContext, "stepsWhenGoogleDisabled", getDetectedSteps!!)

            }

            tvActivateStepsCounter.isVisible = true
            deactivate.isVisible = false
            var isAlreadyActivate = false
            FuroPrefs.putBoolean(applicationContext, "isAlreadyActivate", isAlreadyActivate)

            //            notificationManager!!.cancel(0)
            //
            when {
                notificationManager != null -> {
                    notificationManager!!.cancel(0)
                }
                else -> {

                }
            }
            callServicetoSetGoals(this)

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

    // _________ \\
    @SuppressLint("RemoteViewLayout")
    private fun showNotification(counter: Int) {

        val notifyIntent = Intent(this, Padometer::class.java)
        // Set the Activity to start in a new, empty task
        notifyIntent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        // Create the PendingIntent

        notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

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
//            Utils.showProgressDialogBar(applicationContext)
            RestClient.getAllTipsData(getAccessToken, object : Callback<TipsResponse> {
                override fun onResponse(
                        call: Call<TipsResponse>,
                        response: Response<TipsResponse>
                ) {
//                    Util.dismissProgressDialog()
                    if (response.code() == 200) {
                        Log.d(
                                FqStepsCounterActivity.TAG,
                                "onResponse() called with: , response = [" + response.body() + "]"
                        )
                        if (response.body()!!.data != null
                                && response.body()!!.data.stepCounterData != null
                                && response.body()!!.data.stepCounterData.size > 0
                        ) {
                            val tipsList = response.body()!!.data.stepCounterData
                            tipsListSize = tipsList.size

                            val postDelayed = tipsHandler.postDelayed(object : Runnable {
                                override fun run() {
                                    if (tipsList != null && tipsList.size > 0) {
                                        if (tipsStart == tipsListSize - 1) {
                                            tvPrizmTips.text = tipsList[tipsStart].paragraph
                                            tipsStart = 0
                                        } else {
                                            if (tipsList != null && tipsList.size > 0) {
                                                tvPrizmTips.text = tipsList[tipsStart].paragraph
                                                tipsStart++
                                            }
                                        }
                                    }

                                    tipsHandler.postDelayed(this, 5000)
                                }
                            }, 5000)

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


//    private val tipsRunnable: Runnable = object : Runnable {
//        override fun run() {
//            if (tipsList != null && tipsList.size > 0) {
//                if (tipsStart == tipsListSize - 1) {
//                    tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph())
//                    tipsStart = 0
//                } else {
//                    if (tipsList != null && tipsList.size > 0) {
//                        tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph())
//                        tipsStart++
//                    }
//                }
//            }
//            tipsHandler.postDelayed(this, 5000)
//        }
//    }

    private fun openModifiedAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alertt_dialog_modified_data_, null)
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()



        llFqStepCounter.setClickable(false)
        btn_cancel.setOnClickListener(View.OnClickListener { dialog.dismiss() })
        tvContinue.setOnClickListener(View.OnClickListener { dialog.dismiss() })
        tvModified.setOnClickListener(View.OnClickListener { // FqStepsCounterActivity.super.onBackPressed();
            dialog.dismiss()
            finish()
            /*  finishAffinity();
                System.exit(0);*/
        })
        dialog.show()
    }

    fun getDistanceRun(steps: Long): Float {
        return (steps * 78).toFloat() / 100.toFloat() //distance in km 100000
    }

    fun calculateCalories(steps: Long): Float {
        return (steps * 0.045).toFloat()
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
        am.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pi)
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

                    FuroPrefs.putBoolean(applicationContext, "isGoogleFitDisabled", true)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "There was an error disabling Google Fit", e)
                }
    }


    /*  // [END parse_dataset]
      private fun deleteData() {
          Log.i(TAG, "Deleting today's step count data.")

          // [START delete_dataset]
          // Set a start and end time for our data, using a start time of 1 day before this moment.
          val cal = Calendar.getInstance()
          val now = Date()
          cal.time = now
          val endTime = cal.timeInMillis
          Log.i(TAG, "endTime" + endTime)

          cal.add(Calendar.DAY_OF_YEAR, -1)
          val startTime = cal.timeInMillis
          Log.i(TAG, "startTime" + startTime)
          //  Create a delete request object, providing a data type and a time interval
          val request = DataDeleteRequest.Builder()
              .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
              .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
              .build()

          // Invoke the History API with the HistoryClient object and delete request, and then
          // specify a callback that will check the result.
          Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
              .deleteData(request)
              .addOnCompleteListener { task ->
                  if (task.isSuccessful) {
                      Log.i(TAG, "Successfully deleted today's step count data.")
                  } else {
                      Log.e(
                          TAG,
                          "Failed to delete today's step count data.",
                          task.exception
                      )
                  }
              }
      }*/

    private val SPLASH_DISPLAY_LENGTH = 5000
    fun splashCall(dura: Int, userStep: Int) {
        Handler().postDelayed({
            tvCalories.text = "$getCalculateCalories Cal"
            tbduration.text = ("" + dura)
            // tbduration.text = ("$dura Min")
            tvCountsSteps.text = userStep.toString()

        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

    private val SPLASH_DISPLAY_LENGTH_DISTANCE = 5000
    fun splashCallDistance(stepCount: Float) {
        Handler().postDelayed({
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

        }, SPLASH_DISPLAY_LENGTH_DISTANCE.toLong())
    }

    fun callServicetoSetGoals(context: Context) {
        val calories = FuroPrefs.getFloat(context, "calories")
        val steps = FuroPrefs.getFloat(context, "steps")
        val distance = FuroPrefs.getFloat(context, "distance")
        val stepsAchivedVal = FuroPrefs.getString(context, "stepsAchivedVal")
        val selectNumberAchievedVal = FuroPrefs.getFloat(context, "steps")


        callUserStepGoalApi(context, steps, calories, distance, stepsAchivedVal, selectNumberAchievedVal)
    }

    // add by pankaj kataria on 4-aug-2021 for sending step counters to backend
    private fun callUserStepGoalApi(context: Context, step: Float, calories: Float, distance: Float, stepsAchivedVal: String?, selectNumberAchievedVal: Float) {
        getAccessToken = FuroPrefs.getString(applicationContext, Constants.Get_ACCESS_TOKEN)
        val userStepsGoalRequest = UserStepsGoalRequest()
        userStepsGoalRequest.time = tbduration.text.toString()
        userStepsGoalRequest.distance = tvDistance.text.toString()
        userStepsGoalRequest.calories = tvCalories.text.toString()
        userStepsGoalRequest.count_steps = tvCountsSteps.text.toString()
        userStepsGoalRequest.total_steps = tvTotNumberOfSteps.text.toString()/*added*/

//        if (stepsAchivedVal != null) {
//            userStepsGoalRequest.total_steps = stepsAchivedVal
//        } else {
//            userStepsGoalRequest.total_steps = selectNumberAchievedVal.toString()
//        }
        if (Util.isInternetConnected(context)) {
            RestClient.getUserStepsGoal(getAccessToken, userStepsGoalRequest, object : Callback<UserStepsGoalResponse?> {
                override fun onResponse(call: Call<UserStepsGoalResponse?>, response: Response<UserStepsGoalResponse?>) {
                    if (response.code() == 200) {
                        Log.d("Api succeed", "success")
//                        FuroPrefs.putInt(context, "stepsWhenGoogleDisabled", 0)
                        Toast.makeText(context, "Data saved Successfully !", Toast.LENGTH_SHORT).show()
//                        finish()
                    } else {
                        if (response.code() == 500) {
                            Toast.makeText(context, "Internal server error !", Toast.LENGTH_SHORT).show()
                        }
                        if (response.code() == 403) {
                            Toast.makeText(context, response.code().toString() + "Session expire Please login again", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<UserStepsGoalResponse?>, t: Throwable) {
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


}