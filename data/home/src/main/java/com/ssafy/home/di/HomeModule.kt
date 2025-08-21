package com.ssafy.home.di

import com.ssafy.home.data.HomeDataSource
import com.ssafy.home.data.HomeDataSourceImpl
import com.ssafy.home.data.HomeRepositoryImpl
import com.ssafy.home.domain.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface HomeModule {

    @Binds
    fun bindHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource

    @Binds
    fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}
