package com.ssafy.finance.domain

import com.ssafy.finance.domain.model.ExchangeRatePrediction
import javax.inject.Inject

class GetPredictionFinanceUseCase @Inject constructor(
    private val financeRepository: FinanceRepository,
) {
    suspend operator fun invoke(): Result<ExchangeRatePrediction> {
        return financeRepository.getExchangeRatePrediction()
    }
}
