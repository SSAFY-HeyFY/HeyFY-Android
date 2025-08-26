package com.ssafy.fcm.domain

import javax.inject.Inject

class DeleteFcmTokenUseCase @Inject constructor(
    private val fcmRepository: FcmRepository
) {
    suspend operator fun invoke(fcmToken: String): Result<Unit> {
        return fcmRepository.deleteFcmToken(fcmToken)
    }
}
