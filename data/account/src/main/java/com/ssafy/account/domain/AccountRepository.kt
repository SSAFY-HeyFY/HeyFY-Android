package com.ssafy.account.domain

import com.ssafy.account.domain.model.AccountAuth
import com.ssafy.account.domain.model.AccountCheck
import com.ssafy.account.domain.model.TransactionHistory
import com.ssafy.account.domain.model.TransactionHistoryRequest

interface AccountRepository {
    suspend fun getTransactionHistory(request: TransactionHistoryRequest): Result<TransactionHistory>
    suspend fun getForeignTransactionHistory(request: TransactionHistoryRequest): Result<TransactionHistory>
    suspend fun getAccountAuth(accountNo: String): Result<AccountAuth>
    suspend fun checkAccount(accountNo: String, authCode: String): Result<AccountCheck>
}
