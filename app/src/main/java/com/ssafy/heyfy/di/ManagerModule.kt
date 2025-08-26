package com.ssafy.heyfy.di

import com.ssafy.heyfy.manager.FCMTokenManager
import com.ssafy.heyfy.manager.NotificationPermissionMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {

    @Provides
    @Singleton
    fun provideFCMTokenManager(): FCMTokenManager {
        return FCMTokenManager()
    }

    @Provides
    @Singleton
    fun provideNotificationPermissionMonitor(): NotificationPermissionMonitor {
        return NotificationPermissionMonitor()
    }
}
