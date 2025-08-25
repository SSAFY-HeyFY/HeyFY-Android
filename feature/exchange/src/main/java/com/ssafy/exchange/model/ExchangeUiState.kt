package com.ssafy.exchange.model

sealed class ExchangeUiState {
    data object Init : ExchangeUiState()
    data object Loading : ExchangeUiState()
    data object Success : ExchangeUiState()
    data class Error(val mag: String) : ExchangeUiState()
}
