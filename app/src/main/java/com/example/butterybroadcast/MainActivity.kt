package com.example.butterybroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PorterDuff
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.example.butterybroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var LinkXML: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LinkXML = ActivityMainBinding.inflate(layoutInflater)  // initializes the binding object
        setContentView(LinkXML.root) // get root of XML

        // intent Filter
        val intent = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryBroadcastReceiver, intent)
    }

    private val batteryBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context, intent: Intent?) {

            if (intent?.action == "android.intent.action.BATTERY_CHANGED") {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)

                if (level > 20) {
                    LinkXML.charginglevel.setText("BATTERY is $level")
                    LinkXML.iconBColor.setColorFilter(context.getColor(R.color.grean))
                } else {
                    LinkXML.charginglevel.setText("low Battery \n " +
                                "$level % Battery remaining")
                    LinkXML.iconBColor.setColorFilter(context.getColor(R.color.red))
                }
            }

            val status: Int = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1

            LinkXML.isCharge.isVisible = if (status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL) { true } else { false }

           if( LinkXML.isCharge.isVisible){
               LinkXML.iconBColor.isVisible = false
           } else
               LinkXML.iconBColor.isVisible = true


        } // end onReceive fun
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryBroadcastReceiver)
    }
}