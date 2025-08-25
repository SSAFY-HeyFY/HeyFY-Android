package com.ssafy.account.api.response

data class ForeignTransactionHistoryResponse(
    val totalCount: String,
    val list: List<ForeignTransactionHistoryItem>
)

data class ForeignTransactionHistoryItem(
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
