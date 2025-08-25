package com.ssafy.transfer.api.request

data class TransferRequest(
    val depositAccountNo: String,
    val transactionSummary: String,
    val amount: String,
)
