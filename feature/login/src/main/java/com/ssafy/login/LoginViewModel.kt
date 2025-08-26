package com.ssafy.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.data_store.TokenManager
import com.ssafy.login.domain.LoginUseCase
import com.ssafy.login.model.LoginUiState
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenManager: TokenManager,
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Init)
    val uiState = _uiState.asStateFlow()

    fun login(
        studentId: String,
        password: String,
    ) {
        // TODO : 하드 코딩 제거
        viewModelScope.launch {
            loginUseCase(
                studentId = "12345678",
                password = "StrongPassword123!"
            ).onSuccess { (accessToken, refreshToken) ->
                tokenManager.saveAccessToken(accessToken)
                tokenManager.saveRefreshToken(refreshToken)
                goToAccount()
                updateUiState(LoginUiState.Success)
            }.onFailure(::handleFailure)
        }
    }

    fun goToMain() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Main(),
                isBackStackCleared = true,
            )
        }
    }

    fun goToAccount() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Account(),
                isBackStackCleared = true,
            )
        }
    }

    private fun updateUiState(state: LoginUiState) {
        _uiState.value = state
    }

    private fun handleFailure(throwable: Throwable) {
        updateUiState(LoginUiState.Error(
            mag = throwable.message ?: "An unexpected error has occurred. Please contact the administrator"
        ))
    }
}
