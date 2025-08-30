package com.ssafy.exchange.domain

import com.ssafy.exchange.domain.model.Exchange
import com.ssafy.exchange.domain.model.ExchangeReservation
import com.ssafy.exchange.domain.model.ExchangeReservationHistory

interface ExchangeRepository {
    suspend fun exchange(transactionBalance: Long, pinNumber: String): Result<Exchange>
    suspend fun exchangeForeign(transactionBalance: Long, pinNumber: String): Result<Exchange>
    suspend fun exchangeReservation(transactionBalance: Double, currency: String, pinNumber: String, baseExchangeRate: Double): Result<Boolean>
    suspend fun getReservationHistory(): Result<List<ExchangeReservationHistory>>
    suspend fun cancelReservation(reservationId: Int, pinNumber: String): Result<Boolean>
    suspend fun getHistoricalAnalysis(): Result<Pair<String, String>>
}

