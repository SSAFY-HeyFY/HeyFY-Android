package com.ssafy.exchange_history.model

sealed class ExchangeHistoryUiState {
    data object Init : ExchangeHistoryUiState()
    data object Loading : ExchangeHistoryUiState()
    data object Success : ExchangeHistoryUiState()
    data class Error(val mag: String) : ExchangeHistoryUiState()
}
