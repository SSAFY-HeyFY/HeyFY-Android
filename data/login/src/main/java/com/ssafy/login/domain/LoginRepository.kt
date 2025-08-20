package com.ssafy.login.domain

interface LoginRepository {

    suspend fun login(
        studentId: String,
        password: String,
    ): Result<Pair<String, String>>

}
