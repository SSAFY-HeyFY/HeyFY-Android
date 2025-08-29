package com.ssafy.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.data_store.TokenManager
import com.ssafy.common.error.PinAttemptsExceeded
import com.ssafy.common.error.RefreshInProgressError
import com.ssafy.common.error.RefreshTokenExpiredError
import com.ssafy.common.error.SidExpiredError
import com.ssafy.login.domain.RefreshSidUseCase
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val refreshSidUseCase: RefreshSidUseCase,
    private val tokenManager: TokenManager,
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {

    private val _pinNumber = MutableStateFlow("")
    val pinNumber = _pinNumber.asStateFlow()

    private val _isPasswordError = MutableStateFlow(false)
    val isPasswordError = _isPasswordError.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()


    fun updatePinNumber(number: String) {
        _pinNumber.value = number
    }

    fun updateIsPasswordError(isError: Boolean) {
        _isPasswordError.value = isError
    }

    fun updateErrorMessage(message: String) {
        _errorMessage.value = message
    }

    fun refreshSid() {
        viewModelScope.launch {
            refreshSidUseCase(pinNumber.value)
                .onSuccess {
                    if (it.isCorrect) {
                        tokenManager.saveSid(it.sid)
                        goToHome()
                    } else {
                        updateIsPasswordError(true)
                    }
                }
                .onFailure(::handleFailure)
        }
    }

    private fun goToHome() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Main(),
                isBackStackCleared = true
            )
        }
    }

    fun goToLogin() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Login(),
                isBackStackCleared = true,
            )
        }
    }

    private fun handleFailure(throwable: Throwable) {
        when (throwable) {
            is RefreshTokenExpiredError -> {
                goToLogin()
            }

            is SidExpiredError -> {
                goToLogin()
            }

            is RefreshInProgressError -> {

            }

            is PinAttemptsExceeded -> {
                _errorMessage.value = throwable.massage
            }

            else -> {
                goToLogin()
            }
        }
    }
}
