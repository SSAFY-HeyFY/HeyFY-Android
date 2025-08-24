package com.ssafy.login.data

import com.ssafy.login.api.response.LoginResponse
import retrofit2.Response

interface LoginDataSource {
    suspend fun login(
        studentId: String,
        password: String,
    ): Response<LoginResponse>
}

