package com.ssafy.transfer.data

import com.ssafy.transfer.api.response.TransferResponse
import retrofit2.Response

interface TransferDataSource {
    suspend fun transferDomestic(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
        pinNumber: String,
    ): Response<TransferResponse>

    suspend fun transferForeign(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
        pinNumber: String,
    ): Response<TransferResponse>
}
