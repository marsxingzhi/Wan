package com.abyte.wan.login.model

import androidx.annotation.IntDef

/**
 * 为什么要以注解的方式定义各个类型，而不是直接定义成 static final int；
 * 或者为什么不使用枚举？
 * 如果枚举是整数类型等，在Android中可以使用@IntDef代替枚举
 */
@IntDef(LoginType.ACCOUNT, LoginType.MOBILE, LoginType.WX, LoginType.QQ)
annotation class LoginType {

    companion object {

        const val ACCOUNT = 1

        const val MOBILE = 2

        const val WX = 3

        const val QQ = 4

    }
}