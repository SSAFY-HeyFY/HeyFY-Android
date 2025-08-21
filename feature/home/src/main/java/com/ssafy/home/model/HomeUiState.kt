package com.ssafy.home.model

sealed class HomeUiState {
    data object Init : HomeUiState()
    data object Loading : HomeUiState()
    data object Success : HomeUiState()
    data class Error(val mag: String) : HomeUiState()
}
