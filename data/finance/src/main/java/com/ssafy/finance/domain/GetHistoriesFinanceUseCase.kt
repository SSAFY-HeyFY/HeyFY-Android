package com.ssafy.finance.domain

import com.ssafy.finance.domain.model.ExchangeRateHistories
import javax.inject.Inject

class GetHistoriesFinanceUseCase @Inject constructor(
    private val financeRepository: FinanceRepository,
) {
    suspend operator fun invoke(): Result<ExchangeRateHistories> {
        return financeRepository.getExchangeRateHistories()
    }
}
