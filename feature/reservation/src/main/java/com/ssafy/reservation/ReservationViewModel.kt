package com.ssafy.reservation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.error.RefreshInProgressError
import com.ssafy.common.error.RefreshTokenExpiredError
import com.ssafy.common.error.SidExpiredError
import com.ssafy.exchange.domain.ExchangeReservationUseCase
import com.ssafy.navigation.Destination
import com.ssafy.navigation.DestinationParamConstants
import com.ssafy.navigation.DestinationParamConstants.FX_ACCOUNT
import com.ssafy.navigation.HeyFYAppNavigator
import com.ssafy.reservation.model.ReservationUiEvent
import com.ssafy.reservation.model.ReservationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val exchangeReservationUseCase: ExchangeReservationUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReservationUiState>(ReservationUiState.Init)
    val uiState: StateFlow<ReservationUiState> = _uiState.asStateFlow()

    private val _exchangeAmount = MutableStateFlow("")
    val exchangeAmount = _exchangeAmount.asStateFlow()

    private val _pinNumber = MutableStateFlow("")
    val pinNumber = _pinNumber.asStateFlow()

    private val _checkPin = MutableStateFlow(true)
    val checkPin = _checkPin.asStateFlow()

    private val _showPasswordBottomSheet = MutableStateFlow(false)
    val showPasswordBottomSheet = _showPasswordBottomSheet.asStateFlow()

    private val _targetRate = MutableStateFlow("")
    val targetRate = _targetRate.asStateFlow()


    private val didNavigateToAuth = AtomicBoolean(false)
    private val didNavigateToLogin = AtomicBoolean(false)

    fun action(event: ReservationUiEvent) {
        when (event) {
            ReservationUiEvent.Init -> {

            }

            ReservationUiEvent.Back -> {
                back()
            }

            ReservationUiEvent.Reserve -> {
                exchangeReservation()
            }

            is ReservationUiEvent.UpdateExchangeAmount -> {
                _exchangeAmount.value = event.balance
            }

            is ReservationUiEvent.UpdatePinNumber -> {
                _pinNumber.value = event.pinNumber
            }

            is ReservationUiEvent.UpdateCheckPin -> {
                _checkPin.value = event.checkPin
            }

            is ReservationUiEvent.UpdateShowPasswordBottomSheet -> {
                _showPasswordBottomSheet.value = event.isShow
            }
            is ReservationUiEvent.UpdateTargetRate -> {
                _targetRate.value = event.targetRate
            }
        }
    }

    private fun exchangeReservation() {
        viewModelScope.launch {
            exchangeReservationUseCase(
                transactionBalance = targetRate.value.toDouble(),
                currency = "KRW",
                pinNumber = pinNumber.value,
                baseExchangeRate = exchangeAmount.value.toDouble(),
            ).onSuccess {
                if(it) {
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

    private fun updateUiState(state: ReservationUiState) {
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
                    ReservationUiState.Error(
                        mag = throwable.message
                            ?: "An unexpected error has occurred. Please contact the administrator"
                    )
                )
            }
        }
    }
}
