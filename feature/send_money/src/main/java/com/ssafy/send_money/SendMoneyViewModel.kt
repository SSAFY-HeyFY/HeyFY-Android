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

    private val _account = MutableStateFlow("")
    val account = _account.asStateFlow()

    private val _balance = MutableStateFlow(0.0)
    val balance = _balance.asStateFlow()

    private val _uiState = MutableStateFlow<SendMoneyUiState>(SendMoneyUiState.Init)
    val uiState = _uiState.asStateFlow()

    fun action(event: SendMoneyUiEvent) {
        when (event) {
            SendMoneyUiEvent.Init -> {
                homeFetch()
            }

            is SendMoneyUiEvent.ClickTransfer -> {
                if (isFXAccount) {
                    transferDomestic(
                        depositAccountNo = event.depositAccountNo,
                        amount = event.amount
                    )
                } else {
                    transferForeigner(
                        depositAccountNo = event.depositAccountNo,
                        amount = event.amount
                    )
                }
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
                    _balance.value = it.foreignAccount.balance
                } else {
                    _account.value = it.normalAccount.accountNo
                    _balance.value = it.normalAccount.balance
                }
                updateUiState(SendMoneyUiState.Success)
            }.onFailure(::handleFailure)
        }
    }

    private fun transferDomestic(
        depositAccountNo: String,
        amount: Int,
    ) {
        viewModelScope.launch {
            updateUiState(SendMoneyUiState.Loading)
            transferDomesticUseCase(
                depositAccountNo = depositAccountNo,
                amount = amount
            ).onSuccess {
                goToSuccess()
            }.onFailure(::handleFailure)
        }
    }

    private fun transferForeigner(
        depositAccountNo: String,
        amount: Int,
    ) {
        viewModelScope.launch {
            updateUiState(SendMoneyUiState.Loading)
            transferForeignerUseCase(
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
