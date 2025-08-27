package com.ssafy.login.domain

data class CheckPin(
    val txnToken: String,
    val correct: Boolean
)
