package com.ssafy.exchange.data

import com.ssafy.common.utils.ApiUtils.safeApiCall
import com.ssafy.exchange.api.response.ExchangeResponse
import com.ssafy.exchange.api.response.ExchangeReservationResponse
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

    override suspend fun exchangeReservation(transactionBalance: Double, currency: String, pinNumber: String, baseExchangeRate: Double): Result<Boolean> {
        return safeApiCall(
            apiCall = { exchangeDataSource.exchangeReservation(transactionBalance, currency, pinNumber, baseExchangeRate) },
            convert = { it.success }
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
