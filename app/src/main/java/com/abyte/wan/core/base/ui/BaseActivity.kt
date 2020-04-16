package com.abyte.wan.core.base.ui

import android.os.Bundle
import android.view.WindowManager
import com.abyte.wan.R
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseActivity : DaggerAppCompatActivity() {


    private val compositeDisposable = CompositeDisposable()

    protected fun register(subscription: Disposable) {
        compositeDisposable.add(subscription)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        StatusBarUtil.hideStatusBar(this)
    }

    override fun onStart() {
        super.onStart()
        // 沉浸式布局
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}