package com.ssafy.transfer.domain

import javax.inject.Inject

class TransferUseCase @Inject constructor(
    private val transferRepository: TransferRepository
) {
    suspend operator fun invoke(
        withdrawalAccountNo: String,
        depositAccountNo: String,
        amount: Int
    ): Result<Boolean> {
        return transferRepository.transfer(
            withdrawalAccountNo = withdrawalAccountNo,
            depositAccountNo = depositAccountNo,
            amount = amount,
        )
    }
}
