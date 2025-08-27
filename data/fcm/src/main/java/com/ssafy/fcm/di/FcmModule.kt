package com.ssafy.fcm.di

import com.ssafy.fcm.api.FcmApi
import com.ssafy.fcm.data.FcmDataSource
import com.ssafy.fcm.data.FcmDataSourceImpl
import com.ssafy.fcm.data.FcmRepositoryImpl
import com.ssafy.fcm.domain.FcmRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FcmModule {

    @Binds
    @Singleton
    abstract fun bindFcmDataSource(
        fcmDataSourceImpl: FcmDataSourceImpl
    ): FcmDataSource

    @Binds
    @Singleton
    abstract fun bindFcmRepository(
        fcmRepositoryImpl: FcmRepositoryImpl
    ): FcmRepository
}

@Module
@InstallIn(SingletonComponent::class)
object FcmApiModule {

    @Provides
    @Singleton
    fun provideFcmApi(retrofit: Retrofit): FcmApi {
        return retrofit.create(FcmApi::class.java)
    }
}

