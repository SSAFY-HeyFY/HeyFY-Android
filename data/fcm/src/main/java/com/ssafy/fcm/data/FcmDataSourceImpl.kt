package com.ssafy.fcm.data

import com.ssafy.fcm.api.FcmApi
import com.ssafy.fcm.api.request.FcmTokenRequest
import retrofit2.Response
import javax.inject.Inject

class FcmDataSourceImpl @Inject constructor(
    private val fcmApi: FcmApi,
) : FcmDataSource {

    override suspend fun registerFcmToken(fcmToken: String): Response<Unit> {
        return fcmApi.registerFcmToken(FcmTokenRequest(fcmToken))
    }

    override suspend fun deleteFcmToken(fcmToken: String): Response<Unit> {
        return fcmApi.deleteFcmToken(FcmTokenRequest(fcmToken))
    }
}
