package com.abyte.user.manager

import com.abyte.user.model.User
import com.abyte.user.model.UserEvent
import io.reactivex.Observable

interface IUserManager {

    fun notifyLogin(user: User)

    fun notifyLogout()

//    fun observerLogin(): Observable<User>

    fun observerLoginStatus(): Observable<UserEvent>

}