package com.ssafy.reservation_history.model

import com.ssafy.exchange.domain.model.ExchangeReservationHistory

sealed class ReservationHistoryUiState {
    object Init : ReservationHistoryUiState()
    object Loading : ReservationHistoryUiState()
    data class Success(
        val reservationHistory: List<ExchangeReservationHistory>
    ) : ReservationHistoryUiState()
    data class Error(
        val message: String
    ) : ReservationHistoryUiState()
}
