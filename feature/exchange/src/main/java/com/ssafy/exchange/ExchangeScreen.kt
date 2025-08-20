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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.common.ui.DetailTopBar
import com.ssafy.common.ui.HeyFYPopUp
import kotlinx.coroutines.delay

@Composable
fun ExchangeScreen(
    viewModel: ExchangeViewModel = hiltViewModel<ExchangeViewModel>(),
) {
    var isShowPopUp by remember { mutableStateOf(false) }
    var exchangeAmount by remember { mutableStateOf("") }
    val currentRate = 1347.50

    var showInsufficientBalanceError by remember { mutableStateOf(false) }
    var isUSD by remember { mutableStateOf(false) }
    val receivedAmount = exchangeAmount.toDoubleOrNull()?.let {
        if (isUSD) {
            it * currentRate
        } else {
            it / currentRate
        }
    } ?: 0.0

    LaunchedEffect(showInsufficientBalanceError) {
        if (showInsufficientBalanceError) {
            delay(2000)
            showInsufficientBalanceError = false
        }
    }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .imePadding(),
        topBar = {
            DetailTopBar(
                title = "Currency Exchange",
                onBack = { viewModel.back() }
            )
        },
        bottomBar = {
            ExchangeBottomBar(
                onClick = { isShowPopUp = true }
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
                HistoricalAnalysisCard()
            }

            item {
                AIPredictionCard()
            }

            item {
                ExchangeMainCard(
                    exchangeAmount = exchangeAmount,
                    onAmountChange = { exchangeAmount = it },
                    currentRate = currentRate,
                    receivedAmount = receivedAmount,
                    showInsufficientBalanceError = showInsufficientBalanceError,
                    isUSD = isUSD,
                    updateShowInsufficientBalanceError = { showInsufficientBalanceError = it },
                    onToggleCurrency = { isUSD = !isUSD }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (isShowPopUp) {
        HeyFYPopUp(
            onCancel = {
                isShowPopUp = false
            },
            onConfirm = {
                isShowPopUp = false
                // TODO : 송금 완료 처리
                viewModel.goToSuccess()
            },
            onDismiss = {
                isShowPopUp = false
            }
        )
    }
}
