package com.ssafy.account.api.request

data class AccountCheckRequest(
    val accountNo: String,
    val authCode: String
)
