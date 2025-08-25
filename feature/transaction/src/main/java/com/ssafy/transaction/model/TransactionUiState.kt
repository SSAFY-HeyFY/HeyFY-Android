package com.ssafy.transaction.model

sealed class TransactionUiState {
    data object Init : TransactionUiState()
    data object Loading : TransactionUiState()
    data object Success : TransactionUiState()
    data class Error(val mag: String) : TransactionUiState()
}
