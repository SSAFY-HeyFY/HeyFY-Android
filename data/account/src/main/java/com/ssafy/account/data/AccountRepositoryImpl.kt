package com.ssafy.account.data

import com.ssafy.account.api.response.AccountAuthResponse
import com.ssafy.account.api.response.AccountCheckResponse
import com.ssafy.account.api.response.ExchangeHistoryResponse
import com.ssafy.account.api.response.ForeignTransactionHistoryResponse
import com.ssafy.account.api.response.TransactionHistoryResponse
import com.ssafy.account.domain.AccountRepository
import com.ssafy.account.domain.model.AccountAuth
import com.ssafy.account.domain.model.AccountCheck
import com.ssafy.account.domain.model.ExchangeHistory
import com.ssafy.account.domain.model.TransactionHistory
import com.ssafy.common.text.TextFormat.formatCurrencyKRW
import com.ssafy.common.text.TextFormat.formatCurrencyUSD
import com.ssafy.common.utils.ApiUtils
import com.ssafy.common.utils.ApiUtils.safeApiCall
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDataSource: AccountDataSource,
) : AccountRepository {

    override suspend fun getTransactionHistory(accountNo: String): Result<TransactionHistory> {
        return safeApiCall(
            apiCall = { accountDataSource.getTransactionHistory(accountNo) },
            convert = { it.toTransactionHistory() }
        )
    }

    override suspend fun getForeignTransactionHistory(accountNo: String): Result<TransactionHistory> {
        return safeApiCall(
            apiCall = { accountDataSource.getForeignTransactionHistory(accountNo) },
            convert = { it.toTransactionHistory() }
        )
    }

    override suspend fun getExchangeHistory(): Result<List<ExchangeHistory>> {
        return safeApiCall(
            apiCall = { accountDataSource.getExchangeHistory() },
            convert = { it.toExchangeHistoryList() }
        )
    }

    override suspend fun getAccountAuth(accountNo: String): Result<AccountAuth> {
        return safeApiCall(
            apiCall = { accountDataSource.getAccountAuth(accountNo) },
            convert = { it.toAccountAuth() }
        )
    }

    override suspend fun checkAccount(accountNo: String, authCode: String): Result<AccountCheck> {
        return safeApiCall(
            apiCall = { accountDataSource.checkAccount(code = authCode, accountNo = accountNo) },
            convert = { it.toAccountCheck() }
        )
    }

    private fun TransactionHistoryResponse.toTransactionHistory(): TransactionHistory {
        return TransactionHistory(
            totalCount = totalCount.toIntOrNull() ?: 0,
            list = list.map { it.toTransactionHistoryItem() }
        )
    }

    private fun TransactionHistoryResponse.Item.toTransactionHistoryItem(): TransactionHistory.Item {
        return TransactionHistory.Item(
            id = transactionUniqueNo,
            title = transactionSummary,
            date = transactionDate,
            isIncome = transactionType == "1",
            amount = "₩${formatCurrencyKRW(transactionBalance.toLong())}",
        )
    }

    private fun ForeignTransactionHistoryResponse.toTransactionHistory(): TransactionHistory {
        return TransactionHistory(
            totalCount = totalCount.toIntOrNull() ?: 0,
            list = list.map { it.toTransactionHistoryItem() }
        )
    }

    private fun ForeignTransactionHistoryResponse.Item.toTransactionHistoryItem(): TransactionHistory.Item {
        return TransactionHistory.Item(
            id = transactionUniqueNo,
            title = transactionSummary,
            date = transactionDate,
            isIncome = transactionType == "1",
            amount = "$${formatCurrencyUSD(transactionBalance.toDouble())}",
        )
    }

    private fun ExchangeHistoryResponse.toExchangeHistoryList(): List<ExchangeHistory> {
        return list.map { it.toExchangeHistory() }
    }

    private fun ExchangeHistoryResponse.ExchangeHistoryItem.toExchangeHistory(): ExchangeHistory {
        return ExchangeHistory(
            fromAccountNo = fromAccountNo,
            toAccountNo = toAccountNo,
            currency = currency,
            currencyName = currencyName,
            amount = amount,
            exchangeCurrency = exchangeCurrency,
            exchangeCurrencyName = exchangeCurrencyName,
            exchangeAmount = exchangeAmount,
            exchangeRate = exchangeRate,
            created = created
        )
    }

    private fun AccountAuthResponse.toAccountAuth(): AccountAuth {
        return AccountAuth(
            code = code,
            accountNo = accountNo
        )
    }

    private fun AccountCheckResponse.toAccountCheck(): AccountCheck {
        return AccountCheck(
            accountNo = accountNo
        )
    }
}
