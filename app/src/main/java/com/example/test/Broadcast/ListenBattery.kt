package com.example.test.Broadcast

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.test.model.dao.DataBase
import com.example.test.view.PopupActiviy

class ListenBattery : BroadcastReceiver() {
    companion object {
        const val ACTION_BATTERY_CHANGED = "BATTERY_CHANGED"
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent1: Intent?) {
         if (intent1 != null) {
            val intent3 = Intent(ACTION_BATTERY_CHANGED)
            if (intent1.action.equals(Intent.ACTION_POWER_CONNECTED)) {
                val intent2 = Intent(context, PopupActiviy::class.java)
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent3.putExtra("state", true)
                context?.applicationContext?.startActivity(intent2)
            } else {
                intent3.putExtra("state", false)
            }
            context?.sendBroadcast(intent3);
        }
    }
}