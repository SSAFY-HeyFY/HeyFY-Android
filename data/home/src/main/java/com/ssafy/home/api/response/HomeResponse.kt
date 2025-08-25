package com.ssafy.home.api.response

data class HomeResponse(
    val studentId: String,
    val normalAccount: Account,
    val foreignAccount: Account,
) {
    data class Account(
        val accountNo: String,
        val balance: String,
        val currency: String,
    )
}

