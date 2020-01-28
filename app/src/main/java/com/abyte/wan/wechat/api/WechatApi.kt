package com.abyte.wan.wechat.api

import com.abyte.core.response.BaseResponse
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.main.model.ArticlePage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WechatApi {

    @GET("wxarticle/chapters/json")
    fun getWechatArticles(): Observable<BaseResponse<List<ChapterData>>>

    @GET("wxarticle/list/{authorId}/{page}/json")
    fun getWechatArticlesOfAuthor(@Path("authorId") authorId: Int, @Path("page") page: Int):
            Observable<BaseResponse<ArticlePage>>
}