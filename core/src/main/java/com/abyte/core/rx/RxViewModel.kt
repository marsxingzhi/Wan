package com.abyte.core.rx

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class RxViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    // ViewModel被销毁时，会调用
    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun register(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}