package com.abyte.wan.main.api

import com.abyte.core.response.BaseResponse
import com.abyte.wan.main.model.ArticlePage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApi {


    /**
     * 首页文章列表
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    fun getArticlePages(@Path("page") page: Int): Observable<BaseResponse<ArticlePage>>
}