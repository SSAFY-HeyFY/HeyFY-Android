package com.ssafy.network.api

import com.ssafy.network.model.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/refresh")
    suspend fun refreshToken(
        @Header("Authorization") authorization: String,
        @Header("RefreshToken") refreshToken: String,
    ): Response<RefreshTokenResponse>
}
