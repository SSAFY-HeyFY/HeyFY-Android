package com.ssafy.exchange.data

import com.ssafy.exchange.api.response.ExchangeAiPredictionResponse
import com.ssafy.exchange.api.response.ExchangeHistoricalAnalysisResponse
import com.ssafy.exchange.api.response.ExchangeResponse
import retrofit2.Response

interface ExchangeDataSource {
    suspend fun exchange(transactionBalance: Int): Response<ExchangeResponse>
    suspend fun exchangeForeign(transactionBalance: Int): Response<ExchangeResponse>
    suspend fun getAiPrediction(): Response<ExchangeAiPredictionResponse>
    suspend fun getHistoricalAnalysis(): Response<ExchangeHistoricalAnalysisResponse>
}
