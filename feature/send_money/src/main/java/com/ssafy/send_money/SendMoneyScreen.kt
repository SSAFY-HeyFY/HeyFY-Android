package com.ssafy.send_money

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.ui.DetailTopBar
import kotlinx.coroutines.delay

@Composable
fun SendMoneyScreen() {
    val maxBalance = 12450.5
    var selectedBank by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var transferAmount by remember { mutableStateOf("") }
    var transferNote by remember { mutableStateOf("") }
    var showInsufficientBalanceError by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(showInsufficientBalanceError) {
        if (showInsufficientBalanceError) {
            delay(2000)
            showInsufficientBalanceError = false
        }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            DetailTopBar(
                title = "Send Money",
                onBack = { /* TODO: 뒤로가기 처리 */ }
            )
        },
        bottomBar = {
            SendMoneyBottomBar(
                isEnabled = accountNumber.isNotEmpty() && transferAmount.isNotEmpty(),
                onContinue = { /* TODO: 송금 처리 */ }
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .imePadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            MyAccountSection(maxBalance = maxBalance)

            RecipientAccountSection(
                selectedBank = selectedBank,
                onBankSelected = { selectedBank = it },
                accountNumber = accountNumber,
                onAccountNumberChange = { accountNumber = it }
            )

            TransferAmountSection(
                transferAmount = transferAmount,
                maxBalance = maxBalance,
                showInsufficientBalanceError = showInsufficientBalanceError,
                updateShowInsufficientBalanceError = { showInsufficientBalanceError = it },
                onAmountChange = { transferAmount = it }
            )

            TransferNoteSection(
                transferNote = transferNote,
                onNoteChange = { transferNote = it }
            )

            TransferSummarySection(transferAmount = transferAmount)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
