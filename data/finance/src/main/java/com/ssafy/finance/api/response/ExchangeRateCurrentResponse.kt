package com.ssafy.finance.api.response

data class ExchangeRateCurrentResponse(
    val usd: ExchangeRate,
    val cny: ExchangeRate,
    val vnd: ExchangeRate?,
) {
    data class ExchangeRate(
        val currency: String,
        val date: String,
        val exchangeRate: Double,
        val fluctuationRate: Double,
    )
}
