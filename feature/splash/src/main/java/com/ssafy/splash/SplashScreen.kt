package com.ssafy.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    valModel: SplashViewModel = hiltViewModel<SplashViewModel>(),
) {
    SplashContent(
        isHome = true,
        goToLogin = { valModel.goToLogin() },
        goToMain = { valModel.goToMain() },
    )
}
