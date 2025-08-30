package com.ssafy.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.common.ui.DetailTopBar
import com.ssafy.common.ui.ErrorPopUp
import com.ssafy.transaction.model.TransactionUiEvent
import com.ssafy.transaction.model.TransactionUiState

@Composable
fun TransactionScreen(
    viewModel: TransactionViewModel = hiltViewModel<TransactionViewModel>(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentBalance by viewModel.currentBalance.collectAsStateWithLifecycle()
    val accountNumber by viewModel.accountNumber.collectAsStateWithLifecycle()
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    var errorMessage by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        if (uiState is TransactionUiState.Init) {
            viewModel.action(TransactionUiEvent.Init)
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is TransactionUiState.Error -> {
                errorMessage = (uiState as TransactionUiState.Error).mag
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            DetailTopBar(
                title = "Transaction History",
                onBack = viewModel::back
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CurrentBalanceSection(
                    balance = currentBalance,
                    accountNumber = accountNumber,
                    goToExchangeHistory = { viewModel.action(TransactionUiEvent.CLickExchangeHistory) },
                    goToReservationHistory = { viewModel.action(TransactionUiEvent.ClickReservationHistory) }
                )
            }

            items(transactions) { transaction ->
                TransactionItem(transaction = transaction)
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
