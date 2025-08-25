package com.ssafy.account.domain

import com.ssafy.account.domain.model.AccountAuth
import javax.inject.Inject

class GetAccountAuthUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(accountNo: String): Result<AccountAuth> {
        return accountRepository.getAccountAuth(accountNo)
    }
}
