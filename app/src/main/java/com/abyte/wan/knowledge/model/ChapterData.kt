package com.abyte.wan.knowledge.model

import android.os.Parcelable
import com.abyte.core.anno.PoKo
import kotlinx.android.parcel.Parcelize


@PoKo
@Parcelize
data class ChapterData(
    var name: String,
    var id: Int,
    var order: Int,
    var courseId: Int,
    var parentChapterId: Int,
    var visible: Int,
    var children: ArrayList<ChapterData?>?
) : Parcelable

