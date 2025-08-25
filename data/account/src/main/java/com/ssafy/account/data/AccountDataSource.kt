package com.ssafy.account.data

import com.ssafy.account.api.request.AccountAuthRequest
import com.ssafy.account.api.request.AccountCheckRequest
import com.ssafy.account.api.request.TransactionHistoryRequest
import com.ssafy.account.api.response.AccountAuthResponse
import com.ssafy.account.api.response.AccountCheckResponse
import com.ssafy.account.api.response.ForeignTransactionHistoryResponse
import com.ssafy.account.api.response.TransactionHistoryResponse
import retrofit2.Response

interface AccountDataSource {
    suspend fun getTransactionHistory(request: TransactionHistoryRequest): Response<TransactionHistoryResponse>
    suspend fun getForeignTransactionHistory(request: TransactionHistoryRequest): Response<ForeignTransactionHistoryResponse>
    suspend fun getAccountAuth(request: AccountAuthRequest): Response<AccountAuthResponse>
    suspend fun checkAccount(request: AccountCheckRequest): Response<AccountCheckResponse>
}
