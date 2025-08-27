package com.ssafy.transfer.data

import com.ssafy.transfer.api.TransferApi
import com.ssafy.transfer.api.request.TransferRequest
import com.ssafy.transfer.api.response.TransferResponse
import retrofit2.Response
import javax.inject.Inject

class TransferDataSourceImpl @Inject constructor(
    private val transferApi: TransferApi,
) : TransferDataSource {
    override suspend fun transferDomestic(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
        pinNumber: String,
    ): Response<TransferResponse> {
        return transferApi.transferDomestic(
            TransferRequest(
                depositAccountNo = depositAccountNo,
                transactionSummary = transactionSummary,
                amount = amount,
                pinNumber = pinNumber,
            )
        )
    }

    override suspend fun transferForeign(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
        pinNumber: String,
    ): Response<TransferResponse> {
        return transferApi.transferForeign(
            TransferRequest(
                depositAccountNo = depositAccountNo,
                transactionSummary = transactionSummary,
                amount = amount,
                pinNumber = pinNumber,
            )
        )
    }
}
