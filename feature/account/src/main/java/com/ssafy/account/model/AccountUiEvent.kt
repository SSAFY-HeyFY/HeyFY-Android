package com.ssafy.account.model

sealed class AccountUiEvent {
    data object ClickAccountNumber : AccountUiEvent()
    data object ClickAuthCode : AccountUiEvent()
    data object ClickVerify : AccountUiEvent()
    data class UpdateAccountNumber(val accountNo: String) : AccountUiEvent()
    data class UpdateVerificationCode(val verificationCode: List<String>) : AccountUiEvent()
    data class UpdateShowCode(val showCode: Boolean) : AccountUiEvent()
}
