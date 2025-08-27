package com.ssafy.login.domain

import com.ssafy.login.domain.model.Token

interface LoginRepository {

    suspend fun login(
        studentId: String,
        password: String,
    ): Result<Token>

    suspend fun checkPin(
        pinNumber: String,
    ): Result<CheckPin>

    suspend fun refreshSid(
        pinNumber: String,
    ): Result<RefreshSid>

}

