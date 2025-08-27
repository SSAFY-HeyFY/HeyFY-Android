package com.ssafy.fcm.domain

interface FcmRepository {
    suspend fun registerFcmToken(fcmToken: String): Result<Unit>
    suspend fun deleteFcmToken(fcmToken: String): Result<Unit>
}

