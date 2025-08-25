package com.ssafy.account.domain

import com.ssafy.account.domain.model.TransactionHistory
import com.ssafy.account.domain.model.TransactionHistoryRequest
import javax.inject.Inject

class GetForeignTransactionHistoryUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(accountNo: String): Result<TransactionHistory> {
        return accountRepository.getForeignTransactionHistory(accountNo)
    }
}
