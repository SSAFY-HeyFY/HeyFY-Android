package com.ssafy.exchange.domain.model

data class ExchangeReservationHistory(
    val reservationId: Int,
    val currency: String,
    val amount: Int,
    val baseExchangeRate: Double,
    val createdAt: String,
    val exchangeCompleted: Boolean,
    val canceled: Boolean
)
