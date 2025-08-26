package com.ssafy.fcm.api

import com.ssafy.fcm.api.request.FcmTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface FcmApi {

    @POST("/api/users/fcm-token")
    suspend fun registerFcmToken(
        @Body request: FcmTokenRequest
    ): Response<Unit>

    @HTTP(method = "DELETE", path = "/api/users/tokens/public", hasBody = true)
    suspend fun deleteFcmToken(
        @Body request: FcmTokenRequest
    ): Response<Unit>
}
