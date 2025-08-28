package com.ssafy.send_money

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.data_store.TokenManager
import com.ssafy.common.error.RefreshTokenExpiredError
import com.ssafy.common.error.SidExpiredError
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
import java.util.concurrent.atomic.AtomicBoolean

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val fetchHomeUseCase: FetchHomeUseCase,
    private val transferDomesticUseCase: TransferDomesticUseCase,
    private val transferForeignerUseCase: TransferForeignerUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
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

    private val _pinNumber = MutableStateFlow("")
    val pinNumber = _pinNumber.asStateFlow()

    private val _checkPin = MutableStateFlow(true)
    val checkPin = _checkPin.asStateFlow()

    private val _showPasswordBottomSheet = MutableStateFlow(false)
    val showPasswordBottomSheet = _showPasswordBottomSheet.asStateFlow()

    private val didNavigateToAuth = AtomicBoolean(false)
    private val didNavigateToLogin = AtomicBoolean(false)

    fun action(event: SendMoneyUiEvent) {
        when (event) {
            SendMoneyUiEvent.Init -> {
                homeFetch()
            }

            is SendMoneyUiEvent.ClickTransfer -> {
                transfer()
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
            is SendMoneyUiEvent.UpdatePinNumber -> {
                _pinNumber.value = event.pinNumber
            }

            is SendMoneyUiEvent.UpdateCheckPin -> {
                _checkPin.value = event.checkPin
            }
            is SendMoneyUiEvent.UpdateShowPasswordBottomSheet -> {
                _showPasswordBottomSheet.value = event.isShow
            }

            SendMoneyUiEvent.ClickBack -> back()
        }
    }

    private fun homeFetch() {
        viewModelScope.launch {
            updateUiState(SendMoneyUiState.Loading)
            fetchHomeUseCase().onSuccess {
                if (isFXAccount) {
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

    private fun transfer() {
        if (isFXAccount) {
            transferForeigner()
        } else {
            transferDomestic()
        }
    }

    private fun transferDomestic() {
        if (uiState.value !is SendMoneyUiState.Success) return
        viewModelScope.launch {
            updateUiState(SendMoneyUiState.Loading)
            transferDomesticUseCase(
                // depositAccountNo = depositAccountNo.value, TODO : 적용
                depositAccountNo = "0014084444636603", // TEST
                transactionSummary = transferNote.value,
                amount = transferAmount.value,
                pinNumber = pinNumber.value,
            ).onSuccess { correct ->
                if(correct) {
                    _showPasswordBottomSheet.value = false
                    goToSuccess()
                } else {
                    _checkPin.value = false
                }
                updateUiState(SendMoneyUiState.Success)
            }.onFailure(::handleFailure)
        }
    }

    private fun transferForeigner() {
        viewModelScope.launch {
            updateUiState(SendMoneyUiState.Loading)
            transferForeignerUseCase(
                // depositAccountNo = depositAccountNo.value, TODO : 적용
                depositAccountNo = "0014433880825658", // TEST
                transactionSummary = transferNote.value,
                amount = transferAmount.value,
                pinNumber = pinNumber.value,
            ).onSuccess { correct ->
                if(correct) {
                    _showPasswordBottomSheet.value = false
                    goToSuccess()
                } else {
                    _checkPin.value = false
                }
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

    private fun goToLogin() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Login(),
                isBackStackCleared = true,
            )
        }
    }

    private fun goToAuth() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Auth(),
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
        when(throwable) {
            is RefreshTokenExpiredError -> {
                if (didNavigateToLogin.compareAndSet(false, true)) {
                    goToLogin()
                }
            }
            is SidExpiredError -> {
                if (didNavigateToAuth.compareAndSet(false, true)) {
                    goToAuth()
                }
            }
            else -> {
                updateUiState(
                    SendMoneyUiState.Error(
                        mag = throwable.message
                            ?: "An unexpected error has occurred. Please contact the administrator"
                    )
                )
            }
        }
    }
}
