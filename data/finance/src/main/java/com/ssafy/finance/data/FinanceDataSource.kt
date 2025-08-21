package com.ssafy.finance.data

import com.ssafy.finance.api.response.ExchangeRateCurrentResponse
import com.ssafy.finance.api.response.ExchangeRateHistoriesResponse
import com.ssafy.finance.api.response.ExchangeRatePredictionResponse
import com.ssafy.finance.api.response.ExchangeRateTuitionResponse
import retrofit2.Response

interface FinanceDataSource {
    suspend fun getExchangeRateTuition(): Response<ExchangeRateTuitionResponse>
    suspend fun getExchangeRatePrediction(): Response<ExchangeRatePredictionResponse>
    suspend fun getExchangeRateHistories(): Response<ExchangeRateHistoriesResponse>
    suspend fun getExchangeRateCurrent(): Response<ExchangeRateCurrentResponse>
}
