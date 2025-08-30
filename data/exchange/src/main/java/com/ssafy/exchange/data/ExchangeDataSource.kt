package com.ssafy.exchange.data

import com.ssafy.exchange.api.response.ExchangeHistoricalAnalysisResponse
import com.ssafy.exchange.api.response.ExchangeReservationResponse
import com.ssafy.exchange.api.response.ExchangeResponse
import retrofit2.Response

interface ExchangeDataSource {
    suspend fun exchange(transactionBalance: Long, pinNumber: String): Response<ExchangeResponse>
    suspend fun exchangeForeign(transactionBalance: Long, pinNumber: String): Response<ExchangeResponse>
    suspend fun exchangeReservation(transactionBalance: Double, currency: String, pinNumber: String, baseExchangeRate: Double): Response<ExchangeReservationResponse>
    suspend fun getHistoricalAnalysis(): Response<ExchangeHistoricalAnalysisResponse>
}

