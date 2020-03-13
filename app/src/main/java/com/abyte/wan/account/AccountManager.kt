package com.abyte.wan.account

import com.abyte.core.response.SUCCESS
import com.abyte.user.manager.UserManager
import com.abyte.wan.login.api.LoginService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object AccountManager {


    fun isLogin() = UserManager.currentUser != null


    fun logout() = LoginService.logout()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext {
            if (it.errorCode == SUCCESS) {
                UserManager.notifyLogout()
            }
        }
}