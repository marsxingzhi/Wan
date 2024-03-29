package com.abyte.wan.wechat.repo

import com.abyte.core.utils.RxUtil
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.main.model.ArticlePage
import com.abyte.wan.wechat.api.WechatApi
import io.reactivex.Observable

class WechatRepository(private val api: WechatApi) {

    fun getWechatArticles(): Observable<List<ChapterData>> {
        return api.getWechatArticles()
            .compose(RxUtil.applySchedulers())
            .compose(RxUtil.handleResult())
    }

    fun getWechatArticlesOfAuthor(authorId: Int, page: Int): Observable<ArticlePage> {
        return api.getWechatArticlesOfAuthor(authorId, page)
            .compose(RxUtil.applySchedulers())
            .compose(RxUtil.handleResult())
    }
}