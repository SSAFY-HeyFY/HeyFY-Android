package com.ssafy.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {
    fun goToExchange() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Exchange(),
            )
        }
    }
}
