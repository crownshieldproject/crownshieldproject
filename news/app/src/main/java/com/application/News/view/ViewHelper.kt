package com.application.News.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


object ViewHelper {

    fun createView(context: Context, layoutResId: Int, parent: ViewGroup? = null): View =
        LayoutInflater.from(context).inflate(layoutResId, parent)
}