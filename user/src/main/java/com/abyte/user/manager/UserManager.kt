package com.abyte.user.manager

import com.abyte.core.ext.fromJson
import com.abyte.core.ext.pref
import com.abyte.user.model.User
import com.abyte.user.model.UserEvent
import com.abyte.user.model.UserStatus
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object UserManager : IUserManager {

    private val publishUserStatus = PublishSubject.create<UserEvent>()

    private var userStr by pref("")

    var currentUser: User? = null
        get() {
            if (field == null && userStr.isNotEmpty()) {
                field = Gson().fromJson<User>(userStr)
            }
            return field
        }
        set(value) {
            userStr = if (value == null) "" else Gson().toJson(value)
            field = value
        }


    override fun notifyLogin(user: User) {
        currentUser = user
        publishUserStatus.onNext(UserEvent(user, UserStatus.LOGIN))
    }

    override fun notifyLogout() {
        currentUser = null
        publishUserStatus.onNext(UserEvent(null, UserStatus.LOGOUT))
    }


    override fun observerLoginStatus(): Observable<UserEvent> = publishUserStatus


}

