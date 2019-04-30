package com.schoolteam.educationmanager.commons

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.schoolteam.educationmanager.R

fun Context.showLoadingDialog() = Dialog(this).apply {
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
    setCancelable(false)
    setContentView(R.layout.querying_progress)
    show()
}