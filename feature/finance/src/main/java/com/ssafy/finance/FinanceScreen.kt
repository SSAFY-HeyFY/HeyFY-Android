package com.ssafy.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.common.ui.ErrorPopUp
import com.ssafy.common.ui.HeyFYTopBar
import com.ssafy.finance.model.FinanceUiEvent
import com.ssafy.finance.model.FinanceUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job

@Composable
fun FinanceScreen(
    viewModel: FinanceViewModel = hiltViewModel<FinanceViewModel>(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val current by viewModel.current.collectAsStateWithLifecycle()
    val histories by viewModel.histories.collectAsStateWithLifecycle()
    val prediction by viewModel.prediction.collectAsStateWithLifecycle()
    val tuition by viewModel.tuition.collectAsStateWithLifecycle()
    var errorMessage by remember { mutableStateOf("") }
    var index by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        if (uiState is FinanceUiState.Init) {
            viewModel.action(FinanceUiEvent.Init)
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is FinanceUiState.Error -> {
                errorMessage = (uiState as FinanceUiState.Error).mag
            }

            else -> {}
        }
    }

    LaunchedEffect(index) {
        if(index > 0) {
            delay(500)
            index = 0
        }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            HeyFYTopBar()
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
                CurrencySection(
                    current = current,
                )
            }

            item {
                ExchangeRateChartSection(
                    histories = histories,
                    index = index,
                    updateIndex = { index = it }
                )
            }

            item {
                BullishPredictionCard(
                    prediction = prediction,
                    onClick = {
                        viewModel.action(FinanceUiEvent.ClickExchange)
                    }
                )
            }

            item {
                TuitionPaymentSection(
                    tuition = tuition,
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
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
