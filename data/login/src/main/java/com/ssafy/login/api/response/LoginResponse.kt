package com.ssafy.login.api.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)
