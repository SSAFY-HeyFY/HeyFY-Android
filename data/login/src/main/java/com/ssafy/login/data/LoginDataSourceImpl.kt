package com.ssafy.login.data

import com.ssafy.login.api.LoginApi
import com.ssafy.login.api.request.LoginRequest
import com.ssafy.login.api.request.CheckPinRequest
import com.ssafy.login.api.request.RefreshSidRequest
import com.ssafy.login.api.response.LoginResponse
import com.ssafy.login.api.response.CheckPinResponse
import com.ssafy.login.api.response.RefreshSidResponse
import retrofit2.Response
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginApi: LoginApi
): LoginDataSource {
    override suspend fun login(
        studentId: String,
        password: String,
    ): Response<LoginResponse> {
        return loginApi.login(
            request = LoginRequest(
                studentId = studentId,
                password = password,
            )
        )
    }

    override suspend fun checkPin(
        pinNumber: String,
    ): Response<CheckPinResponse> {
        return loginApi.checkPin(
            request = CheckPinRequest(
                pinNumber = pinNumber
            )
        )
    }

    override suspend fun refreshSid(
        pinNumber: String,
    ): Response<RefreshSidResponse> {
        return loginApi.refreshSid(
            request = RefreshSidRequest(
                pinNumber = pinNumber
            )
        )
    }
}

