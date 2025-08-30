package com.ssafy.exchange.data

import com.ssafy.exchange.api.ExchangeApi
import com.ssafy.exchange.api.request.ExchangeRequest
import com.ssafy.exchange.api.request.ExchangeReservationRequest
import com.ssafy.exchange.api.request.ExchangeReservationCancelRequest
import com.ssafy.exchange.api.response.ExchangeHistoricalAnalysisResponse
import com.ssafy.exchange.api.response.ExchangeReservationResponse
import com.ssafy.exchange.api.response.ExchangeReservationHistoryResponse
import com.ssafy.exchange.api.response.ExchangeResponse
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

    override suspend fun exchangeReservation(transactionBalance: Double, currency: String, pinNumber: String, baseExchangeRate: Double): Response<ExchangeReservationResponse> {
        return exchangeApi.exchangeReservation(
            request = ExchangeReservationRequest(
                transactionBalance = transactionBalance,
                currency = currency,
                pinNumber = pinNumber,
                baseExchangeRate = baseExchangeRate
            )
        )
    }

    override suspend fun getReservationHistory(): Response<ExchangeReservationHistoryResponse> {
        return exchangeApi.getReservationHistory()
    }

    override suspend fun cancelReservation(reservationId: Int, pinNumber: String): Response<ExchangeReservationResponse> {
        return exchangeApi.cancelReservation(
            request = ExchangeReservationCancelRequest(
                reservationId = reservationId,
                pinNumber = pinNumber
            )
        )
    }

    override suspend fun getHistoricalAnalysis(): Response<ExchangeHistoricalAnalysisResponse> {
        return exchangeApi.getHistoricalAnalysis()
    }
}
