package com.abyte.wan.login.repo

import com.abyte.core.utils.RxUtil
import com.abyte.wan.login.api.LoginApi
import com.abyte.wan.login.model.User
import io.reactivex.Observable

class LoginRepository(private val loginApi: LoginApi) {

    fun login(username: String, password: String): Observable<User> {
        return loginApi.login(username, password)
            .compose(RxUtil.applySchedulers())
            .compose(RxUtil.handleResult())
    }
}