package com.abyte.wan.login.api

import com.abyte.core.response.BaseResponse
import com.abyte.core.services.RETROFIT
import com.abyte.wan.login.model.User
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<BaseResponse<User>>


    @GET("user/logout/json")
    fun logout(): Observable<BaseResponse<Any>>
}

object LoginService : LoginApi by RETROFIT.create(LoginApi::class.java)