package com.ssafy.transfer.data

import com.ssafy.common.utils.ApiUtils.safeApiCall
import com.ssafy.transfer.domain.TransferRepository
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val transferDataSource: TransferDataSource,
) : TransferRepository {

    override suspend fun transferDomestic(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
        pinNumber: String,
    ): Result<Boolean> {
        return safeApiCall(
            apiCall = {
                transferDataSource.transferDomestic(
                    depositAccountNo = depositAccountNo,
                    transactionSummary = transactionSummary,
                    amount = amount,
                    pinNumber = pinNumber
                )
            },
            convert = { it.isCorrect }
        )
    }

    override suspend fun transferForeign(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
        pinNumber: String,
    ): Result<Boolean> {
        return safeApiCall(
            apiCall = {
                transferDataSource.transferForeign(
                    depositAccountNo = depositAccountNo,
                    transactionSummary = transactionSummary,
                    amount = amount,
                    pinNumber = pinNumber,
                )
            },
            convert = { it.isCorrect }
        )
    }
}
