package com.ssafy.exchange.domain

import javax.inject.Inject

class CancelReservationUseCase @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) {
    suspend operator fun invoke(reservationId: Int, pinNumber: String): Result<Boolean> {
        return exchangeRepository.cancelReservation(reservationId, pinNumber)
    }
}
