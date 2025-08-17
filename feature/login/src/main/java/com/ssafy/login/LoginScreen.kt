package com.ssafy.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>(),
) {

    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        bottomBar = {
            LoginBottomSection(
                modifier = Modifier.imePadding(),
                goToSignUp = { viewModel.goToSignUp() },
                goToAccount = { viewModel.goToAccount() }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoginContent(
                modifier = Modifier
                    .padding(innerPadding),
            )
        }
    }
}
