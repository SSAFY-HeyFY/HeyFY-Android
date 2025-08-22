package com.ssafy.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.finance.domain.GetCurrentFinanceUseCase
import com.ssafy.finance.domain.GetHistoriesFinanceUseCase
import com.ssafy.finance.domain.GetPredictionFinanceUseCase
import com.ssafy.finance.domain.GetTuitionFinanceUseCase
import com.ssafy.finance.domain.model.ExchangeRateCurrent
import com.ssafy.finance.domain.model.ExchangeRateHistories
import com.ssafy.finance.domain.model.ExchangeRatePrediction
import com.ssafy.finance.domain.model.ExchangeRateTuition
import com.ssafy.finance.model.FinanceUiEvent
import com.ssafy.finance.model.FinanceUiState
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val getTuitionFinanceUseCase: GetTuitionFinanceUseCase,
    private val getPredictionFinanceUseCase: GetPredictionFinanceUseCase,
    private val getHistoriesFinanceUseCase: GetHistoriesFinanceUseCase,
    private val getCurrentFinanceUseCase: GetCurrentFinanceUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow<FinanceUiState>(FinanceUiState.Init)
    val uiState = _uiState.asStateFlow()

    private val _current = MutableStateFlow(ExchangeRateCurrent())
    val current = _current.asStateFlow()

    private val _histories = MutableStateFlow(ExchangeRateHistories())
    val histories = _histories.asStateFlow()

    private val _prediction = MutableStateFlow(ExchangeRatePrediction())
    val prediction = _prediction.asStateFlow()

    private val _tuition = MutableStateFlow(ExchangeRateTuition())
    val tuition = _tuition.asStateFlow()

    fun action(event: FinanceUiEvent) {
        when (event) {
            FinanceUiEvent.Init -> {
                init()
            }
            FinanceUiEvent.ClickExchange -> {
                goToExchange()
            }
        }
    }

    private fun init() {
        viewModelScope.launch {
            getTuitionFinanceUseCase().onSuccess {
                _tuition.value = it
            }.onFailure(::handleFailure)

            getPredictionFinanceUseCase().onSuccess {
                _prediction.value = it
            }.onFailure(::handleFailure)

            getHistoriesFinanceUseCase().onSuccess {
                _histories.value = it
            }.onFailure(::handleFailure)

            getCurrentFinanceUseCase().onSuccess {
                _current.value = it
            }.onFailure(::handleFailure)
        }
    }


    private fun goToExchange() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Exchange(),
            )
        }
    }

    private fun updateUiState(state: FinanceUiState) {
        _uiState.value = state
    }

    private fun handleFailure(throwable: Throwable) {
        updateUiState(FinanceUiState.Error(
            mag = throwable.message ?: "An unexpected error has occurred. Please contact the administrator"
        ))
    }
}
