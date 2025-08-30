package com.ssafy.exchange.data

import com.ssafy.common.utils.ApiUtils.safeApiCall
import com.ssafy.exchange.api.response.ExchangeResponse
import com.ssafy.exchange.api.response.ExchangeReservationResponse
import com.ssafy.exchange.api.response.ExchangeReservationHistoryResponse
import com.ssafy.exchange.domain.ExchangeRepository
import com.ssafy.exchange.domain.model.Exchange
import com.ssafy.exchange.domain.model.ExchangeReservationHistory
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

    override suspend fun getReservationHistory(): Result<List<ExchangeReservationHistory>> {
        return safeApiCall(
            apiCall = { exchangeDataSource.getReservationHistory() },
            convert = { it.toExchangeReservationHistoryList() }
        )
    }

    override suspend fun cancelReservation(reservationId: Int, pinNumber: String): Result<Boolean> {
        return safeApiCall(
            apiCall = { exchangeDataSource.cancelReservation(reservationId, pinNumber) },
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

    private fun ExchangeReservationHistoryResponse.toExchangeReservationHistoryList(): List<ExchangeReservationHistory> {
        return data.map { it.toExchangeReservationHistory() }
    }

    private fun ExchangeReservationHistoryResponse.ExchangeReservationHistoryItem.toExchangeReservationHistory(): ExchangeReservationHistory {
        return ExchangeReservationHistory(
            reservationId = reservationId,
            currency = currency,
            amount = amount,
            baseExchangeRate = baseExchangeRate,
            createdAt = createdAt,
            exchangeCompleted = exchangeCompleted,
            canceled = canceled
        )
    }
}
