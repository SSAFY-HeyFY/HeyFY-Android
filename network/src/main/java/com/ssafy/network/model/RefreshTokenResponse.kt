package com.ssafy.network.model

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
