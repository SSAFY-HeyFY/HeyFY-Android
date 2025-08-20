package com.ssafy.login.model


sealed class LoginUiState {
    data object Init : LoginUiState()
    data object Loading : LoginUiState()
    data object Success : LoginUiState()
    data class Error(val mag: String) : LoginUiState()
}
