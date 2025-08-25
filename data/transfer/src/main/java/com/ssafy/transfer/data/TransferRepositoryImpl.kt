package com.ssafy.transfer.data

import com.ssafy.network.utils.ApiUtils.safeApiCall
import com.ssafy.transfer.domain.TransferRepository
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val transferDataSource: TransferDataSource,
) : TransferRepository {

    override suspend fun transferDomestic(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
    ): Result<Boolean> {
        return safeApiCall(
            apiCall = {
                transferDataSource.transferDomestic(
                    depositAccountNo = depositAccountNo,
                    transactionSummary = transactionSummary,
                    amount = amount
                )
            },
            convert = { it.success }
        )
    }

    override suspend fun transferForeign(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
    ): Result<Boolean> {
        return safeApiCall(
            apiCall = {
                transferDataSource.transferForeign(
                    depositAccountNo = depositAccountNo,
                    transactionSummary = transactionSummary,
                    amount = amount,
                )
            },
            convert = { it.success }
        )
    }
}
