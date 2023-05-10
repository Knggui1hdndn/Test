package com.example.test.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.test.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {
    //    private val myLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK) {
//                // handle the result
//
//            }
//        }
    private var check: Boolean = false
    private var check2: Boolean = false
    private lateinit var pow: PowerManager
    private lateinit var binding: ActivitySettingsBinding

    @SuppressLint("BatteryLife")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pow = getSystemService(Context.POWER_SERVICE) as PowerManager
        with(binding) {
            content1.isVisible = check
            content2.isVisible = check
            imgDropDown.setOnClickListener {
                check = !check
                content1.isVisible = check
            }
            imgDropDown2.setOnClickListener {
                check2 = !check2
                content2.isVisible = check2
            }

            //bật hển thị trên ứng dụng khác
            swt1.setOnClickListener {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
            swt2.setOnClickListener {
                //bật không hạn chế pin
                val intent = Intent(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
            btnSet.setOnClickListener {
                val builder = AlertDialog.Builder(this@Settings )
                builder.setTitle("Thông báo")
                builder.setMessage("Đi đến cài đặt khác bật \n+Hiển thị trên màn hình khóa\n+Hiển thị cửa sổ pop-up khi chạy trong nền\n+Hiển thi cửa sổ pop-up")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Đi") { dialog, id ->
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:$packageName")
                    )
                    startActivity(intent)
                }
                builder.show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            swt1.isChecked = Settings.canDrawOverlays(applicationContext)
            swt2.isChecked = pow.isIgnoringBatteryOptimizations(packageName)
        }
        Log.d("onResume", "onResume")
    }
}