package com.ssafy.login.di

import com.ssafy.login.data.LoginDataSource
import com.ssafy.login.data.LoginDataSourceImpl
import com.ssafy.login.data.LoginRepositoryImpl
import com.ssafy.login.domain.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface LoginModule {

    @Binds
    fun bindLoginDataSource(loginDataSourceImpl: LoginDataSourceImpl): LoginDataSource

    @Binds
    fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}

