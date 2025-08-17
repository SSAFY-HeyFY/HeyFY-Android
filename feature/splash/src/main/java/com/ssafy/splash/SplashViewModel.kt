package com.ssafy.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: HeyFYAppNavigator
): ViewModel() {

    fun goToLogin() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Login(),
                isBackStackCleared = true
            )
        }
    }
}
