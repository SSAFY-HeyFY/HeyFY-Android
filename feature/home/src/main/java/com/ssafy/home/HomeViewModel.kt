package com.ssafy.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: HeyFYAppNavigator
): ViewModel() {

    fun goToCardDetail() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.CardDetail(),
            )
        }
    }

    fun goToSendMoney() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.SendMoney(),
            )
        }
    }

    fun goToTransaction() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Transaction(),
            )
        }
    }

    fun goToMentoClub() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.MentoClub(),
            )
        }
    }

    fun goToTips() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Tips(),
            )
        }
    }

    fun goToExchange() {
        viewModelScope.launch {
            navigator.navigateTo(
                route = Destination.Exchange(),
            )
        }
    }
}
