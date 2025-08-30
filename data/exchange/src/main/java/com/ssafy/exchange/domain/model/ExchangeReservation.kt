package com.ssafy.exchange.domain.model

data class ExchangeReservation(
    val transactionBalance: Int,
    val currency: String,
    val pinNumber: String,
    val baseExchangeRate: Int
)
