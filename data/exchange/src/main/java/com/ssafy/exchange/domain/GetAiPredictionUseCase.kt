package com.ssafy.exchange.domain

import javax.inject.Inject

class GetAiPredictionUseCase @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) {
    suspend operator fun invoke(): Result<String> {
        return exchangeRepository.getAiPrediction()
    }
}
