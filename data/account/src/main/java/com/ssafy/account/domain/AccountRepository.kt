package com.ssafy.account.domain

import com.ssafy.account.domain.model.AccountAuth
import com.ssafy.account.domain.model.AccountCheck
import com.ssafy.account.domain.model.ExchangeHistory
import com.ssafy.account.domain.model.TransactionHistory
import com.ssafy.account.domain.model.TransactionHistoryRequest

interface AccountRepository {
    suspend fun getTransactionHistory(accountNo: String): Result<TransactionHistory>
    suspend fun getForeignTransactionHistory(accountNo: String): Result<TransactionHistory>
    suspend fun getExchangeHistory(): Result<List<ExchangeHistory>>
    suspend fun getAccountAuth(accountNo: String): Result<AccountAuth>
    suspend fun checkAccount(accountNo: String, authCode: String): Result<AccountCheck>
}
