package com.application.News.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.danikula.videocache.HttpProxyCacheServer
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : MultiDexApplication(){

    var proxy: HttpProxyCacheServer? = null

    companion object {
        lateinit var instance: App

    }

    fun getProxy(context: Context): HttpProxyCacheServer{
        val app = context.getApplicationContext() as App
        return app.proxy ?: app.newProxy().also { app.proxy = it }
    }

    private fun newProxy(): HttpProxyCacheServer {
        return HttpProxyCacheServer.Builder(this)
            .maxCacheSize((1024 * 1024).toLong())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)

    }



}