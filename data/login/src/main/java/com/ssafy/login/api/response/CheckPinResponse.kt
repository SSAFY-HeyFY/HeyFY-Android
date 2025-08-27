package com.ssafy.login.api.response

data class CheckPinResponse(
    val txnToken: String?,
    val correct: Boolean
)
