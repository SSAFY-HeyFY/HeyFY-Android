package com.ssafy.account.domain.model

data class TransactionHistory(
    val totalCount: Int,
    val list: List<TransactionHistoryItem>
)

data class TransactionHistoryItem(
    val transactionUniqueNo: String,
    val transactionDate: String,
    val transactionTime: String,
    val transactionType: String,
    val transactionTypeName: String,
    val transactionAccountNo: String,
    val transactionBalance: String,
    val transactionAfterBalance: String,
    val transactionSummary: String,
    val transactionMemo: String
)
