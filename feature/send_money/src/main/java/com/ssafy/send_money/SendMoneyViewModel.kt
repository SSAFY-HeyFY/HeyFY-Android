package com.ssafy.send_money

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import com.ssafy.send_money.model.SendMoneyUiEvent
import com.ssafy.send_money.model.SendMoneyUiState
import com.ssafy.transfer.domain.TransferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val transferUseCase: TransferUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
): ViewModel() {

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
        amount: Int
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
        when (throwable) {
            is IOException -> {
                updateUiState(SendMoneyUiState.NetworkError)
            }

            is Exception -> {
                updateUiState(SendMoneyUiState.Error(
                    mag = throwable.message ?: "예상치 못한 에러가 발생했습니다.\n관리자에게 문의해주세요."
                ))
            }
        }
    }
}
