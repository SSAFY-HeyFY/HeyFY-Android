package com.ssafy.exchange.api.request

data class ExchangeReservationCancelRequest(
    val reservationId: Int,
    val pinNumber: String
)
