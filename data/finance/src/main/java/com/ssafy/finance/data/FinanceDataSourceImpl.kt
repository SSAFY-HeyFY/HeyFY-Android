package com.ssafy.finance.data

import com.ssafy.finance.api.FinanceApi
import com.ssafy.finance.api.response.ExchangeRateCurrentResponse
import com.ssafy.finance.api.response.ExchangeRateHistoriesResponse
import com.ssafy.finance.api.response.ExchangeRatePredictionResponse
import com.ssafy.finance.api.response.ExchangeRateTuitionResponse
import retrofit2.Response
import javax.inject.Inject

class FinanceDataSourceImpl @Inject constructor(
    private val financeApi: FinanceApi
): FinanceDataSource {
    
    override suspend fun getExchangeRateTuition(): Response<ExchangeRateTuitionResponse> {
        return financeApi.getExchangeRateTuition()
    }

    override suspend fun getExchangeRatePrediction(): Response<ExchangeRatePredictionResponse> {
        return financeApi.getExchangeRatePrediction()
    }

    override suspend fun getExchangeRateHistories(): Response<ExchangeRateHistoriesResponse> {
        return financeApi.getExchangeRateHistories()
    }

    override suspend fun getExchangeRateCurrent(): Response<ExchangeRateCurrentResponse> {
        return financeApi.getExchangeRateCurrent()
    }
}
