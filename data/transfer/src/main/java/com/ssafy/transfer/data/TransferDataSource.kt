package com.ssafy.transfer.data

import com.ssafy.transfer.api.response.TransferResponse
import retrofit2.Response

interface TransferDataSource {
    suspend fun transfer(
        withdrawalAccountNo: String,
        depositAccountNo: String,
        amount: Int,
    ): Response<TransferResponse>
}
