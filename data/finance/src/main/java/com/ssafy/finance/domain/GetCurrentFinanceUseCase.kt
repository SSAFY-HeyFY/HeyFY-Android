package com.ssafy.finance.domain

import com.ssafy.finance.domain.model.ExchangeRateCurrent
import javax.inject.Inject

class GetCurrentFinanceUseCase @Inject constructor(
    private val financeRepository: FinanceRepository,
) {
    suspend operator fun invoke(): Result<ExchangeRateCurrent> {
        return financeRepository.getExchangeRateCurrent()
    }
}
