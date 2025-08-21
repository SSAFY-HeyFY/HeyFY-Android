package com.ssafy.finance.api.response

data class ExchangeRateHistoriesResponse(
    val currency: String,
    val rates: List<Rate>,
) {
    data class Rate(
        val currency: String,
        val date: String,
        val exchangeRate: Double,
    )
}
