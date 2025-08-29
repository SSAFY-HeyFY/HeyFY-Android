package com.ssafy.login.data

import com.ssafy.common.utils.ApiUtils.safeApiCall
import com.ssafy.login.domain.LoginRepository
import com.ssafy.login.domain.CheckPin
import com.ssafy.login.domain.RefreshSid
import com.ssafy.login.domain.model.Token
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository {
    override suspend fun login(
        studentId: String,
        password: String,
    ): Result<Token> {
        return safeApiCall(
            apiCall = {
                loginDataSource.login(
                    studentId = studentId,
                    password = password
                )
            },
            convert = { response ->
                Token(response.accessToken, response.refreshToken, response.sid)
            }
        )
    }

    override suspend fun checkPin(
        pinNumber: String,
    ): Result<CheckPin> {
        return safeApiCall(
            apiCall = {
                loginDataSource.checkPin(
                    pinNumber = pinNumber
                )
            },
            convert = { response ->
                CheckPin(
                    txnToken = response.txnToken ?: "",
                    correct = response.correct
                )
            }
        )
    }

    override suspend fun refreshSid(
        pinNumber: String,
    ): Result<RefreshSid> {
        return safeApiCall(
            apiCall = {
                loginDataSource.refreshSid(
                    pinNumber = pinNumber
                )
            },
            convert = { response ->
                RefreshSid(
                    sid = response.sid ?: "",
                    isCorrect = response.isCorrect
                )
            }
        )
    }
}

