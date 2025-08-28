package com.ssafy.exchange.data

import com.ssafy.exchange.api.ExchangeApi
import com.ssafy.exchange.api.request.ExchangeRequest
import com.ssafy.exchange.api.response.ExchangeResponse
import com.ssafy.exchange.api.response.ExchangeAiPredictionResponse
import com.ssafy.exchange.api.response.ExchangeHistoricalAnalysisResponse
import retrofit2.Response
import javax.inject.Inject

class ExchangeDataSourceImpl @Inject constructor(
    private val exchangeApi: ExchangeApi
) : ExchangeDataSource {

    override suspend fun exchange(transactionBalance: Long, pinNumber: String): Response<ExchangeResponse> {
        return exchangeApi.exchange(
            request = ExchangeRequest(
                transactionBalance = transactionBalance,
                pinNumber = pinNumber
            )
        )
    }

    override suspend fun exchangeForeign(transactionBalance: Long, pinNumber: String): Response<ExchangeResponse> {
        return exchangeApi.exchangeForeign(
            request = ExchangeRequest(
                transactionBalance = transactionBalance,
                pinNumber = pinNumber
            )
        )
    }

    override suspend fun getAiPrediction(): Response<ExchangeAiPredictionResponse> {
        return exchangeApi.getAiPrediction()
    }

    override suspend fun getHistoricalAnalysis(): Response<ExchangeHistoricalAnalysisResponse> {
        return exchangeApi.getHistoricalAnalysis()
    }
}

