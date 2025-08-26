package com.ssafy.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.account.domain.CheckAccountUseCase
import com.ssafy.account.domain.GetAccountAuthUseCase
import com.ssafy.account.model.AccountUiEvent
import com.ssafy.account.model.AccountUiState
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountAuthUseCase: GetAccountAuthUseCase,
    private val checkAccountUseCase: CheckAccountUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Init)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    private val currentStep = MutableStateFlow(1)
    val step: StateFlow<Int> = currentStep.asStateFlow()

    private val _accountNumber = MutableStateFlow("")
    val accountNumber: StateFlow<String> = _accountNumber.asStateFlow()

    private val _verificationCode = MutableStateFlow(listOf("", "", "", ""))
    val verificationCode = _verificationCode.asStateFlow()

    private val _showCodeMessage = MutableStateFlow("")
    val showCodeMessage = _showCodeMessage.asStateFlow()

    private var code = ""

    fun action(event: AccountUiEvent) {
        when (event) {

            is AccountUiEvent.ClickAccountNumber -> {
                registerAccount()
            }

            is AccountUiEvent.ClickAuthCode -> {

            }

            is AccountUiEvent.ClickVerify -> {
                verifyAccount()
            }

            is AccountUiEvent.UpdateAccountNumber -> {
                _accountNumber.value = event.accountNo
            }

            is AccountUiEvent.UpdateVerificationCode -> {
                _verificationCode.value = event.verificationCode
            }

            is AccountUiEvent.UpdateShowCode -> {
                _showCodeMessage.value = if (event.showCode) {
                    code
                } else {
                    ""
                }
            }
        }
    }

    private fun verifyAccount() {
        viewModelScope.launch {
            _uiState.value = AccountUiState.Loading
            checkAccountUseCase(
                //accountNo = accountNumber.value,
                accountNo = "0012938990739664",
                authCode = verificationCode.value.joinToString(""),
            ).onSuccess {
                goToMain()
                _uiState.value = AccountUiState.Success
            }.onFailure(::handleFailure)
        }
    }

    private fun registerAccount() {
        viewModelScope.launch {
            _uiState.value = AccountUiState.Loading
            getAccountAuthUseCase(
                //accountNo = accountNumber.value,
                accountNo = "0012938990739664",
            ).onSuccess {
                code = it.code
                currentStep.value = 2
                _uiState.value = AccountUiState.Success
            }.onFailure(::handleFailure)
        }
    }

    fun goToMain() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Main(),
                isBackStackCleared = true,
            )
        }
    }

    private fun updateUiState(state: AccountUiState) {
        _uiState.value = state
    }

    private fun handleFailure(throwable: Throwable) {
        updateUiState(
            AccountUiState.Error(
                mag = throwable.message
                    ?: "An unexpected error has occurred. Please contact the administrator"
            )
        )
    }
}
