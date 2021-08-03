package com.application.News.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.application.News.R

class LoadingDialog(context: Context) {

    private var alertDialog: AlertDialog = AlertDialog.Builder(context)
        .setCancelable(false)
        .setView(R.layout.progress_load).create()

    init {
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun show(){
        if(!isShowing())
            alertDialog.show()
    }

    fun isShowing(): Boolean = alertDialog.isShowing

    fun dismiss(){
        if(isShowing())
            alertDialog.dismiss()
    }
}