package com.ssafy.exchange.model

sealed class ExchangeUiEvent {
    data object Init : ExchangeUiEvent()
    data object Back : ExchangeUiEvent()
    data class Exchange(val balance: Int) : ExchangeUiEvent()
    data object UpdateIsUSD : ExchangeUiEvent()
}
