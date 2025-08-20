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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.ssafy.common.ui.HeyFYPopUp
import com.ssafy.send_money.model.SendMoneyUiEvent
import com.ssafy.send_money.model.SendMoneyUiState
import kotlinx.coroutines.delay

@Composable
fun SendMoneyScreen(
    viewModel: SendMoneyViewModel = hiltViewModel<SendMoneyViewModel>(),
) {
    var isShowPopUp by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val maxBalance = 12450.5
    var selectedBank by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var transferAmount by remember { mutableStateOf("") }
    var transferNote by remember { mutableStateOf("") }
    var showInsufficientBalanceError by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showInsufficientBalanceError) {
        if (showInsufficientBalanceError) {
            delay(2000)
            showInsufficientBalanceError = false
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is SendMoneyUiState.NetworkError -> {
                snackbarHostState.showSnackbar("네트워크를 연결해 주세요.")
            }

            is SendMoneyUiState.Error -> {
                snackbarHostState.showSnackbar("잠시 후 다시 시도해 주세요")
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            DetailTopBar(
                title = "Send Money",
                onBack = { viewModel.action(SendMoneyUiEvent.ClickBack) }
            )
        },
        bottomBar = {
            SendMoneyBottomBar(
                isEnabled = accountNumber.isNotEmpty() && transferAmount.isNotEmpty(),
                onContinue = { isShowPopUp = true }
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


    if (isShowPopUp) {
        HeyFYPopUp(
            onCancel = {
                isShowPopUp = false
            },
            onConfirm = {
                isShowPopUp = false
                viewModel.action(SendMoneyUiEvent.ClickTransfer(
                  withdrawalAccountNo = "0012338458486007",
                    depositAccountNo = "0014084444636603",
                    amount = transferAmount.toInt()
                ))
            },
            onDismiss = {
                isShowPopUp = false
            }
        )
    }

    SnackbarHost(hostState = snackbarHostState)
}
