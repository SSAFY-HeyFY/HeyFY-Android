package com.ssafy.home.domain.model

data class Home(
    val studentId: String,
    val normalAccount: Account,
    val foreignAccount: Account,
) {
    data class Account(
        val accountNo: String,
        val balance: Long,
        val currency: String,
    )
}
