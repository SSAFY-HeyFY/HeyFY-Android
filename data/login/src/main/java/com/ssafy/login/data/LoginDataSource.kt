package com.ssafy.login.data

import com.ssafy.login.api.response.LoginResponse
import com.ssafy.login.api.response.CheckPinResponse
import com.ssafy.login.api.response.RefreshSidResponse
import retrofit2.Response

interface LoginDataSource {
    suspend fun login(
        studentId: String,
        password: String,
    ): Response<LoginResponse>

    suspend fun checkPin(
        pinNumber: String,
    ): Response<CheckPinResponse>

    suspend fun refreshSid(
        pinNumber: String,
    ): Response<RefreshSidResponse>
}

