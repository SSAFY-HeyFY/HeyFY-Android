package com.ssafy.reservation

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.common.ui.DetailTopBar
import com.ssafy.common.ui.ErrorPopUp
import com.ssafy.common.ui.PasswordBottomSheet
import com.ssafy.reservation.model.ReservationUiEvent
import com.ssafy.reservation.model.ReservationUiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    viewModel: ReservationViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val exchangeAmount by viewModel.exchangeAmount.collectAsStateWithLifecycle()
    val password by viewModel.pinNumber.collectAsStateWithLifecycle()
    val checkPin by viewModel.checkPin.collectAsStateWithLifecycle()
    val showPasswordBottomSheet by viewModel.showPasswordBottomSheet.collectAsStateWithLifecycle()
    val targetRate by viewModel.targetRate.collectAsStateWithLifecycle()

    var errorMessage by remember { mutableStateOf("") }
    val target = targetRate.toDoubleOrNull() ?: 1350.0

    val receivedAmount = exchangeAmount.toDoubleOrNull()?.let {
        it / target
    } ?: 0.0

    val exchangeAmountL = exchangeAmount.toLongOrNull() ?: 0L

    var isPasswordError by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val keyboardController = LocalSoftwareKeyboardController.current

    val isExchangeAmountValid: (amount: Long) -> Boolean = { amount ->
        amount >= 1000 && amount % 10 == 0L
    }


    LaunchedEffect(Unit) {
        if (uiState is ReservationUiState.Init) {
            viewModel.action(ReservationUiEvent.Init)
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is ReservationUiState.Error -> {
                errorMessage = (uiState as ReservationUiState.Error).mag
            }

            else -> {}
        }
    }

    LaunchedEffect(password) {
        if (password.length < 6) return@LaunchedEffect
        viewModel.action(ReservationUiEvent.Reserve)

    }

    LaunchedEffect(checkPin) {
        if (checkPin.not()) {
            isPasswordError = true
        }
    }

    LaunchedEffect(isPasswordError) {
        if (isPasswordError) {
            delay(700)
            viewModel.action(ReservationUiEvent.UpdatePinNumber(""))
            viewModel.action(ReservationUiEvent.UpdateCheckPin(true))
            isPasswordError = false
        }
    }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .imePadding(),
        topBar = {
            DetailTopBar(
                title = "Exchange Reservation",
                onBack = { viewModel.action(ReservationUiEvent.Back) }
            )
        },
        bottomBar = {
            ReservationBottomBar(
                isEnabled = isExchangeAmountValid(exchangeAmountL),
                onClick = {
                    keyboardController?.hide()
                    viewModel.action(ReservationUiEvent.UpdateShowPasswordBottomSheet(true))
                }
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { innerPadding ->

        ReservationMainCard(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            exchangeAmount = exchangeAmount,
            onAmountChange = { viewModel.action(ReservationUiEvent.UpdateExchangeAmount(it)) },
            receivedAmount = receivedAmount,
            targetRate = targetRate,
            onTargetRateChange = {
                viewModel.action(ReservationUiEvent.UpdateTargetRate(it))
            }
        )
    }

    if (showPasswordBottomSheet) {
        PasswordBottomSheet(
            bottomSheetState = bottomSheetState,
            password = password,
            isPasswordError = isPasswordError,
            updateShowPasswordBottomSheet = {
                viewModel.action(
                    ReservationUiEvent.UpdateShowPasswordBottomSheet(
                        it
                    )
                )
            },
            updatePassword = { viewModel.action(ReservationUiEvent.UpdatePinNumber(it)) },
        )
    }

    if (errorMessage.isNotEmpty()) {
        ErrorPopUp(
            message = errorMessage,
            onDismiss = {
                errorMessage = ""
            }
        )
    }
}
