package com.example.test.view

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.test.AnimationHelper
import com.example.test.SendData
import com.example.test.TimeUtil
import com.example.test.databinding.ActivityShowBinding
import com.example.test.model.Animation
import com.example.test.model.dao.DataBase
import com.example.test.services.JobServices
import com.example.test.view.bottomsheet.BottomSheet1
import com.example.test.view.bottomsheet.BottomSheet2

class ShowActivity : AppCompatActivity(), SendData {
    private lateinit var binding: ActivityShowBinding
    private lateinit var ani: Animation
    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var intentFilter: IntentFilter

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val boolean = intent.getBooleanExtra("add", false)
            if (boolean) {
                btnSet.text = "Thêm hoạt ảnh"

            }
            btnCustom.setOnClickListener {
                val bottomSheetDialog = BottomSheet2()
                val bundle = Bundle()
                bundle.putSerializable("ani", ani)
                bottomSheetDialog.arguments = bundle
                bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)

            }
            val da0 = DataBase.getDatabase(this@ShowActivity).dao()
            btnSet.setOnClickListener {
                if (boolean) {
                    da0.insert(ani)
                    finish()
                } else {
                    val a = da0.getAnimation()
                    if (a != null) {
                        a.selected = false
                        da0.update(a)
                    }

                    ani.selected = true
                    da0.update(ani)
                    try {
                        val serviceIntent = Intent(this@ShowActivity, JobServices::class.java)
                        startService(serviceIntent)
                        Toast.makeText(
                            applicationContext,
                            "Đặt hoạt ảnh thành công",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } catch (e: Exception) {
                        Log.e("ssssssss1", e.toString())
                    }
                }

            }
            TimeUtil.setTime(txtDate, txtTime)
            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val batteryPercentage =
                batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            txtLevel.text = "$batteryPercentage % "
            ani = intent.getSerializableExtra("ani") as Animation
            txtDate.isVisible = ani.dateTime
            txtLevel.isVisible = ani.lever
            txtTime.isVisible = ani.dateTime
            img.isVisible = ani.type != "video"
            vd.isVisible = ani.type == "video"
            AnimationHelper.startAnimationForImageOrVideo(ani, img, vd)
            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    with(binding) {
                        if (intent != null) {
                            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                            txtLevel.text = (level * 100 / scale.toFloat()).toString() + " % "
                        }
                    }
                }
            }

            intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

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

    override fun sendData(index: Int, boolean: Boolean) {
        Log.d("sendData", "$index   $boolean")
        when (index) {
            1 -> {
                ani.repeat = boolean
            }

            2 -> {
                ani.lever = boolean
                binding.txtLevel.isVisible = boolean
            }

            3 -> {
                ani.dateTime = boolean
                binding.txtDate.isVisible = boolean
                binding.txtTime.isVisible = boolean

            }
        }
    }

    override fun confirm() {
        DataBase.getDatabase(this).dao().update(ani)
    }
}