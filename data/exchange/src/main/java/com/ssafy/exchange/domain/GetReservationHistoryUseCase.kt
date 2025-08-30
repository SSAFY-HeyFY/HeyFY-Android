package com.ssafy.exchange.domain

import com.ssafy.exchange.domain.model.ExchangeReservationHistory
import javax.inject.Inject

class GetReservationHistoryUseCase @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) {
    suspend operator fun invoke(): Result<List<ExchangeReservationHistory>> {
        return exchangeRepository.getReservationHistory()
    }
}
