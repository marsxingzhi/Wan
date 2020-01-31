package com.abyte.wan.knowledge.api

import com.abyte.core.response.BaseResponse
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.knowledge.model.KnowledgeNavigationData
import com.abyte.wan.main.model.ArticlePage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KnowledgeApi {


    @GET("tree/json")
    fun getChapterList(): Observable<BaseResponse<List<ChapterData>>>

    @GET("navi/json")
    fun getKnowledgeNaviList(): Observable<BaseResponse<List<KnowledgeNavigationData>>>

    @GET("article/list/{page}/json")
    fun getKnowledgeArticlesByChildrenId(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseResponse<ArticlePage>>
}