package com.ssafy.account.api

import com.ssafy.account.api.request.AccountAuthRequest
import com.ssafy.account.api.request.AccountCheckRequest
import com.ssafy.account.api.request.TransactionHistoryRequest
import com.ssafy.account.api.response.AccountAuthResponse
import com.ssafy.account.api.response.AccountCheckResponse
import com.ssafy.account.api.response.ExchangeHistoryResponse
import com.ssafy.account.api.response.ForeignTransactionHistoryResponse
import com.ssafy.account.api.response.TransactionHistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountApi {

    @POST("/inquire/transactionhistory")
    suspend fun getTransactionHistory(
        @Body request: TransactionHistoryRequest
    ): Response<TransactionHistoryResponse>

    @POST("/inquire/foreigntransactionhistory")
    suspend fun getForeignTransactionHistory(
        @Body request: TransactionHistoryRequest
    ): Response<ForeignTransactionHistoryResponse>

    @POST("/inquire/exchangehistory")
    suspend fun getExchangeHistory(): Response<ExchangeHistoryResponse>

    @POST("/accountauth")
    suspend fun getAccountAuth(
        @Body request: AccountAuthRequest
    ): Response<AccountAuthResponse>

    @POST("/accouncheck")
    suspend fun checkAccount(
        @Body request: AccountCheckRequest
    ): Response<AccountCheckResponse>
}
