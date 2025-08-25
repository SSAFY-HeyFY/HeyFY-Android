package com.ssafy.account.di

import com.ssafy.account.api.AccountApi
import com.ssafy.account.data.AccountDataSource
import com.ssafy.account.data.AccountDataSourceImpl
import com.ssafy.account.data.AccountRepositoryImpl
import com.ssafy.account.domain.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountDataSource(accountApi: AccountApi): AccountDataSource {
        return AccountDataSourceImpl(accountApi)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(accountDataSource: AccountDataSource): AccountRepository {
        return AccountRepositoryImpl(accountDataSource)
    }
}
