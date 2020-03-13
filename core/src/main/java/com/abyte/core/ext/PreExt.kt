package com.abyte.core.ext

import com.abyte.core.AppContext
import com.abyte.core.utils.Preference
import kotlin.reflect.jvm.jvmName

inline fun <reified R, T> R.pref(default: T) = Preference(AppContext, "", default, R::class.jvmName)