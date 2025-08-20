package com.ssafy.transfer.di

import com.ssafy.transfer.api.TransferApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class TransferApiModule {

    @Provides
    fun provideTransferApi(retrofit: Retrofit): TransferApi {
        return retrofit.create(TransferApi::class.java)
    }
}
