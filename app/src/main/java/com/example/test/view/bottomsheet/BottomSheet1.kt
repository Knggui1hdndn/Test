package com.example.test.view.bottomsheet



import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheet1(private var context: Context) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 //        onViewCreatedđược gọi ngay sau onCreateView(phương thức bạn khởi tạo và tạo tất cả các đố
//                i tượng của mình, bao gồm cả của bạn TextView)
        super.onViewCreated(view, savedInstanceState)
        (view.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }
}