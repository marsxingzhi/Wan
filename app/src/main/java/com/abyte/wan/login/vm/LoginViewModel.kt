package com.abyte.wan.login.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abyte.core.exception.ApiErrorCode
import com.abyte.core.exception.ApiException
import com.abyte.core.ext.log
import com.abyte.core.rx.RxViewModel
import com.abyte.wan.login.repo.LoginRepository
import com.abyte.wan.user.UserManager

class LoginViewModel(private val repo: LoginRepository) : RxViewModel() {

    private val loginResult = MutableLiveData<Boolean>()

    private val errorResult = MutableLiveData<Pair<Int, String>>()

    fun getLoginResult(): LiveData<Boolean> = loginResult

    fun getErrorResult(): LiveData<Pair<Int, String>> = errorResult


    fun login(username: String, password: String) {
        log("username = $username, password = $password")
        register(repo.login(username, password).subscribe({
            log("login---success---username = ${it.username}")
            loginResult.value = true
            UserManager.notifyLogin(it)
            // 保存
        }, {
            val exception = it as ApiException
            log("login---fail = $exception")
            errorResult.value = Pair(exception.code, exception.msg ?: "登录失败")
        }))
    }


    fun checkUsernameAndPassword(username: String?, password: String?): Boolean {
        return check(!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            "用户名或密码错误"
        }
    }


    private fun check(value: Boolean, lazyMessage: () -> Any): Boolean {
        if (!value) {
            val message = lazyMessage()
            errorResult.value =
                Pair(first = ApiErrorCode.ERROR_USERNAME_OR_PASSWORD, second = message.toString())
        }
        return value
    }
}