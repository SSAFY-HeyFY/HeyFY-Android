package com.ssafy.transaction

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.account.domain.GetForeignTransactionHistoryUseCase
import com.ssafy.account.domain.GetTransactionHistoryUseCase
import com.ssafy.account.domain.model.TransactionHistory
import com.ssafy.common.error.RefreshTokenExpiredError
import com.ssafy.common.error.SidExpiredError
import com.ssafy.common.text.TextFormat.formatCurrencyKRW
import com.ssafy.common.text.TextFormat.formatCurrencyUSD
import com.ssafy.home.domain.FetchHomeUseCase
import com.ssafy.navigation.Destination
import com.ssafy.navigation.DestinationParamConstants
import com.ssafy.navigation.HeyFYAppNavigator
import com.ssafy.transaction.model.TransactionUiEvent
import com.ssafy.transaction.model.TransactionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.concurrent.atomic.AtomicBoolean

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getTransactionHistoryUseCase: GetTransactionHistoryUseCase,
    private val getForeignTransactionHistoryUseCase: GetForeignTransactionHistoryUseCase,
    private val fetchHomeUseCase: FetchHomeUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val isFXAccount = (savedStateHandle.get<String>(DestinationParamConstants.TRANSACTION_TYPE)
        ?: "") == DestinationParamConstants.FX_ACCOUNT

    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState.Init)
    val uiState = _uiState.asStateFlow()

    private val _currentBalance = MutableStateFlow("")
    val currentBalance = _currentBalance.asStateFlow()

    private val _accountNumber = MutableStateFlow("")
    val accountNumber = _accountNumber.asStateFlow()

    private val _transactions = MutableStateFlow(emptyList<TransactionHistory.Item>())
    val transactions = _transactions.asStateFlow()

    private val didNavigateToAuth = AtomicBoolean(false)
    private val didNavigateToLogin = AtomicBoolean(false)

    fun action(event: TransactionUiEvent) {
        when(event) {
            TransactionUiEvent.Init ->  {
                init()
            }
            TransactionUiEvent.Back -> back()
        }
    }

    private fun init() {
        fetchHome()
    }

    private fun fetchHome() {
        viewModelScope.launch {
            updateUiState(TransactionUiState.Loading)
            fetchHomeUseCase()
                .onSuccess {
                    if (isFXAccount) {
                        val account = it.foreignAccount.accountNo
                        _currentBalance.value = "$${formatCurrencyUSD(it.foreignAccount.balance)}"
                        _accountNumber.value = account
                        fetchTransactionHistory(account)
                    } else {
                        val account = it.normalAccount.accountNo
                        _currentBalance.value = "₩${formatCurrencyKRW(it.normalAccount.balance)}"
                        _accountNumber.value = it.normalAccount.accountNo
                        fetchTransactionHistory(account)
                    }
                    updateUiState(TransactionUiState.Success)
                }
                .onFailure(::handleFailure)
        }
    }

    private fun fetchTransactionHistory(
        account: String
    ) {
        viewModelScope.launch {
            updateUiState(TransactionUiState.Loading)
            if (isFXAccount) {
                getForeignTransactionHistoryUseCase(
                    accountNo = account
                ).onSuccess {
                    _transactions.value = it.list
                    updateUiState(TransactionUiState.Success)
                }.onFailure(::handleFailure)
            } else {
                getTransactionHistoryUseCase(
                    accountNo = account
                ).onSuccess {
                    _transactions.value = it.list
                    updateUiState(TransactionUiState.Success)
                }.onFailure(::handleFailure)
            }
        }
    }

    private fun goToLogin() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Login(),
                isBackStackCleared = true
            )
        }
    }

    private fun goToAuth() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Auth(),
                isBackStackCleared = true
            )
        }
    }

    fun back() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateBack()
        }
    }


    private fun updateUiState(state: TransactionUiState) {
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
                    TransactionUiState.Error(
                        mag = throwable.message
                            ?: "An unexpected error has occurred. Please contact the administrator"
                    )
                )
            }
        }
    }
}
