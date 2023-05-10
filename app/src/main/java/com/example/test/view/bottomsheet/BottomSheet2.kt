package com.example.test.view.bottomsheet


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.test.R
import com.example.test.databinding.BottomSheet2Binding
import com.example.test.model.Animation
import com.example.test.view.ShowActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheet2( ) :
    BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheet2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //        onViewCreatedđược gọi ngay sau onCreateView(phương thức bạn khởi tạo và tạo tất cả các đố
//                i tượng của mình, bao gồm cả của bạn TextView)
        binding = BottomSheet2Binding.bind(view)
        super.onViewCreated(binding.root, savedInstanceState)

        val activity = activity as ShowActivity
        (view.parent as View).setBackgroundColor(Color.TRANSPARENT)
        val ani = arguments?.getSerializable("ani") as Animation
        var check1 = ani.repeat
        var check2 = ani.lever
        var check3 = ani.dateTime

        with(binding) {
            swt1.isChecked=check1
            swt2.isChecked=check2
            swt3.isChecked=check3
            swt1.setOnClickListener {
                 activity.sendData(1, swt1.isChecked)
            }
            swt2.setOnClickListener {
                 activity.sendData(2,  swt2.isChecked)
            }
            swt3.setOnClickListener {
                check3 != check3
                activity.sendData(3,  swt3.isChecked)
            }
            btnOK.setOnClickListener {
                activity.confirm()
dismiss()
            }
        }
    }
}