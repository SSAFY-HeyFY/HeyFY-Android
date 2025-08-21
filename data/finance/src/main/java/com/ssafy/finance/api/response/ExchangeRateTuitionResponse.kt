package com.ssafy.finance.api.response

data class ExchangeRateTuitionResponse(
    val period: Period,
    val recommendedDate: String,
    val recommendationNote: String,
) {
    data class Period(
        val start: String,
        val end: String,
    )
}
