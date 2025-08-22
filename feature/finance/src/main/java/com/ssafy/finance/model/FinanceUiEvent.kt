package com.ssafy.finance.model

sealed class FinanceUiEvent {
    data object Init : FinanceUiEvent()
    data object ClickExchange : FinanceUiEvent()
}
