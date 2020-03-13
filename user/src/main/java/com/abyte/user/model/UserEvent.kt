package com.abyte.user.model

import com.abyte.user.model.User
import com.abyte.user.model.UserStatus


class UserEvent(private val user: User?, @UserStatus private val status: Int) {

    fun isLogin() = status == UserStatus.LOGIN

    fun isLogout() = status == UserStatus.LOGOUT

    fun isUpdate() = status == UserStatus.UPDATE


}