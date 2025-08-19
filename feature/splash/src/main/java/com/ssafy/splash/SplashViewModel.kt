package com.ssafy.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.data_store.TokenManager
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: HeyFYAppNavigator,
    private val tokenManager: TokenManager,
): ViewModel() {

    private val _isAnimationCompleted = MutableStateFlow(false)
    val isAnimationCompleted: StateFlow<Boolean> = _isAnimationCompleted.asStateFlow()

    val token: StateFlow<String?> = tokenManager.getAccessToken()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "",
        )

    fun goToLogin() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Login(),
                isBackStackCleared = true
            )
        }
    }

    fun goToMain() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Main(),
                isBackStackCleared = true
            )
        }
    }

    fun updateIsAnimationCompleted() {
        _isAnimationCompleted.update {
            true
        }
    }
}
