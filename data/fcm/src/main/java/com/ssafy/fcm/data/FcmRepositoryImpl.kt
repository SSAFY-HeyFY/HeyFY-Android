package com.ssafy.fcm.data

import com.ssafy.fcm.domain.FcmRepository
import com.ssafy.network.utils.ApiUtils.safeApiCall
import javax.inject.Inject

class FcmRepositoryImpl @Inject constructor(
    private val fcmDataSource: FcmDataSource,
) : FcmRepository {

    override suspend fun registerFcmToken(fcmToken: String): Result<Unit> {
        return safeApiCall(
            apiCall = { fcmDataSource.registerFcmToken(fcmToken) },
            convert = { }
        )
    }

    override suspend fun deleteFcmToken(fcmToken: String): Result<Unit> {
        return safeApiCall(
            apiCall = { fcmDataSource.deleteFcmToken(fcmToken) },
            convert = { }
        )
    }
}
