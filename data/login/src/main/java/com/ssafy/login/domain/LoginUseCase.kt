package com.ssafy.login.domain

import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    suspend operator fun invoke(
        studentId: String,
        password: String,
    ): Result<Pair<String, String>> {
        return loginRepository.login(
            studentId = studentId,
            password = password,
        )
    }
}

