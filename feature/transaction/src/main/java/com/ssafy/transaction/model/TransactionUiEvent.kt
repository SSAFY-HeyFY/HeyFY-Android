package com.ssafy.transaction.model

sealed class TransactionUiEvent {
    data object Init : TransactionUiEvent()
    data object Back : TransactionUiEvent()
    data object CLickExchangeHistory : TransactionUiEvent()
}
