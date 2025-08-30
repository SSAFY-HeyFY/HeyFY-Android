package com.ssafy.exchange_history.model

sealed class ExchangeHistoryUiEvent {
    data object Init : ExchangeHistoryUiEvent()
    data object Back : ExchangeHistoryUiEvent()
}
