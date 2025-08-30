package com.ssafy.account.data

import com.ssafy.account.api.request.AccountAuthRequest
import com.ssafy.account.api.request.AccountCheckRequest
import com.ssafy.account.api.request.TransactionHistoryRequest
import com.ssafy.account.api.response.AccountAuthResponse
import com.ssafy.account.api.response.AccountCheckResponse
import com.ssafy.account.api.response.ExchangeHistoryResponse
import com.ssafy.account.api.response.ForeignTransactionHistoryResponse
import com.ssafy.account.api.response.TransactionHistoryResponse
import retrofit2.Response

interface AccountDataSource {
    suspend fun getTransactionHistory(accountNo: String): Response<TransactionHistoryResponse>
    suspend fun getForeignTransactionHistory(accountNo: String): Response<ForeignTransactionHistoryResponse>
    suspend fun getExchangeHistory(): Response<ExchangeHistoryResponse>
    suspend fun getAccountAuth(accountNo: String): Response<AccountAuthResponse>
    suspend fun checkAccount(code: String , accountNo: String): Response<AccountCheckResponse>
}
