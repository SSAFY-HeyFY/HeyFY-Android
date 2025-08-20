package com.ssafy.send_money.model

sealed class SendMoneyUiEvent {
    data class ClickTransfer(
        val withdrawalAccountNo: String,
        val depositAccountNo: String,
        val amount: Int,
    ) : SendMoneyUiEvent()

    data object ClickBack : SendMoneyUiEvent()
}
