package com.ssafy.transfer.api.request

data class TransferRequest(
    val withdrawalAccountNo: String,
    val depositAccountNo: String,
    val amount: Int,
)
