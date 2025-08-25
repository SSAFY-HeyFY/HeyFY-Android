package com.ssafy.account.domain

import com.ssafy.account.domain.model.AccountCheck
import javax.inject.Inject

class CheckAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(accountNo: String, authCode: String): Result<AccountCheck> {
        return accountRepository.checkAccount(accountNo, authCode)
    }
}
