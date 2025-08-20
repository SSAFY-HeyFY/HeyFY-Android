package com.ssafy.transfer.domain

interface TransferRepository {

    suspend fun transfer(
        withdrawalAccountNo: String,
        depositAccountNo: String,
        amount: Int,
    ): Result<Boolean>

}
