package com.abyte.wan.core.base.ui

import android.os.Bundle
import com.abyte.core.utils.StatusBarUtil
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
        StatusBarUtil.hideStatusBar(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}