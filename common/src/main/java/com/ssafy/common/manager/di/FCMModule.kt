package com.ssafy.common.manager.di

import android.content.Context
import com.ssafy.common.manager.FCMTokenManager
import com.ssafy.common.manager.FCMTokenManagerImpl
import com.ssafy.common.manager.NotificationPermissionMonitor
import com.ssafy.common.manager.NotificationPermissionMonitorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FCMModule {

    @Provides
    fun provideFCMTokenManager(

    ): FCMTokenManager = FCMTokenManagerImpl()

    @Provides
    fun provideNotificationPermissionMonitor(
        @ApplicationContext context: Context,
    ): NotificationPermissionMonitor = NotificationPermissionMonitorImpl(context)
}
