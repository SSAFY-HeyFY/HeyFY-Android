package com.ssafy.finance.domain.model

data class ExchangeRateTuition(
    val period: Period,
    val recommendedDate: String,
    val recommendationNote: String,
) {
    data class Period(
        val start: String,
        val end: String,
    )
}

data class ExchangeRatePrediction(
    val trend: String,
    val description: String,
    val changePercent: Double,
    val periodDays: Int,
    val actionLabel: String,
)

data class ExchangeRateHistories(
    val currency: String,
    val rates: List<Rate>,
) {
    data class Rate(
        val currency: String,
        val date: String,
        val exchangeRate: Double,
    )
}

data class ExchangeRateCurrent(
    val usd: ExchangeRate,
    val cny: ExchangeRate,
    val vnd: ExchangeRate,
) {
    data class ExchangeRate(
        val currency: String,
        val date: String,
        val exchangeRate: Double,
    )
}
