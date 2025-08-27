package com.ssafy.exchange.domain

import com.ssafy.exchange.domain.model.Exchange
import javax.inject.Inject

class ExchangeForeignUseCase @Inject constructor(
    private val exchangeRepository: ExchangeRepository,
) {
    suspend operator fun invoke(transactionBalance: Int): Result<Exchange> {
        return exchangeRepository.exchangeForeign(transactionBalance)
    }
}

