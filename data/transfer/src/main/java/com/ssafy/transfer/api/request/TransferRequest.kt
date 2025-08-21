package com.ssafy.transfer.api.request

data class TransferRequest(
    val depositAccountNo: String,
    val amount: Int,
)
