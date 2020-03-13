package com.abyte.wan

import android.app.Application
import android.content.ContextWrapper
import com.abyte.core.AppContextUtil
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

private lateinit var INSTANCE: Application

class WanApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerWanComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AppContextUtil.setContext(this)
    }
}