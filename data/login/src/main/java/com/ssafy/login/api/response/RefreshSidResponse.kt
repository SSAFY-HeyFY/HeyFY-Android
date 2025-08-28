package com.ssafy.login.api.response

data class RefreshSidResponse(
    val sid: String,
    val correct: Boolean,
)
