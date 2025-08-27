package com.ssafy.send_money.model

sealed class SendMoneyUiEvent {
    data object Init : SendMoneyUiEvent()
    data object ClickTransfer : SendMoneyUiEvent()
    data object ClickBack : SendMoneyUiEvent()
    data class UpdateTransferNote(val note: String) : SendMoneyUiEvent()
    data class UpdateTransferAmount(val amount: String) : SendMoneyUiEvent()
    data class UpdateDepositAccountNo(val accountNo: String) : SendMoneyUiEvent()
    data class UpdatePinNumber(val pinNumber: String) : SendMoneyUiEvent()
    data class UpdateCheckPin(val checkPin: Boolean) : SendMoneyUiEvent()
    data class UpdateShowPasswordBottomSheet(val isShow: Boolean) : SendMoneyUiEvent()
}
