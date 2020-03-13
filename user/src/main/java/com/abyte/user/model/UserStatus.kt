package com.abyte.user.model

import androidx.annotation.IntDef


@IntDef(UserStatus.LOGIN, UserStatus.LOGOUT, UserStatus.UPDATE)
annotation class UserStatus {

    companion object {

        const val LOGIN = 1

        const val LOGOUT = 2

        const val UPDATE = 3
    }
}