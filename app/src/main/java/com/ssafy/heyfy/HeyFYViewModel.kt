package com.ssafy.heyfy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.error.RefreshInProgressError
import com.ssafy.common.error.RefreshTokenExpiredError
import com.ssafy.common.error.SidExpiredError
import com.ssafy.common.manager.FCMTokenManager
import com.ssafy.common.manager.NotificationPermissionMonitor
import com.ssafy.fcm.domain.DeleteFcmTokenUseCase
import com.ssafy.fcm.domain.RegisterFcmTokenUseCase
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class HeyFYViewModel @Inject constructor(
    private val heyFYAppNavigator: HeyFYAppNavigator,
    private val notificationPermissionMonitor: NotificationPermissionMonitor,
    private val fcmTokenManager: FCMTokenManager,
    private val deleteFcmTokenUseCase: DeleteFcmTokenUseCase,
    private val registerFcmTokenUseCase: RegisterFcmTokenUseCase,
) : ViewModel() {
    val navigationChannel = heyFYAppNavigator.navigationChannel

    val hasNotificationPermission = notificationPermissionMonitor.observePermissionState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )

    private val didNavigateToAuth = AtomicBoolean(false)
    private val didNavigateToLogin = AtomicBoolean(false)

    fun registerToken() {
        viewModelScope.launch {
            val token = fcmTokenManager.getToken() ?: return@launch
            registerFcmTokenUseCase(token)
                .onFailure(::handleFailure)

        }
    }

    fun deleteToken() {
        viewModelScope.launch {
            val token = fcmTokenManager.getToken() ?: return@launch
            deleteFcmTokenUseCase(token)
                .onSuccess {
                    fcmTokenManager.deleteToken()
                }
                .onFailure(::handleFailure)
        }
    }

    fun goToLogin() {
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
                isBackStackCleared = true,
            )
        }
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
                goToLogin()
            }
        }
    }

}
