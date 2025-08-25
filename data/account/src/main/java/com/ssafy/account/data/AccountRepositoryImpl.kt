package com.ssafy.account.data

import com.ssafy.account.api.request.AccountAuthRequest
import com.ssafy.account.api.request.AccountCheckRequest
import com.ssafy.account.api.request.TransactionHistoryRequest
import com.ssafy.account.data.mapper.toDomainModel
import com.ssafy.account.domain.AccountRepository
import com.ssafy.account.domain.model.AccountAuth
import com.ssafy.account.domain.model.AccountCheck
import com.ssafy.account.domain.model.TransactionHistory
import com.ssafy.network.utils.ApiUtils
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDataSource: AccountDataSource
) : AccountRepository {

    override suspend fun getTransactionHistory(request: TransactionHistoryRequest): Result<TransactionHistory> {
        return ApiUtils.safeApiCall(
            apiCall = { accountDataSource.getTransactionHistory(request) },
            convert = { it.toDomainModel() }
        )
    }

    override suspend fun getForeignTransactionHistory(request: TransactionHistoryRequest): Result<TransactionHistory> {
        return ApiUtils.safeApiCall(
            apiCall = { accountDataSource.getForeignTransactionHistory(request) },
            convert = { it.toDomainModel() }
        )
    }

    override suspend fun getAccountAuth(accountNo: String): Result<AccountAuth> {
        return ApiUtils.safeApiCall(
            apiCall = { accountDataSource.getAccountAuth(AccountAuthRequest(accountNo)) },
            convert = { it.toDomainModel() }
        )
    }

    override suspend fun checkAccount(accountNo: String, authCode: String): Result<AccountCheck> {
        return ApiUtils.safeApiCall(
            apiCall = { accountDataSource.checkAccount(AccountCheckRequest(accountNo, authCode)) },
            convert = { it.toDomainModel() }
        )
    }
}
