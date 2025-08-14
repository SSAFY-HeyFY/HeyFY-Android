package com.ssafy.navigation.di

import com.ssafy.navigation.HeyFYAppNavigator
import com.ssafy.navigation.HeyFYAppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Singleton
    @Binds
    fun bindHeyFYAppNavigator(heyFYAppNavigatorImpl: HeyFYAppNavigatorImpl): HeyFYAppNavigator
}
