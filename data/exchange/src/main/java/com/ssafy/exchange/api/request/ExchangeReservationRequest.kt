package com.ssafy.exchange.api.request

data class ExchangeReservationRequest(
    val transactionBalance: Double,
    val currency: String,
    val pinNumber: String,
    val baseExchangeRate: Double
)
