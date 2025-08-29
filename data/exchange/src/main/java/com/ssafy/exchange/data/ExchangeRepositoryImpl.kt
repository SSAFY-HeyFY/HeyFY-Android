package com.ssafy.exchange.data

import com.ssafy.common.utils.ApiUtils.safeApiCall
import com.ssafy.exchange.api.response.ExchangeResponse
import com.ssafy.exchange.domain.ExchangeRepository
import com.ssafy.exchange.domain.model.Exchange
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(
    private val exchangeDataSource: ExchangeDataSource,
) : ExchangeRepository {

    override suspend fun exchange(transactionBalance: Long, pinNumber: String): Result<Exchange> {
        return safeApiCall(
            apiCall = { exchangeDataSource.exchange(transactionBalance, pinNumber) },
            convert = { it.toExchange() }
        )
    }

    override suspend fun exchangeForeign(transactionBalance: Long, pinNumber: String): Result<Exchange> {
        return safeApiCall(
            apiCall = { exchangeDataSource.exchangeForeign(transactionBalance, pinNumber) },
            convert = { it.toExchange() }
        )
    }

    override suspend fun getHistoricalAnalysis(): Result<Pair<String, String>> {
        return safeApiCall(
            apiCall = { exchangeDataSource.getHistoricalAnalysis() },
            convert = { it.historicalAnalysis.message to it.aiPrediction.message }
        )
    }

    private fun ExchangeResponse.toExchange(): Exchange {
        return Exchange(
            depositAccountBalance = depositAccountBalance,
            withdrawalAccountBalance = withdrawalAccountBalance,
            transactionBalance = transactionBalance,
            isCorrect = isCorrect
        )
    }
}
