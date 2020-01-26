package com.abyte.wan.rank.model

import android.os.Parcelable
import com.abyte.core.anno.PoKo
import kotlinx.android.parcel.Parcelize

@PoKo
@Parcelize
data class RankPage(
    var curPage: Int,
    var datas: ArrayList<RankInfo>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
) : Parcelable


@PoKo
@Parcelize
data class RankInfo(
    var coinCount: Int,
    var level: Int,
    var rank: Int,
    var userId: Int,
    var username: String
) : Parcelable