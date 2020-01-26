package com.abyte.wan.user

import com.abyte.wan.login.model.User
import com.abyte.wan.user.model.UserEvent
import io.reactivex.Observable

interface IUserManager {

    fun notifyLogin(user: User)

    fun notifyLogout()

//    fun observerLogin(): Observable<User>

    fun observerLoginStatus(): Observable<UserEvent>

}