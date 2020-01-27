package com.abyte.wan.knowledge.model

import android.os.Parcelable
import com.abyte.core.anno.PoKo
import com.abyte.wan.main.model.Article
import kotlinx.android.parcel.Parcelize

@PoKo
@Parcelize
data class KnowledgeNavigationData(
    var name: String,
    var cid: Int,
    var articles: ArrayList<Article>
) : Parcelable