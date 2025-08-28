package com.ssafy.transfer.api.response

data class TransferResponse(
    val depositAccountNo: Boolean,
    val amount: String,
    val currency: String,
    val transactionSummary: String,
    val completedAt: String,
    val isCorrect: Boolean,
)
