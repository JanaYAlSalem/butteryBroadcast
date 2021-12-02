package com.example.butterybroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
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

        override fun onReceive(context: Context?, intent: Intent?) {

            if (intent?.action == "android.intent.action.BATTERY_CHANGED") {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)

                if (level > 15)
                    LinkXML.charginglevel.setText("BATTERY is $level")
                else
                    LinkXML.charginglevel.setText(
                        "low Battery \n " +
                                "$level % Battery remaining"
                    )

            }

            val status: Int = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1

            LinkXML.isCharge.isVisible = if (status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL) { true } else { false }

        } // end onReceive fun
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryBroadcastReceiver)
    }
}