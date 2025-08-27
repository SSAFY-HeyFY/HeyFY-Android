package com.ssafy.finance

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.ssafy.navigation.DestinationParamConstants.ACCOUNT
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleNextRefresh(updatedAt: String) {
        viewModelScope.launch {
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
                val lastUpdated = LocalDateTime.parse(updatedAt, formatter)
                val nextRefresh = lastUpdated.plusMinutes(10)
                val now = LocalDateTime.now()

                val delaySeconds = ChronoUnit.SECONDS.between(now, nextRefresh)

                if (delaySeconds > 0) {
                    Timber.d("Scheduling next refresh in ${delaySeconds} seconds")
                    delay(delaySeconds * 1000L)

                    refreshExchangeRate()
                } else {
                    Timber.d("10 minutes have passed, refreshing immediately")
                    refreshExchangeRate()
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to schedule next refresh")
                delay(10 * 60 * 1000L)
                refreshExchangeRate()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun refreshExchangeRate() {
        viewModelScope.launch {
            Timber.d("Refreshing exchange rate data")
            getCurrentFinanceUseCase()
                .onSuccess { financeData ->
                    _current.value = financeData
                    scheduleNextRefresh(financeData.usd.updatedAt)
                }
                .onFailure {
                    Timber.e(it, "Failed to refresh exchange rate")
                    delay(60 * 1000L)
                    refreshExchangeRate()
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
                scheduleNextRefresh(it.usd.updatedAt)
            }.onFailure(::handleFailure)

            updateUiState(FinanceUiState.Success)
        }
    }

    private fun goToExchange() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Exchange(ACCOUNT),
            )
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

    private fun updateUiState(state: FinanceUiState) {
        _uiState.value = state
    }

    private fun handleFailure(throwable: Throwable) {
        when(throwable.message) {
            "EXPIRED_TOKEN" -> {
                // TODO : 토큰 만료
            }
            "EXPIRED_REFRESH_TOKEN" -> {
                goToLogin()
                // TODO : 리프레쉬 토큰 만료
            }
            "SID_INVALID_OR_EXPIRED" -> {
                goToAuth()
                // TODO : 세션 만료
            }
            else -> {
                updateUiState(
                    FinanceUiState.Error(
                        mag = throwable.message
                            ?: "An unexpected error has occurred. Please contact the administrator"
                    )
                )
            }
        }
    }
}
