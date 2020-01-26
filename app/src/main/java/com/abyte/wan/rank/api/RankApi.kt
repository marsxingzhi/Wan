package com.abyte.wan.rank.api

import com.abyte.core.response.BaseResponse
import com.abyte.wan.rank.model.RankPage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RankApi {

    @GET("coin/rank/{page}/json")
    fun getRankList(@Path("page") page: Int): Observable<BaseResponse<RankPage>>
}