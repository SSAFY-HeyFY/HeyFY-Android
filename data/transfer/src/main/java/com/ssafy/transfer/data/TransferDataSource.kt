package com.ssafy.transfer.data

import com.ssafy.transfer.api.response.TransferResponse
import retrofit2.Response

interface TransferDataSource {
    suspend fun transferDomestic(
        depositAccountNo: String,
        amount: Int,
    ): Response<TransferResponse>

    suspend fun transferForeign(
        depositAccountNo: String,
        amount: Int,
    ): Response<TransferResponse>
}
