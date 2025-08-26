package com.ssafy.home.model

sealed class HomeUiEvent {
    data object Init : HomeUiEvent()
    data object ClickCard : HomeUiEvent()
    data class CLickSendMoney(val type: String) : HomeUiEvent()
    data class ClickTransaction(val type: String) : HomeUiEvent()
    data class ClickMentoClub(val type: String) : HomeUiEvent()
    data object ClickTips : HomeUiEvent()
    data class ClickExchange(val type: String) : HomeUiEvent()
    data object RegisterToken : HomeUiEvent()
    data object DeleteToken : HomeUiEvent()

}
