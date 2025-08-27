package com.ssafy.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.error.RefreshTokenExpiredError
import com.ssafy.common.error.SidExpiredError
import com.ssafy.common.manager.FCMTokenManager
import com.ssafy.common.manager.NotificationPermissionMonitor
import com.ssafy.fcm.domain.DeleteFcmTokenUseCase
import com.ssafy.fcm.domain.RegisterFcmTokenUseCase
import com.ssafy.home.domain.FetchHomeUseCase
import com.ssafy.home.domain.model.Home
import com.ssafy.home.model.HomeUiEvent
import com.ssafy.home.model.HomeUiState
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.concurrent.atomic.AtomicBoolean

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchHomeUseCase: FetchHomeUseCase,
    private val registerFcmTokenUseCase: RegisterFcmTokenUseCase,
    private val deleteFcmTokenUseCase: DeleteFcmTokenUseCase,
    private val fcmTokenManager: FCMTokenManager,
    private val notificationPermissionMonitor: NotificationPermissionMonitor,
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

    val hasNotificationPermission = notificationPermissionMonitor.observePermissionState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )

    private val didNavigateToAuth = AtomicBoolean(false)
    private val didNavigateToLogin = AtomicBoolean(false)

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

            is HomeUiEvent.ClickExchange -> {
                //goToAuth()
                goToExchange(event.type)
            }

            is HomeUiEvent.ClickMentoClub -> {
                goToMentoClub(event.type)
            }

            HomeUiEvent.ClickTips -> {
                goToTips()
            }

            is HomeUiEvent.ClickTransaction -> {
                goToTransaction(event.type)
            }

            HomeUiEvent.RegisterToken -> {
                viewModelScope.launch {
                    val token = fcmTokenManager.getToken() ?: return@launch
                    registerFcmTokenUseCase(token)
                        .onFailure { }

                }
            }

            HomeUiEvent.DeleteToken -> {
                viewModelScope.launch {
                    val token = fcmTokenManager.getToken() ?: return@launch
                    deleteFcmTokenUseCase(token)
                        .onSuccess {
                            fcmTokenManager.deleteToken()
                        }
                        .onFailure { }
                }
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

    private fun goToTransaction(type: String) {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Transaction(type),
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

    private fun goToExchange(type: String) {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Exchange(type)
            )
        }
    }

    private fun goToLogin() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Login(),
                isBackStackCleared = true,
            )
        }
    }

    private fun goToAuth() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Auth(),
                isBackStackCleared = true,
            )
        }
    }

    private fun updateUiState(state: HomeUiState) {
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

            else -> {
                updateUiState(
                    HomeUiState.Error(
                        mag = throwable.message
                            ?: "An unexpected error has occurred. Please contact the administrator"
                    )
                )
            }
        }
    }
}
