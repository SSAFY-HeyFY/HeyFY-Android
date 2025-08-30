package com.ssafy.reservation.model

sealed class ReservationUiEvent {
    data object Init : ReservationUiEvent()
    data object Back : ReservationUiEvent()
    data object Reserve : ReservationUiEvent()
    data class UpdateExchangeAmount(val balance: String) : ReservationUiEvent()
    data class UpdatePinNumber(val pinNumber: String) : ReservationUiEvent()
    data class UpdateCheckPin(val checkPin: Boolean) : ReservationUiEvent()
    data class UpdateShowPasswordBottomSheet(val isShow: Boolean) : ReservationUiEvent()
    data class UpdateTargetRate(val targetRate: String) : ReservationUiEvent()
}
