package com.abyte.wan.knowledge.repo

import com.abyte.core.utils.RxUtil
import com.abyte.wan.knowledge.api.KnowledgeApi
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.knowledge.model.KnowledgeNavigationData
import com.abyte.wan.main.model.ArticlePage
import io.reactivex.Observable

class KnowledgeRepository(private val api: KnowledgeApi) {

    fun getChapterList(): Observable<List<ChapterData>> {
        return api.getChapterList()
            .compose(RxUtil.applySchedulers())
            .compose(RxUtil.handleResult())
    }


    fun getKnowledgeNaviList(): Observable<List<KnowledgeNavigationData>> {
        return api.getKnowledgeNaviList()
            .compose(RxUtil.applySchedulers())
            .compose(RxUtil.handleResult())
    }

    fun getKnowledgeArticlesByChildrenId(page: Int, cid: Int): Observable<ArticlePage> {
        return api.getKnowledgeArticlesByChildrenId(page, cid)
            .compose(RxUtil.applySchedulers())
            .compose(RxUtil.handleResult())
    }
}