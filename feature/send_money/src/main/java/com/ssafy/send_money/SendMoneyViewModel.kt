package com.ssafy.send_money

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.home.domain.FetchHomeUseCase
import com.ssafy.navigation.Destination
import com.ssafy.navigation.DestinationParamConstants
import com.ssafy.navigation.HeyFYAppNavigator
import com.ssafy.send_money.model.SendMoneyUiEvent
import com.ssafy.send_money.model.SendMoneyUiState
import com.ssafy.transfer.domain.TransferDomesticUseCase
import com.ssafy.transfer.domain.TransferForeignerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val heyFYAppNavigator: HeyFYAppNavigator,
    private val fetchHomeUseCase: FetchHomeUseCase,
    private val transferDomesticUseCase: TransferDomesticUseCase,
    private val transferForeignerUseCase: TransferForeignerUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val isFXAccount = (savedStateHandle.get<String>(DestinationParamConstants.SEND_MONEY_TYPE)
        ?: "") == DestinationParamConstants.FX_ACCOUNT

    private val _uiState = MutableStateFlow<SendMoneyUiState>(SendMoneyUiState.Init)
    val uiState = _uiState.asStateFlow()

    private val _account = MutableStateFlow("")
    val account = _account.asStateFlow()

    private val _balanceF = MutableStateFlow(0.0)
    val balanceF = _balanceF.asStateFlow()

    private val _balanceN = MutableStateFlow(0L)
    val balanceN = _balanceN.asStateFlow()

    private val _depositAccountNo = MutableStateFlow("")
    val depositAccountNo = _depositAccountNo.asStateFlow()

    private val _transferNote = MutableStateFlow("")
    val transferNote = _transferNote.asStateFlow()

    private val _transferAmount = MutableStateFlow("")
    val transferAmount = _transferAmount.asStateFlow()

    fun action(event: SendMoneyUiEvent) {
        when (event) {
            SendMoneyUiEvent.Init -> {
                homeFetch()
            }

            is SendMoneyUiEvent.ClickTransfer -> {
                if (isFXAccount) {
                    transferForeigner()
                } else {
                    transferDomestic()
                }
            }

            is SendMoneyUiEvent.UpdateTransferNote -> {
                _transferNote.value = event.note
            }
            is SendMoneyUiEvent.UpdateTransferAmount -> {
                _transferAmount.value = event.amount
            }
            is SendMoneyUiEvent.UpdateDepositAccountNo -> {
                _depositAccountNo.value = event.accountNo
            }
            SendMoneyUiEvent.ClickBack -> back()
        }
    }

    private fun homeFetch() {
        viewModelScope.launch {
            updateUiState(SendMoneyUiState.Loading)
            fetchHomeUseCase().onSuccess {
                if(isFXAccount) {
                    _account.value = it.foreignAccount.accountNo
                    _balanceF.value = it.foreignAccount.balance
                } else {
                    _account.value = it.normalAccount.accountNo
                    _balanceN.value = it.normalAccount.balance
                }
                updateUiState(SendMoneyUiState.Success)
            }.onFailure(::handleFailure)
        }
    }

    private fun transferDomestic() {
        viewModelScope.launch {
            updateUiState(SendMoneyUiState.Loading)
            transferDomesticUseCase(
                // depositAccountNo = depositAccountNo.value,
                depositAccountNo = "0014084444636603",
                transactionSummary = transferNote.value,
                amount = transferAmount.value,
            ).onSuccess {
                goToSuccess()
            }.onFailure(::handleFailure)
        }
    }

    private fun transferForeigner() {
        viewModelScope.launch {
            updateUiState(SendMoneyUiState.Loading)
            transferForeignerUseCase(
                // depositAccountNo = depositAccountNo.value,
                depositAccountNo = "0014433880825658",
                transactionSummary = transferNote.value,
                amount = transferAmount.value,
            ).onSuccess {
                goToSuccess()
            }.onFailure(::handleFailure)
        }
    }

    private fun goToSuccess() {
        viewModelScope.launch {
            delay(500)
            heyFYAppNavigator.navigateTo(
                route = Destination.Success(),
                isBackStackCleared = true,
            )
        }
    }

    private fun back() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateBack()
        }
    }

    private fun updateUiState(state: SendMoneyUiState) {
        _uiState.value = state
    }

    private fun handleFailure(throwable: Throwable) {
        updateUiState(
            SendMoneyUiState.Error(
                mag = throwable.message
                    ?: "An unexpected error has occurred. Please contact the administrator"
            )
        )
    }
}
