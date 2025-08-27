package com.ssafy.login.domain

import javax.inject.Inject

class RefreshSidUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        pinNumber: String,
    ): Result<RefreshSid> {
        return loginRepository.refreshSid(pinNumber)
    }
}
