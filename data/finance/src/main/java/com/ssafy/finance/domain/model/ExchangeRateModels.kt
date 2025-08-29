package com.ssafy.finance.domain.model

data class ExchangeRateTuition(
    val period: Period = Period(),
    val recommendedDate: String = "",
    val recommendationNote: String = "",
) {
    data class Period(
        val start: String = "",
        val end: String = "",
    )
}

data class ExchangeRatePrediction(
    val trend: String = "",
    val description: String = "",
    val changePercent: Double = 0.0,
    val periodDays: Int = 0,
)

data class ExchangeRateHistories(
    val currency: String = "",
    val rates: List<Rate> = listOf(),
) {
    data class Rate(
        val currency: String = "",
        val date: String = "",
        val rate: Double = 0.0,
        val modelName: String = "",
        val prediction: Boolean = false,
    )
}

data class ExchangeRateCurrent(
    val usd: ExchangeRate = ExchangeRate(),
    val cny: ExchangeRate = ExchangeRate(),
    val vnd: ExchangeRate = ExchangeRate(),
) {
    data class ExchangeRate(
        val currency: String = "",
        val updatedAt: String = "",
        val rate: Double = 0.0,
        val fluctuation: Double = 0.0,
    )
}
