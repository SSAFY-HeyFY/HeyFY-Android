package com.ssafy.login.domain.model

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val sid: String,
)
