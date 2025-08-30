package com.ssafy.account.data

import com.ssafy.account.api.AccountApi
import com.ssafy.account.api.request.AccountAuthRequest
import com.ssafy.account.api.request.AccountCheckRequest
import com.ssafy.account.api.request.TransactionHistoryRequest
import com.ssafy.account.api.response.AccountAuthResponse
import com.ssafy.account.api.response.AccountCheckResponse
import com.ssafy.account.api.response.ExchangeHistoryResponse
import com.ssafy.account.api.response.ForeignTransactionHistoryResponse
import com.ssafy.account.api.response.TransactionHistoryResponse
import retrofit2.Response
import javax.inject.Inject

class AccountDataSourceImpl @Inject constructor(
    private val accountApi: AccountApi
) : AccountDataSource {

    override suspend fun getTransactionHistory(accountNo: String): Response<TransactionHistoryResponse> {
        return accountApi.getTransactionHistory(TransactionHistoryRequest(accountNo))
    }

    override suspend fun getForeignTransactionHistory(accountNo: String): Response<ForeignTransactionHistoryResponse> {
        return accountApi.getForeignTransactionHistory(TransactionHistoryRequest(accountNo))
    }

    override suspend fun getExchangeHistory(): Response<ExchangeHistoryResponse> {
        return accountApi.getExchangeHistory()
    }

    override suspend fun getAccountAuth(accountNo: String): Response<AccountAuthResponse> {
        return accountApi.getAccountAuth(AccountAuthRequest(accountNo))
    }

    override suspend fun checkAccount(code: String , accountNo: String): Response<AccountCheckResponse> {
        return accountApi.checkAccount(AccountCheckRequest(accountNo, code))
    }
}
