package com.ssafy.exchange.domain

import javax.inject.Inject

class ExchangeReservationUseCase @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) {
    suspend operator fun invoke(transactionBalance: Double, currency: String, pinNumber: String, baseExchangeRate: Double): Result<Boolean> {
        return exchangeRepository.exchangeReservation(transactionBalance, currency, pinNumber, baseExchangeRate)
    }
}
