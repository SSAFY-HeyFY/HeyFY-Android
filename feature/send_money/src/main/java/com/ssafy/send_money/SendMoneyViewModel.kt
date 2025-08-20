package com.ssafy.send_money

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import com.ssafy.transfer.domain.TransferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val transferUseCase: TransferUseCase,
    private val heyFYAppNavigator: HeyFYAppNavigator,
): ViewModel()  {

    fun goToMain() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Main(),
                isBackStackCleared = true,
            )
        }
    }

    fun goToSuccess() {
        viewModelScope.launch {
            delay(500)
            heyFYAppNavigator.navigateTo(
                route = Destination.Success(),
            )
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateBack()
        }
    }
}
