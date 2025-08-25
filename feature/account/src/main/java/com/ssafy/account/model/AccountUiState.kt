package com.ssafy.account.model

sealed class AccountUiState {
    data object Init : AccountUiState()
    data object Loading : AccountUiState()
    data object Success : AccountUiState()
    data class Error(val mag: String) : AccountUiState()
}
