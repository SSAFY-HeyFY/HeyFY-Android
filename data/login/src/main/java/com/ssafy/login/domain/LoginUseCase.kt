package com.ssafy.login.domain

import com.ssafy.login.domain.model.Token
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    suspend operator fun invoke(
        studentId: String,
        password: String,
    ): Result<Token> {
        return loginRepository.login(
            studentId = studentId,
            password = password,
        )
    }
}

