package com.ssafy.transfer.di

import com.ssafy.transfer.data.TransferDataSource
import com.ssafy.transfer.data.TransferDataSourceImpl
import com.ssafy.transfer.data.TransferRepositoryImpl
import com.ssafy.transfer.domain.TransferRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface TransferModule {

    @Binds
    fun bindTransferDataSource(transferDataSourceImpl: TransferDataSourceImpl): TransferDataSource

    @Binds
    fun bindTransferRepository(transferRepositoryImpl: TransferRepositoryImpl): TransferRepository
}
