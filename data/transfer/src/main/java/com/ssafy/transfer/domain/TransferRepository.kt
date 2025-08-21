package com.ssafy.transfer.domain

interface TransferRepository {

    suspend fun transferDomestic(
        depositAccountNo: String,
        amount: Int,
    ): Result<Boolean>


    suspend fun transferForeign(
        depositAccountNo: String,
        amount: Int,
    ): Result<Boolean>
}
