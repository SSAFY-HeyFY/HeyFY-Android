package com.ssafy.login.api

import com.ssafy.login.api.request.LoginRequest
import com.ssafy.login.api.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/auth/signin")
    suspend fun login(
        @Body request: LoginRequest,
    ): Response<LoginResponse>
}

