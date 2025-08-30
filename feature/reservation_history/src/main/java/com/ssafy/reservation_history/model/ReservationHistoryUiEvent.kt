package com.ssafy.reservation_history.model

sealed class ReservationHistoryUiEvent {
    object LoadReservationHistory : ReservationHistoryUiEvent()
    data class CancelReservation(val reservationId: Int) : ReservationHistoryUiEvent()

    data class UpdateShowPasswordBottomSheet(val show: Boolean) : ReservationHistoryUiEvent()
    data class UpdatePinNumber(val pinNumber: String) : ReservationHistoryUiEvent()
    data class UpdateCheckPin(val checkPin: Boolean) : ReservationHistoryUiEvent()

}
