package com.ssafy.finance.api.response

data class ExchangeRatePredictionResponse(
    val trend: String,
    val description: String,
    val changePercent: Double,
    val periodDays: Int,
    val actionLabel: String,
)
