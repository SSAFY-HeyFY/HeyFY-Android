package com.ssafy.exchange.domain.model

data class Exchange(
    val depositAccountBalance: Double,
    val withdrawalAccountBalance: Double,
    val transactionBalance: Double,
    val isCorrect: Boolean,
)

