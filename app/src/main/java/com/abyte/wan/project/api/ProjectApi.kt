package com.abyte.wan.project.api

import com.abyte.core.response.BaseResponse
import com.abyte.wan.knowledge.model.ChapterData
import io.reactivex.Observable
import retrofit2.http.GET

interface ProjectApi {

    @GET("project/tree/json")
    fun getProjectTabs(): Observable<BaseResponse<List<ChapterData>>>
}