package com.ssafy.home.domain.model

data class Home(
    val studentId: String,
    val normalAccount: NAccount,
    val foreignAccount: FAccount,
) {
    data class FAccount(
        val accountNo: String = "",
        val balance: Double = 0.0,
        val currency: String = "",
    )

    data class NAccount(
        val accountNo: String = "",
        val balance: Long = 0L,
        val currency: String = "",
    )
}
