package com.ssafy.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.data_store.TokenManager
import com.ssafy.login.domain.CheckPinUseCase
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
    private val checkPinUseCase: CheckPinUseCase,
    private val tokenManager: TokenManager,
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {

    private val _pinNumber = MutableStateFlow("")
    val pinNumber = _pinNumber.asStateFlow()

    private val _isPasswordError = MutableStateFlow(false)
    val isPasswordError = _isPasswordError.asStateFlow()


    fun updatePinNumber(number: String) {
        _pinNumber.value = number
    }

    fun updateIsPasswordError(isError: Boolean) {
        _isPasswordError.value = isError
    }

    fun checkPin() {
        viewModelScope.launch {
            checkPinUseCase(pinNumber.value)
                .onSuccess {
                    if (it.correct) {
                        refreshSid()
                    } else {
                        updateIsPasswordError(true)
                    }
                }
        }
    }

    fun refreshSid() {
        viewModelScope.launch {
            refreshSidUseCase(pinNumber.value)
                .onSuccess {
                    tokenManager.deleteSid()
                    tokenManager.saveSid(it.sid)
                    goToHome()
                }
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
}
