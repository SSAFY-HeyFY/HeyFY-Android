package com.ssafy.exchange.di

import com.ssafy.exchange.api.ExchangeApi
import com.ssafy.exchange.data.ExchangeDataSource
import com.ssafy.exchange.data.ExchangeDataSourceImpl
import com.ssafy.exchange.data.ExchangeRepositoryImpl
import com.ssafy.exchange.domain.ExchangeRepository
import com.ssafy.exchange.domain.ExchangeReservationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExchangeModule {

    @Provides
    @Singleton
    fun provideExchangeApi(retrofit: Retrofit): ExchangeApi {
        return retrofit.create(ExchangeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExchangeDataSource(exchangeApi: ExchangeApi): ExchangeDataSource {
        return ExchangeDataSourceImpl(exchangeApi)
    }

    @Provides
    @Singleton
    fun provideExchangeRepository(exchangeDataSource: ExchangeDataSource): ExchangeRepository {
        return ExchangeRepositoryImpl(exchangeDataSource)
    }

    @Provides
    @Singleton
    fun provideExchangeReservationUseCase(exchangeRepository: ExchangeRepository): ExchangeReservationUseCase {
        return ExchangeReservationUseCase(exchangeRepository)
    }
}

