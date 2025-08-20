package com.ssafy.login.data

import com.ssafy.login.api.LoginApi
import com.ssafy.login.api.request.LoginRequest
import com.ssafy.login.api.response.LoginResponse
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
}
