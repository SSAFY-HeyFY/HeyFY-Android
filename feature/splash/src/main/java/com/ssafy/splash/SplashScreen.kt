package com.ssafy.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel<SplashViewModel>(),
) {

    val token by viewModel.token.collectAsStateWithLifecycle()
    val isAnimationCompleted by viewModel.isAnimationCompleted.collectAsStateWithLifecycle()

    LaunchedEffect(isAnimationCompleted) {
        if(isAnimationCompleted) {
            if(token.isNullOrEmpty()) {
                viewModel.goToLogin()
            } else {
                viewModel.goToMain()
            }
        }
    }

    SplashContent(
        isHome = token.isNullOrEmpty().not(),
        updateAnimationCompleted = viewModel::updateIsAnimationCompleted
    )
}
