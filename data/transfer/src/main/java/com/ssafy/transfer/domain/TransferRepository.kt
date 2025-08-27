package com.ssafy.transfer.domain

interface TransferRepository {

    suspend fun transferDomestic(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
        pinNumber: String,
    ): Result<Unit>


    suspend fun transferForeign(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
        pinNumber: String,
    ): Result<Unit>
}
