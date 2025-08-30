package com.ssafy.exchange_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.account.domain.GetExchangeHistoryUseCase
import com.ssafy.account.domain.model.ExchangeHistory
import com.ssafy.common.error.RefreshInProgressError
import com.ssafy.common.error.RefreshTokenExpiredError
import com.ssafy.common.error.SidExpiredError
import com.ssafy.exchange_history.model.ExchangeHistoryUiEvent
import com.ssafy.exchange_history.model.ExchangeHistoryUiState
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class ExchangeHistoryViewModel @Inject constructor(
    private val getExchangeHistoryUseCase: GetExchangeHistoryUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExchangeHistoryUiState>(ExchangeHistoryUiState.Init)
    val uiState = _uiState.asStateFlow()

    private val _exchanges = MutableStateFlow(emptyList<ExchangeHistory>())
    val exchanges = _exchanges.asStateFlow()

    private val didNavigateToAuth = AtomicBoolean(false)
    private val didNavigateToLogin = AtomicBoolean(false)


    fun action(event: ExchangeHistoryUiEvent) {
        when (event) {
            ExchangeHistoryUiEvent.Init -> {
                init()
            }

            ExchangeHistoryUiEvent.Back -> back()
        }
    }

    private fun init() {
        getExchangeHistory()
    }

    private fun getExchangeHistory() {
        viewModelScope.launch {
            updateUiState(ExchangeHistoryUiState.Loading)
            getExchangeHistoryUseCase()
                .onSuccess {
                    _exchanges.value = it
                    updateUiState(ExchangeHistoryUiState.Success)
                }
                .onFailure(::handleFailure)

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


    private fun updateUiState(state: ExchangeHistoryUiState) {
        _uiState.value = state
    }

    private fun handleFailure(throwable: Throwable) {
        when (throwable) {
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

            is RefreshInProgressError -> {

            }

            else -> {
                updateUiState(
                    ExchangeHistoryUiState.Error(
                        mag = throwable.message
                            ?: "An unexpected error has occurred. Please contact the administrator"
                    )
                )
            }
        }
    }
}
