package com.example.test.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

import androidx.core.view.isVisible
import com.example.test.AnimationHelper
import com.example.test.Broadcast.ListenBattery
import com.example.test.R
import com.example.test.TimeUtil
import com.example.test.databinding.ActivityPopupActiviyBinding
import com.example.test.model.dao.DataBase

class PopupActiviy : AppCompatActivity() {
    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var intentFilter: IntentFilter
    private lateinit var binding: ActivityPopupActiviyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
        super.onCreate(savedInstanceState)
        binding = ActivityPopupActiviyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao = DataBase.getDatabase(this).dao()
        val anim = dao.getAnimation()
        with(binding) {
            anim?.let { anim ->
                txtDate.isVisible = anim.dateTime
                txtTime.isVisible = anim.dateTime
                txtLever.isVisible = anim.lever

                img.isVisible = anim.type != "video"
                vd.isVisible = anim.type == "video"
                TimeUtil.setTime(txtDate, txtTime)

                AnimationHelper.startAnimationForImageOrVideo(anim, img, vd)
                broadcastReceiver = object : BroadcastReceiver() {
                    @SuppressLint("SetTextI18n")
                    override fun onReceive(context: Context?, intent: Intent?) {
                        if (intent != null) {
                            Log.d("sssss", intent.action.toString())
                            if (intent.action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                                val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                                val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                                txtLever.text = (level * 100 / scale.toFloat()).toString() + " % "
                            } else {
                                if (!intent.getBooleanExtra("state", false)) {
                                    finish()
                                }
                            }
                        }
                    }
                }
            }
            intentFilter = IntentFilter()
            intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
            intentFilter.addAction(ListenBattery.ACTION_BATTERY_CHANGED)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }
}