package com.ssafy.transfer.api.response

data class TransferResponse(
    val success: Boolean,
    val history: History,
    val error: String,
) {
    data class History(
        val fromAccountMasked: String,
        val toAccountMasked: String,
        val amount: String,
        val currency: String,
        val completedAt: String,
    )
}
