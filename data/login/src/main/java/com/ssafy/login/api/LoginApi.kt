package com.ssafy.login.api

import com.ssafy.login.api.request.LoginRequest
import com.ssafy.login.api.request.CheckPinRequest
import com.ssafy.login.api.request.RefreshSidRequest
import com.ssafy.login.api.response.LoginResponse
import com.ssafy.login.api.response.CheckPinResponse
import com.ssafy.login.api.response.RefreshSidResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/auth/signin")
    suspend fun login(
        @Body request: LoginRequest,
    ): Response<LoginResponse>

    @POST("/auth/checkpin")
    suspend fun checkPin(
        @Body request: CheckPinRequest,
    ): Response<CheckPinResponse>

    @POST("/auth/sid/refresh")
    suspend fun refreshSid(
        @Body request: RefreshSidRequest,
    ): Response<RefreshSidResponse>
}

