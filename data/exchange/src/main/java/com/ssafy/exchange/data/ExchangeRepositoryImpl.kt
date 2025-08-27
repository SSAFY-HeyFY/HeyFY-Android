package com.ssafy.exchange.data

import com.ssafy.exchange.api.response.ExchangeResponse
import com.ssafy.exchange.domain.ExchangeRepository
import com.ssafy.exchange.domain.model.Exchange
import com.ssafy.network.utils.ApiUtils
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(
    private val exchangeDataSource: ExchangeDataSource,
) : ExchangeRepository {

    override suspend fun exchange(transactionBalance: Int, pinNumber: String): Result<Exchange> {
        return ApiUtils.safeApiCall(
            apiCall = { exchangeDataSource.exchange(transactionBalance, pinNumber) },
            convert = { it.toExchange() }
        )
    }

    override suspend fun exchangeForeign(transactionBalance: Int, pinNumber: String): Result<Exchange> {
        return ApiUtils.safeApiCall(
            apiCall = { exchangeDataSource.exchangeForeign(transactionBalance, pinNumber) },
            convert = { it.toExchange() }
        )
    }

    override suspend fun getAiPrediction(): Result<String> {
        return ApiUtils.safeApiCall(
            apiCall = { exchangeDataSource.getAiPrediction() },
            convert = { it.message }
        )
    }

    override suspend fun getHistoricalAnalysis(): Result<String> {
        return ApiUtils.safeApiCall(
            apiCall = { exchangeDataSource.getHistoricalAnalysis() },
            convert = { it.message }
        )
    }

    private fun ExchangeResponse.toExchange(): Exchange {
        return Exchange(
            depositAccountBalance = depositAccountBalance,
            withdrawalAccountBalance = withdrawalAccountBalance,
            transactionBalance = transactionBalance,
        )
    }
}
