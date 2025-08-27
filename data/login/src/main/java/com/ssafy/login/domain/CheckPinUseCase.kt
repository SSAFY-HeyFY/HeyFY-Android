package com.ssafy.login.domain

import javax.inject.Inject

class CheckPinUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        pinNumber: String,
    ): Result<CheckPin> {
        return loginRepository.checkPin(pinNumber)
    }
}
