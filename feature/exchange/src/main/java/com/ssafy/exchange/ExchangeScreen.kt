package com.ssafy.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.ssafy.common.ui.HeyFYPopUp
import com.ssafy.common.ui.PasswordBottomSheet
import com.ssafy.exchange.model.ExchangeUiEvent
import com.ssafy.exchange.model.ExchangeUiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeScreen(
    viewModel: ExchangeViewModel = hiltViewModel<ExchangeViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val historicalAnalysis by viewModel.historicalAnalysis.collectAsStateWithLifecycle()
    val aiPrediction by viewModel.aiPrediction.collectAsStateWithLifecycle()
    val isUSD by viewModel.isUSD.collectAsStateWithLifecycle()
    val exchangeAmount by viewModel.exchangeAmount.collectAsStateWithLifecycle()
    val password by viewModel.pinNumber.collectAsStateWithLifecycle()
    val checkPin by viewModel.checkPin.collectAsStateWithLifecycle()
    val showPasswordBottomSheet by viewModel.showPasswordBottomSheet.collectAsStateWithLifecycle()
    val currentRate by viewModel.currentRate.collectAsStateWithLifecycle()
    val fluctuation by viewModel.fluctuation.collectAsStateWithLifecycle()

    var errorMessage by remember { mutableStateOf("") }

    val receivedAmount = exchangeAmount.toDoubleOrNull()?.let {
        if (isUSD) {
            it * currentRate
        } else {
            it / currentRate
        }
    } ?: 0.0

    val exchangeAmountL = exchangeAmount.toLongOrNull() ?: 0L

    var isPasswordError by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val keyboardController = LocalSoftwareKeyboardController.current

    val isExchangeAmountValid: (amount: Long, isUSD: Boolean) -> Boolean =  { amount, isUSD ->
        if (isUSD) {
            amount >= 100 && amount % 10 == 0L
        } else {
            amount >= 1000 && amount % 10 == 0L
        }
    }


    LaunchedEffect(Unit) {
        if (uiState is ExchangeUiState.Init) {
            viewModel.action(ExchangeUiEvent.Init)
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is ExchangeUiState.Error -> {
                errorMessage = (uiState as ExchangeUiState.Error).mag
            }

            else -> {}
        }
    }

    LaunchedEffect(password) {
        if (password.length < 6) return@LaunchedEffect
        viewModel.action(ExchangeUiEvent.Exchange)

    }

    LaunchedEffect(checkPin) {
        if(checkPin.not()) {
            isPasswordError = true
        }
    }

    LaunchedEffect(isPasswordError) {
        if(isPasswordError) {
            delay(700)
            viewModel.action(ExchangeUiEvent.UpdatePinNumber(""))
            viewModel.action(ExchangeUiEvent.UpdateCheckPin(true))
            isPasswordError = false
        }
    }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .imePadding(),
        topBar = {
            DetailTopBar(
                title = "Currency Exchange",
                onBack = { viewModel.action(ExchangeUiEvent.Back) }
            )
        },
        bottomBar = {
            ExchangeBottomBar(
                isEnabled = isExchangeAmountValid(exchangeAmountL, isUSD),
                onClick = {
                    keyboardController?.hide()
                    viewModel.action(ExchangeUiEvent.UpdateShowPasswordBottomSheet(true))
                }
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HistoricalAnalysisCard(
                    content = historicalAnalysis,
                )
            }

            item {
                AIPredictionCard(
                    content = aiPrediction,
                )
            }

            item {
                ExchangeMainCard(
                    exchangeAmount = exchangeAmount,
                    onAmountChange = { viewModel.action(ExchangeUiEvent.UpdateExchangeAmount(it)) },
                    currentRate = currentRate,
                    fluctuation = fluctuation,
                    receivedAmount = receivedAmount,
                    isUSD = isUSD,
                    onToggleCurrency = { viewModel.action(ExchangeUiEvent.UpdateIsUSD) },
                    goToReservation = { viewModel.action(ExchangeUiEvent.ClickReservation) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (showPasswordBottomSheet) {
        PasswordBottomSheet(
            bottomSheetState = bottomSheetState,
            password = password,
            isPasswordError = isPasswordError,
            updateShowPasswordBottomSheet = { viewModel.action(ExchangeUiEvent.UpdateShowPasswordBottomSheet(it)) },
            updatePassword = { viewModel.action(ExchangeUiEvent.UpdatePinNumber(it)) },
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
