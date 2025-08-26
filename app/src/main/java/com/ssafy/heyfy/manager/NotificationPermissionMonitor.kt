package com.ssafy.heyfy.manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificationPermissionMonitor() {

    fun observePermissionState(context: Context): Flow<Boolean> = flow {
        var currentPermission = hasNotificationPermission(context)
        emit(currentPermission)

        while (true) {
            kotlinx.coroutines.delay(1000) // 1초마다 확인

            val newPermission = hasNotificationPermission(context)
            if (newPermission != currentPermission) {
                currentPermission = newPermission
                emit(newPermission)
            }
        }
    }

    private fun hasNotificationPermission(context: Context): Boolean {
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
