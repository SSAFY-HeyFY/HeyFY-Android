package com.ssafy.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.data_store.TokenManager
import com.ssafy.login.domain.LoginUseCase
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenManager: TokenManager,
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {


    fun login() {
        viewModelScope.launch {
            loginUseCase(
                studentId = "19111239",
                password = "StrongPassword123!"
            ).onSuccess { (accessToken, refreshToken) ->
                tokenManager.saveAccessToken(accessToken)
                tokenManager.saveRefreshToken(refreshToken)
                goToAccount()
            }.onFailure {

            }
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

    fun goToSignUp() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.SignUp(),
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
}
