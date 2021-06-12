//package com.app.furoapp.activity.newFeature.StepsTracker
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import android.widget.Toast.LENGTH_LONG
//import android.widget.Toast.LENGTH_SHORT
//import androidx.appcompat.app.AppCompatActivity
//import com.app.furoapp.R
//import kotlin.reflect.KClass
//
//class FqStepsCounterInKotlinActivity : AppCompatActivity() {
//    var ivBackIcon: ImageView? = null
//    var ivSetting: ImageView? = null
//    var ivLeadBord: ImageView? = null
//    var tvActivateStepsCounter: TextView? = null
//    var tvDiActivate: TextView? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_fq_steps_counter_in__kotlin_)
//        initViews();
//        clickEvent();
//    }
//
//    private fun initViews() {
//        ivBackIcon = findViewById<ImageView>(R.id.ivBackIcon)
//    }
//
//    private fun clickEvent() {
//        ivBackIcon?.setOnClickListener {
//            Toast.makeText(this, "back Success", LENGTH_SHORT).show()
//        }
//        ivSetting?.setOnClickListener {
//            Toast.makeText(this, "Setting Success", LENGTH_SHORT).show()
//        }
//        ivLeadBord?.setOnClickListener {
//            Toast.makeText(this, "Success", LENGTH_SHORT).show()
//        }
//        tvActivateStepsCounter?.setOnClickListener {
//            Toast.makeText(this, " Success", LENGTH_SHORT).show()
//        }
//        tvDiActivate?.setOnClickListener {
//            Toast.makeText(this, "back Success", LENGTH_SHORT).show()
//        }
//
//    }
//
//
//}