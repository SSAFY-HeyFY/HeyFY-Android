package com.ssafy.send_money.model

sealed class SendMoneyUiState {
    data object Init : SendMoneyUiState()
    data object Loading : SendMoneyUiState()
    data object Success : SendMoneyUiState()
    data class Error(val mag: String) : SendMoneyUiState()
}
