package com.abyte.wan.login.model

import android.os.Bundle
import android.os.Parcelable
import com.abyte.core.anno.PoKo
import kotlinx.android.parcel.Parcelize


@PoKo
@Parcelize
data class LoginInfo(@LoginType val loginType: Int, val bundle: Bundle?) : Parcelable