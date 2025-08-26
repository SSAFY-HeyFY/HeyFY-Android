package com.ssafy.common.manager

import kotlinx.coroutines.flow.Flow

interface NotificationPermissionMonitor {
    fun observePermissionState(): Flow<Boolean>
}
