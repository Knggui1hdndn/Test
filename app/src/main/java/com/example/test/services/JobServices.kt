package com.example.test.services

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.test.Broadcast.ListenBattery
import com.example.test.R
import com.example.test.model.dao.DataBase
import java.util.logging.Handler

class JobServices : Service() {
    override fun onBind(intent: Intent?): IBinder? {
         return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val broadcastReceiver =ListenBattery()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        applicationContext.registerReceiver(broadcastReceiver, filter)
        Log.d("ssssssss1", "onStartJob")
        val notification = NotificationCompat.Builder(this, "1")
            .setContentTitle("Battery Service")
            .setContentText("The service is running.")
            .setSmallIcon(R.drawable.img)
            .build()
        startForeground(1, notification)
        return START_STICKY
    }
}