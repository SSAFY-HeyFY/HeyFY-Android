package com.ssafy.finance.model

sealed class FinanceUiState {
    data object Init : FinanceUiState()
    data object Loading : FinanceUiState()
    data object Success : FinanceUiState()
    data class Error(val mag: String) : FinanceUiState()
}
