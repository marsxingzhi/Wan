package com.abyte.wan.wechat.api

import com.abyte.core.response.BaseResponse
import com.abyte.wan.knowledge.model.ChapterData
import io.reactivex.Observable
import retrofit2.http.GET

interface WechatApi {

    @GET("wxarticle/chapters/json")
    fun getWechatArticles(): Observable<BaseResponse<List<ChapterData>>>
}