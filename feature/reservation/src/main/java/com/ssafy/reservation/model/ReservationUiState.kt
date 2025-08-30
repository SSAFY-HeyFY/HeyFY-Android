package com.ssafy.reservation.model

sealed class ReservationUiState {
    data object Init : ReservationUiState()
    data object Loading : ReservationUiState()
    data object Success : ReservationUiState()
    data class Error(val mag: String) : ReservationUiState()
}
