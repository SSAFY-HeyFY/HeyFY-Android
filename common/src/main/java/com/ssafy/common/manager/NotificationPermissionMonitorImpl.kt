package com.ssafy.common.manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificationPermissionMonitorImpl(
    private val context: Context
) :NotificationPermissionMonitor {

    override fun observePermissionState(): Flow<Boolean> = flow {
        var currentPermission = hasNotificationPermission()
        emit(currentPermission)

        while (true) {
            delay(1000) // 1초마다 확인

            val newPermission = hasNotificationPermission()
            if (newPermission != currentPermission) {
                currentPermission = newPermission
                emit(newPermission)
            }
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}
