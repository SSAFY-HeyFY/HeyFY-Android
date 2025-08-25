package com.ssafy.account.data.mapper

import com.ssafy.account.api.response.AccountAuthResponse
import com.ssafy.account.api.response.AccountCheckResponse
import com.ssafy.account.api.response.ForeignTransactionHistoryResponse
import com.ssafy.account.api.response.TransactionHistoryResponse
import com.ssafy.account.domain.model.AccountAuth
import com.ssafy.account.domain.model.AccountCheck
import com.ssafy.account.domain.model.TransactionHistory
import com.ssafy.account.domain.model.TransactionHistoryItem

fun TransactionHistoryResponse.toDomainModel(): TransactionHistory {
    return TransactionHistory(
        totalCount = totalCount.toIntOrNull() ?: 0,
        list = list.map { it.toDomainModel() }
    )
}

fun TransactionHistoryResponse.TransactionHistoryItem.toDomainModel(): TransactionHistoryItem {
    return TransactionHistoryItem(
        transactionUniqueNo = transactionUniqueNo,
        transactionDate = transactionDate,
        transactionTime = transactionTime,
        transactionType = transactionType,
        transactionTypeName = transactionTypeName,
        transactionAccountNo = transactionAccountNo,
        transactionBalance = transactionBalance,
        transactionAfterBalance = transactionAfterBalance,
        transactionSummary = transactionSummary,
        transactionMemo = transactionMemo
    )
}

fun ForeignTransactionHistoryResponse.toDomainModel(): TransactionHistory {
    return TransactionHistory(
        totalCount = totalCount.toIntOrNull() ?: 0,
        list = list.map { it.toDomainModel() }
    )
}

fun ForeignTransactionHistoryResponse.ForeignTransactionHistoryItem.toDomainModel(): TransactionHistoryItem {
    return TransactionHistoryItem(
        transactionUniqueNo = transactionUniqueNo,
        transactionDate = transactionDate,
        transactionTime = transactionTime,
        transactionType = transactionType,
        transactionTypeName = transactionTypeName,
        transactionAccountNo = transactionAccountNo,
        transactionBalance = transactionBalance,
        transactionAfterBalance = transactionAfterBalance,
        transactionSummary = transactionSummary,
        transactionMemo = transactionMemo
    )
}

fun AccountAuthResponse.toDomainModel(): AccountAuth {
    return AccountAuth(
        code = code,
        accountNo = accountNo
    )
}

fun AccountCheckResponse.toDomainModel(): AccountCheck {
    return AccountCheck(
        accountNo = accountNo
    )
}
