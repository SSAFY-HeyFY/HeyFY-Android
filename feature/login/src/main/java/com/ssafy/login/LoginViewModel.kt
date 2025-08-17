package com.ssafy.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {

    fun goToSignUp() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.SignUp(),
            )
        }
    }
}
