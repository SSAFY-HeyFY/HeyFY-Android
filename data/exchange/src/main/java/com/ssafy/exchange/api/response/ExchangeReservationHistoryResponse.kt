package com.ssafy.exchange.api.response

data class ExchangeReservationHistoryResponse(
    val data: List<ExchangeReservationHistoryItem>
) {
    data class ExchangeReservationHistoryItem(
        val reservationId: Int,
        val currency: String,
        val amount: Int,
        val baseExchangeRate: Double,
        val createdAt: String,
        val exchangeCompleted: Boolean,
        val canceled: Boolean
    )
}
