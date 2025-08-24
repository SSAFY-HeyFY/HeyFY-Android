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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.common.ui.DetailTopBar
import com.ssafy.common.ui.ErrorPopUp
import com.ssafy.common.ui.PasswordBottomSheet
import com.ssafy.send_money.model.SendMoneyUiEvent
import com.ssafy.send_money.model.SendMoneyUiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(
    viewModel: SendMoneyViewModel = hiltViewModel<SendMoneyViewModel>(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val account by viewModel.account.collectAsStateWithLifecycle()
    val balance by viewModel.balance.collectAsStateWithLifecycle()

    val isFxAccount = viewModel.isFXAccount

    var transferNote by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var transferAmount by remember { mutableStateOf("") }
    var showInsufficientBalanceError by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current
    val memoFocusRequester = remember { FocusRequester() }
    val accountNumberFocusRequester = remember { FocusRequester() }
    val transferAmountFocusRequester = remember { FocusRequester() }
    var errorMessage by remember { mutableStateOf("") }

    // Password
    var showPasswordBottomSheet by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var isPasswordError by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val correctPassword = "123456"

    val currency = if (isFxAccount) "USD" else "KRW"

    LaunchedEffect(Unit) {
        if (uiState is SendMoneyUiState.Init) {
            viewModel.action(SendMoneyUiEvent.Init)
        }
    }

    LaunchedEffect(showInsufficientBalanceError) {
        if (showInsufficientBalanceError) {
            delay(2000)
            showInsufficientBalanceError = false
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is SendMoneyUiState.Error -> {
                errorMessage = (uiState as SendMoneyUiState.Error).mag
            }

            else -> {}
        }
    }

    LaunchedEffect(password) {
        if (password.length < 6) return@LaunchedEffect
        if (password == correctPassword) {
            showPasswordBottomSheet = false
            password = ""
            isPasswordError = false
            viewModel.action(
                SendMoneyUiEvent.ClickTransfer(
                    depositAccountNo = account,
                    amount = transferAmount.toInt()
                )
            )
        } else {
            isPasswordError = true
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
                onContinue = {
                    showPasswordBottomSheet = true
                }
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

            MyAccountSection(
                balance = balance.toDouble(),
                account = account,
                currency = currency,
            )

            RecipientAccountSection(
                memo = transferNote,
                updateMemo = { transferNote = it },
                accountNumber = accountNumber,
                onAccountNumberChange = { accountNumber = it },
                memoFocusRequester = memoFocusRequester,
                accountNumberFocusRequester = accountNumberFocusRequester,
                onMemoNext = { accountNumberFocusRequester.requestFocus() },
                onAccountNumberNext = { transferAmountFocusRequester.requestFocus() }
            )

            TransferAmountSection(
                transferAmount = transferAmount,
                maxBalance = balance.toDouble(),
                currency = currency,
                showInsufficientBalanceError = showInsufficientBalanceError,
                updateShowInsufficientBalanceError = { showInsufficientBalanceError = it },
                onAmountChange = { transferAmount = it },
                transferAmountFocusRequester = transferAmountFocusRequester,
                onAmountDone = {
                    focusManager.clearFocus()
                }
            )

            TransferSummarySection(transferAmount = transferAmount, currency = currency)

            Spacer(modifier = Modifier.height(16.dp))
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

    if (showPasswordBottomSheet) {
        PasswordBottomSheet(
            bottomSheetState = bottomSheetState,
            password = password,
            isPasswordError = isPasswordError,
            updateShowPasswordBottomSheet = { showPasswordBottomSheet = it },
            updatePassword = { password = it },
            updateIsPasswordError = { isPasswordError = it }
        )
    }
}
