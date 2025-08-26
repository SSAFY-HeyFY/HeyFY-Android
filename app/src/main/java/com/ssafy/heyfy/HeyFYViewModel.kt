package com.ssafy.heyfy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.common.manager.FCMTokenManager
import com.ssafy.common.manager.NotificationPermissionMonitor
import com.ssafy.fcm.domain.DeleteFcmTokenUseCase
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HeyFYViewModel @Inject constructor(
    heyFYAppNavigator: HeyFYAppNavigator,
    private val notificationPermissionMonitor: NotificationPermissionMonitor,
    private val fcmTokenManager: FCMTokenManager,
    private val deleteFcmTokenUseCase: DeleteFcmTokenUseCase,
) : ViewModel() {
    val navigationChannel = heyFYAppNavigator.navigationChannel

    val hasNotificationPermission = notificationPermissionMonitor.observePermissionState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )

    fun deleteToken() {
        viewModelScope.launch {
            val token = fcmTokenManager.getToken() ?: return@launch
            deleteFcmTokenUseCase(token)
                .onSuccess {
                    fcmTokenManager.deleteToken()
                }
                .onFailure {
                    Timber.e(it, "Failed to delete FCM token: ${it.message}")
                }
        }
    }

}
