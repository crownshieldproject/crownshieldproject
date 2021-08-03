package com.application.News.util

interface UtilInterface {

    fun toast(message: String)
    fun requestPermission(vararg permissions: String, result: (permissionGranted: Boolean) -> Unit)
    fun showProgress()
    fun isProgressShowing(): Boolean
    fun dismissProgress()
}