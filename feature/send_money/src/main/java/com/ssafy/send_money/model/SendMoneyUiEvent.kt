package com.ssafy.send_money.model

sealed class SendMoneyUiEvent {
    data object Init : SendMoneyUiEvent()

    data class ClickTransfer(
        val depositAccountNo: String,
        val amount: Int,
    ) : SendMoneyUiEvent()

    data object ClickBack : SendMoneyUiEvent()
}
