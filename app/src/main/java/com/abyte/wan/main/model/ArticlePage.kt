package com.abyte.wan.main.model

import android.os.Parcelable
import com.abyte.core.anno.PoKo
import kotlinx.android.parcel.Parcelize


@PoKo
@Parcelize
data class ArticlePage(
    var curPage: Int,
    var datas: ArrayList<Article>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
) : Parcelable


@PoKo
@Parcelize
data class Article(
    var apkLink: String = "",
    var audit: Int,
    var author: String?,
    var chapterId: Int,
    var chapterName: String = "",
    var collect: Boolean,
    var courseId: Int,
    var desc: String = "",
    var envelopePic: String = "",
    var fresh: Boolean,
    var id: Int,
    var link: String = "",
    var niceDate: String = "",
    var niceShareDate: String = "",
    var origin: String,
    var prefix: String,
    var projectLink: String,
    var publishTime: Long,
    var selfVisible: Int,
    var shareDate: Long,
    var shareUser: String,
    var superChapterId: Int,
    var superChapterName: String,
    var tags: ArrayList<ArticleTag?>?,
    var title: String,
    var type: Int,
    var userId: Int,
    var visible: Int,
    var zan: Int,
    var top: Boolean
) : Parcelable


@PoKo
@Parcelize
data class ArticleTag(
    var name: String,
    var url: String
) : Parcelable