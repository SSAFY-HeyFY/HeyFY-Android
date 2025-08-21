package com.ssafy.finance.di

import com.ssafy.finance.data.FinanceDataSource
import com.ssafy.finance.data.FinanceDataSourceImpl
import com.ssafy.finance.data.FinanceRepositoryImpl
import com.ssafy.finance.domain.FinanceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface FinanceModule {

    @Binds
    fun bindFinanceDataSource(financeDataSourceImpl: FinanceDataSourceImpl): FinanceDataSource

    @Binds
    fun bindFinanceRepository(financeRepositoryImpl: FinanceRepositoryImpl): FinanceRepository
}
