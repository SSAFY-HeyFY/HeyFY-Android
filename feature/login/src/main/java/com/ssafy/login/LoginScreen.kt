package com.ssafy.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.common.ui.ErrorPopUp
import com.ssafy.login.model.LoginUiState

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var errorMessage by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Error -> {
                errorMessage = (uiState as LoginUiState.Error).mag
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        bottomBar = {
            LoginBottomSection(
                modifier = Modifier.imePadding(),
                onClick = {
                    viewModel.login(
                        studentId = studentId,
                        password = password,
                    )
                }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            LoginContent(
                studentId = studentId,
                password = password,
                passwordVisible = passwordVisible,
                updateStudentId = { studentId = it },
                updatePassword = { password = it },
                updatePasswordVisible = { passwordVisible = it },
                login = {
                    viewModel.login(
                        studentId = studentId,
                        password = password,
                    )
                }
            )
        }
    }

    if (errorMessage.isNotEmpty()) {
        keyboardController?.hide()
        ErrorPopUp(
            message = errorMessage,
            onDismiss = {
                errorMessage = ""
            }
        )
    }
}
