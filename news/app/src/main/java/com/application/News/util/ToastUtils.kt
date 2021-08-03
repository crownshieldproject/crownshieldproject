package com.application.News.util

import android.content.Context
import android.widget.Toast

object ToastUtils {

    fun show(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}