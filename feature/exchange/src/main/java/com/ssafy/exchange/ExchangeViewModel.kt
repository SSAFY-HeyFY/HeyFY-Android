package com.ssafy.exchange

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.error.RefreshInProgressError
import com.ssafy.common.error.RefreshTokenExpiredError
import com.ssafy.common.error.SidExpiredError
import com.ssafy.exchange.domain.ExchangeForeignUseCase
import com.ssafy.exchange.domain.ExchangeUseCase
import com.ssafy.exchange.domain.GetHistoricalAnalysisUseCase
import com.ssafy.exchange.model.ExchangeUiEvent
import com.ssafy.exchange.model.ExchangeUiState
import com.ssafy.finance.domain.GetCurrentFinanceUseCase
import com.ssafy.navigation.Destination
import com.ssafy.navigation.DestinationParamConstants
import com.ssafy.navigation.DestinationParamConstants.FX_ACCOUNT
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getHistoricalAnalysisUseCase: GetHistoricalAnalysisUseCase,
    private val getCurrentFinanceUseCase: GetCurrentFinanceUseCase,
    private val exchangeUseCase: ExchangeUseCase,
    private val exchangeForeignUseCase: ExchangeForeignUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val type = savedStateHandle.get<String>(DestinationParamConstants.EXCHANGE_TYPE) ?: ""

    private val _uiState = MutableStateFlow<ExchangeUiState>(ExchangeUiState.Init)
    val uiState: StateFlow<ExchangeUiState> = _uiState.asStateFlow()

    private val _historicalAnalysis = MutableStateFlow("")
    val historicalAnalysis: StateFlow<String> = _historicalAnalysis.asStateFlow()

    private val _aiPrediction = MutableStateFlow("")
    val aiPrediction: StateFlow<String> = _aiPrediction.asStateFlow()

    private val _isUSD = MutableStateFlow(type == FX_ACCOUNT)
    val isUSD: StateFlow<Boolean> = _isUSD.asStateFlow()

    private val _exchangeAmount = MutableStateFlow("")
    val exchangeAmount = _exchangeAmount.asStateFlow()

    private val _pinNumber = MutableStateFlow("")
    val pinNumber = _pinNumber.asStateFlow()

    private val _checkPin = MutableStateFlow(true)
    val checkPin = _checkPin.asStateFlow()

    private val _showPasswordBottomSheet = MutableStateFlow(false)
    val showPasswordBottomSheet = _showPasswordBottomSheet.asStateFlow()

    private val _currentRate = MutableStateFlow(0.0)
    val currentRate = _currentRate.asStateFlow()

    private val _fluctuation = MutableStateFlow(0.0)
    val fluctuation = _fluctuation.asStateFlow()

    private val didNavigateToAuth = AtomicBoolean(false)
    private val didNavigateToLogin = AtomicBoolean(false)

    fun action(event: ExchangeUiEvent) {
        when (event) {
            ExchangeUiEvent.Init -> {
                init()
            }

            ExchangeUiEvent.Back -> {
                back()
            }

            ExchangeUiEvent.Exchange -> {
                exchange()
            }

            ExchangeUiEvent.UpdateIsUSD -> {
                _isUSD.value = isUSD.value.not()
            }

            is ExchangeUiEvent.UpdateExchangeAmount -> {
                _exchangeAmount.value = event.balance
            }

            is ExchangeUiEvent.UpdatePinNumber -> {
                _pinNumber.value = event.pinNumber
            }

            is ExchangeUiEvent.UpdateCheckPin -> {
                _checkPin.value = event.checkPin
            }

            is ExchangeUiEvent.UpdateShowPasswordBottomSheet -> {
                _showPasswordBottomSheet.value = event.isShow
            }
        }
    }

    private fun init() {
        fetch()
        getCurrentFinance()
    }

    private fun fetch() {
        viewModelScope.launch {
            getHistoricalAnalysisUseCase()
                .onSuccess { (historicalAnalysis, aiPrediction) ->
                    _historicalAnalysis.value = historicalAnalysis
                    _aiPrediction.value = aiPrediction
                    updateUiState(ExchangeUiState.Success)
                }
                .onFailure {
                    handleFailure(it)
                }
        }
    }

    private fun getCurrentFinance() {
        viewModelScope.launch {
            getCurrentFinanceUseCase()
                .onSuccess {
                    _currentRate.value = it.usd.rate
                    _fluctuation.value = it.usd.fluctuation
                    updateUiState(ExchangeUiState.Success)
                }
                .onFailure {
                    handleFailure(it)
                }
        }
    }

    private fun exchange() {
        viewModelScope.launch {
            updateUiState(ExchangeUiState.Loading)
            if (isUSD.value) {
                exchangeUseCase(exchangeAmount.value.toLong(), pinNumber.value)
                    .onSuccess {
                        if (it.isCorrect) {
                            _showPasswordBottomSheet.value = false
                            goToSuccess()
                        } else {
                            _checkPin.value = false
                        }
                        updateUiState(ExchangeUiState.Success)
                    }.onFailure(::handleFailure)
            } else {
                exchangeForeignUseCase(exchangeAmount.value.toLong(), pinNumber.value)
                    .onSuccess {
                        if (it.isCorrect) {
                            _showPasswordBottomSheet.value = false
                            goToSuccess()
                        } else {
                            _checkPin.value = false
                        }
                        updateUiState(ExchangeUiState.Success)
                    }.onFailure(::handleFailure)
            }
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

    private fun updateUiState(state: ExchangeUiState) {
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
                    ExchangeUiState.Error(
                        mag = throwable.message
                            ?: "An unexpected error has occurred. Please contact the administrator"
                    )
                )
            }
        }
    }
}
