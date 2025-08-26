package com.ssafy.finance.api.response

data class ExchangeRateCurrentResponse(
    val usd: ExchangeRate,
    val cny: ExchangeRate,
    val vnd: ExchangeRate,
) {
    data class ExchangeRate(
        val currency: String,
        val updatedAt: String,
        val rate: Double,
        val fluctuation: Double,
    )
}
