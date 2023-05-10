package com.example.test

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.util.Calendar
import java.util.Date

class TimeUtil() {

    companion object {
        private val handler = Handler(Looper.getMainLooper())

        fun setTime(txtDate: TextView, txtTime: TextView) {
            val date = Calendar.getInstance()
            var day = ""
            when (date.get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> {
                    day = "Thứ hai"
                }

                Calendar.TUESDAY -> {
                    day = "Thứ ba"
                }

                Calendar.WEDNESDAY -> {
                    day = "Thứ tư"
                }

                Calendar.THURSDAY -> {
                    day = "Thứ năm"
                }

                Calendar.FRIDAY -> {
                    day = "Thứ sau"
                }

                Calendar.SATURDAY -> {
                    day = "Thứ bảy"
                }

                Calendar.SUNDAY -> {
                    day = "Chủ nhật"
                }

            }
            txtDate.text =
                "${day} ${date.get(Calendar.DAY_OF_MONTH)}/${date.get(Calendar.MONTH) + 1} "
            txtTime.text = "${date.get(Calendar.HOUR_OF_DAY)}:${date.get(Calendar.MINUTE)} "
            val runnable = Runnable {
                setTime(txtDate, txtTime)
            }
            handler.postDelayed(runnable, 1000)
        }
    }
}