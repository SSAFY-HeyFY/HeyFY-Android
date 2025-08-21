package com.ssafy.finance.domain

import com.ssafy.finance.domain.model.ExchangeRateTuition
import javax.inject.Inject

class GetTuitionFinanceUseCase @Inject constructor(
    private val financeRepository: FinanceRepository,
) {
    suspend operator fun invoke(): Result<ExchangeRateTuition> {
        return financeRepository.getExchangeRateTuition()
    }
}
