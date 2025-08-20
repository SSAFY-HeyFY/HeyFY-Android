package com.ssafy.login.data

import com.ssafy.login.domain.LoginRepository
import com.ssafy.network.utils.ApiUtils.safeApiCall
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository {
    override suspend fun login(
        studentId: String,
        password: String,
    ): Result<Pair<String, String>> {
        return safeApiCall(
            apiCall = {
                loginDataSource.login(
                    studentId = studentId,
                    password = password
                )
            },
            convert = { response ->
                response.accessToken to response.refreshToken
            }
        )
    }
}
