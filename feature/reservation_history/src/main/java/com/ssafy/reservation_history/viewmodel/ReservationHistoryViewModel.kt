package com.ssafy.reservation_history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.exchange.domain.GetReservationHistoryUseCase
import com.ssafy.exchange.domain.CancelReservationUseCase
import com.ssafy.exchange.domain.model.ExchangeReservationHistory
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import com.ssafy.reservation_history.model.ReservationHistoryUiEvent
import com.ssafy.reservation_history.model.ReservationHistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationHistoryViewModel @Inject constructor(
    private val getReservationHistoryUseCase: GetReservationHistoryUseCase,
    private val cancelReservationUseCase: CancelReservationUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReservationHistoryUiState>(ReservationHistoryUiState.Init)
    val uiState: StateFlow<ReservationHistoryUiState> = _uiState.asStateFlow()

    private val _pinNumber = MutableStateFlow("")
    val pinNumber = _pinNumber.asStateFlow()

    private val _checkPin = MutableStateFlow(true)
    val checkPin = _checkPin.asStateFlow()

    private val _showPasswordBottomSheet = MutableStateFlow(false)
    val showPasswordBottomSheet = _showPasswordBottomSheet.asStateFlow()

    private val _histories = MutableStateFlow<List<ExchangeReservationHistory>>(emptyList())
    val histories = _histories.asStateFlow()

    var reservationId: Int = -1

    fun action(event: ReservationHistoryUiEvent) {
        when (event) {
            is ReservationHistoryUiEvent.LoadReservationHistory -> {
                loadReservationHistory()
            }
            is ReservationHistoryUiEvent.CancelReservation -> {
                reservationId = event.reservationId
                _showPasswordBottomSheet.value = true
            }

            is ReservationHistoryUiEvent.UpdatePinNumber -> {
                _pinNumber.value = event.pinNumber
            }
            is ReservationHistoryUiEvent.UpdateShowPasswordBottomSheet -> {
                _showPasswordBottomSheet.value = event.show
            }
            is ReservationHistoryUiEvent.UpdateCheckPin -> {
                _checkPin.value = event.checkPin
            }
        }
    }

    private fun loadReservationHistory() {
        viewModelScope.launch {
            _uiState.value = ReservationHistoryUiState.Loading
            
            getReservationHistoryUseCase()
                .onSuccess { reservationHistory ->
                    _histories.value = reservationHistory
                    _uiState.value = ReservationHistoryUiState.Success(reservationHistory)
                }
        }
    }

    fun cancelReservation(reservationId: Int, pinNumber: String) {
        viewModelScope.launch {
            cancelReservationUseCase(reservationId, pinNumber)
                .onSuccess { success ->
                    if(success) {
                        goToSuccess()
                    }
                }
                .onFailure { error ->
                    _uiState.value = ReservationHistoryUiState.Error(error.message ?: "예약 취소에 실패했습니다.")
                }
        }
    }

    fun goToSuccess() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Success(),
                isBackStackCleared = true
            )
        }
    }

    fun back() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateBack()
        }
    }
}
