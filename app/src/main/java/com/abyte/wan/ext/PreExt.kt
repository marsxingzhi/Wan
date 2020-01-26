package com.abyte.wan.ext

import com.abyte.wan.AppContext
import com.abyte.wan.utils.Preference
import kotlin.reflect.jvm.jvmName

inline fun <reified R, T> R.pref(default: T) = Preference(AppContext, "", default, R::class.jvmName)