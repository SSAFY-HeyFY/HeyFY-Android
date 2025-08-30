package com.ssafy.exchange.model

sealed class ExchangeUiEvent {
    data object Init : ExchangeUiEvent()
    data object Back : ExchangeUiEvent()
    data object Exchange : ExchangeUiEvent()
    data object UpdateIsUSD : ExchangeUiEvent()
    data class UpdateExchangeAmount(val balance: String) : ExchangeUiEvent()
    data class UpdatePinNumber(val pinNumber: String) : ExchangeUiEvent()
    data class UpdateCheckPin(val checkPin: Boolean) : ExchangeUiEvent()
    data class UpdateShowPasswordBottomSheet(val isShow: Boolean) : ExchangeUiEvent()
    data object ClickReservation : ExchangeUiEvent()
}
