package com.ssafy.account.domain

import com.ssafy.account.domain.model.ExchangeHistory
import javax.inject.Inject

class GetExchangeHistoryUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Result<List<ExchangeHistory>> {
        return accountRepository.getExchangeHistory()
    }
}
