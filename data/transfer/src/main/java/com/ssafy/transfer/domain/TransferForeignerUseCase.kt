package com.ssafy.transfer.domain

import javax.inject.Inject

class TransferForeignerUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend operator fun invoke(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
        pinNumber: String,
    ): Result<Unit> {
        return transferRepository.transferForeign(
            depositAccountNo,
            transactionSummary,
            amount,
            pinNumber
        )
    }
}
