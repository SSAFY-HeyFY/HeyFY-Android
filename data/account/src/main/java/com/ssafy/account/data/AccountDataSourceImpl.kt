package com.ssafy.account.data

import com.ssafy.account.api.AccountApi
import com.ssafy.account.api.request.AccountAuthRequest
import com.ssafy.account.api.request.AccountCheckRequest
import com.ssafy.account.api.request.TransactionHistoryRequest
import com.ssafy.account.api.response.AccountAuthResponse
import com.ssafy.account.api.response.AccountCheckResponse
import com.ssafy.account.api.response.ForeignTransactionHistoryResponse
import com.ssafy.account.api.response.TransactionHistoryResponse
import retrofit2.Response
import javax.inject.Inject

class AccountDataSourceImpl @Inject constructor(
    private val accountApi: AccountApi
) : AccountDataSource {

    override suspend fun getTransactionHistory(request: TransactionHistoryRequest): Response<TransactionHistoryResponse> {
        return accountApi.getTransactionHistory(request)
    }

    override suspend fun getForeignTransactionHistory(request: TransactionHistoryRequest): Response<ForeignTransactionHistoryResponse> {
        return accountApi.getForeignTransactionHistory(request)
    }

    override suspend fun getAccountAuth(request: AccountAuthRequest): Response<AccountAuthResponse> {
        return accountApi.getAccountAuth(request)
    }

    override suspend fun checkAccount(request: AccountCheckRequest): Response<AccountCheckResponse> {
        return accountApi.checkAccount(request)
    }
}
