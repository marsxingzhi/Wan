package com.abyte.wan.main.repo

import com.abyte.core.utils.RxUtil
import com.abyte.wan.main.api.MainApi
import com.abyte.wan.main.model.ArticlePage
import io.reactivex.Observable

class MainRepository(private val mainApi: MainApi) {


    fun getArticlePages(pageIndex: Int): Observable<ArticlePage> {
        return mainApi.getArticlePages(pageIndex)
            .compose(RxUtil.applySchedulers())
            .compose(RxUtil.handleResult())
    }
}