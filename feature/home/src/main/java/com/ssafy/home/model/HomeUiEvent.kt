package com.ssafy.home.model

sealed class HomeUiEvent {
    data object Init : HomeUiEvent()
    data object ClickCard : HomeUiEvent()
    data class CLickSendMoney(val type: String) : HomeUiEvent()
    data object ClickTransaction : HomeUiEvent()
    data class ClickMentoClub(val type: String) : HomeUiEvent()
    data object ClickTips : HomeUiEvent()
    data object ClickExchange : HomeUiEvent()
}
