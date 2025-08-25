package com.ssafy.account.domain.model

data class TransactionHistory(
    val totalCount: Int,
    val list: List<Item>
) {
    data class Item(
        val id: String,
        val title: String,
        val date: String,
        val amount: String,
        val isIncome: Boolean,
    )
}
