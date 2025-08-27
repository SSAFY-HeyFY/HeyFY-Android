package com.ssafy.fcm.data

import retrofit2.Response

interface FcmDataSource {
    suspend fun registerFcmToken(fcmToken: String): Response<Unit>
    suspend fun deleteFcmToken(fcmToken: String): Response<Unit>
}

