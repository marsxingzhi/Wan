package com.abyte.wan.user.model

import com.abyte.wan.login.model.User

class UserEvent(private val user: User?, @UserStatus private val status: Int) {

    fun isLogin() = status == UserStatus.LOGIN

    fun isLogout() = status == UserStatus.LOGOUT

    fun isUpdate() = status == UserStatus.UPDATE


}