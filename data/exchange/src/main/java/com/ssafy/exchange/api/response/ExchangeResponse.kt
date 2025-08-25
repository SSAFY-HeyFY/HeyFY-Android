package com.ssafy.exchange.api.response

data class ExchangeResponse(
    val depositAccountBalance: Double,
    val withdrawalAccountBalance: Double,
    val transactionBalance: Double
)
