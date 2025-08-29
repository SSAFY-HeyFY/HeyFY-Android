package com.ssafy.exchange.api.response

data class ExchangeHistoricalAnalysisResponse(
    val todayRate: Double,
    val finalPredictedRate: Double,
    val historicalAnalysis : HistoricalAnalysis,
    val aiPrediction : AiPrediction,
) {
    data class HistoricalAnalysis(
        val message: String,
    )

    data class AiPrediction(
        val message: String
    )
}



