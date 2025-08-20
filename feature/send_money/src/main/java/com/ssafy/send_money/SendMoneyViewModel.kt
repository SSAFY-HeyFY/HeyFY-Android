package com.ssafy.send_money

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.Destination
import com.ssafy.navigation.DestinationParamConstants
import com.ssafy.navigation.HeyFYAppNavigator
import com.ssafy.send_money.model.SendMoneyUiEvent
import com.ssafy.send_money.model.SendMoneyUiState
import com.ssafy.transfer.domain.TransferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val transferUseCase: TransferUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val type = savedStateHandle.get<String>(DestinationParamConstants.SEND_MONEY_TYPE) ?: ""

    private val _uiState = MutableStateFlow<SendMoneyUiState>(SendMoneyUiState.Init)
    val uiState = _uiState.asStateFlow()

    fun action(event: SendMoneyUiEvent) {
        when (event) {
            is SendMoneyUiEvent.ClickTransfer -> transfer(
                withdrawalAccountNo = event.withdrawalAccountNo,
                depositAccountNo = event.depositAccountNo,
                amount = event.amount
            )

            SendMoneyUiEvent.ClickBack -> back()
        }
    }

    private fun transfer(
        withdrawalAccountNo: String,
        depositAccountNo: String,
        amount: Int,
    ) {
        viewModelScope.launch {
            updateUiState(SendMoneyUiState.Loading)
            transferUseCase(
                withdrawalAccountNo = withdrawalAccountNo,
                depositAccountNo = depositAccountNo,
                amount = amount
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
