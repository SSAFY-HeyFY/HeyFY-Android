package com.ssafy.transfer.domain

import javax.inject.Inject

class TransferDomesticUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend operator fun invoke(
        depositAccountNo: String,
        transactionSummary: String,
        amount: String,
    ): Result<Boolean> {
        return transferRepository.transferDomestic(depositAccountNo, transactionSummary,amount)
    }
}
