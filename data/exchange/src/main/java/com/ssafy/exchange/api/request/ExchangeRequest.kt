package com.ssafy.exchange.api.request

data class ExchangeRequest(
    val transactionBalance: Long,
    val pinNumber: String,
)

