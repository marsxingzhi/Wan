package com.abyte.wan.knowledge.api

import com.abyte.core.response.BaseResponse
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.knowledge.model.KnowledgeNavigationData
import io.reactivex.Observable
import retrofit2.http.GET

interface KnowledgeApi {


    @GET("tree/json")
    fun getChapterList(): Observable<BaseResponse<List<ChapterData>>>

    @GET("navi/json")
    fun getKnowledgeNaviList(): Observable<BaseResponse<List<KnowledgeNavigationData>>>
}