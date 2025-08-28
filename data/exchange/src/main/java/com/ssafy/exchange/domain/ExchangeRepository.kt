package com.ssafy.exchange.domain

import com.ssafy.exchange.domain.model.Exchange

interface ExchangeRepository {
    suspend fun exchange(transactionBalance: Long, pinNumber: String): Result<Exchange>
    suspend fun exchangeForeign(transactionBalance: Long, pinNumber: String): Result<Exchange>
    suspend fun getAiPrediction(): Result<String>
    suspend fun getHistoricalAnalysis(): Result<String>
}

