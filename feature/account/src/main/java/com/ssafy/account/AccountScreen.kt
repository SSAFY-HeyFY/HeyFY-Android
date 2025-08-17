package com.ssafy.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.common.ui.DetailTopBar
import kotlinx.coroutines.delay

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel<AccountViewModel>(),
) {
    var currentStep by remember { mutableIntStateOf(1) }
    var accountNumber by remember { mutableStateOf("") }
    var verificationCode by remember { mutableStateOf(listOf("", "", "", "")) }
    var timeRemaining by remember { mutableIntStateOf(180) }
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(currentStep) {
        if (currentStep == 2) {
            while (timeRemaining > 0) {
                delay(1000)
                timeRemaining--
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            DetailTopBar(
                title = "Account",
                isBack = false,
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .imePadding()
                    .padding(20.dp)
            ) {
                if (currentStep == 1) {
                    AccountRegistrationBottomBar(
                        onClick = { currentStep = 2 }
                    )
                } else {
                    AccountVerificationBottomBar(
                        verificationCode = verificationCode,
                        onClick = {
                            keyboardController?.hide()
                            viewModel.goToMain()
                        }
                    )
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            ProgressIndicator(currentStep = currentStep)

            Spacer(modifier = Modifier.height(40.dp))

            if (currentStep == 1) {
                AccountRegistrationStep(
                    accountNumber = accountNumber,
                    onAccountNumberChange = { accountNumber = it },
                )
            } else {
                AccountVerificationStep(
                    accountNumber = accountNumber,
                    verificationCode = verificationCode,
                    onVerificationCodeChange = { index, value ->
                        verificationCode = verificationCode.toMutableList().apply {
                            this[index] = value
                        }
                    },
                    timeRemaining = timeRemaining,
                    onResendCode = { timeRemaining = 167 },
                )
            }
        }
    }
}
