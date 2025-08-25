package com.ssafy.exchange

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.exchange.domain.ExchangeForeignUseCase
import com.ssafy.exchange.domain.ExchangeUseCase
import com.ssafy.exchange.domain.GetAiPredictionUseCase
import com.ssafy.exchange.domain.GetHistoricalAnalysisUseCase
import com.ssafy.exchange.model.ExchangeUiEvent
import com.ssafy.exchange.model.ExchangeUiState
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
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getAiPredictionUseCase: GetAiPredictionUseCase,
    private val getHistoricalAnalysisUseCase: GetHistoricalAnalysisUseCase,
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

    fun action(event: ExchangeUiEvent) {
        when (event) {
            ExchangeUiEvent.Init -> {
                init()
            }

            ExchangeUiEvent.Back -> {
                back()
            }

            is ExchangeUiEvent.Exchange -> {
                exchange(event.balance)
            }

            ExchangeUiEvent.UpdateIsUSD -> {
                _isUSD.value = isUSD.value.not()
            }
        }
    }

    private fun init() {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            getHistoricalAnalysisUseCase()
                .onSuccess {
                    _historicalAnalysis.value = it
                    updateUiState(ExchangeUiState.Success)
                }
                .onFailure {
                    handleFailure(it)
                }
            getAiPredictionUseCase()
                .onSuccess {
                    _aiPrediction.value = it
                    updateUiState(ExchangeUiState.Success)
                }
                .onFailure {
                    handleFailure(it)
                }
        }
    }

    private fun exchange(balance: Int) {
        viewModelScope.launch {
            if (isUSD.value) {
                exchangeUseCase(balance)
                    .onSuccess {
                        goToSuccess()
                    }.onFailure(::handleFailure)
            } else {
                exchangeForeignUseCase(balance)
                    .onSuccess {
                        goToSuccess()
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

    private fun back() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateBack()
        }
    }

    private fun updateUiState(state: ExchangeUiState) {
        _uiState.value = state
    }

    private fun handleFailure(throwable: Throwable) {
        updateUiState(
            ExchangeUiState.Error(
                mag = throwable.message
                    ?: "An unexpected error has occurred. Please contact the administrator"
            )
        )
    }
}
