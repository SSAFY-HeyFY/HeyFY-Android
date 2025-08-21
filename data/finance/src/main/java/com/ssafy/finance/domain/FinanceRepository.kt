package com.ssafy.finance.domain

import com.ssafy.finance.domain.model.ExchangeRateCurrent
import com.ssafy.finance.domain.model.ExchangeRateHistories
import com.ssafy.finance.domain.model.ExchangeRatePrediction
import com.ssafy.finance.domain.model.ExchangeRateTuition

interface FinanceRepository {
    suspend fun getExchangeRateTuition(): Result<ExchangeRateTuition>
    suspend fun getExchangeRatePrediction(): Result<ExchangeRatePrediction>
    suspend fun getExchangeRateHistories(): Result<ExchangeRateHistories>
    suspend fun getExchangeRateCurrent(): Result<ExchangeRateCurrent>
}
