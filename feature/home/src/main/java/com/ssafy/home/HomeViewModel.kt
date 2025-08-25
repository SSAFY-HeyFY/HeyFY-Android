package com.ssafy.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.home.domain.FetchHomeUseCase
import com.ssafy.home.domain.model.Home
import com.ssafy.home.model.HomeUiEvent
import com.ssafy.home.model.HomeUiState
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchHomeUseCase: FetchHomeUseCase,
    private val navigator: HeyFYAppNavigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState = _uiState.asStateFlow()

    private val _studentId = MutableStateFlow("")
    val studentId = _studentId.asStateFlow()

    private val _normalAccount = MutableStateFlow(Home.NAccount())
    val normalAccount = _normalAccount.asStateFlow()

    private val _foreignAccount = MutableStateFlow(Home.FAccount())
    val foreignAccount = _foreignAccount.asStateFlow()

    fun action(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.Init -> {
                fetch()
            }

            is HomeUiEvent.CLickSendMoney -> {
                goToSendMoney(event.type)
            }

            HomeUiEvent.ClickCard -> {
                goToCardDetail()
            }

            HomeUiEvent.ClickExchange -> {
                goToExchange()
            }

            is HomeUiEvent.ClickMentoClub -> {
                goToMentoClub(event.type)
            }

            HomeUiEvent.ClickTips -> {
                goToTips()
            }

            HomeUiEvent.ClickTransaction -> {
                goToTransaction()
            }
        }
    }

    private fun fetch() {
        viewModelScope.launch {
            fetchHomeUseCase()
                .onSuccess { home ->
                    _studentId.value = home.studentId
                    _normalAccount.value = home.normalAccount
                    _foreignAccount.value = home.foreignAccount
                    updateUiState(HomeUiState.Success)
                }
                .onFailure(::handleFailure)
        }
    }

    private fun goToCardDetail() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.CardDetail(),
            )
        }
    }

    private fun goToSendMoney(type: String) {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.SendMoney(type),
            )
        }
    }

    private fun goToTransaction() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Transaction(),
            )
        }
    }

    private fun goToMentoClub(type: String) {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.MentoClub(type),
            )
        }
    }

    private fun goToTips() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Tips(),
            )
        }
    }

    private fun goToExchange() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Exchange(),
            )
        }
    }

    private fun updateUiState(state: HomeUiState) {
        _uiState.value = state
    }

    private fun handleFailure(throwable: Throwable) {
        updateUiState(HomeUiState.Error(
            mag = throwable.message ?: "An unexpected error has occurred. Please contact the administrator"
        ))
    }
}
