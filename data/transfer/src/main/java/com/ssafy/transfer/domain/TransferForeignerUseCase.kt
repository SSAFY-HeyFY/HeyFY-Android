package com.ssafy.transfer.domain

import javax.inject.Inject

class TransferForeignerUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend operator fun invoke(
        depositAccountNo: String,
        amount: Int,
    ): Result<Boolean> {
        return transferRepository.transferForeign(
            depositAccountNo,
            amount
        )
    }
}
