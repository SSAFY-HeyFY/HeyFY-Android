package com.ssafy.exchange.domain

import com.ssafy.exchange.domain.model.Exchange

interface ExchangeRepository {
    suspend fun exchange(transactionBalance: Int, pinNumber: String): Result<Exchange>
    suspend fun exchangeForeign(transactionBalance: Int, pinNumber: String): Result<Exchange>
    suspend fun getAiPrediction(): Result<String>
    suspend fun getHistoricalAnalysis(): Result<String>
}

